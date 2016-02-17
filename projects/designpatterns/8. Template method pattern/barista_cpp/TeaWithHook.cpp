#include <iostream>
#include "TeaWithHook.h"

void TeaWithHook::brew(){
	std::cout << "Steeping the tea" << std::endl;
}

void TeaWithHook::addCondiments(){
	std::cout << "Adding Lemon" << std::endl;
}

bool TeaWithHook::customerWantsCondiments(){
	std::string answer = getUserInput();

	if (answer.at(0) == 'y') {
		return true;
	}
	else {
		return false;
	}
}

std::string TeaWithHook::getUserInput(){
	std::string answer = "";

	std::cout << "Would you like milk and sugar with your coffee (y/n)? " << std::endl;

	try {
		std::cin >> answer;
	}
	catch (std::exception e) {
		std::cerr << "IO error trying to read your answer" << std::endl;
	}
	if (answer.compare("") == 0) {
		return "no";
	}
	return answer;
}