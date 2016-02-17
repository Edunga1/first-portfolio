#ifndef _COFFEE_H_
#define _COFFEE_H_
#include "CaffeineBeverage.h"

class Coffee : public CaffeineBeverage
{
public:
	virtual void brew();
	virtual void addCondiments();
};

#endif