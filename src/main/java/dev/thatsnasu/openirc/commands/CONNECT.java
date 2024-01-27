package dev.thatsnasu.openirc.commands;

public class CONNECT extends Command {

	public CONNECT() {
		super("-1", "CONNNECT");
	}

	@Override
	public boolean handle() {
		return true;
	}

}
