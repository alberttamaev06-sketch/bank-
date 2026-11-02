package org.example;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Bank bank = new Bank();

        while (true) {
            printMenu();
            String choice = sc.nextLine().trim();
            if (choice.equals("0")) {
                System.out.println("До свидания!");
                return;
            }
            try {
                handle(choice, sc, bank);
            } catch (BankException | IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private static void handle(String choice, Scanner sc, Bank bank) {
        switch (choice) {
            case "1" -> {
                System.out.print("Имя клиента: ");
                Client c = bank.createClient(sc.nextLine());
                System.out.println("Создан клиент: " + c);
            }
            case "2" -> {
                System.out.print("ID клиента (например CL-1): ");
                Account a = bank.openAccount(sc.nextLine().trim());
                System.out.println("Открыт счёт: " + a);
            }
            case "3" -> {
                System.out.print("Номер счёта: ");
                String number = sc.nextLine().trim();
                bank.deposit(number, readAmount(sc));
                System.out.println("Готово. " + bank.getAccount(number));
            }
            case "4" -> {
                System.out.print("Номер счёта: ");
                String number = sc.nextLine().trim();
                double amount = readAmount(sc);
                System.out.print("Категория (например Супермаркеты, Кафе): ");
                String category = sc.nextLine().trim();
                bank.pay(number, amount, category);
                System.out.println("Готово. " + bank.getAccount(number));
            }
            case "5" -> {
                System.out.print("Со счёта: ");
                String from = sc.nextLine().trim();
                System.out.print("На счёт: ");
                String to = sc.nextLine().trim();
                bank.transfer(from, to, readAmount(sc));
                System.out.println("Перевод выполнен.");
            }
            case "6" -> {
                System.out.print("Номер счёта: ");
                List<Transaction> history = bank.getAccount(sc.nextLine().trim()).getHistory();
                if (history.isEmpty()) {
                    System.out.println("Операций пока нет.");
                } else {
                    history.forEach(System.out::println);
                }
            }
            case "7" -> {
                System.out.print("Номер счёта: ");
                double cashback = bank.calculateCashback(sc.nextLine().trim());
                System.out.println("Кэшбэк: " + cashback);
            }
            case "8" -> {
                List<Client> clients = bank.getClients();
                if (clients.isEmpty()) {
                    System.out.println("Клиентов пока нет.");
                }
                for (Client c : clients) {
                    System.out.println(c);
                    bank.getAccountsOf(c.getId()).forEach(a -> System.out.println("   " + a));
                }
            }
            default -> System.out.println("Неизвестная команда");
        }
    }

    private static double readAmount(Scanner sc) {
        System.out.print("Сумма: ");
        try {
            return Double.parseDouble(sc.nextLine().trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("сумма должна быть числом");
        }
    }

    private static void printMenu() {
        System.out.println("\n=== T-Mini Bank ===");
        System.out.println("1. Создать клиента");
        System.out.println("2. Открыть счёт");
        System.out.println("3. Пополнить счёт");
        System.out.println("4. Оплатить (с категорией)");
        System.out.println("5. Перевод между счетами");
        System.out.println("6. История операций");
        System.out.println("7. Кэшбэк");
        System.out.println("8. Клиенты и счета");
        System.out.println("0. Выход");
        System.out.print("Выбор: ");
    }
}
