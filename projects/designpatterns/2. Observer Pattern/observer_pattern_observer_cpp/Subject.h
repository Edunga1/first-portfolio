#ifndef _SUBJECT_H_
#define _SUBJECT_H_
#include "Observer.h"

class Subject
{
public:
	// observer 등록
	virtual void registerObserver(Observer *o) = 0;
	// observer 해지
	virtual void unregisterObserver(Observer *o) = 0;
	// 모든 observer에 통지
	virtual void notifyObservers() = 0;
};

#endif