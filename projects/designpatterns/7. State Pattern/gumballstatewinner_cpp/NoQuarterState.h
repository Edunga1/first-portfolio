#ifndef _NOQUARTERSTATE_H_
#define _NOQUARTERSTATE_H_
#include <iostream>
#include "State.h"
#include "GumballMachine.h"

using namespace std;

class NoQuarterState : public State
{
private:
	GumballMachine *gumballMachine;

public:
	NoQuarterState(GumballMachine *gumballMachine){
		this->gumballMachine = gumballMachine;
	}

	virtual void insertQuarter(){
		cout << "You inserted a quarter" << endl;
		gumballMachine->setState(gumballMachine->getHasQuarterState());
	}

	virtual void ejectQuarter(){
		cout << "You haven't inserted a quarter" << endl;
	}

	virtual void turnCrank(){
		cout << "You turned, but there's no quarter" << endl;
	}

	virtual void dispense(){
		cout << "You need to pay first" << endl;
	}

	virtual string toString(){
		return "waiting for quarter";
	}
};

#endif