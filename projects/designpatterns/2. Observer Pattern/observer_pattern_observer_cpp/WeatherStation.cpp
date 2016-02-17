#include "WeatherData.h"
#include "CurrentConditionsDisplay.h"
#include "StatisticDisplay.h"
#include "ForecastDisplay.h"

int main()
{
	// subject
	WeatherData *weatherData = new WeatherData();

	// �ʱⰪ
	weatherData->setMeasurements(77, 88, 31.1f);

	// observers
	CurrentConditionsDisplay *currentDisplay = new CurrentConditionsDisplay(weatherData);
	StatisticDisplay *statisticDisplay = new StatisticDisplay(weatherData);
	ForecastDisplay *forecastDisplay = new ForecastDisplay(weatherData);

	// ���� �ùķ��̼� - pull
	currentDisplay->pullState();

	// ���� �ùķ��̼� - push
	weatherData->setMeasurements(80, 65, 30.4f);
	weatherData->setMeasurements(82, 70, 29.2f);
	weatherData->setMeasurements(78, 90, 29.2f);

	delete weatherData;
	delete currentDisplay;
	delete statisticDisplay;
	delete forecastDisplay;

	return 0;
}