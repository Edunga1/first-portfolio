#ifndef _FLYWITHWINGS_
#define _FLYWITHWINGS_
#include <iostream>
#include "FlyBehavior.h"

using namespace std;

class FlyWithWings : public FlyBehavior
{
public:
	virtual void fly()
	{
		cout << "�Ĵ� �Ĵ� �Ĵ� �Ĵ� �Ĵ� �Ĵ�" << endl;
	}
};

#endif