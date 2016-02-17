#ifndef _DECORATOR_H_
#define _DECORATOR_H_
#include "Airplane.h"

class Decorator : public Airplane
{
protected:
	Airplane *airplane;
public:
	virtual void attack() = 0;
};

#endif