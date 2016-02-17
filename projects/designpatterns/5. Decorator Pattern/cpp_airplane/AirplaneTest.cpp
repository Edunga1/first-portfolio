#include <iostream>
#include "F14Tomcat.h"
#include "F15Eagle.h"
#include "F16FightingFalcon.h"
#include "M61Vulcan.h"
#include "AIM120Amraam.h"
#include "AIM7Sparrow.h"
#include "AIM9Sidewinder.h"

using namespace std;

void main()
{
	Airplane *airplane = new F14Tomcat();
	airplane = new M61Vulcan(airplane);
	airplane = new AIM7Sparrow(airplane);
	airplane = new AIM9Sidewinder(airplane);
	airplane->attack();
	cout << endl;

	Airplane *airplane2 = new F15Eagle();
	airplane2 = new M61Vulcan(airplane2);
	airplane2 = new AIM120Amraam(airplane2);
	airplane2->attack();
	cout << endl;

	Airplane *airplane3 = new F16FightingFalcon();
	airplane3 = new AIM9Sidewinder(airplane3);
	airplane3->attack();

	delete airplane;
	delete airplane2;
	delete airplane3;
}