package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Bank {
    private final Map<String, Client> clients = new HashMap<>();
    private final Map<String, Account> accounts = new HashMap<>();
    private int nextClientId = 1;
    private int nextAccountNumber = 1001;

    public Client createClient(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
        Client client = new Client("CL-" + nextClientId++, name.trim());
        clients.put(client.getId(), client);
        return client;
    }

    public Account openAccount(String clientId) {
        if (!clients.containsKey(clientId)) {
            throw new ClientNotFoundException(clientId);
        }
        Account account = new Account("ACC-" + nextAccountNumber++, clientId);
        accounts.put(account.getNumber(), account);
        return account;
    }

    public Account getAccount(String number) {
        Account account = accounts.get(number);
        if (account == null) {
            throw new AccountNotFoundException(number);
        }
        return account;
    }

    public List<Client> getClients() {
        return new ArrayList<>(clients.values());
    }

    public List<Account> getAccountsOf(String clientId) {
        return accounts.values().stream()
                .filter(a -> a.getClientId().equals(clientId))
                .collect(Collectors.toList());
    }

    public void deposit(String number, double amount) {
        getAccount(number).deposit(amount);
    }

    public void pay(String number, double amount, String category) {
        getAccount(number).withdraw(amount, category);
    }

    public void transfer(String fromNumber, String toNumber, double amount) {
        if (fromNumber.equals(toNumber)) {
            throw new IllegalArgumentException("Нельзя перевести деньги на тот же счёт");
        }
        Account from = getAccount(fromNumber);
        Account to = getAccount(toNumber);

        from.transferOut(amount, toNumber);
        to.transferIn(amount, fromNumber);
    }

    public double calculateCashback(String number) {
        return getAccount(number).getHistory().stream()
                .filter(t -> t.getType() == TransactionType.WITHDRAW)
                .mapToDouble(t -> t.getCategory().equalsIgnoreCase("Супермаркеты")
                        ? t.getAmount() * 0.05
                        : t.getAmount() * 0.01)
                .sum();
    }
}
