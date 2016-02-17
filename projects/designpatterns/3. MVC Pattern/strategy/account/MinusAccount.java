package dp.strategy.account;

public class MinusAccount extends Account {
	private int creditlimit;	// 신용한도액
	
	public MinusAccount(int n, long d, int c) {
		balance = n;
		openingDay = d;
		creditlimit = c;
		withdrawalBehavior = new MinusAccountWithdraw(creditlimit);
		interestCalcBehavior = new NormalInterestCalc();
	}
	
	@Override
	public void display() {
		System.out.println("= MinusAccount info ==========");
		System.out.println("예금액: " + balance);
		System.out.println("신용한도액: " + creditlimit);
		System.out.println("------------------------------");
		System.out.println("");
	}
}
