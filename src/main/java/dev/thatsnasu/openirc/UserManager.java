package dev.thatsnasu.openirc;

import java.util.HashMap;

public class UserManager {
	private HashMap<String, User> users;
	private HashMap<String, User> arrivingUsers;
	
	
	public UserManager() {
		this.users = new HashMap<String, User>();
		this.arrivingUsers = new HashMap<String, User>();
	}
	
	public void arriveUser(String nickname, User user) {
		this.arrivingUsers.put(nickname, user);
	}
	
	public User getUserByNick(String nickname) {
		if(this.users.containsKey(nickname)) return this.users.get(nickname);
		if(this.arrivingUsers.containsKey(nickname)) return this.arrivingUsers.get(nickname);
		return null;
	}
}
