#ifndef _F16FIGHTINGFALCON_H_
#define _F16FIGHTINGFALCON_H_
#include <iostream>
#include "Airplane.h"

using namespace std;

class F16FightingFalcon : public Airplane
{
public:
	virtual void attack(){
		cout << "F-16 Fighting Falcon이 공격을 시작합니다." << endl;
	}
};

#endif