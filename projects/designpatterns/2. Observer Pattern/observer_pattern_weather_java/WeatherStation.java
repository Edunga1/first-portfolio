
public class WeatherStation {

	public static void main(String[] args) {
		// subject
		WeatherData weatherData = new WeatherData();
		
		// �ʱⰪ
		weatherData.setMeasurements(77, 88, 31.1f);

		// Observers
		CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay(weatherData);
		StatisticDisplay statisticsDisplay = new StatisticDisplay(weatherData);
		ForecastDisplay forecastDisplay = new ForecastDisplay(weatherData);

		// ���� �ùķ��̼� - pull
		currentDisplay.pullState();
		
		// ���� �ùķ��̼� - push
		weatherData.setMeasurements(80, 65, 30.4f); 
		weatherData.setMeasurements(82, 70, 29.2f);
		weatherData.setMeasurements(78, 90, 29.2f);
	}

}
