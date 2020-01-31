package com.example.gpsactivity.manager;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class DataFileManager {
    private File file;
    private FileWriter fileWriter;
    private FileOutputStream stream;

    public DataFileManager(@NonNull String path) throws IOException {
        file = new File(path);

        if (!file.exists() || !file.isFile()) {
            file.createNewFile();
        }

        stream = new FileOutputStream(file, true);
    }

    public void close() throws IOException {
        stream.flush();
        stream.close();
    }

    public void write(String string) throws IOException {
        String line = string + System.lineSeparator();
        stream.write(line.getBytes());
    }
}
