package dp.strategy.account;

public class MinusAccount extends Account {
	private int creditlimit;	// �ſ��ѵ���
	
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
		System.out.println("���ݾ�: " + balance);
		System.out.println("�ſ��ѵ���: " + creditlimit);
		System.out.println("------------------------------");
		System.out.println("");
	}
}
