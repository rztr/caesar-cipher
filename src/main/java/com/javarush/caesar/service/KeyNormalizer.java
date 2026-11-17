package com.javarush.caesar.service;

public class KeyNormalizer {
    public int normalize(int key, int alphabetLength) {
        return Math.floorMod(key, alphabetLength);
    }
}
