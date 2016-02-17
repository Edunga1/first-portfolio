#ifndef _OBSERVER_H_
#define _OBSERVER_H_

class Observer
{
public:
	virtual void update(float t, float h, float p) = 0;
};

#endif