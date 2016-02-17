package pdu.decorator.airplane;

public class F14Tomcat implements Airplane {
	
	@Override
	public void attack() {
		System.out.println("F-15 Tomcat이 공격을 시작합니다.");
	}
}
