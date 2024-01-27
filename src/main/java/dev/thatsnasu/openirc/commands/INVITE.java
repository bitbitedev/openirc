package dev.thatsnasu.openirc.commands;

public class INVITE extends Command {

	public INVITE() {
		super("-1", "INVITE");
	}

	@Override
	public boolean handle() {
		return true;
	}

}
