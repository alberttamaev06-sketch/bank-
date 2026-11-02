package org.example;

public class AccountNotFoundException extends BankException {
    public AccountNotFoundException(String accountNumber) {
        super("Счёт не найден: " + accountNumber);
    }
}
