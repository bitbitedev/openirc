package dev.thatsnasu.openirc.commands;

public class WHO extends Command {

	public WHO() {
		super("-1", "WHO");
	}

	@Override
	public boolean handle() {
		return true;
	}

}
