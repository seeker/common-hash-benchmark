package com.github.seeker.commonhash;

import java.util.List;

import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import com.aparapi.device.Device;
import com.aparapi.device.JavaDevice;
import com.aparapi.device.OpenCLDevice;
import com.github.seeker.commonhash.perceptual.phash.kernel.DCTKernel;

@State(Scope.Thread)
public class TestData {
	public double result;
	public double[] testDataMatrix;
	public DCTKernel kernelGpu;
	public DCTKernel kernelCpu;
	public DCTKernel kernelJTP;
	Device gpu;
	Device cpu;
	
	@Setup(Level.Trial)
	public void setUp() {
		testDataMatrix = randomMatrixGen();
		
		kernelGpu = new DCTKernel();
		kernelCpu = new DCTKernel();
		kernelJTP = new DCTKernel();
		
		List<OpenCLDevice> gpuDevices = OpenCLDevice.listDevices(Device.TYPE.GPU);
		List<OpenCLDevice> cpuDevices = OpenCLDevice.listDevices(Device.TYPE.CPU);
		
		for(OpenCLDevice d : gpuDevices) {
			String deviceName = d.getOpenCLPlatform().getVendor().toLowerCase();
			
			if(deviceName.contains("nvidia") || deviceName.contains("amd")) {
				gpu = d;
				break;
			}
		}
		
		for(OpenCLDevice d : cpuDevices) {
			String deviceName = d.getOpenCLPlatform().getVendor().toLowerCase();
			
			if(deviceName.contains("intel") || deviceName.contains("amd") || deviceName.contains("pocl")) {
				cpu = d;
				break;
			}
		}
		
		System.out.println("Using GPU: " + gpu.toString());
		System.out.println("Using CPU: " + cpu.toString());
		
		kernelGpu.setDevice(gpu);
		kernelCpu.setDevice(cpu);
		kernelJTP.setDevice(JavaDevice.THREAD_POOL);
	}
	
	private static double[] randomMatrixGen() {
		int size = 64;
		double[] m = new double[size];
		
		for (int i=0; i < size; i++) {
			m[i] = Math.random();
		}
		
		return m;
	}
}
