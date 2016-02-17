#ifndef _RUBBERDUCK_
#define _RUBBERDUCK_
#include <iostream>
#include "Duck.h"
#include "FlyNoWay.h"
#include "QuackArtificiality.h"

using namespace std;

class RubberDuck : public Duck
{
public:
	RubberDuck()
	{
		flyBehavior = new FlyNoWay();
		quackBehavior = new QuackArtificiality();
	}
	~RubberDuck(){}
	virtual void display()
	{
		cout << "고무오리다!!" << endl;
	}
};

#endif