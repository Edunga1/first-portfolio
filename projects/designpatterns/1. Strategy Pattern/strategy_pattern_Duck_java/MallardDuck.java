/**
 *	청둥오리
 */
public class MallardDuck extends Duck {

	public MallardDuck() {
		flyBehavior = new FlyWithWings();
		quackBehavior = new QuackWithMouth();
	}
	
	@Override
	public void display() {
		System.out.println("청둥오리 입니다.");
	}

}
