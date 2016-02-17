
public class WeatherStation {

	public static void main(String[] args) {
		// subject
		WeatherData weatherData = new WeatherData();
		
		// 초기값
		weatherData.setMeasurements(77, 88, 31.1f);

		// Observers
		CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay(weatherData);
		StatisticDisplay statisticsDisplay = new StatisticDisplay(weatherData);
		ForecastDisplay forecastDisplay = new ForecastDisplay(weatherData);

		// 기상대 시뮬레이션 - pull
		currentDisplay.pullState();
		
		// 기상대 시뮬레이션 - push
		weatherData.setMeasurements(80, 65, 30.4f); 
		weatherData.setMeasurements(82, 70, 29.2f);
		weatherData.setMeasurements(78, 90, 29.2f);
	}

}
