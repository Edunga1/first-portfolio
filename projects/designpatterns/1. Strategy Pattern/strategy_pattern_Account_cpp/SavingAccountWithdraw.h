#ifndef _WITHDRAWALSAVING_
#define _WITHDRAWALSAVING_
#include <time.h>
#include "WithdrawalBehavior.h"

class SavingAccountWithdraw : public WithdrawalBehavior
{
private:
	time_t expirationDay;
public:
	SavingAccountWithdraw(time_t openingDay)
	{
		expirationDay = openingDay + 1000 * 60 * 60 * 24;
	}
	virtual int withdraw(int balance, int amount)
	{
		if (expirationDay <= time(NULL) && balance >= amount)
			balance -= amount;

		return balance;
	}
};

#endif