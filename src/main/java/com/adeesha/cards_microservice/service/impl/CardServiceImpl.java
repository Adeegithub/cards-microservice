package com.adeesha.cards_microservice.service.impl;

import com.adeesha.cards_microservice.constants.CardConstants;
import com.adeesha.cards_microservice.dto.CardsDto;
import com.adeesha.cards_microservice.entity.Cards;
import com.adeesha.cards_microservice.exception.CardAlreadyExistsException;
import com.adeesha.cards_microservice.exception.ResourceNotFoundException;
import com.adeesha.cards_microservice.mapper.CardsMapper;
import com.adeesha.cards_microservice.repository.CardsRepository;
import com.adeesha.cards_microservice.service.ICardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardServiceImpl implements ICardService {

    //Dependency Injection
    CardsRepository cardsRepository;

    @Override
    public void createCard(String mobileNumber) {
        Optional<Cards> optionalCards = cardsRepository.findByMobileNumber(mobileNumber);
        if (optionalCards.isPresent()){
            throw new CardAlreadyExistsException("Card Already Registered with the Given Mobile Number: " + mobileNumber);
        }
        cardsRepository.save(createNewCard(mobileNumber));
    }

    @Override
    public CardsDto fetchCard(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card","Mobile NUmber", mobileNumber)
        );
        return CardsMapper.mapToCardsDto(cards,new CardsDto());
    }

    private Cards createNewCard(String mobileNumber) {
        Cards newCard = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardConstants.NEW_CARD_LIMIT);
        return newCard;
    }

}
