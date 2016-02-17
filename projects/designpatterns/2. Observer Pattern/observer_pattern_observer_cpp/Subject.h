#ifndef _SUBJECT_H_
#define _SUBJECT_H_
#include "Observer.h"

class Subject
{
public:
	// observer ���
	virtual void registerObserver(Observer *o) = 0;
	// observer ����
	virtual void unregisterObserver(Observer *o) = 0;
	// ��� observer�� ����
	virtual void notifyObservers() = 0;
};

#endif