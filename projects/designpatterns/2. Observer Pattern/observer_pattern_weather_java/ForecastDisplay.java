
public class ForecastDisplay implements Observer, DisplayElement {
	private float temp;
	private Subject weatherData;
	
	public ForecastDisplay(Subject weatherData) {
		this.weatherData = weatherData;
		this.weatherData.registerObserver(this);
	}
	
	@Override
	public void display() {
		System.out.println("Forecast: " + temp + "F degrees");
	}

	@Override
	public void update(float t, float h, float p) {
		temp = t;
		display();
	}

}
