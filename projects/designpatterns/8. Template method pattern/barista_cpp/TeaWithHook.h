#ifndef _TEAWITHHOOK_H_
#define _TEAWITHHOOK_H_
#include <string>
#include "CaffeineBeverageWithHook.h"

class TeaWithHook : public CaffeineBeverageWithHook
{
public:
	virtual void brew();
	virtual void addCondiments();
	virtual bool customerWantsCondiments();
private:
	std::string getUserInput();
};

#endif