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
	 * 새 고객을 데이터베이스에 등록한다.
	 * @param u
	 */
	public void registCustomer(User u) {
		customers.add(u);
		notifyObservers();
	}
	
	/**
	 * 모든 고객에게 문자를 전송한다.
	 * @param text 문자 내용
	 */
	public void sendMessage(String text) {
		for(User u : customers){
			u.addMessage(text);
		}
		notifyObservers();
	}
	
	/**
	 * 고객 목록을 반환한다.
	 * @return
	 */
	public ArrayList<User> getCustomers() {
		return customers;
	}
	
	/**
	 * 고객의 새 계좌를 개설한다.
	 * @param u
	 */
	public void newAccount(User u) {
		Account a = new NormalAccount(0, System.currentTimeMillis());
		u.getAccounts().add(a);
		notifyObservers();
	}
}
