#ifndef _TEA_H_
#define _TEA_H_
#include "CaffeineBeverage.h"

class Tea : public CaffeineBeverage
{
public:
	virtual void brew();
	virtual void addCondiments();
};

#endif