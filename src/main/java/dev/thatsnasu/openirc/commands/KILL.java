package dev.thatsnasu.openirc.commands;

public class KILL extends Command {

	public KILL() {
		super("-1", "KILL");
	}

	@Override
	public boolean handle() {
		return true;
	}

}
