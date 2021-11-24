package org.xhliu.springwebflux.reactor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink.OverflowStrategy;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;
import reactor.core.scheduler.Schedulers;

public class BasicDemo {

	public static void basicCreateDemo() {
		Flux<String> flux = Flux.just("Hello", "World", "Reactor");
		flux.subscribe(System.out::println);

		flux = Flux.fromIterable(Arrays.asList("Hello", "World", "Reactor"));
		flux.subscribe(System.out::println);


		Mono<String> mono = Mono.fromDirect(flux);
		mono.subscribe(System.out::println);

		Random random = new Random();
		mono = Mono.fromCallable(random::nextInt).map(String::valueOf);
		mono.subscribe(System.out::println);

//		flux = Flux.interval(Duration.ofMillis(1000)).map(String::valueOf).take(5);
//		flux.subscribe(System.out::println);

		flux = Flux.range(10, 20).map(String::valueOf);
		flux.subscribe(System.out::println);

	}

	public static void createByGenerate() {
		Scanner sc = new Scanner(System.in);

//		 Flux.<String>generate(sink->{
//						sink.next(sc.next());
//				 	 })
//					.doFinally(any->sc.close())
//					.map(String::toUpperCase)
//					.subscribe(System.out::println)
//					;

		  Flux.generate(()->0, (Integer state, SynchronousSink<String> sink)->{
			 					if (state == 1) {
			 						sink.next("ture");
			 						 return 0;
			 					}else {
			 						sink.next("false");
			 						 return 1;
			 					}

		 					})
				 			.take(10)
				 			.subscribe(System.out::println)
		 					;
	}


	public static void createByCreateDemo() throws InterruptedException {
		 SinkHolder holder = new SinkHolder();

		 Flux.<String>create(holder)// sink->{sink.next}
					.subscribe(System.out::println)
					;

		 CountDownLatch latch = new CountDownLatch(1);
		 new Thread(()->{
			 holder.emit("hello");
			 holder.emit("world");
			 holder.emit("reactor");
			 try {
				Thread.sleep(800);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			holder.emit("end");
			latch.countDown();
		 }).start();


		 latch.await();
	}


	public static void basicAPI1() throws InterruptedException {
		Flux.fromIterable(Arrays.asList("2", "5", "7"))
			.map(Integer::valueOf)
			.filter(i-> i%2 !=0)
			.subscribe(System.out::println);

		Flux.fromIterable(Arrays.asList("k1", "v1", "k2", "v2", "k3", "v3"))
		.buffer(2)
		.filter(l->l.size()==2)
		.collectMap(l->l.get(0), l->l.get(1))
		.subscribe(System.out::println);


		Flux.merge(Flux.interval(Duration.ofMillis(100)), Flux.interval(Duration.ofMillis(200)))
				.take(10)
				.subscribe(System.out::println)
		;
		Thread.sleep(5000);
	}



	public static void parallelCallDemo() throws InterruptedException {
		Mono<String> mono2 = Mono.fromCallable(Util::service1)
							.flatMap(ret1 -> Mono.fromCallable(()->Util.service2(ret1)).subscribeOn(Schedulers.newSingle("service2")));
		Mono<String> mono3 = Mono.fromCallable(Util::service3).subscribeOn(Schedulers.newSingle("service3"));

		Mono ret4 = Flux.zip(mono2, mono3).single()
						.flatMap(tuple -> Mono.fromCallable(()->Util.service4(tuple.getT1(), tuple.getT2())));

		Thread.sleep(1000);

		System.out.println("begin=====>");
		ret4
			.doOnSubscribe(any->System.out.println("onSubscribe:" + LocalDateTime.now()))
			.doFinally(any->System.out.println("finally:" + LocalDateTime.now()))
			.subscribe(System.out::println);

	}



	public static void backPressureDemo() throws InterruptedException {
		CountDownLatch latch = new CountDownLatch(1);

		SinkHolder holder = new SinkHolder();
		Flux.create(holder,
				//OverflowStrategy.ERROR
				//OverflowStrategy.DROP
				OverflowStrategy.LATEST
				//OverflowStrategy.BUFFER
				)
			//.onBackpressureBuffer(5)
			.log()
			//.limitRate(4)
			.doFinally(any->latch.countDown())
			.publishOn(Schedulers.newSingle("consumer"), 1)
			.subscribe(new SlowSubscriber(1000))
		;

		AtomicInteger counter = new AtomicInteger();
		new Thread(()->{//emit event per 200 millis
			while(true) {
				holder.emit(counter.getAndIncrement());
				System.out.println(counter.get());
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();


		latch.await();
	}


	public static void schedulerDemo() {
//		Flux flux = Flux.generate(sink->{
//			System.out.println("generate : " + Thread.currentThread().getName());
//			sink.next("");
//		})
//		.doOnNext(any -> System.out.println("1st doOnNext:" + Thread.currentThread().getName()))
//		.take(3)
//		;
//
//		new Thread(flux::subscribe, "subscribeThread").start();


//		Flux flux = Flux.generate(sink->{
//			System.out.println("generate : " + Thread.currentThread().getName());
//			sink.next("");
//		})
//			.doOnNext(any -> System.out.println("1st doOnNext:" + Thread.currentThread().getName()))
//			.publishOn(Schedulers.newSingle("pubThread"))
//			.doOnNext(any -> System.out.println("2nd doOnNext:" + Thread.currentThread().getName()))
//			.take(3)
//			;
//		new Thread(flux::subscribe, "subscribeThread").start();

		Flux flux = Flux.generate(sink->{
			System.out.println("generate : " + Thread.currentThread().getName());
			sink.next("");
		})
		.doOnNext(any -> System.out.println("1st doOnNext:" + Thread.currentThread().getName()))
		.subscribeOn(Schedulers.newSingle("pubThread"), false)
		.doOnNext(any -> System.out.println("2nd doOnNext:" + Thread.currentThread().getName()))
		.take(3)
		;
		new Thread(flux::subscribe, "subscribeThread").start();

//		Flux.just("Hello", "World", "Reactor")
//			.publishOn(Schedulers.newSingle("pubThread"))
//			.doOnNext(any -> System.out.println("1st doOnNext:" + Thread.currentThread().getName()))
//			.subscribeOn(Schedulers.newSingle("subThread"))
//			.doOnNext(any -> System.out.println("2nd doOnNext:" + Thread.currentThread().getName()))
//			.subscribe()
//			;



	}

		public static void cold2HotDemo() throws InterruptedException {
		CountDownLatch latch = new CountDownLatch(1);

		Flux flux = Flux.interval(Duration.ofMillis(500))
							.doFinally(any->latch.countDown())
							//.publish().autoConnect() // turn cold to hot
							//.replay(3).autoConnect()//trun cold to hot
							//share()
							;
		flux  = flux
					.publish()
//					.replay(3)

//					.autoConnect()
					.refCount(1);

		flux.subscribe(v->System.out.println("subcriber1 " + v));
		Thread.sleep(4000);
		flux.subscribe(v->System.out.println("subcriber2 " + v));
		Thread.sleep(1000);
		flux.subscribe(v->System.out.println("subcriber3 " + v));


		latch.await();
	}


	public static void main(String[] args) throws InterruptedException {
		//basicCreateDemo();
		//createByGenerate();
		//createByCreateDemo();
		//basicAPI1();
		parallelCallDemo();
		//backPressureDemo();
		cold2HotDemo();
		//schedulerDemo();


	}
}
