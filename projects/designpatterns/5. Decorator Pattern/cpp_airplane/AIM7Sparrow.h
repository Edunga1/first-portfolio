#ifndef _AIM7SPARROW_H_
#define _AIM7SPARROW_H_
#include <iostream>
#include "Decorator.h"

using namespace std;

class AIM7Sparrow : public Decorator
{
public:
	AIM7Sparrow(Airplane *airplane){
		this->airplane = airplane;
	}
	virtual void attack(){
		airplane->attack();
		cout << "AIM-7 Sparrow 미사일을 발사합니다!" << endl;
	}
};

#endif