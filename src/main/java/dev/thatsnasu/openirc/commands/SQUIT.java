package dev.thatsnasu.openirc.commands;

public class SQUIT extends Command {

	public SQUIT() {
		super("-1", "SQUIT");
	}

	@Override
	public boolean handle() {
		
		return true;
	}

}
