package com.javarush.caesar.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import com.javarush.caesar.exception.CipherException;

public class FileManager {
    private static final int BUFFER_SIZE = 64 * 1024;

    public String readFile(String filePath) throws CipherException {
        Path path = Paths.get(filePath);
        try (Reader reader = new InputStreamReader(Files.newInputStream(path), StandardCharsets.UTF_8)) {
            StringBuilder sb = new StringBuilder((int) Math.min(Files.size(path), Integer.MAX_VALUE - 8));
            char[] buf = new char[BUFFER_SIZE];
            int n;
            while ((n = reader.read(buf)) != -1) {
                sb.append(buf, 0, n);
            }
            return sb.toString();
        } catch (IOException e) {
            throw new CipherException("Ошибка чтения файла: " + filePath, e);
        }
    }

    public void writeFile(String content, String filePath) throws CipherException {
        Path path = Paths.get(filePath);
        try (Writer writer = new OutputStreamWriter(
                Files.newOutputStream(path, StandardOpenOption.CREATE_NEW),
                StandardCharsets.UTF_8
        )) {
            writer.write(content);
        } catch (IOException e) {
            throw new CipherException("Ошибка записи файла: " + filePath, e);
        }
    }
}
