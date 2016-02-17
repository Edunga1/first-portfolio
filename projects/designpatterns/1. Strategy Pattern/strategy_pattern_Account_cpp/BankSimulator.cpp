#include "Account.h"
#include "NormalAccount.h"
#include "SavingAccount.h"
#include "MinusAccount.h"

int main()
{
	Account *normal = new NormalAccount(1000, time(NULL));
	Account *minus = new MinusAccount(1000, time(NULL), 1000);
	Account *savingNew = new SavingAccount(1000, time(NULL));
	Account *savingOld = new SavingAccount(1000, time(NULL) - 1000 * 60 * 60 * 24);
	
	normal->withdraw(1000);
	minus->withdraw(2000);
	savingNew->withdraw(1000);
	savingOld->withdraw(1000);

	normal->display();
	minus->display();
	savingNew->display();
	savingOld->display();

	delete normal;
	delete minus;
	delete savingNew;
	delete savingOld;

	return 0;
}