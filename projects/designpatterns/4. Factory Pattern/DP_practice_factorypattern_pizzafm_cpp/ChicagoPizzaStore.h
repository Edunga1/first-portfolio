#ifndef _CHICAGOPIZZASTORE_H_
#define _CHICAGOPIZZASTORE_H_
#include "PizzaStore.h"
#include "ChicagoStyleCheesePizza.h"
#include "ChicagoStyleClamPizza.h"
#include "ChicagoStylePepperoniPizza.h"
#include "ChicagoStyleVeggiePizza.h"

class ChicagoPizzaStore : public PizzaStore
{
public:
	Pizza* createPizza(string item){
		if (item.compare("cheese") == 0) {
			 return new ChicagoStyleCheesePizza();
		}
		else if (item.compare("veggie") == 0) {
			return new ChicagoStyleVeggiePizza();
		}
		else if (item.compare("clam") == 0) {
			return new ChicagoStyleClamPizza();
		}
		else if (item.compare("pepperoni") == 0) {
			return new ChicagoStylePepperoniPizza();
		}
		else return NULL;
	}
};

#endif