package dev.thatsnasu.openirc.commands;

import dev.thatsnasu.openirc.Command;
import dev.thatsnasu.openirc.IRCServer;
import dev.thatsnasu.openirc.Message;
import dev.thatsnasu.openirc.User;

public class NICK extends Command {

	public NICK(IRCServer server) {
		super(server, "", "NICK");
	}

	@Override
	public void handle(Message message) {
		String nickname = message.parameters.split(" ")[0];
		
		if(this.server.getUserManager().getUserByNick(nickname) != null) {}// already in use exception
		
		User user = new User(nickname);
		this.server.getUserManager().arriveUser(nickname, user);
	}
}
