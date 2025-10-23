package org.example.cards;

import org.example.accounts.BaseBankAccount;

import java.util.UUID;

public class PaymentCardFactory {

    private PaymentCardNumberGenerator paymentCardNumberGenerator;
    private PaymentCardCvvGenerator paymentCardCvvGenerator;
    private PaymentCardPinGenerator  paymentCardPinGenerator = new PaymentCardPinGenerator();
    private PaymentCardExpirationCalculator paymentCardExpirationCalculator = new PaymentCardExpirationCalculator();

    public PaymentCardFactory(
            PaymentCardNumberGenerator paymentCardNumberGenerator,
            PaymentCardCvvGenerator paymentCardCvvGenerator,
            PaymentCardPinGenerator  paymentCardPinGenerator,
            PaymentCardExpirationCalculator paymentCardExpirationCalculator
    ) {
        this.paymentCardNumberGenerator = paymentCardNumberGenerator;
        this.paymentCardCvvGenerator = paymentCardCvvGenerator;
        this.paymentCardPinGenerator = paymentCardPinGenerator;
        this.paymentCardExpirationCalculator = paymentCardExpirationCalculator;
    }

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
