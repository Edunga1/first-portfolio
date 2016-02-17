#include <iostream>
#include "pizza.h"
#include "PizzaStore.h"
#include "NYPizzaStore.h"
#include "ChicagoPizzaStore.h"

using namespace std;

void main()
{
	PizzaStore *nyStore = new NYPizzaStore();
	PizzaStore *chicagoStore = new ChicagoPizzaStore();

	Pizza *pizza = nyStore->orderPizza("cheese");
	cout << "Ethan ordered a " << pizza->getName() << endl;
	delete pizza;

	pizza = chicagoStore->orderPizza("cheese");
	cout << "Joel ordered a " << pizza->getName() << endl;
	delete pizza;

	pizza = nyStore->orderPizza("clam");
	cout << "Ethan ordered a " << pizza->getName() << endl;
	delete pizza;

	pizza = chicagoStore->orderPizza("clam");
	cout << "Joel ordered a " << pizza->getName() << endl;
	delete pizza;

	pizza = nyStore->orderPizza("pepperoni");
	cout << "Ethan ordered a " << pizza->getName() << endl;
	delete pizza;

	pizza = chicagoStore->orderPizza("pepperoni");
	cout << "Joel ordered a " << pizza->getName() << endl;
	delete pizza;

	pizza = nyStore->orderPizza("veggie");
	cout << "Ethan ordered a " << pizza->getName() << endl;
	delete pizza;

	pizza = chicagoStore->orderPizza("veggie");
	cout << "Joel ordered a " << pizza->getName() << endl;
	delete pizza;

	delete nyStore;
	delete chicagoStore;
}