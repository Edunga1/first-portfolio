#ifndef _CURRENTCONDITIONSDISPLAY_H_
#define _CURRENTCONDITIONSDISPLAY_H_
#include <iostream>
#include "Observer.h"
#include "DisplayElement.h"
#include "Subject.h"

using namespace std;

class CurrentConditionsDisplay : public Observer, public DisplayElement
{
private:
	float temp;
	float humidity;
	Subject *weatherData;
public:
	CurrentConditionsDisplay(Subject *weatherData)
	{
		this->weatherData = weatherData;
		this->weatherData->registerObserver(this);
	}

	virtual void update(float t, float h, float p)
	{
		temp = t;
		humidity = h;
		display();
	}

	virtual void display()
	{
		cout << "Current Conditions: " << temp << "F degrees and " << humidity << "% humidity" << endl;
	}

	// Subject로부터 직접 상태를 가져온다.
	void pullState()
	{
		if (typeid(*weatherData) == typeid(WeatherData))
		{
			temp = ((WeatherData*)weatherData)->getTemp();
			humidity = ((WeatherData*)weatherData)->getHumidity();
			display();
		}
	}
};

#endif