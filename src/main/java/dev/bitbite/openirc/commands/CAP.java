package dev.bitbite.openirc.commands;

import dev.bitbite.openirc.Command;
import dev.bitbite.openirc.IRCServer;
import dev.bitbite.openirc.Message;

public class CAP implements Command {

    @Override
    public String handle(IRCServer server, Message message) {
        throw new UnsupportedOperationException("Unimplemented method 'handle'");
    }
    
}
