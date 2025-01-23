package com.terminal.pradius;

import android.content.Context;
import android.os.Environment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileManager {
    private final Context context;
    private final String configFile = "config.txt";
    private final String defaultUrl = "http://wt.pradius.local";

    public FileManager(Context context) {
        this.context = context;
    }

    public void writeFile() {
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File myFile = new File(context.getExternalFilesDir(null), configFile);
                if (!myFile.exists() || myFile.length() == 0) {
                    try (FileOutputStream outputStream = new FileOutputStream(myFile)) {
                        outputStream.write(defaultUrl.getBytes());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readFile() {
        File myFile = new File(context.getExternalFilesDir(null), configFile);

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(myFile)))) {
            String line = bufferedReader.readLine();
            return (line != null) ? line : defaultUrl;
        } catch (IOException e) {
            e.printStackTrace();
            return defaultUrl;
        }
    }
}