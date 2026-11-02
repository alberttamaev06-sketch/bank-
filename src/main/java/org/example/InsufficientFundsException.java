package org.example;

public class InsufficientFundsException extends BankException {
    public InsufficientFundsException(String accountNumber, double balance, double requested) {
        super("Недостаточно средств на счёте " + accountNumber
                + ": баланс " + balance + ", нужно " + requested);
    }
}
