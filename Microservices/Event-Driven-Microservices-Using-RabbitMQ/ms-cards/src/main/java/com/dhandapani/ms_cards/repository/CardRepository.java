package com.dhandapani.ms_cards.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dhandapani.ms_cards.entity.Card;

public interface CardRepository extends JpaRepository<Card, Long> {

	Optional<Card> findByMobileNumber(String mobileNumber);
	
	Optional<Card> findByCardNumber(String cardNumber);
}
