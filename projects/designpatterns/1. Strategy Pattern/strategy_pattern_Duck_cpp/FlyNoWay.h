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
		cout << "�� �� �����ϴ� T_T" << endl;
	}
};

#endif