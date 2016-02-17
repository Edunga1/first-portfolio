package pdu.decorator.airplane;

public class F15Eagle implements Airplane {
	
	@Override
	public void attack() {
		System.out.println("F-15 Eagle이 공격을 시작합니다.");
	}
}
