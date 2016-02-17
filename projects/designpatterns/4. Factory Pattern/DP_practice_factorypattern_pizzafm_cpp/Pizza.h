#ifndef _PIZZA_H_
#define _PIZZA_H_
#include <iostream>
#include <string>
#include <vector>

using namespace std;

class Pizza
{
protected:
	string name;
	string dough;
	string sauce;
	vector<string> toppings;

public:
	void prepare(){
		cout << "Preparing " << name << endl;
		cout << "Tossing dough..." << endl;
		cout << "Adding sauce..." << endl;
		cout << "Adding toppings: " << endl;
		for (vector<string>::iterator i = toppings.begin(); i != toppings.end(); i++) {
			cout << "   " + *i << endl;
		}
	}

	void bake(){
		cout << "Bake for 25 minutes at 350" << endl;
	}

	void cut(){
		cout << "Cutting the pizza into diagonal slices" << endl;
	}

	void box(){
		cout << "Place pizza in official PizzaStore box" << endl;
	}

	string getName(){
		return name;
	}

	string toString(){
		string display = "---- " + name + " ----\n";
		display += dough + "\n";
		display += sauce + "\n";
		for (vector<string>::iterator i = toppings.begin(); i != toppings.end(); i++) {
			display += *i + "\n";
		}
		return display;
	}
};

#endif