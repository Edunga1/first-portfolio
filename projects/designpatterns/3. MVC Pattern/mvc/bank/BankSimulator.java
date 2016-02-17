package dp.mvc.bank;

public class BankSimulator {

	public static void main(String[] args) {
		BankDatabase model = new BankDatabase();
		BankController controller = new BankController(model);
	}

}
