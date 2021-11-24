package org.xhliu.springwebflux.reactor;

import reactor.core.publisher.Flux;

public class Util {
	public static void print(Flux<?> flux) {
		flux.subscribe(System.out::println);
	}

	public static String service1() throws InterruptedException {
		Thread.sleep(1000);
		return "ret1";
	}
	public static String service2(String inputFrom1) throws InterruptedException {
		Thread.sleep(1500);
		return "ret2:" + inputFrom1;
	}
	public static String service3() throws InterruptedException {
		Thread.sleep(1200);
		return "ret3";
	}
	public static String service4(String inputFrom2, String inputFrom3) throws InterruptedException {
		Thread.sleep(500);
		return "ret4:" + inputFrom2 + ":" + inputFrom3;
	}

}
