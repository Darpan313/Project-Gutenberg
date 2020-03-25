package com.example.notes;

import java.util.List;

public class KeyWithNotes {
    String key;
    List<String> noteList;
    public KeyWithNotes(String key,List<String> noteList){
        this.key = key;
        this.noteList = noteList;
    }
    public List<String> getNoteList() {
        return noteList;
    }

    public void addNote(String note) {
        this.noteList.add(note);
    }
    public void setKey(String key){
        this.key = key;
    }
    public String getKey(){
        return this.key;
    }
}
