package org.dataone.daks.pbase;

public class RunReachabilityBenchmark {

	public static void main(String[] args) {
		ReachabilityBenchmark benchmark = new ReachabilityBenchmark(args[0]);
		//reachabilityBenchmark(int benchmarkReps, int queryReps, int testCases)
		benchmark.reachabilityBenchmark(5, 10, 3);
		benchmark.shutdownDB();
	}

}
