package dev.thatsnasu.openirc.logging;

@FunctionalInterface
public interface Processor {
	public String process(String message);
}
