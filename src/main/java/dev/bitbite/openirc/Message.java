package dev.bitbite.openirc;

import dev.bitbite.openirc.exceptions.MalformedMessageException;
import dev.bitbite.openirc.exceptions.MessageLengthExceededException;
import dev.bitbite.openirc.exceptions.MessagePrefixException;

public class Message {
	public String prefix;
	public String command;
	public String parameters;
	
	public Message(String prefix, String command, String parameters) throws MessagePrefixException, MessageLengthExceededException, MalformedMessageException {
		if(command == null)
			throw new MalformedMessageException("Command cannot be null");

		if(prefix != null) {
			if(prefix.equals(""))
				throw new MessagePrefixException("Prefix needs to contain at least 1 character");

			if(prefix.contains(" "))
				throw new MessagePrefixException("prefix must not contain any whitespaces");

			while(prefix.startsWith(":"))
				prefix = prefix.substring(1);
		}
			
		if(parameters != null && parameters.equals(""))
			throw new MalformedMessageException("Parameter needs to contain at least 1 charactor");
		
		if((
			(prefix == null ? 0 : prefix.length() + 2 /* including ":" before and SPACE after prefix */) +
			command.length() +
			(parameters == null ? 0 : parameters.length() + 1 /* including SPACE after command */)) 
				> 512)
			throw new MessageLengthExceededException("Exceeded maximum length for messages, only 510 characters are allowed.");
		
		this.prefix = prefix;
		this.command = command;
		this.parameters = parameters;
	}
	
	@Override
	public String toString() {
		String message = "";
		if(this.prefix != null)
			message += ":"+this.prefix+" ";
		message += this.command;
		if(this.parameters != null)
			message += " "+this.parameters;
		message += "\r\n";
		
		return message;
	}
	
	
	/*
	 * BNF representation
	 * 
	 * <message>  ::= [':' <prefix> <SPACE> ] <command> <params> <crlf>
	 * <prefix>   ::= <servername> | <nick> [ '!' <user> ] [ '@' <host> ]
	 * <command>  ::= <letter> { <letter> } | <number> <number> <number>
	 * <SPACE>    ::= ' ' { ' ' }
	 * <params>   ::= <SPACE> [ ':' <trailing> | <middle> <params> ]
	 * 
	 * <middle>   ::= <Any *non-empty* sequence of octets not including SPACE
	 * 				or NUL or CR or LF, the first of which may not be ':'>
	 * <trailing> ::= <Any, possibly *empty*, sequence of octets not including
	 * 					NUL or CR or LF>
	 * 
	 * <crlf>     ::= CR LF
	 * 
	 * 
	 * Use of the extended prefix (['!' <user> ] ['@' <host> ]) must
	 * not be used in server to server communications and is only
	 * intended for server to client messages in order to provide
	 * clients with more useful information about who a message is
	 * from without the need for additional queries.
	 * 
	 * 
	 * Most protocol messages specify additional semantics and syntax for
	 * the extracted parameter strings dictated by their position in the
	 * list.  For example, many server commands will assume that the first
	 * parameter after the command is the list of targets, which can be
	 * described with:
 	 * 
	 * <target>     ::= <to> [ "," <target> ]
	 * <to>         ::= <channel> | <user> '@' <servername> | <nick> | <mask>
	 * <channel>    ::= ('#' | '&') <chstring>
	 * <servername> ::= <host>
	 * <host>       ::= see RFC 952 [DNS:4] for details on allowed hostnames
	 * <nick>       ::= <letter> { <letter> | <number> | <special> }
	 * <mask>       ::= ('#' | '$') <chstring>
	 * <chstring>   ::= <any 8bit code except SPACE, BELL, NUL, CR, LF and
	 * 				comma (',')>
	 * 
	 * Other parameter syntaxes are:
	 * 
	 * <user>       ::= <nonwhite> { <nonwhite> }
	 * <letter>     ::= 'a' ... 'z' | 'A' ... 'Z'
	 * <number>     ::= '0' ... '9'
	 * <special>    ::= '-' | '[' | ']' | '\' | '`' | '^' | '{' | '}'
	 * <nonwhite>   ::= <any 8bit code except SPACE (0x20), NUL (0x0), CR
	 * (0xd), and LF (0xa)>
	 */
}
