#ifndef _WEATHERDATA_H_
#define _WEATHERDATA_H_
#include <vector>
#include "Subject.h"
#include "Observer.h"

class WeatherData : public Subject
{
private:
	std::vector<Observer*> observers;
	float temp;
	float humidity;
	float pressure;
public:
	WeatherData()
	{
	}

	virtual void registerObserver(Observer *o)
	{
		observers.push_back(o);
	}

	virtual void unregisterObserver(Observer *o)
	{
		for (std::vector<Observer*>::iterator it = observers.begin(); it != observers.end(); it++)
		{
			if (*it == o){
				observers.erase(it);
				return;
			}
		}
	}

	virtual void notifyObservers()
	{
		for (std::vector<Observer*>::iterator it = observers.begin(); it != observers.end(); it++)
		{
			(*it)->update(temp, humidity, pressure);
		}
	}

	// ���� ����
	void setMeasurements(float t, float h, float p)
	{
		temp = t;
		humidity = h;
		pressure = p;
		measurementsChanged();
	}

	// observer�鿡�� ���� ���� ����
	void measurementsChanged()
	{
		notifyObservers();
	}

	// ���� ��ȯ
	float getTemp()
	{
		return temp;
	}

	float getHumidity()
	{
		return humidity;
	}

	float getPressure()
	{
		return pressure;
	}
};

#endif