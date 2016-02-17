public abstract class Account {
	protected long openingDay;	// 개설일
	protected int balance;		// 잔고
	protected WithdrawalBehavior withdrawalBehavior;	// 출금 방법
	
	public void deposit(int amount){
		if(amount > 0)
			balance += amount;
	}
	
	public void withdraw(int amount){
		balance = withdrawalBehavior.withdraw(balance, amount);
	}
	
	public abstract void display();
}
