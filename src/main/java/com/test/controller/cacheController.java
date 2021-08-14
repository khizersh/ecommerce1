package com.test.controller;

import com.test.bean.product.Product;
import com.test.dto.ProductDto;
import com.test.dto.ProductSectionDto;
import com.test.repo.ProductRepo;
import com.test.repo.SectionRepo;
import com.test.service.ProductService;
import com.test.service.SectionService;
import com.test.utility.GlobalService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/cache")
@RestController
public class cacheController {
    @Autowired
    private SectionRepo sectionRepo;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private SectionService sectionService;
    @Autowired
    private GlobalService service;


    @PostMapping("/product")
    public ResponseEntity addProductCache() throws IOException {

        JSONArray productList = new JSONArray();
        for (Product i:productRepo.findByOrderByIdAsc() ) {
            ProductDto pro = productService.productDetailFull(i);
            productList.add(sectionService.convertProductIntoJson(pro));
        }

        try (FileWriter file = new FileWriter("product.json")) {
            file.write(productList.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return service.getSuccessResponse("successfully write!");

    }

    @GetMapping("/product")
    public ResponseEntity getProductCache() throws IOException {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("product.json"))
        {
            Object obj = jsonParser.parse(reader);
            JSONArray productList = (JSONArray) obj;
            return service.getSuccessResponse(productList);
        } catch (FileNotFoundException | ParseException e) {
            e.printStackTrace();
        }

        return null;
    }



}
