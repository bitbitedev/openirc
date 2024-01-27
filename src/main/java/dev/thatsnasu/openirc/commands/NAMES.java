package dev.thatsnasu.openirc.commands;

public class NAMES extends Command {

	public NAMES() {
		super("-1", "NAMES");
	}

	@Override
	public boolean handle() {
		return true;
	}

}
