package org.dataone.daks.pbase;


public class RunReachabilityBenchmark {

	//java RunReachabilityBenchmark <graphDBFile> <true|false>(use tree cover index)
	//java RunReachabilityBenchmark <graphDBFile> <benchmarkFile> <true|false>(use tree cover index)
	public static void main(String[] args) {
		int benchmarkReps = 4;
		int queryReps = 3;
		ReachabilityBenchmark benchmark = null;
		if(args.length == 2) {
			//reachabilityBenchmark(int benchmarkReps, int queryReps, int testCases)
			benchmark = new ReachabilityBenchmark(args[0], Boolean.valueOf(args[1]));
			benchmark.runFromScratch(benchmarkReps, queryReps, 3);
		}
		else if(args.length == 3) {
			//runFromfile(String benchmarkFile, int benchmarkReps, int queryReps)
			benchmark = new ReachabilityBenchmark(args[0], Boolean.valueOf(args[2]));
			benchmark.runFromfile(args[1], benchmarkReps, queryReps);
		}
		benchmark.shutdownDB();
	}

	
}
