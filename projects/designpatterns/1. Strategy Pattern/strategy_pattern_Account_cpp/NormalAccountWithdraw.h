#ifndef _WITHDRAWALNORMAL_
#define _WITHDRAWALNORMAL_
#include "WithdrawalBehavior.h"

class NormalAccountWithdraw : public WithdrawalBehavior
{
public:
	virtual int withdraw(int balance, int amount)
	{
		if (balance >= amount)
			balance -= amount;

		return balance;
	}
};

#endif