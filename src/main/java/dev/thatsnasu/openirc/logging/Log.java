package dev.thatsnasu.openirc.logging;

import java.util.LinkedList;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.Function;

public class Log {
	private LinkedList<Consumer<String>> consumers;
	private TreeMap<Integer, Function<String, String>> processors;
	
	
	public String someKindOfProcessing(String messageToProcess) {
		
		return messageToProcess;
	}
	
	public String log(String message) {
		this.consumers.stream().forEach((consumer) -> consumer.accept(message));
		
		return message;
	}
	
	public void onLog() {
		
	}
	
	public Consumer<String> addConsumer(Consumer<String> consumerParam) {
		this.consumers.add(consumerParam);
		return consumerParam;
	}
	
	public Function<String, String> addProcessor(int index, Function<String, String> processor) throws Exception {
		if(this.processors.containsKey(index)) throw new Exception("Processor already set");
		this.processors.put(index, processor);
		return processor;
	}
}
