#ifndef _M61VULCAN_H_
#define _M61VULCAN_H_
#include <iostream>
#include "Decorator.h"

using namespace std;

class M61Vulcan : public Decorator
{
public:
	M61Vulcan(Airplane *airplane){
		this->airplane = airplane;
	}
	virtual void attack(){
		airplane->attack();
		cout << "M61 �������� źȯ�� �д� 7200�� �߻��մϴ�!" << endl;
	}
};

#endif