/*
 * The MIT License (MIT)
 * Copyright (c) 2019 Nicholas Wright
 * http://opensource.org/licenses/MIT
 */
package com.github.seeker.commonhash;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Warmup;

@Fork(value=3)
@Warmup(iterations=2, time=5, timeUnit=TimeUnit.SECONDS)
@Measurement(iterations=3, time=10, timeUnit=TimeUnit.SECONDS)
@BenchmarkMode(Mode.Throughput)
public class DCTKernelBenchmark {
	@Benchmark
	public double[] GPU(TestData state) {
		return state.kernelGpu.transformDCT(state.testDataMatrix);
	}
	
	@Benchmark
	public double[] CPU(TestData state) {
		return state.kernelCpu.transformDCT(state.testDataMatrix);
	}
	
	@Benchmark
	public double[] JTP(TestData state) {
		return state.kernelJTP.transformDCT(state.testDataMatrix);
	}
}
