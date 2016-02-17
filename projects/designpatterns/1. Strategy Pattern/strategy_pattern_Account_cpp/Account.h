#ifndef _ACCOUNT_
#define _ACCOUNT_
#include <time.h>
#include "WithdrawalBehavior.h"

class Account
{
protected:
	time_t openingDay;
	int	balance;
	WithdrawalBehavior *withdrawalBehavior;
public:
	Account(){}
	~Account()
	{
		if (withdrawalBehavior)
			delete withdrawalBehavior;
	}
	void deposit(int amount)
	{
		if (amount > 0)
			balance += amount;
	}
	void withdraw(int amount)
	{
		if (withdrawalBehavior)
			balance = withdrawalBehavior->withdraw(balance, amount);
	}
	virtual void display() = 0;
};

#endif