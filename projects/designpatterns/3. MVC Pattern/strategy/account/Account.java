package dp.strategy.account;

public abstract class Account {

	protected long openingDay;	// ������
	protected int balance;		// �ܰ�
	protected double interest;	// �⺻ ������
	protected WithdrawalBehavior withdrawalBehavior;	// ��� ���
	protected InterestCalcBehavior interestCalcBehavior;// ���� ����
	
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
