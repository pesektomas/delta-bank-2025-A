package org.example;

import org.example.accounts.BankAccount;
import org.example.accounts.BaseBankAccount;
import org.example.accounts.SaveBankAccount;
import org.example.cards.PaymentCard;
import org.example.persons.customers.Customer;

public class App {

    Container container = new Container();

    public void run() {
        Customer customer = container.customerFactory.createCustomer("c-123", "Tomas", "Pesek");
        container.logger.log(customer.getUuid() + ": " + customer.getFirstName() + " " + customer.getLastName());

        container.logger.log("=== TEST BANK ACCOUNT");
        BaseBankAccount account1 = testBankAccount(customer);

        container.logger.log(account1 instanceof BankAccount ? "Bank" : "Save");

        container.logger.log("=== TEST SAVE ACCOUNT");
        BaseBankAccount account2 = testSaveAccount(customer);
        container.logger.log(account2 instanceof  BankAccount ? "Bank" : "Save");

        if (account2 instanceof SaveBankAccount) {
            float interestRate = ((SaveBankAccount)account2).getInterestRate();
            container.logger.log("Interest Rate: " + interestRate);
        }

        this.testPaymentCard(customer);
    }

    private BaseBankAccount testSaveAccount(Customer customer) {
        BaseBankAccount account = container.bankAccountService.createSaveBankAccount(
                "u-123",
                customer,
                5
        );

        try{
            container.logger.log(account.getUuid() + "(" + account.getBankAccountNumber() + "): " + account.getBalance());

            // account.addBalance(500);
            container.bankAccountDepositManipulationService.deposit(account, 500);
            container.logger.log(account.getUuid() + ": " + account.getBalance());

            // account.subtractBalance(400);
            container.bankAccountDepositManipulationService.withdraw(account, 500);
            container.logger.log(account.getUuid() + ": " + account.getBalance());

        } catch (Exception e) {
            container.logger.log("Error: " + e.getMessage());
        }

        return account;
    }

    private BaseBankAccount testBankAccount(Customer customer) {
        BaseBankAccount account = container.bankAccountService.createBankAccount(
                "u-123",
                customer
        );

        try {
            container.logger.log(account.getUuid() + " (" + account.getBankAccountNumber() + "): " + account.getBalance());

            // account.addBalance(500);
            container.bankAccountDepositManipulationService.deposit(account, 500);
            container.logger.log(account.getUuid() + ": " + account.getBalance());

            container.bankAccountDepositManipulationService.deposit(account, 400);
            container.logger.log(account.getUuid() + ": " + account.getBalance());

            // account.subtractBalance(300);
            container.bankAccountDepositManipulationService.withdraw(account, 300);

        } catch (Exception e) {
            container.logger.log("Error: " + e.getMessage());
        }

        return account;
    }

    private void testPaymentCard(Customer customer) {

        container.logger.log("=== PAYMENT CARD test ===");

        BaseBankAccount bankAccount = container.bankAccountService.createBankAccount("c-123", customer);
        container.bankAccountDepositManipulationService.deposit(bankAccount, 50000);

        PaymentCard paymentCard = container.paymentCardService.create(bankAccount);

        container.logger.log("cardNumber: " + paymentCard.getCardNumber());
        container.logger.log("balance: " + paymentCard.getBankAccount().getBalance());

        container.bankAccountCardManipulationService.atmDeposit(paymentCard.getCardNumber(), paymentCard.getPin(), 500);
        container.logger.log("balance: " + paymentCard.getBankAccount().getBalance());
    }
}
