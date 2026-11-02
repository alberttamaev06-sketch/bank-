package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final TransactionType type;
    private final double amount;
    private final String category;
    private final String description;
    private final LocalDateTime time;

    public Transaction(TransactionType type, double amount, String category, String description) {
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.time = LocalDateTime.now();
    }

    public TransactionType getType() { return type; }
    public double getAmount() { return amount; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public LocalDateTime getTime() { return time; }

    @Override
    public String toString() {
        return time.format(FORMAT) + " | " + type + " | " + amount + " | " + category + " | " + description;
    }
}
