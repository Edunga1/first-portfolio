public class NormalAccount extends Account {
	public NormalAccount(int n, long d) {
		balance = n;
		openingDay = d;
		withdrawalBehavior = new NormalAccountWithdraw();
	}
	
	@Override
	public void display() {
		System.out.println("= NormalAccount info =========");
		System.out.println("¿¹±Ý¾×: " + balance);
		System.out.println("------------------------------");
		System.out.println("");
	}
}
