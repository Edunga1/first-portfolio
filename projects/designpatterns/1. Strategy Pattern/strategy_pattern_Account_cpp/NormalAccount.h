#ifndef _NORMALACCOUNT_
#define _NORMALACCOUNT_
#include <iostream>
#include "NormalAccountWithdraw.h"

using namespace std;

class NormalAccount : public Account
{
public:
	NormalAccount(int n, time_t t)
	{
		balance = n;
		openingDay = t;
		withdrawalBehavior = new NormalAccountWithdraw();
	}
	virtual void display()
	{
		cout << "= NormalAccount info =========" << endl;
		cout << "¿¹±Ý¾×: " << balance << endl;
		cout << "------------------------------" << endl << endl;
	}
};

#endif