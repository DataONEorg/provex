package org.dataone.daks.pbase;

public class RunReachabilityBenchmarkMultiple {

	public static void main(String[] args) {
		int benchmarkReps = 4;
		int queryReps = 3;
		int nInstances = 5;
		ReachabilityBenchmarkMultiple benchmark = new ReachabilityBenchmarkMultiple(args[0]);
		if(args.length == 2)
			//runFromfile(String benchmarkFile, int benchmarkReps, int queryReps)
			benchmark.runFromFile(args[1], benchmarkReps, queryReps, nInstances);
		benchmark.shutdownDB();
	}

}
