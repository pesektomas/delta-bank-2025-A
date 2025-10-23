package org.example;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.example.accounts.BankAccount;
import org.example.accounts.BaseBankAccount;
import org.example.accounts.SaveBankAccount;
import org.example.accounts.services.BankAccountCardManipulationService;
import org.example.accounts.services.BankAccountDepositManipulationService;
import org.example.accounts.services.BankAccountService;
import org.example.cards.PaymentCard;
import org.example.cards.PaymentCardFactory;
import org.example.cards.PaymentCardService;
import org.example.logger.Logger;
import org.example.persons.customers.Customer;
import org.example.persons.customers.factories.CustomerFactory;

@Singleton
public class App {

    @Inject
    private CustomerFactory customerFactory;

    @Inject
    private BankAccountService bankAccountService;

    @Inject
    private Logger logger;

    @Inject
    private BankAccountDepositManipulationService bankAccountDepositManipulationService;

    @Inject
    private BankAccountCardManipulationService bankAccountCardManipulationService;

    @Inject
    private PaymentCardService paymentCardService;

    public void run() {
        Customer customer = customerFactory.createCustomer("c-123", "Tomas", "Pesek");
        this.logger.log(customer.getUuid() + ": " + customer.getFirstName() + " " + customer.getLastName());

        this.logger.log("=== TEST BANK ACCOUNT");
        BaseBankAccount account1 = testBankAccount(customer);

        this.logger.log(account1 instanceof BankAccount ? "Bank" : "Save");

        this.logger.log("=== TEST SAVE ACCOUNT");
        BaseBankAccount account2 = testSaveAccount(customer);
        this.logger.log(account2 instanceof  BankAccount ? "Bank" : "Save");

        if (account2 instanceof SaveBankAccount) {
            float interestRate = ((SaveBankAccount)account2).getInterestRate();
            this.logger.log("Interest Rate: " + interestRate);
        }

        this.testPaymentCard(customer);
    }

    private BaseBankAccount testSaveAccount(Customer customer) {
        BaseBankAccount account = this.bankAccountService.createSaveBankAccount(
                "u-123",
                customer,
                5
        );

        try{
            this.logger.log(account.getUuid() + "(" + account.getBankAccountNumber() + "): " + account.getBalance());

            // account.addBalance(500);
            this.bankAccountDepositManipulationService.deposit(account, 500);
            this.logger.log(account.getUuid() + ": " + account.getBalance());

            // account.subtractBalance(400);
            this.bankAccountDepositManipulationService.withdraw(account, 500);
            this.logger.log(account.getUuid() + ": " + account.getBalance());

        } catch (Exception e) {
            this.logger.log("Error: " + e.getMessage());
        }

        return account;
    }

    private BaseBankAccount testBankAccount(Customer customer) {
        BaseBankAccount account = this.bankAccountService.createBankAccount(
                "u-123",
                customer
        );

        try {
            this.logger.log(account.getUuid() + " (" + account.getBankAccountNumber() + "): " + account.getBalance());

            // account.addBalance(500);
            this.bankAccountDepositManipulationService.deposit(account, 500);
            this.logger.log(account.getUuid() + ": " + account.getBalance());

            this.bankAccountDepositManipulationService.deposit(account, 400);
            this.logger.log(account.getUuid() + ": " + account.getBalance());

            // account.subtractBalance(300);
            this.bankAccountDepositManipulationService.withdraw(account, 300);

        } catch (Exception e) {
            this.logger.log("Error: " + e.getMessage());
        }

        return account;
    }

    private void testPaymentCard(Customer customer) {

        this.logger.log("=== PAYMENT CARD test ===");

        BaseBankAccount bankAccount = this.bankAccountService.createBankAccount("c-123", customer);
        this.bankAccountDepositManipulationService.deposit(bankAccount, 50000);

        PaymentCard paymentCard = this.paymentCardService.create(bankAccount);

        this.logger.log("cardNumber: " + paymentCard.getCardNumber());
        this.logger.log("balance: " + paymentCard.getBankAccount().getBalance());

        this.bankAccountCardManipulationService.atmDeposit(paymentCard.getCardNumber(), paymentCard.getPin(), 500);
        this.logger.log("balance: " + paymentCard.getBankAccount().getBalance());
    }
}
