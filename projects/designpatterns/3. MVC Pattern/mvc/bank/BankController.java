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
	 * �Ա�
	 * @param account
	 * @param amount
	 */
	public void deposit(Account account, int amount) {
		account.deposit(amount);
		view.updateUser();
	}
	
	/**
	 * ���
	 * @param account
	 * @param amount
	 */
	public void withdraw(Account account, int amount) {
		account.withdraw(amount);
		view.updateUser();
	}
	
	/**
	 * ��� ���� ���ڸ� �����Ѵ�.
	 * @param text ���� ����
	 */
	public void sendMessage(String text) {
		model.sendMessage(text);
	}
	
	/**
	 * �� ���� ����Ѵ�.
	 * @param name
	 */
	public void registCustomer(String name) {
		User u = new User(name);
		model.registCustomer(u);
	}
	
	/**
	 * ���� �� ���¸� �����Ѵ�.
	 * @param u
	 */
	public void newAccount(User u) {
		if(u.getAccounts().size() < 3)
			model.newAccount(u);
	}
}
