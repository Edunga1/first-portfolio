package pdu.decorator.airplane;

public class M61Vulcan extends Decorator {
	
	public M61Vulcan(Airplane airplane) {
		this.airplane = airplane;
	}
	
	@Override
	public void attack() {
		airplane.attack();
		System.out.println("M61 �������� źȯ�� �д� 7200�� �߻��մϴ�!");
	}
}
