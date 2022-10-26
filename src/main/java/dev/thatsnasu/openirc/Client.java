package dev.thatsnasu.openirc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class Client implements Runnable {
	protected Socket socket;
	protected BufferedReader reader;
	protected PrintWriter writer;

	private boolean connected;
	private boolean running;


	public Client() {
		this.running = true;
	}

	public void connect(ConnectionConfiguration connectionConfiguration) throws IOException, IRCException {
		this.socket = new Socket(connectionConfiguration.getHost(), connectionConfiguration.getPort());
		this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		this.sendRawLine("PASS "+connectionConfiguration.getAuthentication());
		this.sendRawLine("NICK "+connectionConfiguration.getNickname());
		this.sendRawLine("USER "+connectionConfiguration.getUsername());
	}

	public final void sendRawLine(String data) {
		this.writer.println(data);
		this.writer.flush();
	}

	// returns true if the event will be consumed, false if we dont know what the message means
	private final boolean handleLine(String line) {
		String[] splits = line.split(" ");
		// assume splits_0 is prefix, splits_1 is the responsecode and splits_2 the response
		if(splits[1].equals("004")) {
			this.connected = true; // got message of the day, so we must be connected;
			return true;
		}
		System.out.println(line);
		return false;
	}

	@Override
	public void run() {
		String line;
		while(this.running) {
			try {
				while((line = this.reader.readLine()) != null) {
					this.handleLine(line);
				}
				// we got here, so we must have been disconnected
				this.connected = false;
				this.onDisconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public final void quitServer() {
		this.running = false;
		this.onQuitRequest();
	}

	public final boolean isConnected() {
		return this.connected;
	}

	protected void onDisconnect() {}
	protected void onQuitRequest() {}
}
