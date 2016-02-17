#include <iostream>
#include "CaffeineBeverageWithHook.h"

void CaffeineBeverageWithHook::prepareRecipe(){
	boilWater();
	brew();
	pourInCup();
	if (customerWantsCondiments()) {
		addCondiments();
	}
}

void CaffeineBeverageWithHook::boilWater(){
	std::cout << "Boiling water" << std::endl;
}

void CaffeineBeverageWithHook::pourInCup(){
	std::cout << "Pouring into cup" << std::endl;
}

bool CaffeineBeverageWithHook::customerWantsCondiments(){
	return true;
}