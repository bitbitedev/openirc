package dev.bitbite.openirc.commands;

import dev.bitbite.openirc.Command;
import dev.bitbite.openirc.IRCServer;
import dev.bitbite.openirc.Message;
import dev.bitbite.openirc.User;

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
