package com.example.searchlog;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;


@RestController
public class SaveLog {
    private static String time;
    private static String keyword;
    private static final Logger logger = LogManager.getLogger(SaveLog.class);

    @CrossOrigin(origins = "http://ec2-35-173-247-4.compute-1.amazonaws.com")
    @RequestMapping(value = "/saveLog",method = RequestMethod.POST)
    public ResponseEntity<Object> saveSearchLog(@RequestBody Info i){
        File f = new File("logs/app.log");
        int count = 0;
        if(f.exists()){
            try {
                BufferedReader br = new BufferedReader(new FileReader(f));
                String line = br.readLine();
                while(line!=null){
                    if(line.contains("Keyword:"+i.getKeyword())){
                        count ++;
                    }
                    line = br.readLine();
                }
                br.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.info("Time:"+i.getTime()+","+"Keyword:"+i.getKeyword()+",Frequency:"+(count+1));
        return new ResponseEntity<>("Successful", HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://ec2-35-173-247-4.compute-1.amazonaws.com")
    @RequestMapping(value = "/seeLogs/gtbrg.log",method = RequestMethod.GET)
    public ResponseEntity<Object> seeLogs(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        InputStreamResource resource = null;
        try {
            resource = new InputStreamResource(new FileInputStream("logs/app.log"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(new File("logs/app.log").length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }
}
