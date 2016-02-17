package dp.strategy.account;

import java.math.BigDecimal;

public class IncreaseByDayCalc implements InterestCalcBehavior {
	long openingDay;
	
	public IncreaseByDayCalc(long openingDay) {
		this.openingDay = openingDay;
	}
	
	@Override
	public double getCalculatedInetrest(double base) {
		// �ʴ� 0.001 % ����
		BigDecimal add = new BigDecimal(System.currentTimeMillis() - openingDay).multiply(new BigDecimal("0.000001"));
		return add.add(new BigDecimal(""+base)).doubleValue();
	}

}
