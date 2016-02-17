#ifndef _F14TOMCAT_H_
#define _F14TOMCAT_H_
#include <iostream>
#include "Airplane.h"

using namespace std;

class F14Tomcat : public Airplane
{
public:
	virtual void attack(){
		cout << "F-15 Tomcat이 공격을 시작합니다." << endl;
	}
};

#endif