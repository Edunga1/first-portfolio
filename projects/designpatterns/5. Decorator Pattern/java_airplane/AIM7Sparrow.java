package pdu.decorator.airplane;

public class AIM7Sparrow extends Decorator {
	
	public AIM7Sparrow(Airplane airplane) {
		this.airplane = airplane;
	}
	
	@Override
	public void attack() {
		airplane.attack();
		System.out.println("AIM-7 Sparrow 미사일을 발사합니다!");
	}
}
