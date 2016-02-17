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
		cout << "û�տ��� �Դϴ�." << endl;
	}
};

#endif