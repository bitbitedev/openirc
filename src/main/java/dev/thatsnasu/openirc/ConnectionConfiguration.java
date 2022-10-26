package dev.thatsnasu.openirc;

public class ConnectionConfiguration {
	private String authentication;
	private String username;
	private String nickname;
	private String realname;

	private String host;
	private int port;


	public ConnectionConfiguration() {
		this.authentication = null;
		this.username = "OpenIRC";
		this.nickname = "OpenIRC";
		this.realname = "OpenIRC";

		this.host = null;
		this.port = 6667;
	}

	public final void setAuthentication(String authentication) {
		this.authentication = authentication;
	}

	public final void setUsername(String username) {
		this.username = username;
		if(this.nickname.equals("OpenIRC")) this.nickname = this.username;
	}

	public final void setNickname(String nickname) {
		this.nickname = nickname;
		if(this.username.equals("OpenIRC")) this.username = this.nickname;
	}

	public final void setRealName(String realname) {
		this.realname = realname;
	}

	public final void setHost(String host) {
		this.host = host;
	}

	public final void setPort(int port) {
		this.port = port;
	}

	public final String getUsername() {
		return this.username;
	}

	public final String getNickname() {
		return this.nickname;
	}

	public final String getRealname() {
		return this.realname;
	}

	public final String getAuthentication() {
		return this.authentication;
	}

	public final String getHost() {
		return this.host;
	}

	public final int getPort() {
		return this.port;
	}
}
