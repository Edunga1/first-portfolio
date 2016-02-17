package pdu.decorator.airplane;

public class AIM9Sidewinder extends Decorator {
	
	public AIM9Sidewinder(Airplane airplane) {
		this.airplane = airplane;
	}
	
	@Override
	public void attack() {
		airplane.attack();
		System.out.println("AIM-9 Sidewinder 공대공 미사일을 발사합니다!");
	}
}
