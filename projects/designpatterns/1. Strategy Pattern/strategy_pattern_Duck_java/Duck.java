/**
 * 오리 입니다.
 */
public abstract class Duck {
	FlyBehavior flyBehavior;
	QuackBehavior quackBehavior;
	
	/**
	 * 오리의 정보를 출력합니다.
	 */
	abstract void display();
	
	/**
	 * 오리가 헤엄을 시작합니다.
	 */
	public void swim(){
		System.out.println("All ducks float, even decoys!");
	}
	
	/**
	 * 오리가 비행을 시작합니다.
	 */
	public void performFly(){
		if(flyBehavior != null)
			flyBehavior.fly();
	}
	
	/**
	 * 오리가 나는 방법을 바꿉니다.
	 * @param fb	나는 방법
	 */
	public void setFlyBehavior(FlyBehavior fb){
		this.flyBehavior = fb;
	}
	
	/**
	 * 오리가 웁니다.
	 */
	public void performQuack(){
		if(quackBehavior != null)
			quackBehavior.quack();
	}
}
