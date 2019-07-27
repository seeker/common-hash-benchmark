/*
 * The MIT License (MIT)
 * Copyright (c) 2019 Nicholas Wright
 * http://opensource.org/licenses/MIT
 */
package com.github.seeker.commonhash;

import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Warmup;

import com.github.dozedoff.commonj.util.ImageUtil;

@Fork(value=3)
@Warmup(iterations=2, time=2, timeUnit=TimeUnit.SECONDS)
@Measurement(iterations=3, time=5, timeUnit=TimeUnit.SECONDS)
@BenchmarkMode(Mode.Throughput)
public class ResizeBenchmark {
	@Benchmark
	public BufferedImage commonjImageUtil(ResizeTestData state) {
		return ImageUtil.resizeImage(state.testImages.get(state.image), state.imageSize, state.imageSize);
	}
	
	@Benchmark
	public BufferedImage ImgScalrSpeedExact(ResizeTestData state) {
		return Scalr.resize(state.testImages.get(state.image), Method.SPEED, org.imgscalr.Scalr.Mode.FIT_EXACT, state.imageSize);
	}
}
