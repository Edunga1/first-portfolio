#ifndef _COFFEEWITHHOOK_H_
#define _COFFEEWITHHOOK_H_
#include <string>
#include "CaffeineBeverageWithHook.h"

class CoffeeWithHook : public CaffeineBeverageWithHook
{
public:
	virtual void brew();
	virtual void addCondiments();
	virtual bool customerWantsCondiments();
private:
	std::string getUserInput();
};

#endif