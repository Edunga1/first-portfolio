/**
 * 장난감 고무오리
 */
public class RubberDuck extends Duck {

	public RubberDuck() {
		flyBehavior = new FlyNoWay();
		quackBehavior = new QuackArtificiality();
	}
	
	@Override
	public void display() {
		System.out.println("고무오리다!!");
	}

}
