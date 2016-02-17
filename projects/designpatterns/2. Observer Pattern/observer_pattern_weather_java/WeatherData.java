import java.util.ArrayList;


public class WeatherData implements Subject {
	ArrayList<Observer> observers;
	private float temp;
	private float humidity;
	private float pressure;

	public WeatherData() {
		observers = new ArrayList<Observer>();
	}
	
	@Override
	public void registerObserver(Observer o) {
		observers.add(o);
	}

	@Override
	public void unregisterObserver(Observer o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers() {
		for (Observer observer : observers) {
			observer.update(temp, humidity, pressure);
		}
	}
	
	// ���� ����
	public void setMeasurements(float t, float h, float p) {
		temp = t;
		humidity = h;
		pressure = p;
		measurementsChanged();
	}
	
	// observer�鿡�� ���� ���� ����
	public void measurementsChanged() {
		notifyObservers();
	}
	
	// ���� ��ȯ
	float getTemp() {
		return temp;
	}
	
	float getHumidity() {
		return humidity;
	}
	
	float getPressure() {
		return pressure;
	}

}
