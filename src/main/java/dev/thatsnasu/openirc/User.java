package dev.thatsnasu.openirc;

public class User {
	public String username;
	public String hostname;
	public String servername;
	public String realname;
	public String nickname;
	public String previousNickname;
	
	public User(String nickname) {
		this.nickname = nickname;
	}
	
	public void setUserData(String username, String hostname, String servername, String realname) {
		this.username = username;
		this.hostname = hostname;
		this.servername = servername;
		this.realname = realname;
	}
	
	public void setNickname(String nickname) {
		this.previousNickname = this.nickname;
		this.nickname = nickname;
	}
	
	@Override
	public String toString() {
		return this.username+"!"+this.hostname+"@"+this.servername+"("+this.realname+") using "+this.nickname+", previously known as "+this.previousNickname;
	}
}
