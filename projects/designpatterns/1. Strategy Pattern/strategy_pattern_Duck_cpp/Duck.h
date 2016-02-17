#ifndef _DUCK_
#define _DUCK_
#include <iostream>
#include "FlyBehavior.h"
#include "QuackBehavior.h"

using namespace std;

class Duck
{
	friend class MallardDuck;
	friend class RubberDuck;
private:
	FlyBehavior		*flyBehavior;
	QuackBehavior	*quackBehavior;
public:
	Duck(){}
	~Duck()
	{
		if (flyBehavior)
			delete flyBehavior;
		if (quackBehavior)
			delete quackBehavior;
	}
	virtual void display() = 0;
	void swim()
	{
		cout << "All ducks float, even decoys!" << endl;
	}
	void performFly()
	{
		if (flyBehavior)
			flyBehavior->fly();
	}
	void setFlyBehavior(FlyBehavior* fb)
	{
		if (flyBehavior)
			delete flyBehavior;
		flyBehavior = fb;
	}
	void performQuack()
	{
		if (quackBehavior)
			quackBehavior->quack();
	}
};

#endif