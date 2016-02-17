#ifndef _SOLDSTATE_H_
#define _SOLDSTATE_H_
#include <iostream>
#include "State.h"
#include "GumballMachine.h"

using namespace std;

class SoldState : public State
{
private:
	GumballMachine *gumballMachine;

public:
	SoldState(GumballMachine *gumballMachine){
		this->gumballMachine = gumballMachine;
	}

	virtual void insertQuarter(){
		cout << "Please wait, we're already giving you a gumball" << endl;
	}

	virtual void ejectQuarter(){
		cout << "Sorry, you already turned the crank" << endl;
	}

	virtual void turnCrank(){
		cout << "Turning twice doesn't get you another gumball!" << endl;
	}

	virtual void dispense(){
		gumballMachine->releaseBall();
		if (gumballMachine->getCount() > 0){
			gumballMachine->setState(gumballMachine->getNoQuarterState());
		} else {
			cout << "Oops, out of gumballs!" << endl;
			gumballMachine->setState(gumballMachine->getSoldOutState());
		}
	}

	virtual string toString(){
		return "dispensing a gumball";
	}
};

#endif