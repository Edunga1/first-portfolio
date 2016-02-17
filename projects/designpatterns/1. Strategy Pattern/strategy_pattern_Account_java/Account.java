public abstract class Account {
	protected long openingDay;	// ������
	protected int balance;		// �ܰ�
	protected WithdrawalBehavior withdrawalBehavior;	// ��� ���
	
	public void deposit(int amount){
		if(amount > 0)
			balance += amount;
	}
	
	public void withdraw(int amount){
		balance = withdrawalBehavior.withdraw(balance, amount);
	}
	
	public abstract void display();
}
