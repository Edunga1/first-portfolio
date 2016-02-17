#ifndef _WINNERSTATE_H_
#define _WINNERSTATE_H_
#include <iostream>
#include "State.h"
#include "GumballMachine.h"

using namespace std;

class WinnerState : public State
{
private:
	GumballMachine *gumballMachine;

public:
	WinnerState(GumballMachine *gumballMachine){
		this->gumballMachine = gumballMachine;
	}

	virtual void insertQuarter(){
		cout << "Please wait, we're already giving you a Gumball" << endl;
	}

	virtual void ejectQuarter(){
		cout << "Please wait, we're already giving you a Gumball" << endl;
	}

	virtual void turnCrank(){
		cout << "Turning again doesn't get you another gumball!" << endl;
	}

	virtual void dispense(){
		cout << "YOU'RE A WINNER! You get two gumballs for your quarter" << endl;
		gumballMachine->releaseBall();
		if (gumballMachine->getCount() == 0){
			gumballMachine->setState(gumballMachine->getSoldOutState());
		} else {
			gumballMachine->releaseBall();
			if (gumballMachine->getCount() > 0) {
				gumballMachine->setState(gumballMachine->getNoQuarterState());
			}
			else {
				cout << "Oops, out of gumballs!" << endl;
				gumballMachine->setState(gumballMachine->getSoldOutState());
			}
		}

	}

	virtual string toString(){
		return "despensing two gumballs for your quarter, because YOU'RE A WINNER!";
	}
};

#endif