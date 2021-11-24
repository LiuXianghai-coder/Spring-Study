package org.xhliu.springwebflux.reactor;

import java.util.function.Consumer;

import reactor.core.publisher.FluxSink;

public class SinkHolder implements Consumer<FluxSink> {
	private FluxSink sink;

	@Override
	public void accept(FluxSink t) {
		this.sink = t;
	}

	public void emit(Object value) {
		sink.next(value);
	}

}
