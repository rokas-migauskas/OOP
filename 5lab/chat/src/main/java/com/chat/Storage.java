package com.chat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.google.gson.Gson;


public class Storage {
    private static final String FILE_NAME = "chat_data.json";

    public static void saveData(String json) {
        Path path = Paths.get(FILE_NAME);
        try {
            Files.writeString(path, json);
        } catch (IOException e) {
            System.err.println("Error saving data to file: " + e.getMessage());
        }
    }

    public static ChatData loadData() {
        Path path = Paths.get(FILE_NAME);
        if (Files.exists(path)) {
            try {
                String json = Files.readString(path);
                Gson gson = new Gson();
                ChatData chatData = gson.fromJson(json, ChatData.class);
                return chatData;
            } catch (IOException e) {
                System.err.println("Error reading data from file: " + e.getMessage());
            }
        }
        return new ChatData(); // Return an empty ChatData object if the file does not exist
    }
}
