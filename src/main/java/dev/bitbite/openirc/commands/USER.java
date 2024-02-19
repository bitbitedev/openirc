package dev.bitbite.openirc.commands;

import dev.bitbite.openirc.Command;
import dev.bitbite.openirc.IRCServer;
import dev.bitbite.openirc.Message;
import dev.bitbite.openirc.User;

public class USER implements Command {

	@Override
	public String handle(IRCServer server, Message message) {
		System.out.println("Starting processing of USER");
		
		String[] parameters = message.parameters.split(" ");
		String username = parameters[0];
		String hostname = parameters[1];
		String servername = parameters[2];
		String realname = "";
		for(int i = 3; i < parameters.length; i++) realname += parameters[i]+" ";
		if(realname.endsWith(" ")) realname = realname.substring(0, realname.length()-1);
		
		User user = (server.getUserManager().getUserByNick(username) != null) ? server.getUserManager().getUserByNick(username) : new User(username);
		user.setUserData(username, hostname, servername, realname);
		server.getUserManager().arriveUser(user.nickname, user);

		return "";
	}
}
