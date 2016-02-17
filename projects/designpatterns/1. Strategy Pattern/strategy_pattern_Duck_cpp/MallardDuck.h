#ifndef _MALLARDDUCK_
#define _MALLARDDUCK_
#include <iostream>
#include "Duck.h"
#include "FlyWithWings.h"
#include "QuackWithMouth.h"

using namespace std;

class MallardDuck : public Duck
{
public:
	MallardDuck()
	{
		flyBehavior = new FlyWithWings();
		quackBehavior = new QuackWithMouth();
	}
	~MallardDuck(){}
	 void display()
	{
		cout << "청둥오리 입니다." << endl;
	}
};

#endif