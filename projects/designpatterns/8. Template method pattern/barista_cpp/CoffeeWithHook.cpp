#include <iostream>
#include "CoffeeWithHook.h"

void CoffeeWithHook::brew(){
	std::cout << "Dripping Coffee through filter" << std::endl;
}

void CoffeeWithHook::addCondiments(){
	std::cout << "Adding Sugar and Milk" << std::endl;
}

bool CoffeeWithHook::customerWantsCondiments(){
	std::string answer = getUserInput();

	if (answer.at(0) == 'y') {
		return true;
	}
	else {
		return false;
	}
}

std::string CoffeeWithHook::getUserInput(){
	std::string answer = "";

	std::cout << "Would you like milk and sugar with your coffee (y/n)? " << std::endl;

	try {
		std::cin >> answer;
	}
	catch(std::exception e) {
		std::cerr << "IO error trying to read your answer" << std::endl;
	}
	if (answer.compare("") == 0) {
		return "no";
	}
	return answer;
}