#ifndef _F15EAGLE_H_
#define _F15EAGLE_H_
#include <iostream>
#include "Airplane.h"

using namespace std;

class F15Eagle : public Airplane
{
public:
	virtual void attack(){
		cout << "F-15 Eagle�� ������ �����մϴ�." << endl;
	}
};

#endif