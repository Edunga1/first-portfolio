package pdu.decorator.airplane;

public class AirplaneTest {

	public static void main(String[] args) {
		Airplane airplane = new F14Tomcat();
		airplane = new M61Vulcan(airplane);
		airplane = new AIM7Sparrow(airplane);
		airplane = new AIM9Sidewinder(airplane);
		airplane.attack();
		System.out.println();
		
		Airplane airplane2 = new F15Eagle();
		airplane2 = new M61Vulcan(airplane2);
		airplane2 = new AIM120Amraam(airplane2);
		airplane2.attack();
		System.out.println();
		
		Airplane airplane3 = new F16FightingFalcon();
		airplane3 = new AIM9Sidewinder(airplane3);
		airplane3.attack();
	}

}
