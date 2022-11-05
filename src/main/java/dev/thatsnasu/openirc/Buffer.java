package dev.thatsnasu.openirc;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Buffer implements Runnable {
	private ConcurrentHashMap<String, PrintWriter> messageBuffer;
	private ArrayList<Client> clients;
	private int bufferTimeout;

	public Buffer(int bufferTimeout) {
		this.messageBuffer = new ConcurrentHashMap<String, PrintWriter>();
		this.clients = new ArrayList<Client>();
		this.bufferTimeout = bufferTimeout;
	}

	public final synchronized void addMessageToBuffer(String message, PrintWriter writer) {
		this.messageBuffer.put(message, writer);
	}

	public final void addClient(Client client) {
		if(!this.clients.contains(client)) this.clients.add(client);
	}

	public void removeClient(Client client) {
		if(this.clients.contains(client)) this.clients.remove(client);
	}

	@Override
	public void run() {
		while(this.clients.size() != 0) {
			for(Map.Entry<String, PrintWriter> entrySet : this.messageBuffer.entrySet()) {
				entrySet.getValue().println(entrySet.getKey());
				entrySet.getValue().flush();
				this.messageBuffer.remove(entrySet.getKey());
				try {
					Thread.sleep(bufferTimeout);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
			try {
				Thread.sleep(bufferTimeout);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
