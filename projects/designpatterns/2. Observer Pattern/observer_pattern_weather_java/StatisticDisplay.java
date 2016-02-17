
public class StatisticDisplay implements Observer, DisplayElement {
	private float temp;
	private float humidity;
	private float pressure;
	private Subject weatherData;

	public StatisticDisplay(Subject weatherData) {
		this.weatherData = weatherData;
		this.weatherData.registerObserver(this);
	}
	
	@Override
	public void display() {
		System.out.println("Statistics: " + temp + "F degrees and " + humidity + "% humidity and " + pressure + " hPa");
	}

	@Override
	public void update(float t, float h, float p) {
		temp = t;
		humidity = h;
		pressure = p;
		display();
	}

}
