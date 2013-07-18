package org.dataone.daks.pbase;

public class CreateReachabilityBenchmarkFile {

	public static void main(String[] args) {
		//CreateReachabilityBenchmarkFile <graphDBFile> <outBenchmarkFile> <N test cases>
		ReachabilityBenchmark benchmark = new ReachabilityBenchmark(args[0]);
		benchmark.createBenchmarkFile(args[1], Integer.parseInt(args[2]));
		benchmark.shutdownDB();
	}

}
