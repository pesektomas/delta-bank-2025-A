package org.example;

import org.example.accounts.factories.BankAccountFactory;
import org.example.accounts.generators.BankAccountNumberGenerator;
import org.example.accounts.services.BankAccountCardManipulationService;
import org.example.accounts.services.BankAccountDepositManipulationService;
import org.example.accounts.services.BankAccountService;
import org.example.cards.*;
import org.example.logger.ConsoleLogger;
import org.example.logger.Logger;
import org.example.persons.customers.factories.CustomerFactory;

public class Container {

    public PaymentCardCvvGenerator paymentCardCvvGenerator;
    public PaymentCardPinGenerator paymentCardPinGenerator;
    public PaymentCardExpirationCalculator paymentCardExpirationCalculator;
    public PaymentCardNumberGenerator paymentCardNumberGenerator;
    public PaymentCardFactory paymentCardFactory;
    public PaymentCardService paymentCardService;

    public Logger logger;

    public CustomerFactory customerFactory;
    public BankAccountDepositManipulationService bankAccountDepositManipulationService;
    public BankAccountFactory bankAccountFactory;
    public BankAccountService bankAccountService;

    public BankAccountNumberGenerator bankAccountNumberGenerator;
    public BankAccountCardManipulationService bankAccountCardManipulationService;

    public Container() {
        this.logger = new ConsoleLogger();

        this.bankAccountNumberGenerator = new BankAccountNumberGenerator();

        this.paymentCardNumberGenerator = new PaymentCardNumberGenerator();
        this.paymentCardCvvGenerator = new PaymentCardCvvGenerator();
        this.paymentCardPinGenerator = new PaymentCardPinGenerator();
        this.paymentCardExpirationCalculator = new PaymentCardExpirationCalculator();

        this.paymentCardFactory = new PaymentCardFactory(paymentCardNumberGenerator, paymentCardCvvGenerator, paymentCardPinGenerator, paymentCardExpirationCalculator);

        this.customerFactory = new CustomerFactory();
        this.bankAccountDepositManipulationService = new BankAccountDepositManipulationService(this.logger);
        this.bankAccountFactory = new BankAccountFactory(this.bankAccountNumberGenerator);

        this.bankAccountService = new BankAccountService(this.bankAccountFactory);
        this.paymentCardService = new PaymentCardService(this.paymentCardFactory);

        this.bankAccountCardManipulationService = new BankAccountCardManipulationService(
                this.bankAccountDepositManipulationService,
                this.paymentCardService
        );

    }

}
