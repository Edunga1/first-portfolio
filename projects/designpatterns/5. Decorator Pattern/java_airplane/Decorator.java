package pdu.decorator.airplane;

public abstract class Decorator implements Airplane {
	Airplane airplane;
	public abstract void attack();
}
