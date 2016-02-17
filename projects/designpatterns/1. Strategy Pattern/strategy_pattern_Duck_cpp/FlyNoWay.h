#ifndef _FLYNOWAY_
#define _FLYNOWAY_
#include <iostream>
#include "FlyBehavior.h"

using namespace std;

class FlyNoWay : public FlyBehavior
{
public:
	virtual void fly()
	{
		cout << "날 수 없습니다 T_T" << endl;
	}
};

#endif