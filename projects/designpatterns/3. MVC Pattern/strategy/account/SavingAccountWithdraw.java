package dp.strategy.account;

import java.util.Date;

public class SavingAccountWithdraw implements WithdrawalBehavior {
	private long expirationDay;

	public SavingAccountWithdraw(long openingDay) {
		expirationDay = openingDay + (1000*60*60*24);
	}
	
	@Override
	public int withdraw(int balance, int amount) {
		if(expirationDay <= new Date().getTime() && balance >= amount)
			balance -= amount;
		
		return balance;
	}

}
