package org.xhliu.springwebflux.reactor;

import org.reactivestreams.Subscription;

import reactor.core.publisher.BaseSubscriber;

public class SlowSubscriber<T> extends BaseSubscriber<T> {
	private int consumeMills;

	public SlowSubscriber(int consumeMills){
		this.consumeMills = consumeMills;
	}

	@Override
	protected void hookOnSubscribe(Subscription subscription) {
		subscription.request(1);
	}

	@Override
	protected void hookOnNext(T value) {
		try {
			Thread.sleep(consumeMills);
			//System.out.println(value);
			request(1);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

	}

}
