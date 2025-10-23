package org.example.cards;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.example.accounts.BaseBankAccount;

import java.util.UUID;

@Singleton
public class PaymentCardFactory {

    @Inject
    private PaymentCardNumberGenerator paymentCardNumberGenerator;

    @Inject
    private PaymentCardCvvGenerator paymentCardCvvGenerator;

    @Inject
    private PaymentCardPinGenerator  paymentCardPinGenerator;

    @Inject
    private PaymentCardExpirationCalculator paymentCardExpirationCalculator;

    public PaymentCard create(BaseBankAccount  baseBankAccount) {

        String uuid = UUID.randomUUID().toString();
        String cardNUmber = this.paymentCardNumberGenerator.generateCardNumber();
        String cvv = this.paymentCardCvvGenerator.generateCvv();
        // !!!
        String pin = this.paymentCardPinGenerator.generatePin();
        String expireMonth = this.paymentCardExpirationCalculator.calculateMonthExpire();
        String expireYear = this.paymentCardExpirationCalculator.calculateYearExpire();

        return new PaymentCard(uuid, cardNUmber, cvv, pin, expireMonth, expireYear, baseBankAccount);
    }
}
