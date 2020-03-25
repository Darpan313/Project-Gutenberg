package com.example.notes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NotesController {
    //addNotes
    //returnNotes
    private static String note;
    @CrossOrigin(origins = "http://ec2-35-173-247-4.compute-1.amazonaws.com")
    @RequestMapping(value = "/addNote",method = RequestMethod.POST)
    public ResponseEntity<Object>addNote(@RequestBody Note note, @RequestParam String key){
        System.out.println(key+";"+note.getNote());
        NotesDB DB = new NotesDB();
        DB.addNote(note,key);
        return new ResponseEntity<Object>("Successful", HttpStatus.OK);
    }
    @CrossOrigin(origins = "http://ec2-35-173-247-4.compute-1.amazonaws.com")
    @RequestMapping(value = "/findNote",method = RequestMethod.GET)
    public ResponseEntity<Object>findNote(@RequestParam String key){
        NotesDB DB = new NotesDB();
        List<String> result = DB.findNote(key);
        return new ResponseEntity<Object>(result, HttpStatus.OK);
    }
}
