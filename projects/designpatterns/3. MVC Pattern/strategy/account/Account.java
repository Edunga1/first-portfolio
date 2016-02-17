package dp.strategy.account;

public abstract class Account {

	protected long openingDay;	// 개설일
	protected int balance;		// 잔고
	protected double interest;	// 기본 이자율
	protected WithdrawalBehavior withdrawalBehavior;	// 출금 방법
	protected InterestCalcBehavior interestCalcBehavior;// 이자 계산법
	
	public Account() {
		interest = 0.05;
	}
	
	public void deposit(int amount){
		if(amount > 0)
			balance += amount;
	}
	
	public void withdraw(int amount){
		balance = withdrawalBehavior.withdraw(balance, amount);
	}
	
	public abstract void display();
	
	public long getOpeningDay() {
		return openingDay;
	}

	public int getBalance() {
		return balance;
	}
	
	public double getInterest() {
		return interestCalcBehavior.getCalculatedInetrest(interest);
	}
	
	public void setInterestCalcBehavior(InterestCalcBehavior b) {
		interestCalcBehavior = b;
	}
}
