package dev.thatsnasu.openirc;

public class ConnectionConfiguration {
	public String authentication;
	public String username;
	public String nickname;
	public String realname;

	public String host;
	public int port;
	public int timeout;


	public ConnectionConfiguration() {
		this.authentication = null;
		this.username = "OpenIRC";
		this.nickname = "OpenIRC";
		this.realname = "OpenIRC";

		this.host = null;
		this.port = 6667;
		this.timeout = 15000;
	}
}
