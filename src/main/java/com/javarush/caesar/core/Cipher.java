package com.javarush.caesar.core;

import java.util.HashMap;
import java.util.Map;

public class Cipher {

    private final char[] alphabet;
    private final int alphabetLength;
    private final Map<Character, Integer> indexOf = new HashMap<>();

    public Cipher(char[] alphabet) {
        this.alphabet = alphabet;
        this.alphabetLength = alphabet.length;
        for (int i = 0; i < alphabetLength; i++) {
            indexOf.put(alphabet[i], i);
        }
    }

    public String encrypt(String text, int shift) {
        StringBuilder encrypted = new StringBuilder(text.length());
        for (char c : text.toCharArray()) {
            Integer originalIndex = indexOf.get(c);
            if (originalIndex != null) {
                int newIndex = (originalIndex + shift) % alphabetLength;
                encrypted.append(alphabet[newIndex]);
            } else {
                encrypted.append(c);
            }
        }
        return encrypted.toString();
    }

    public String decrypt(String encryptedText, int shift) {
        StringBuilder decrypted = new StringBuilder(encryptedText.length());
        for (char c : encryptedText.toCharArray()) {
            Integer originalIndex = indexOf.get(c);
            if (originalIndex != null) {
                int newIndex = (originalIndex - shift + alphabetLength) % alphabetLength;
                decrypted.append(alphabet[newIndex]);
            } else {
                decrypted.append(c);
            }
        }
        return decrypted.toString();
    }
}
