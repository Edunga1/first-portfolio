#ifndef _CAFFEINEBEVERAGEWITHHOOK_H_
#define _CAFFEINEBEVERAGEWITHHOOK_H_

class CaffeineBeverageWithHook
{
public:
	void prepareRecipe();
	virtual void brew() = 0;
	virtual void addCondiments() = 0;
	void boilWater();
	void pourInCup();
	virtual bool customerWantsCondiments();
};

#endif