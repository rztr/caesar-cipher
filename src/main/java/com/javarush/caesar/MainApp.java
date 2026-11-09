package com.javarush.caesar;

import com.javarush.caesar.core.BruteForce;
import com.javarush.caesar.core.Cipher;
import com.javarush.caesar.exception.CipherException;
import com.javarush.caesar.service.FileManager;
import com.javarush.caesar.service.KeyNormalizer;
import com.javarush.caesar.service.Validator;

import java.util.Scanner;

public class MainApp {

    private static final char[] ALPHABET = {
            'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п',
            'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я',
            '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '
    };

    private final Scanner scanner = new Scanner(System.in);
    private final FileManager fileManager = new FileManager();
    private final KeyNormalizer keyNormalizer = new KeyNormalizer();
    private final Validator validator = new Validator();
    private final Cipher cipher = new Cipher(ALPHABET);
    private final BruteForce bruteForce = new BruteForce();

    public static void main(String[] args) {
        new MainApp().run();
    }

    private void run() {
        System.out.println("Шифр Цезаря: шифрование и расшифровка файлов");
        while (true) {
            printMenu();
            String choice = ask("Выберите действие: ");
            try {
                switch (choice) {
                    case "1":
                        encryptFile();
                        break;
                    case "2":
                        decryptFile();
                        break;
                    case "3":
                        bruteForceFile();
                        break;
                    case "0":
                    case "exit":
                        System.out.println("До свидания!");
                        return;
                    default:
                        System.out.println("Нет такого пункта: " + choice);
                }
            } catch (CipherException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("1. Зашифровать файл");
        System.out.println("2. Расшифровать файл с ключом");
        System.out.println("3. Взломать перебором ключей");
        System.out.println("0. Выход (или exit)");
    }

    private void encryptFile() throws CipherException {
        String file = ask("Введите имя файла для шифрования: ");
        String resultFile = ask("Введите имя файла для результата: ");
        int key = Integer.parseInt(ask("Введите ключ: "));

        if (!validator.isFileExists(file)) {
            throw new CipherException("Файл не найден: " + file);
        }
        if (validator.isFileExists(resultFile)) {
            throw new CipherException("Файл результата уже существует: " + resultFile);
        }
        if (!validator.isValidKey(key, ALPHABET)) {
            key = keyNormalizer.normalize(key, ALPHABET.length);
        }

        String text = fileManager.readFile(file);
        String encryptedText = cipher.encrypt(text, key);
        fileManager.writeFile(encryptedText, resultFile);
    }

    private void decryptFile() throws CipherException {
        String file = ask("Введите имя файла для расшифровки: ");
        String resultFile = ask("Введите имя файла для результата: ");
        int key = Integer.parseInt(ask("Введите ключ: "));

        if (!validator.isFileExists(file)) {
            throw new CipherException("Файл не найден: " + file);
        }
        if (validator.isFileExists(resultFile)) {
            throw new CipherException("Файл результата уже существует: " + resultFile);
        }
        if (!validator.isValidKey(key, ALPHABET)) {
            key = keyNormalizer.normalize(key, ALPHABET.length);
        }

        String text = fileManager.readFile(file);
        String decryptedText = cipher.decrypt(text, key);
        fileManager.writeFile(decryptedText, resultFile);
    }

    private void bruteForceFile() throws CipherException {
        String file = ask("Введите имя файла для расшифровки: ");
        String resultFile = ask("Введите имя файла для результата: ");

        if (!validator.isFileExists(file)) {
            throw new CipherException("Файл не найден: " + file);
        }
        if (validator.isFileExists(resultFile)) {
            throw new CipherException("Файл результата уже существует: " + resultFile);
        }

        String text = fileManager.readFile(file);
        String decryptedText = bruteForce.decryptByBruteForce(text, ALPHABET);
        fileManager.writeFile(decryptedText, resultFile);
    }

    private String ask(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
