package dp.mvc.bank;

import java.util.ArrayList;

import dp.strategy.account.Account;
import dp.strategy.account.NormalAccount;

public class BankDatabase implements UserInterface {
	private ArrayList<User> customers;
	private ArrayList<UserObserver> userObservers;

	public BankDatabase() {
		customers = new ArrayList<User>();
		userObservers = new ArrayList<UserObserver>();
	}

	@Override
	public void removeUserObserver(UserObserver o) {
		userObservers.remove(o);
	}

	@Override
	public void registUserObserver(UserObserver o) {
		userObservers.add(o);
	}
	
	@Override
	public void notifyObservers() {
		for(UserObserver o : userObservers){
			o.updateUser();
		}
	}
	
	/**
	 * �� ���� �����ͺ��̽��� ����Ѵ�.
	 * @param u
	 */
	public void registCustomer(User u) {
		customers.add(u);
		notifyObservers();
	}
	
	/**
	 * ��� ������ ���ڸ� �����Ѵ�.
	 * @param text ���� ����
	 */
	public void sendMessage(String text) {
		for(User u : customers){
			u.addMessage(text);
		}
		notifyObservers();
	}
	
	/**
	 * �� ����� ��ȯ�Ѵ�.
	 * @return
	 */
	public ArrayList<User> getCustomers() {
		return customers;
	}
	
	/**
	 * ���� �� ���¸� �����Ѵ�.
	 * @param u
	 */
	public void newAccount(User u) {
		Account a = new NormalAccount(0, System.currentTimeMillis());
		u.getAccounts().add(a);
		notifyObservers();
	}
}
