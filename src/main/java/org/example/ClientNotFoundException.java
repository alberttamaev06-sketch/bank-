package org.example;

public class ClientNotFoundException extends BankException {
    public ClientNotFoundException(String clientId) {
        super("Клиент не найден: " + clientId);
    }
}
