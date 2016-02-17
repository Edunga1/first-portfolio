/**
 *	û�տ���
 */
public class MallardDuck extends Duck {

	public MallardDuck() {
		flyBehavior = new FlyWithWings();
		quackBehavior = new QuackWithMouth();
	}
	
	@Override
	public void display() {
		System.out.println("û�տ��� �Դϴ�.");
	}

}
