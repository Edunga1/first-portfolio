#ifndef _QUACKARTIFICIALITY_
#define _QUACKARTIFICIALITY_
#include <iostream>
#include "QuackBehavior.h"

using namespace std;

class QuackArtificiality : public QuackBehavior
{
public:
	virtual void quack()
	{
		cout << "뿌아아아아아아아아아앙" << endl;
	}
};

#endif