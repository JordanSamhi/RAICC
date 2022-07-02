package lu.uni.trux.raicc;

import lu.uni.trux.raicc.config.SootConfig;
import lu.uni.trux.raicc.patch.Instrumenter;
import lu.uni.trux.raicc.propagation.IFDSProblem;
import lu.uni.trux.raicc.utils.CommandLineOptions;
import lu.uni.trux.raicc.utils.Constants;
import lu.uni.trux.raicc.utils.TimeOut;
import lu.uni.trux.raicc.utils.Writer;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.profiler.StopWatch;
import soot.G;
import soot.PackManager;
import soot.jimple.infoflow.InfoflowConfiguration;
import soot.jimple.infoflow.android.InfoflowAndroidConfiguration;
import soot.jimple.infoflow.android.SetupApplication;
import soot.jimple.infoflow.android.manifest.ProcessManifest;
import soot.jimple.toolkits.ide.icfg.JimpleBasedInterproceduralCFG;
import soot.options.Options;

import java.util.Date;

/*-
 * #%L
 * RAICC
 *
 * %%
 * Copyright (C) 2022 Jordan Samhi
 * University of Luxembourg - Interdisciplinary Centre for
 * Security Reliability and Trust (SnT) - TruX - All rights reserved
 *
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 2.1 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 *
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-2.1.html>.
 * #L%
 */

public class Main {

    public static void main(String[] args) {
        TimeOut to = null;
        try {
            StopWatch analysisTime = new StopWatch("Analysis");
            analysisTime.start("Analysis");
            CommandLineOptions.v().parseArgs(args);
            ResultsAccumulator.v().setAppName(FilenameUtils.getBaseName(CommandLineOptions.v().getApk()));
            Writer.v().pinfo(String.format("%s v1.0 started on %s\n", Constants.TOOL_NAME, new Date()));

            if (CommandLineOptions.v().hasTimeout()) {
                int timeout = CommandLineOptions.v().getTimeout();
                if (timeout != 0) {
                    to = new TimeOut(timeout);
                    to.launch();
                }
            }

            StopWatch initialization = new StopWatch("Initialization");
            initialization.start("Initialization");
            ProcessManifest pm = null;
            try {
                pm = new ProcessManifest(CommandLineOptions.v().getApk());
            } catch (Exception ignored) {
            }
            if (pm != null) {
                Writer.v().pinfo(String.format("Processing: %s", pm.getPackageName()));
            }

            Writer.v().pinfo("Initializing Environment");
            initializeSoot();
            Writer.v().psuccess("Done");
            initialization.stop();

            StopWatch propagation = new StopWatch("Propagation");
            propagation.start("Propagation");
            Writer.v().pinfo("Propagating AICC target types");
            IFDSProblem problem = new IFDSProblem(new JimpleBasedInterproceduralCFG());
            problem.solve();
            Writer.v().psuccess("Done");
            propagation.stop();

            StopWatch instrumentationTime = new StopWatch("Instrumentation");
            instrumentationTime.start("Instrumentation");
            Writer.v().pinfo("Instrumenting app with ICC calls");
            Instrumenter.v().instrument(problem);
            Writer.v().psuccess("Done");
            instrumentationTime.stop();

            StopWatch writingTime = new StopWatch("Writing");
            writingTime.start("Writing");
            if (ResultsAccumulator.v().isStatementAdded()) {
                Writer.v().pinfo(String.format("Writing new apk in: %s", Options.v().output_dir()));
                PackManager.v().writeOutput();
                Writer.v().psuccess("Done");
            } else {
                Writer.v().pinfo("No new statement added");
            }
            writingTime.stop();

            analysisTime.stop();
            ResultsAccumulator.v().setAppName(FilenameUtils.getBaseName(CommandLineOptions.v().getApk()));
            ResultsAccumulator.v().setAnalysisElapsedTime(analysisTime.elapsedTime() / (double) 1000000000);
            ResultsAccumulator.v().setInstrumentationElapsedTime(instrumentationTime.elapsedTime() / (double) 1000000000);
            ResultsAccumulator.v().setPropagationElapsedTime(propagation.elapsedTime() / (double) 1000000000);
            ResultsAccumulator.v().setInitializationElapsedTime(initialization.elapsedTime() / (double) 1000000000);
            ResultsAccumulator.v().setWritingElapsedTime(writingTime.elapsedTime() / (double) 1000000000);
            if (CommandLineOptions.v().hasRaw()) {
                ResultsAccumulator.v().printVectorResults();
            } else {
                ResultsAccumulator.v().printResults();
            }
            if (to != null) {
                to.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (to != null) {
                to.cancel();
            }
        }
    }

    /**
     * Initialize Soot options
     */
    private static SetupApplication initializeSoot() {
        G.reset();
        InfoflowAndroidConfiguration ifac = new InfoflowAndroidConfiguration();
        ifac.getAnalysisFileConfig().setAndroidPlatformDir(CommandLineOptions.v().getPlatforms());
        ifac.getAnalysisFileConfig().setTargetAPKFile(CommandLineOptions.v().getApk());
        ifac.setMergeDexFiles(true);
        SetupApplication sa = new SetupApplication(ifac);
        sa.setSootConfig(new SootConfig());
        sa.getConfig().setCallgraphAlgorithm(InfoflowConfiguration.CallgraphAlgorithm.SPARK);
        sa.constructCallgraph();
        sa.getConfig().setSootIntegrationMode(InfoflowAndroidConfiguration.SootIntegrationMode.UseExistingInstance);
        return sa;
    }
}
