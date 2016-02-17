#ifndef _GUMBALLMACHINE_H_
#define _GUMBALLMACHINE_H_
#include <string>
#include "State.h"

class GumballMachine
{
private:
	State *soldOutState;
	State *noQuarterState;
	State *hasQuarterState;
	State *soldState;
	State *winnerState;

	State *state;
	int count = 0;

public:
	GumballMachine(int numberGumballs);
	void insertQuarter();
	void ejectQuarter();
	void turnCrank();
	void setState(State *state);
	void releaseBall();
	int getCount();
	void refill(int count);
	State* getState();
	State* getSoldOutState();
	State* getNoQuarterState();
	State* getHasQuarterState();
	State* getSoldState();
	State* getWinnerState();
	std::string toString();
};

#endif