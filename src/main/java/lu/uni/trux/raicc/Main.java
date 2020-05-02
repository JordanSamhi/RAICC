package lu.uni.trux.raicc;

import edu.psu.cse.siis.ic3.Ic3CommandLineArguments;
import edu.psu.cse.siis.ic3.Ic3CommandLineParser;
import lu.uni.preprocessIntentSender.ISPreprocessor;

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
