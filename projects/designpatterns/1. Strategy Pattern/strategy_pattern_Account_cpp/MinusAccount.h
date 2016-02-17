#ifndef _MINUSACCOUNT_
#define _MINUSACCOUNT_
#include "MinusAccountWithdraw.h"

class MinusAccount : public Account
{
private:
	int creditlimit;
public:
	MinusAccount(int n, time_t t, int c)
	{
		balance = n;
		openingDay = t;
		creditlimit = c;
		withdrawalBehavior = new MinusAccountWithdraw(creditlimit);
	}
	virtual void display()
	{
		cout << "= MinusAccount info ==========" << endl;
		cout << "예금액: " << balance << endl;
		cout << "신용한도액: " << creditlimit << endl;
		cout << "------------------------------" << endl << endl;
	}
};

#endif