/**
 * ���� �Դϴ�.
 */
public abstract class Duck {
	FlyBehavior flyBehavior;
	QuackBehavior quackBehavior;
	
	/**
	 * ������ ������ ����մϴ�.
	 */
	abstract void display();
	
	/**
	 * ������ ����� �����մϴ�.
	 */
	public void swim(){
		System.out.println("All ducks float, even decoys!");
	}
	
	/**
	 * ������ ������ �����մϴ�.
	 */
	public void performFly(){
		if(flyBehavior != null)
			flyBehavior.fly();
	}
	
	/**
	 * ������ ���� ����� �ٲߴϴ�.
	 * @param fb	���� ���
	 */
	public void setFlyBehavior(FlyBehavior fb){
		this.flyBehavior = fb;
	}
	
	/**
	 * ������ ��ϴ�.
	 */
	public void performQuack(){
		if(quackBehavior != null)
			quackBehavior.quack();
	}
}
