package dev.thatsnasu.openirc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class Client implements Runnable {
	// Object Internals
	private ConnectionConfiguration connectionConfiguration;

	// Inheritance
	protected Socket socket;
	protected BufferedReader reader;
	protected PrintWriter writer;

	// Accessibles
	private boolean connected;
	private Buffer buffer;


	public Client() {
		this.buffer = null;
	}

	public void connect(ConnectionConfiguration connectionConfiguration) throws IOException, IRCException {
		this.connectionConfiguration = connectionConfiguration;
		this.socket = new Socket(this.connectionConfiguration.getHost(), this.connectionConfiguration.getPort());
		this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		this.sendRawLine("PASS "+this.connectionConfiguration.getAuthentication());
		this.sendRawLine("NICK "+this.connectionConfiguration.getNickname());
		this.sendRawLine("USER "+this.connectionConfiguration.getUsername());
	}

	public final void sendRawLine(String data) {
		if(this.buffer != null) {
			this.buffer.addMessageToBuffer(data, this.writer);
		} else {
			this.writer.println(data);
			this.writer.flush();
		}
	}

	public final void quitServer() {
		this.onQuitRequest();
		try {
			this.reader.close();
			this.writer.close();
			this.socket.close();
			this.onQuit();
		} catch(IOException e) {
			this.onQuitFailed();
			e.printStackTrace();
		}
	}

	protected void sendMessage(String target, String message) {
		this.sendRawLine("PRIVMSG "+target+" :"+message);
	}

	protected void joinChannel(String channel, String password) {
		String line = (password != null) ? "JOIN "+channel+" "+password : "JOIN "+channel;
		this.sendRawLine(line);
		this.onJoinAttempt();
	}

	@Override
	public void run() {
		String line;
		try {
			while((line = this.reader.readLine()) != null) {
				this.handleLine(line);
			}
		} catch (IOException e) {
			this.connected = false;
			this.onDisconnect();
		}
	}

	protected void requestNameList(String... channels) {
		String listBuilder = "NAMES ";
		for(String listItem : channels) listBuilder += listItem+",";
		this.sendRawLine(listBuilder);
	}

	private final boolean handleLine(String line) {
		//System.out.println(line);
		String[] splits = line.split(" ");

		if(splits[0].equals("PING")) {
			this.onServerPing(splits[1]);
			return true;
		}

		String sourceNick = null;
		String sourceLogin = null;
		String sourceHost = null;
		String senderMetaData = splits[0].substring(1);
		String response = splits[1];
		String target = (splits[1].equals("353")) ? splits[4] : splits[2];
		String content = null;
		if(line.chars().filter(ch -> ch == ':').count() >= 2) {
			content = line.substring(line.indexOf(":", line.indexOf(":")+1)+1);
		}
		
		int exclamation = senderMetaData.indexOf("!");
		int at = senderMetaData.indexOf("@");
		if(exclamation > 0 && at > 0 && exclamation < at) {
			sourceNick = senderMetaData.substring(0, exclamation);
			sourceLogin = senderMetaData.substring(exclamation+1, at);
			sourceHost = senderMetaData.substring(at+1);
		}

		switch(response) {
			case "001":
				this.onWelcome(sourceNick, target, content);
				return true;
			case "002":
				this.onYourHost(sourceNick, target, content);
				return true;
			case "003":
				this.onCreation(sourceNick, target, content);
				return true;
			case "004":
				this.connected = true; // got server info, so we must be connected;
				this.onServerInfo(sourceNick, target, content);
				this.onConnect();
				return true;
			case "005":
				this.onBounce(sourceNick, target, content);
				return true;
			case "353":
				String[] list = content.split(" ");
				for(String nickname : list) this.onJoin(nickname, nickname, sourceHost, target);
				return true;
			case "366":
				this.onEndOfNames();
				return true;
			case "372":
				this.onMessageOfTheDay(sourceNick, target, content);
				return true;
			case "375":
				this.onMessageOfTheDayStart(sourceNick, target, content);
				return true;
			case "376":
				this.onMessageOfTheDayEnd(sourceNick, target, content);
				return true;
			case "JOIN":
				this.onJoin(sourceNick, sourceLogin, sourceHost, target);
				return true;
			case "PART":
				this.onPart(sourceNick, sourceLogin, sourceHost, target);
				return true;
			case "PRIVMSG":
				this.onMessage(sourceNick, sourceLogin, sourceHost, target, content);
				return true;
			case "NOTICE":
				// something like notice handling for stuff like "login unsuccessful" and trying to reconnect and such lame bullshit.
				return true;
			default:
				// something like log unknown stuff, maybe firing an onUnknown() event.
				// CAP * ACK on CAP REQ (i guess something like capabilities acknowledged)
				this.onUnknownResponse(sourceNick, sourceLogin, sourceHost, target, content);
				return false;
		}
	}

	public final boolean isConnected() {
		return this.connected;
	}

	public final ConnectionConfiguration getConnectionConfiguration() {
		return this.connectionConfiguration;
	}

	public final void setBuffer(Buffer buffer) {
		if(buffer != null) {
			buffer.addClient(this);
		} else if(this.buffer != null) {
			this.buffer.removeClient(this);
		}
		this.buffer = buffer;
	}

	protected void onServerPing(String token) {
		this.sendRawLine("PONG "+token);
	}

	protected void onDisconnect() {}
	protected void onQuitRequest() {}
	protected void onQuitFailed() {}
	protected void onQuit() {}
	protected void onConnect() {}
	protected void onWelcome(String sourceNick, String target, String content) {}
	protected void onYourHost(String sourceNick, String target, String content) {}
	protected void onCreation(String sourceNick, String target, String content) {}
	protected void onServerInfo(String sourceNick, String target, String content) {}
	protected void onBounce(String sourceNick, String target, String content) {}
	protected void onMessageOfTheDayStart(String sourceNick, String target, String content) {}
	protected void onMessageOfTheDay(String sourceNick, String target, String content) {}
	protected void onMessageOfTheDayEnd(String sourceNick, String target, String content) {}
	protected void onJoinAttempt() {}
	protected void onJoin(String sourceNick, String sourceLogin, String sourceHost, String target) {}
	protected void onJoinSuccess() {}
	protected void onJoinFailed() {}
	protected void onUserPing() {}
	protected void onPart(String sourceNick, String sourceLogin, String sourceHost, String target) {}
	protected void onEndOfNames() {}
	protected void onMessage(String sourceNick, String sourceLogin, String sourceHost, String target, String content) {}
	protected void onUnknownResponse(String sourceNick, String sourceLogin, String sourceHost, String target, String content) {}
}
