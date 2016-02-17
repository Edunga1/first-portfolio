#ifndef _SOLDOUTSTATE_H_
#define _SOLDOUTSTATE_H_
#include <iostream>
#include "State.h"
#include "GumballMachine.h"

using namespace std;

class SoldOutState : public State
{
private:
	GumballMachine *gumballMachine;

public:
	SoldOutState(GumballMachine *gumballMachine){
		this->gumballMachine = gumballMachine;
	}

	virtual void insertQuarter(){
		cout << "You can't insert a quarter, the machine is sold out" << endl;
	}

	virtual void ejectQuarter(){
		cout << "You can't eject, you haven't inserted a quarter yet" << endl;
	}

	virtual void turnCrank(){
		cout << "You turned, but there are no gumballs" << endl;
	}

	virtual void dispense(){
		cout << "No gumball dispensed" << endl;
	}

	virtual string toString(){
		return "sold out";
	}
};

#endif