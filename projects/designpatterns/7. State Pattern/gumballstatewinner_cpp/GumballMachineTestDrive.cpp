#include <iostream>
#include "gumballMachine.h"

using namespace std;

void main()
{
	GumballMachine *gumballMachine = new GumballMachine(10);

	cout << gumballMachine->toString() << endl;

	gumballMachine->insertQuarter();
	gumballMachine->turnCrank();
	gumballMachine->insertQuarter();
	gumballMachine->turnCrank();

	cout << gumballMachine->toString() << endl;

	gumballMachine->insertQuarter();
	gumballMachine->turnCrank();
	gumballMachine->insertQuarter();
	gumballMachine->turnCrank();

	cout << gumballMachine->toString() << endl;

	gumballMachine->insertQuarter();
	gumballMachine->turnCrank();
	gumballMachine->insertQuarter();
	gumballMachine->turnCrank();

	cout << gumballMachine->toString() << endl;

	gumballMachine->insertQuarter();
	gumballMachine->turnCrank();
	gumballMachine->insertQuarter();
	gumballMachine->turnCrank();

	cout << gumballMachine->toString() << endl;

	gumballMachine->insertQuarter();
	gumballMachine->turnCrank();
	gumballMachine->insertQuarter();
	gumballMachine->turnCrank();

	cout << gumballMachine->toString() << endl;

	delete gumballMachine;
}