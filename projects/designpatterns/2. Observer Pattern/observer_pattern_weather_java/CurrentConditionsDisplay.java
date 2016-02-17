
public class CurrentConditionsDisplay implements Observer, DisplayElement {
	private float temp;
	private float humidity;
	private Subject weatherData;
	
	public CurrentConditionsDisplay(Subject weatherData) {
		this.weatherData = weatherData;
		this.weatherData.registerObserver(this);
	}
	
	@Override
	public void display() {
		System.out.println("Current Conditions: " + temp + "F degrees and " + humidity + "% humidity");
	}

	@Override
	public void update(float t, float h, float p) {
		temp = t;
		humidity = h;
		display();
	}
	
	// Subject로부터 직접 상태를 가져온다.
	void pullState() {
		if(weatherData instanceof WeatherData){
			temp = ((WeatherData)weatherData).getTemp();
			humidity = ((WeatherData)weatherData).getHumidity();
			display();
		}
	}

}
