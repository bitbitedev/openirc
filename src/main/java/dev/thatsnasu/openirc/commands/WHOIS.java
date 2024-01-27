package dev.thatsnasu.openirc.commands;

public class WHOIS extends Command {

	public WHOIS() {
		super("-1", "WHOIS");
	}

	@Override
	public boolean handle() {
		return true;
	}

}
