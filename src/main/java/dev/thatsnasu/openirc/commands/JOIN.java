package dev.thatsnasu.openirc.commands;

public class JOIN extends Command {

	public JOIN() {
		super("-1", "JOIN");
	}

	@Override
	public boolean handle() {
		return true;
	}

}
