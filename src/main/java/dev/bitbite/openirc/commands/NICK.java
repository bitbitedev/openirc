package dev.bitbite.openirc.commands;

import dev.bitbite.openirc.Command;
import dev.bitbite.openirc.IRCServer;
import dev.bitbite.openirc.Message;
import dev.bitbite.openirc.User;

public class NICK implements Command {

	@Override
	public String handle(IRCServer server, Message message) {
		String nickname = message.parameters.split(" ")[0];
		
		if(server.getUserManager().getUserByNick(nickname) != null) {}// already in use exception
		
		User user = new User(nickname);
		server.getUserManager().arriveUser(nickname, user);

		return "";
	}
}
