package com.adeesha.cards_microservice.service;

import com.adeesha.cards_microservice.dto.CardsDto;

public interface ICardService {
    void createCard(String mobileNumber);

    CardsDto fetchCard(String mobileNumber);

    boolean updateCard(CardsDto cardsDto);

    boolean deleteCard(String mobileNumber);
}
