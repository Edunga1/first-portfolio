#include <iostream>
#include "MallardDuck.h"
#include "RubberDuck.h"
#include "FlyRocketPowered.h"

using namespace std;

int main()
{
	Duck *mallard = new MallardDuck();
	mallard->performFly();

	Duck *rubber = new RubberDuck();
	rubber->performFly();

	rubber->setFlyBehavior(new FlyRocketPowered());
	rubber->performFly();

	mallard->performQuack();
	rubber->performQuack();

	delete mallard;
	delete rubber;

	return 0;
}