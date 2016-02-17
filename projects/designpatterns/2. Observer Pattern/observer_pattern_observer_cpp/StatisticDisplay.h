#ifndef _STATISTICDISPLAY_H_
#define _STATISTICDISPLAY_H_
#include <iostream>
#include "Subject.h"
#include "Observer.h"
#include "DisplayElement.h"

using namespace std;

class StatisticDisplay : public Observer, public DisplayElement
{
private:
	float temp;
	float humidity;
	float pressure;
	Subject *weatherData;
public:
	StatisticDisplay(Subject *weatherData)
	{
		this->weatherData = weatherData;
		this->weatherData->registerObserver(this);
	}

	virtual void update(float t, float h, float p)
	{
		temp = t;
		humidity = h;
		pressure = p;
		display();
	}

	virtual void display()
	{
		cout << "Statistics: " << temp << "F degrees and " << humidity << "% humidity and " << pressure << " hPa" << endl;
	}
};

#endif