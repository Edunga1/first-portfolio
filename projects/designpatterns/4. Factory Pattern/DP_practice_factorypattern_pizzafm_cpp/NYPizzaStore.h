#ifndef _NYPIZZASTORE_H_
#define _NYPIZZASTORE_H_
#include "PizzaStore.h"
#include "NYStyleCheesePizza.h"
#include "NYStyleClamPizza.h"
#include "NYStylePepperoniPizza.h"
#include "NYStyleVeggiePizza.h"

class NYPizzaStore : public PizzaStore
{
public:
	Pizza* createPizza(string item){
		if (item.compare("cheese") == 0) {
			return new NYStyleCheesePizza();
		}
		else if (item.compare("veggie") == 0) {
			return new NYStyleVeggiePizza();
		}
		else if (item.compare("clam") == 0) {
			return new NYStyleClamPizza();
		}
		else if (item.compare("pepperoni") == 0) {
			return new NYStylePepperoniPizza();
		}
		else return NULL;
	}
};

#endif