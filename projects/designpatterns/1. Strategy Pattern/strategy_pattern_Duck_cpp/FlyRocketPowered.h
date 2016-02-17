#ifndef _FLYROCKETPOWERED_
#define _FLYROCKETPOWERED_
#include <iostream>
#include "FlyBehavior.h"

using namespace std;

class FlyRocketPowered : public FlyBehavior
{
public:
	virtual void fly()
	{
		cout << "乔蜡快快快快快快快快旷------" << endl;
	}
};

#endif