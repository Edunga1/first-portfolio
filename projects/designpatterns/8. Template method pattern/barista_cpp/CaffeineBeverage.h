#ifndef _CAFFEINEBEVERAGE_H_
#define _CAFFEINEBEVERAGE_H_

class CaffeineBeverage
{
public:
	const void prepareRecipe();
	virtual void brew() = 0;
	virtual void addCondiments() = 0;
	void boilWater();
	void pourInCup();
};

#endif