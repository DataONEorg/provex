package org.dataone.daks.pbase;

public class RunReachabilityBenchmark {

	public static void main(String[] args) {
		ReachabilityBenchmark benchmark = new ReachabilityBenchmark(args[0]);
		if(args.length == 1)
			//reachabilityBenchmark(int benchmarkReps, int queryReps, int testCases)
			benchmark.runFromScratch(5, 10, 3);
		else if(args.length == 2)
			//runFromfile(String benchmarkFile, int benchmarkReps, int queryReps)
			benchmark.runFromfile(args[1], 5, 10);
		benchmark.shutdownDB();
	}

}
