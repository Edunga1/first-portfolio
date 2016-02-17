package dp.strategy.account;

public class NormalInterestCalc implements InterestCalcBehavior {

	@Override
	public double getCalculatedInetrest(double base) {
		return base;
	}

}
