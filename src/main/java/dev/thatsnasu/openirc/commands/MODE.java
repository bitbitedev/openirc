package dev.thatsnasu.openirc.commands;

public class MODE extends Command {

	public MODE() {
		super("-1", "MODE");
	}

	@Override
	public boolean handle() {
		return true;
	}

}
