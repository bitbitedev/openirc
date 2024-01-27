package dev.thatsnasu.openirc.commands;

public class KICK extends Command {

	public KICK() {
		super("-1", "KICK");
	}

	@Override
	public boolean handle() {
		return true;
	}

}
