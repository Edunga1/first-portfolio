package pdu.decorator.airplane;

public class M61Vulcan extends Decorator {
	
	public M61Vulcan(Airplane airplane) {
		this.airplane = airplane;
	}
	
	@Override
	public void attack() {
		airplane.attack();
		System.out.println("M61 벌컨으로 탄환을 분당 7200발 발사합니다!");
	}
}
