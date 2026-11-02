package org.example;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private final String number;
    private final String clientId;
    private double balance;
    private final List<Transaction> history = new ArrayList<>();

    public Account(String number, String clientId) {
        this.number = number;
        this.clientId = clientId;
    }

    public String getNumber() { return number; }
    public String getClientId() { return clientId; }
    public double getBalance() { return balance; }

    public List<Transaction> getHistory() {
        return new ArrayList<>(history);
    }

    public void deposit(double amount) {
        checkAmount(amount);
        balance += amount;
        history.add(new Transaction(TransactionType.DEPOSIT, amount, "Пополнение", "Пополнение счёта"));
    }

    public void withdraw(double amount, String category) {
        checkAmount(amount);
        checkFunds(amount);
        balance -= amount;
        history.add(new Transaction(TransactionType.WITHDRAW, amount, category, "Оплата: " + category));
    }

    public void transferOut(double amount, String toNumber) {
        checkAmount(amount);
        checkFunds(amount);
        balance -= amount;
        history.add(new Transaction(TransactionType.TRANSFER_OUT, amount, "Перевод", "Перевод на счёт " + toNumber));
    }

    public void transferIn(double amount, String fromNumber) {
        checkAmount(amount);
        balance += amount;
        history.add(new Transaction(TransactionType.TRANSFER_IN, amount, "Перевод", "Перевод со счёта " + fromNumber));
    }

    private void checkAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма должна быть больше нуля");
        }
    }

    private void checkFunds(double amount) {
        if (amount > balance) {
            throw new InsufficientFundsException(number, balance, amount);
        }
    }

    @Override
    public String toString() {
        return number + " | клиент " + clientId + " | баланс " + balance;
    }
}
