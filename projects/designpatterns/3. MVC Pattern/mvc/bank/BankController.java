package dp.mvc.bank;

import dp.strategy.account.Account;

public class BankController implements ControllerInterface {
	private SystemView view;
	private BankDatabase model;
	
	public BankController(BankDatabase model) {
		this.model = model;
		view = new SystemView(this, model);
	}
	
	/**
	 * 입금
	 * @param account
	 * @param amount
	 */
	public void deposit(Account account, int amount) {
		account.deposit(amount);
		view.updateUser();
	}
	
	/**
	 * 출금
	 * @param account
	 * @param amount
	 */
	public void withdraw(Account account, int amount) {
		account.withdraw(amount);
		view.updateUser();
	}
	
	/**
	 * 모든 고객에 문자를 전송한다.
	 * @param text 문자 내용
	 */
	public void sendMessage(String text) {
		model.sendMessage(text);
	}
	
	/**
	 * 새 고객을 등록한다.
	 * @param name
	 */
	public void registCustomer(String name) {
		User u = new User(name);
		model.registCustomer(u);
	}
	
	/**
	 * 고객의 새 계좌를 개설한다.
	 * @param u
	 */
	public void newAccount(User u) {
		if(u.getAccounts().size() < 3)
			model.newAccount(u);
	}
}
