#ifndef _AIM9SIDEWINDER_H_
#define _AIM9SIDEWINDER_H_
#include <iostream>
#include "Decorator.h"

using namespace std;

class AIM9Sidewinder : public Decorator
{
public:
	AIM9Sidewinder(Airplane *airplane){
		this->airplane = airplane;
	}
	virtual void attack(){
		airplane->attack();
		cout << "AIM-9 Sidewinder 공대공 미사일을 발사합니다!" << endl;
	}
};

#endif