package com.dhandapani.ms_cards.mapper;

import com.dhandapani.ms_cards.dto.CardDto;
import com.dhandapani.ms_cards.entity.Card;

public class CardMapper {

	public static CardDto mapToCardDto(Card card, CardDto cardDto) {
		cardDto.setMobileNumber(card.getMobileNumber());
		cardDto.setCardNumber(card.getCardNumber());
		cardDto.setCardType(card.getCardType());
		cardDto.setTotalLimit(card.getTotalLimit());
		cardDto.setAmountUsed(card.getAmountUsed());
		cardDto.setAvailableAmount(card.getAvailableAmount());
		return cardDto;
	}

	public static Card mapToCard(CardDto cardDto, Card card) {
		card.setMobileNumber(cardDto.getMobileNumber());
		card.setCardNumber(cardDto.getCardNumber());
		card.setCardType(cardDto.getCardType());
		card.setTotalLimit(cardDto.getTotalLimit());
		card.setAmountUsed(cardDto.getAmountUsed());
		card.setAvailableAmount(cardDto.getAvailableAmount());
		return card;
	}
}
