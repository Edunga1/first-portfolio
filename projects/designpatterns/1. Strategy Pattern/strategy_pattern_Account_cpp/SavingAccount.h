#ifndef _SAVINGACCOUNT_
#define _SAVINGACCOUNT_
#include "SavingAccountWithdraw.h"

class SavingAccount : public Account
{
protected:
	int creditlimit;
public:
	SavingAccount(int n, time_t t)
	{
		balance = n;
		openingDay = t;
		withdrawalBehavior = new SavingAccountWithdraw(openingDay);
	}
	virtual void display()
	{
		cout << "= SavingAccount info =========" << endl;
		cout << "¿¹±Ý¾×: " << balance << endl;
		cout << "------------------------------" << endl << endl;
	}
};

#endif