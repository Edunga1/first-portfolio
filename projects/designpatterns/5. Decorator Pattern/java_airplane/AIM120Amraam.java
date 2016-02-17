package pdu.decorator.airplane;

public class AIM120Amraam extends Decorator {
	
	public AIM120Amraam(Airplane airplane) {
		this.airplane = airplane;
	}
	
	@Override
	public void attack() {
		airplane.attack();
		System.out.println("AIM-120 Amraam ����� �̻����� �߻��մϴ�!");
	}
}
