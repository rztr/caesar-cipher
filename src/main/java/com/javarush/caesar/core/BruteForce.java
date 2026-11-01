package com.javarush.caesar.core;

import java.util.HashMap;
import java.util.Map;

public class BruteForce {

    private static final Map<Character, Float> FREQUENCIES = Map.ofEntries(
        Map.entry(' ', 17.5f),
        Map.entry('о', 8.79f),
        Map.entry('е', 6.79f),
        Map.entry('а', 6.40f),
        Map.entry('и', 5.89f),
        Map.entry('н', 5.36f),
        Map.entry('т', 5.05f),
        Map.entry('с', 4.38f),
        Map.entry('р', 3.80f),
        Map.entry('в', 3.63f),
        Map.entry('л', 3.47f),
        Map.entry('к', 2.79f),
        Map.entry('м', 2.56f),
        Map.entry('д', 2.38f),
        Map.entry('п', 2.24f),
        Map.entry('у', 2.09f),
        Map.entry('я', 1.60f),
        Map.entry('ы', 1.52f),
        Map.entry('ь', 1.39f),
        Map.entry('г', 1.35f),
        Map.entry('з', 1.31f),
        Map.entry('б', 1.27f),
        Map.entry('ч', 1.16f),
        Map.entry('й', 0.97f),
        Map.entry('х', 0.77f),
        Map.entry('ж', 0.75f),
        Map.entry('ш', 0.57f),
        Map.entry('ю', 0.51f),
        Map.entry('ц', 0.39f),
        Map.entry('щ', 0.29f),
        Map.entry('э', 0.26f),
        Map.entry('ф', 0.21f),
        Map.entry('ъ', 0.03f),
        Map.entry('ё', 0.01f),
        Map.entry(',', 1.5f),
        Map.entry('.', 1.0f)
    );

    public String decryptByBruteForce(String encryptedText, char[] alphabet) {
        Cipher cipher = new Cipher(alphabet);
        String bestText = encryptedText;
        float bestScore = Float.MAX_VALUE;

        for (int key = 1; key < alphabet.length; key++) {
            String candidate = cipher.decrypt(encryptedText, key);
            float score = score(countCharFrequency(candidate, alphabet));
            if (score < bestScore) {
                bestScore = score;
                bestText = candidate;
            }
        }
        return bestText;
    }

    private float score(Map<Character, Float> frequenciesToScore) {
        float sum = 0f;
        for (Map.Entry<Character, Float> entry : frequenciesToScore.entrySet()) {
            float diff = entry.getValue() - FREQUENCIES.getOrDefault(entry.getKey(), 0f);
            sum += diff * diff;
        }
        return sum;
    }

    private Map<Character, Float> countCharFrequency(String decryptedText, char[] alphabet) {
        Map<Character, Integer> counts = new HashMap<>();
        for (char symbol : alphabet) {
            counts.put(symbol, 0);
        }

        int total = 0;
        for (char c : decryptedText.toCharArray()) {
            if (counts.containsKey(c)) {
                counts.merge(c, 1, Integer::sum);
                total++;
            }
        }

        Map<Character, Float> frequencies = new HashMap<>();
        for (Map.Entry<Character, Integer> entry : counts.entrySet()) {
            float percent = total == 0 ? 0f : entry.getValue() * 100f / total;
            frequencies.put(entry.getKey(), percent);
        }
        return frequencies;
    }
}
