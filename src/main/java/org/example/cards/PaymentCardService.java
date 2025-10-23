package org.example.cards;

import org.example.accounts.BaseBankAccount;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PaymentCardService {

    private Map<String, PaymentCard> paymentCards;

    private PaymentCardFactory paymentCardFactory;

    public PaymentCardService(PaymentCardFactory paymentCardFactory) {
        this.paymentCards = new HashMap<>();

        this.paymentCardFactory = paymentCardFactory;
    }

    public PaymentCard create(BaseBankAccount bankAccount) {
        PaymentCard paymentCard = this.paymentCardFactory.create(bankAccount);
        this.paymentCards.put(paymentCard.getCardNumber(), paymentCard);

        return paymentCard;
    }

    public PaymentCard getPaymentCard(String cardNumber) {
        return this.paymentCards.get(cardNumber);
    }
}
