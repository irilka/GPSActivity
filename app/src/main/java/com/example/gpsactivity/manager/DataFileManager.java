package com.example.gpsactivity.manager;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DataFileManager {
    private File file;
    private FileWriter fileWriter;
    private FileOutputStream outputStream;
    private Scanner scanner;

    public DataFileManager(@NonNull String path) throws IOException {
        file = new File(path);

        if (!file.exists() || !file.isFile()) {
            file.createNewFile();
        }

        outputStream = new FileOutputStream(file, true);
        scanner = new Scanner(file);
    }

    public void close() throws IOException {
        outputStream.flush();
        outputStream.close();
    }

    public void write(String string) throws IOException {
        String line = string + System.lineSeparator();
        outputStream.write(line.getBytes());
    }

    public ArrayList<String> read() {
        ArrayList<String> data = new ArrayList<>();

        while(scanner.hasNextLine()) {
            String row = scanner.nextLine();
            data.add(row);
        }

        return data;
    }
}
