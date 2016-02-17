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
		cout << "AIM-7 Sparrow �̻����� �߻��մϴ�!" << endl;
	}
};

#endif