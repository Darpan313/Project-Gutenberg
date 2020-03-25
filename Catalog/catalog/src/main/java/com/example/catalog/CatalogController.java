package com.example.catalog;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CatalogController {
    private static final int CAPACITY = 100;
    private Map<String, List<Catalog>> caching = new LinkedHashMap<String, List<Catalog>>(){
        protected boolean removeEldestEntry(Map.Entry<String, List<Catalog>> eldest)
        {
            return size() > CAPACITY;
        }
    };;

    @CrossOrigin(origins = "http://ec2-35-173-247-4.compute-1.amazonaws.com")
    @RequestMapping(value = "/searchResult", method = RequestMethod.GET)
    public ResponseEntity<Object> searchResult(@RequestParam String keyword) {
        List<Catalog> result = null;
        keyword = keyword.toLowerCase();
        if(caching.containsKey(keyword)){
            System.out.println("Fast result");
            result = caching.get(keyword);
        }else {
            CatalogDB db = new CatalogDB();
            result = db.getAllCatalogWithKeyword(keyword);
            caching.put(keyword,result);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
