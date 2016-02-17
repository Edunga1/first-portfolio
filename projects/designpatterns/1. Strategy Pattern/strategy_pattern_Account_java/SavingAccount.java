public class SavingAccount extends Account {
	public SavingAccount(int n, long d) {
		balance = n;
		openingDay = d;
		withdrawalBehavior = new SavingAccountWithdraw(openingDay);
	}
	
	@Override
	public void display() {
		System.out.println("= SavingAccount info =========");
		System.out.println("���ݾ�: " + balance);
		System.out.println("------------------------------");
		System.out.println("");
	}
}
