#ifndef _HASQUARTERSTATE_H_
#define _HASQUARTERSTATE_H_
#include <iostream>
#include <cstdlib>
#include "State.h"
#include "GumballMachine.h"

using namespace std;

class HasQuarterState : public State
{
private:
	GumballMachine *gumballMachine;

public:
	HasQuarterState(GumballMachine *gumballMachine){
		this->gumballMachine = gumballMachine;
	}

	virtual void insertQuarter(){
		cout << "You can't insert another quarter" << endl;
	}

	virtual void ejectQuarter(){
		cout << "Quarter returned" << endl;
		gumballMachine->setState(gumballMachine->getNoQuarterState());
	}

	virtual void turnCrank(){
		cout << "You turned..." << endl;
		int winner = rand() % 10;
		if ((winner == 0) && (gumballMachine->getCount() > 1)){
			gumballMachine->setState(gumballMachine->getWinnerState());
		} else {
			gumballMachine->setState(gumballMachine->getSoldState());
		}
	}

	virtual void dispense(){
		cout << "No gumball dispensed" << endl;
	}

	virtual string toString(){
		return "waiting for turn of crank";
	}
};

#endif