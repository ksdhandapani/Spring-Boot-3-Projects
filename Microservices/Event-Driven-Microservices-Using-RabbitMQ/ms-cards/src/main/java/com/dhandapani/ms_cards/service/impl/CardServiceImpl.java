package com.dhandapani.ms_cards.service.impl;

import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.dhandapani.ms_cards.constant.CardConstants;
import com.dhandapani.ms_cards.dto.CardDto;
import com.dhandapani.ms_cards.entity.Card;
import com.dhandapani.ms_cards.exception.ResourceAlreadyExistsException;
import com.dhandapani.ms_cards.exception.ResourceNotFoundException;
import com.dhandapani.ms_cards.mapper.CardMapper;
import com.dhandapani.ms_cards.repository.CardRepository;
import com.dhandapani.ms_cards.service.ICardService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CardServiceImpl implements ICardService {

	private CardRepository cardRepository;

	private Card createNewCard(String mobileNumber) {
		Card newCard = new Card();
		long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
		newCard.setCardNumber(Long.toString(randomCardNumber));
		newCard.setCardType(CardConstants.CREDIT_CARD);
		newCard.setMobileNumber(mobileNumber);
		newCard.setTotalLimit(CardConstants.NEW_CREDIT_CARD_LIMIT);
		newCard.setAmountUsed(0);
		newCard.setAvailableAmount(CardConstants.NEW_CREDIT_CARD_LIMIT);
		return newCard;
	}

	@Override
	public void createCardByMobileNumber(String mobileNumber) {
		Optional<Card> optionalCard = cardRepository.findByMobileNumber(mobileNumber);
		if (optionalCard.isPresent()) {
			throw new ResourceAlreadyExistsException("Card", "mobileNumber", mobileNumber);
		}
		cardRepository.save(createNewCard(mobileNumber));
	}

	@Override
	public CardDto fetchCardByMobileNumber(String mobileNumber) {
		Optional<Card> optionalCard = cardRepository.findByMobileNumber(mobileNumber);
		if (optionalCard.isEmpty()) {
			throw new ResourceNotFoundException("Card", "mobileNumber", mobileNumber);
		}
		return CardMapper.mapToCardDto(optionalCard.get(), new CardDto());
	}

	@Override
	public boolean updateCard(CardDto cardDto) {
		Optional<Card> optionalCard = cardRepository.findByCardNumber(cardDto.getCardNumber());
		if (!optionalCard.isPresent()) {
			throw new ResourceNotFoundException("Card", "cardNumber", cardDto.getCardNumber());
		}
		Card cardToUpdate = CardMapper.mapToCard(cardDto, optionalCard.get());
		cardRepository.save(cardToUpdate);
		return true;
	}

	@Override
	public boolean deleteCardByMobileNumber(String mobileNumber) {
		Card foundCard = cardRepository.findByMobileNumber(mobileNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));
		cardRepository.deleteById(foundCard.getCardId());
		return true;
	}

}
