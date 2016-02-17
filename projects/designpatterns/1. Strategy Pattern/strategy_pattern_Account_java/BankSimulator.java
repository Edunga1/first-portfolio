import java.util.Date;


public class BankSimulator {
	public static void main(String[] args) {
		Account normal = new NormalAccount(1000, new Date().getTime());
		Account minus = new MinusAccount(1000, new Date().getTime(), 1000);
		Account savingNew = new SavingAccount(1000, new Date().getTime());
		Account savingOld = new SavingAccount(1000, new Date(System.currentTimeMillis() - 1000*60*60*24).getTime());
		
		normal.withdraw(1000);
		minus.withdraw(2000);
		savingNew.withdraw(1000);
		savingOld.withdraw(1000);
		
		normal.display();
		minus.display();
		savingNew.display();
		savingOld.display();
	}
}
