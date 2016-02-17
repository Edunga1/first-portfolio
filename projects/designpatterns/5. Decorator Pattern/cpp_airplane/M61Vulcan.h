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
		cout << "M61 벌컨으로 탄환을 분당 7200발 발사합니다!" << endl;
	}
};

#endif