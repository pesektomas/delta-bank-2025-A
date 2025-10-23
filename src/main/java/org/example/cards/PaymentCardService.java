package org.example.cards;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.example.accounts.BaseBankAccount;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class PaymentCardService {

    private final Map<String, PaymentCard> paymentCards = new HashMap<>();;

    @Inject
    private PaymentCardFactory paymentCardFactory;

    public PaymentCard create(BaseBankAccount bankAccount) {
        PaymentCard paymentCard = this.paymentCardFactory.create(bankAccount);
        this.paymentCards.put(paymentCard.getCardNumber(), paymentCard);

        return paymentCard;
    }

    public PaymentCard getPaymentCard(String cardNumber) {
        return this.paymentCards.get(cardNumber);
    }
}
