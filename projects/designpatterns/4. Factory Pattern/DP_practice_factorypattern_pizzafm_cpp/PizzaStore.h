#ifndef _PIZZASTORE_H_
#define _PIZZASTORE_H_
#include <string>
#include <iostream>
#include "Pizza.h"

using namespace std;

class PizzaStore
{
protected:
	virtual Pizza* createPizza(string item) = 0;
public:
	Pizza* orderPizza(string type){
		Pizza *pizza = createPizza(type);
		cout << "--- Making a " << pizza->getName() << " ---" << endl;
		pizza->prepare();
		pizza->bake();
		pizza->cut();
		pizza->box();
		return pizza;
	}
};

#endif