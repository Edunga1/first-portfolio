#ifndef _FORECASTDISPLAY_H_
#define _FORECASTDISPLAY_H_
#include <iostream>
#include "Subject.h"
#include "Observer.h"
#include "DisplayElement.h"

using namespace std;

class ForecastDisplay : public Observer, public DisplayElement
{
private:
	float temp;
	Subject *weatherData;
public:
	ForecastDisplay(Subject *weatherData)
	{
		this->weatherData = weatherData;
		this->weatherData->registerObserver(this);
	}

	virtual void update(float t, float h, float p)
	{
		temp = t;
		display();
	}

	virtual void display()
	{
		cout << "Forecast: " << temp << "F degrees" << endl;
	}
};

#endif