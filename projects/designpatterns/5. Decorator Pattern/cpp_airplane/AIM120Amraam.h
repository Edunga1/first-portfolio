#ifndef _AIM120AMRAAM_H_
#define _AIM120AMRAAM_H_
#include <iostream>
#include "Decorator.h"

using namespace std;

class AIM120Amraam : public Decorator
{
public:
	AIM120Amraam(Airplane *airplane){
		this->airplane = airplane;
	}
	virtual void attack(){
		airplane->attack();
		cout << "AIM-120 Amraam 공대공 미사일을 발사합니다!" << endl;
	}
};

#endif