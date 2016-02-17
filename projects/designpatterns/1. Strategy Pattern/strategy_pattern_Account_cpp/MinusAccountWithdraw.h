#ifndef _WITHDRAWALMINUS_
#define _WITHDRAWALMINUS_
#include "WithdrawalBehavior.h"

class MinusAccountWithdraw : public WithdrawalBehavior
{
private:
	int creditlimit;
public:
	MinusAccountWithdraw(int c) : creditlimit(c) {}
	virtual int withdraw(int balance, int amount)
	{
		if (balance + creditlimit >= amount)
			balance -= amount;

		return balance;
	}
};

#endif