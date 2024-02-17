package dev.bitbite.openirc.commands;

import dev.bitbite.openirc.Command;
import dev.bitbite.openirc.IRCServer;
import dev.bitbite.openirc.Message;
import dev.bitbite.openirc.User;

public class USER extends Command {

	public USER(IRCServer server) {
		super(server, "", "USER");
	}

	@Override
	public void handle(Message message) {
		System.out.println("Starting processing of USER");
		
		String[] parameters = message.parameters.split(" ");
		String username = parameters[0];
		String hostname = parameters[1];
		String servername = parameters[2];
		String realname = "";
		for(int i = 3; i < parameters.length; i++) realname += parameters[i]+" ";
		if(realname.endsWith(" ")) realname = realname.substring(0, realname.length()-1);
		
		User user = (this.server.getUserManager().getUserByNick(username) != null) ? this.server.getUserManager().getUserByNick(username) : new User(username);
		user.setUserData(username, hostname, servername, realname);
		this.server.getUserManager().arriveUser(user.nickname, user);
	}
}
