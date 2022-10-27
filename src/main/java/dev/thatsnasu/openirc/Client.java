package dev.thatsnasu.openirc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class Client implements Runnable {
	private ConnectionConfiguration connectionConfiguration;

	protected Socket socket;
	protected BufferedReader reader;
	protected PrintWriter writer;

	private boolean connected;


	public Client() {}

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
		System.out.println("Sending: \""+data+"\" to server");
		this.writer.println(data);
		this.writer.flush();
	}

	public final void quitServer() {
		try {
			this.reader.close();
			this.writer.close();
			this.socket.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		this.onQuitRequest();
	}

	protected void sendMessage(String target, String message) {
		this.sendRawLine("PRIVMSG "+target+" "+message);
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
		System.out.println(line);
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
		String target = splits[2];
		String content = null;
		if(line.chars().filter(ch -> ch == ':').count() >= 2) {
			content = line.substring(line.indexOf(":", line.indexOf(":")+1));
		}
		
		int exclamation = senderMetaData.indexOf("!");
		int at = senderMetaData.indexOf("@");
		if(exclamation > 0 && at > 0 && exclamation < at) {
			sourceNick = senderMetaData.substring(1, exclamation);
			sourceLogin = senderMetaData.substring(exclamation+1, at);
			sourceHost = senderMetaData.substring(at+1);
		}

		switch(response) {
			case "004":
				this.connected = true; // got server info, so we must be connected;
				this.onServerInfo(sourceNick, target, content);
				return true;
			case "353":
				String[] list = content.split(" ");
				for(String nickname : list) this.onJoin(nickname, nickname, sourceHost, target);
				return true;
			case "366":
				this.onEndOfNames();
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
			default:
				// something like log unknown stuff, maybe firing an onUnknown() event.
				// CAP * ACK on CAP REQ (i guess something like capabilities acknowledged)
				return false;
		}
	}

	public final boolean isConnected() {
		return this.connected;
	}

	protected void onServerPing(String token) {
		this.sendRawLine("PONG "+token);
	}

	protected void onDisconnect() {}
	protected void onQuitRequest() {}
	protected void onWelcome() {}
	protected void onYourHost() {}
	protected void onCreation() {}
	protected void onServerInfo(String sourceNick, String target, String content) {}
	protected void onMessageOfTheDayStart() {}
	protected void onMessageOfTheDay() {}
	protected void onMessageOfTheDayEnd() {}
	protected void onJoinAttempt() {}
	protected void onJoin(String sourceNick, String sourceLogin, String sourceHost, String target) {}
	protected void onJoinSuccess() {}
	protected void onJoinFailed() {}
	protected void onUserPing() {}
	protected void onPart(String sourceNick, String sourceLogin, String sourceHost, String target) {}
	protected void onEndOfNames() {}
	protected void onMessage(String sourceNick, String sourceLogin, String sourceHost, String target, String content) {}
}
