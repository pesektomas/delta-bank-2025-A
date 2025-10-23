package org.example.accounts.services;

import org.example.cards.PaymentCard;
import org.example.cards.PaymentCardService;

public class BankAccountCardManipulationService {

    private PaymentCardService paymentCardService;
    private BankAccountDepositManipulationService  bankAccountDepositManipulationService;

    public BankAccountCardManipulationService(
            BankAccountDepositManipulationService  bankAccountDepositManipulationService,
            PaymentCardService paymentCardService
    ) {
        this.bankAccountDepositManipulationService = bankAccountDepositManipulationService;
        this.paymentCardService = paymentCardService;
    }

    public void atmDeposit(String cardNumber, String pin, double amount) {
        PaymentCard card = paymentCardService.getPaymentCard(cardNumber);

        // TODO move validations into new service
        if (card == null) {
            throw new IllegalArgumentException("Card not found.");
        }


        if (!card.getPin().contains(pin)) {
            throw new IllegalArgumentException("Bad pin.");
        }

        if (card.getBankAccount().getBalance() < amount) {
            throw new IllegalArgumentException("Not enough balance.");
        }

        this.bankAccountDepositManipulationService.withdraw(card.getBankAccount(), amount);
    }

}
