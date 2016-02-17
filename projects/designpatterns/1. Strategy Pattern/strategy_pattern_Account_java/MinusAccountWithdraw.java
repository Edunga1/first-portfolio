public class MinusAccountWithdraw implements WithdrawalBehavior {
	private int creditlimit;

	public MinusAccountWithdraw(int creditlimit) {
		this.creditlimit = creditlimit;
	}
	
	@Override
	public int withdraw(int balance, int amount) {
		if(balance + creditlimit >= amount){
			balance -= amount;
		}
		
		return balance;
	}

}
