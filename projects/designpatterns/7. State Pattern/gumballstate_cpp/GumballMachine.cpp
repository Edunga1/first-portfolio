#include "GumballMachine.h"
#include <iostream>
#include <string>
#include "SoldOutState.h"
#include "HasQuarterState.h"
#include "SoldState.h"
#include "NoQuarterState.h"

GumballMachine::GumballMachine(int numberGumballs){
	soldOutState = new SoldOutState(this);
	noQuarterState = new NoQuarterState(this);
	hasQuarterState = new HasQuarterState(this);
	soldState = new SoldState(this);

	count = numberGumballs;
	if (numberGumballs > 0){
		state = noQuarterState;
	}
}

void GumballMachine::insertQuarter(){
	state->insertQuarter();
}

void GumballMachine::ejectQuarter(){
	state->ejectQuarter();
}

void GumballMachine::turnCrank(){
	state->turnCrank();
	state->dispense();
}

void GumballMachine::setState(State *state){
	this->state = state;
}

void GumballMachine::releaseBall(){
	cout << "A gumball comes rolling out the slot..." << endl;
	if (count != 0) {
		count = count - 1;
	}
}

int GumballMachine::getCount(){
	return count;
}

void GumballMachine::refill(int count){
	this->count = count;
	state = noQuarterState;
}

State* GumballMachine::getState(){
	return state;
}

State* GumballMachine::getSoldOutState(){
	return soldOutState;
}


State* GumballMachine::getNoQuarterState(){
	return noQuarterState;
}

State* GumballMachine::getHasQuarterState(){
	return hasQuarterState;
}

State* GumballMachine::getSoldState(){
	return soldState;
}

string GumballMachine::toString(){
	string result = "";
	result += "\nMighty Gumball, Inc.";
	result += "\nJava-enabled Standing Gumball Model #2004";
	result += "\nInventory: " + to_string(count) + " gumball";
	if (count != 0)
		result += "s";
	result += "\n";
	result += "Machine is " + state->toString();
	result += "\n";

	return result;
}