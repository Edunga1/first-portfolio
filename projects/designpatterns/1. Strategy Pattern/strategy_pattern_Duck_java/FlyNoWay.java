/**
 *	어떤 방법으로도 날 수 없습니다.
 */
public class FlyNoWay implements FlyBehavior {

	@Override
	public void fly() {
		System.out.println("날 수 없습니다 T_T");
	}

}
