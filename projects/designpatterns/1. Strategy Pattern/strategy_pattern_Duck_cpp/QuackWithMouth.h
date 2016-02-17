#ifndef _QUACKWITHMOUTH_
#define _QUACKWITHMOUTH_
#include <iostream>
#include "QuackBehavior.h"

using namespace std;

class QuackWithMouth : public QuackBehavior
{
public:
	virtual void quack()
	{
		cout << "Ва! Ва! Ва! Ва! Ва! Ва! Ва! Ва! Ва!" << endl;
	}
};

#endif