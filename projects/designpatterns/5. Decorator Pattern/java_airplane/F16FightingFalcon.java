package pdu.decorator.airplane;

public class F16FightingFalcon implements Airplane {
	
	@Override
	public void attack() {
		System.out.println("F-16 Fighting Falcon이 공격을 시작합니다.");
	}
}
