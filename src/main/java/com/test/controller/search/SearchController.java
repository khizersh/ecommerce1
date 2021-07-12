package com.test.controller.search;

import com.test.repo.ProductRepo;
import com.test.utility.GlobalService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    ProductRepo productRepo;

    @Autowired
    GlobalService service;


    @GetMapping("/cache/{keyword}")
    public ResponseEntity getResultByKeyword(@PathVariable String keyword){
        return service.getSuccessResponse(productRepo.findByKeywordsContainingIgnoreCase(keyword));
    }

    @GetMapping("/{keyword}")
    public ResponseEntity getResultFromCache(@PathVariable String keyword) throws IOException {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("product.json"))
        {
            Object obj = jsonParser.parse(reader);
            JSONArray productList = (JSONArray) obj;
            JSONArray returnList = new JSONArray();

            for (int i = 0; i < productList.size(); i++) {
               JSONObject pro = (JSONObject) productList.get(i);
               String key = (String) pro.get("keywords");
              if(key != null){
                  if(key.contains(keyword)){
                      returnList.add(productList.get(i));
                  }
              }
            }

            return service.getSuccessResponse(returnList);
        } catch (FileNotFoundException | ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}

