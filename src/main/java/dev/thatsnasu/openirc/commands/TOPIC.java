package dev.thatsnasu.openirc.commands;

public class TOPIC extends Command {

	public TOPIC() {
		super("-1", "TOPIC");
	}

	@Override
	public boolean handle() {
		return true;
	}

}
