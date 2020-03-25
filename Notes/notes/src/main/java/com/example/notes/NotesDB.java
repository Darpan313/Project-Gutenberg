package com.example.notes;

import com.google.gson.*;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class NotesDB {
    public int addNote(Note note, String keyword) {
        String path = "users/";
        String fileName = "userNotes.json";
        String key = keyword.toLowerCase();
        String comment = note.getNote();
        Gson gson = new Gson();
        if (!Files.exists(Paths.get(path))) {
            try {
                Files.createDirectories(Paths.get(path));
                File f = new File(path + fileName);
                if (!f.exists()) {
                    f.createNewFile();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            File f = new File(path + fileName);
            JsonArray msg = new JsonArray();
            List<KeyWithNotes> keyWithNotes = new ArrayList<>();
            boolean flag = false;
            if (f.length() > 0) {
                JsonParser parser = new JsonParser();
                Object obj = parser.parse(new FileReader(path + "userNotes.json"));
                JsonObject jsonObject = (JsonObject) obj;
                System.out.println(jsonObject.get("Notes").toString());
                JsonArray noteList = (JsonArray) jsonObject.get("Notes");
                for (int i = 0; i < noteList.size(); i++) {
                    JsonObject keysAndNotes = (JsonObject) noteList.get(i);
                    String k = keysAndNotes.get("key").getAsString();
                    if (k.equals(key)) {
                        flag = true;
                        System.out.println("Update list:" + keysAndNotes.get("noteList").toString());
                        List<String> n = gson.fromJson(keysAndNotes.get("noteList"), List.class);
                        n.add(note.getNote());
                        KeyWithNotes temp = new KeyWithNotes(key, n);
                        keyWithNotes.add(temp);
                    } else {
                        System.out.println("Just add:" + keysAndNotes.get("noteList").toString());
                        keyWithNotes.add(gson.fromJson(keysAndNotes, KeyWithNotes.class));
                    }
                }
            }
            if (!flag) {
                List<String> n = new ArrayList<>();
                n.add(note.getNote());
                KeyWithNotes temp = new KeyWithNotes(key, n);
                keyWithNotes.add(temp);
            }
            HashMap<String, List<KeyWithNotes>> map = new HashMap<>();
            map.put("Notes", keyWithNotes);
            FileWriter fw = new FileWriter(f, false);
            JsonWriter jw = new JsonWriter(fw);
            gson.toJson(map, HashMap.class, jw);
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<String> findNote(String key) {
        List<String> res = new ArrayList<>();
        key = key.toLowerCase();
        Gson gson = new Gson();
        if (new File("users/userNotes.json").exists()) {
            JsonParser parser = new JsonParser();
            Object obj = null;
            try {
                obj = parser.parse(new FileReader("users/userNotes.json"));
                JsonObject jsonObject = (JsonObject) obj;
                System.out.println(jsonObject.get("Notes").toString());
                JsonArray noteList = (JsonArray) jsonObject.get("Notes");
                for (int i = 0; i < noteList.size(); i++) {
                    JsonObject keysAndNotes = (JsonObject) noteList.get(i);
                    String k = keysAndNotes.get("key").getAsString();
                    if (k.equals(key)) {
                        System.out.println("Update list:" + keysAndNotes.get("noteList").toString());
                        res = gson.fromJson(keysAndNotes.get("noteList"), List.class);
                        break;
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return res;
    }
}
