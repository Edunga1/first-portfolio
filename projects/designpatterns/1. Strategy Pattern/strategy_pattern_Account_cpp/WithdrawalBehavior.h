#ifndef _WITHDRAWALBEHAVIOR_
#define _WITHDRAWALBEHAVIOR_

class WithdrawalBehavior
{
public:
	virtual int withdraw(int balance, int amount) = 0;
};

#endif