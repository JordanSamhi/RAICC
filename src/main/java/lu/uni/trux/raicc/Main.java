package lu.uni.trux.raicc;

import edu.psu.cse.siis.ic3.Ic3CommandLineArguments;
import edu.psu.cse.siis.ic3.Ic3CommandLineParser;
import lu.uni.preprocessIntentSender.ISPreprocessor;

/*-
 * #%L
 * RAICC
 * 
 * %%
 * Copyright (C) 2020 Jordan Samhi
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
		PreliminaryAnalysis analysis = new PreliminaryAnalysis();
		Ic3CommandLineParser parser = new Ic3CommandLineParser();
		Ic3CommandLineArguments commandLineArguments =
				parser.parseCommandLine(args, Ic3CommandLineArguments.class);
		if (commandLineArguments == null) {
			return;
		}
		commandLineArguments.processCommandLineArguments();
		ISPreprocessor isp = new ISPreprocessor(commandLineArguments.getApk(), commandLineArguments.getClasspath());
		isp.run();
		commandLineArguments.setApk(isp.getResultingApkPath());
		analysis.performAnalysis(commandLineArguments);
	}
}
