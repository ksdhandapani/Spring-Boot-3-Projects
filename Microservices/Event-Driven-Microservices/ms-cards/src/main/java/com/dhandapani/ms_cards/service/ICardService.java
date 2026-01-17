package com.dhandapani.ms_cards.service;

import com.dhandapani.ms_cards.dto.CardDto;

public interface ICardService {
	
	void createCardByMobileNumber(String mobileNumber);
	
	CardDto fetchCardByMobileNumber(String mobileNumber);
	
	boolean updateCard(CardDto cardDto);
	
	boolean deleteCardByMobileNumber(String mobileNumber);

}
