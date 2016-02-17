package dp.mvc.bank;

import java.util.ArrayList;

import dp.strategy.account.Account;


public class User {
	private String name;
	private ArrayList<Account> accounts;
	private ArrayList<String> messages;
	
	public User(String name) {
		this.name = name;
		accounts = new ArrayList<Account>();
		messages = new ArrayList<String>();
	}
	
	public String getName(){
		return name;
	}
	
	public ArrayList<Account> getAccounts() {
		return accounts;
	}

	public ArrayList<String> getMessages() {
		return messages;
	}
	
	public void addMessage(String text) {
		messages.add(text);
	}
}
