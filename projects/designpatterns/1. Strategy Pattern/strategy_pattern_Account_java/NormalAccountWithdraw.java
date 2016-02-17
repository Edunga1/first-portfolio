
public class NormalAccountWithdraw implements WithdrawalBehavior {

	@Override
	public int withdraw(int balance, int amount) {
		if(balance >= amount)
			balance -= amount;
		
		return balance;
	}
}
