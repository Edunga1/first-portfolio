/**
 * �峭�� ������
 */
public class RubberDuck extends Duck {

	public RubberDuck() {
		flyBehavior = new FlyNoWay();
		quackBehavior = new QuackArtificiality();
	}
	
	@Override
	public void display() {
		System.out.println("��������!!");
	}

}
