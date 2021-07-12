package com.test.controller;

import com.test.bean.sections.ProductSections;
import com.test.dto.ProductDto;
import com.test.dto.ProductSectionDto;
import com.test.repo.ProductRepo;
import com.test.repo.SectionItemsRepo;
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
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/section")
public class SectionController {

    @Autowired
    private GlobalService service;

    @Autowired
    private SectionRepo sectionRepo;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private SectionItemsRepo itemsRepo;

    @Autowired
    private SectionService sectionService;

    @GetMapping("/cache")
    public ResponseEntity getAll(){

        List<ProductSectionDto> list = new ArrayList<>();

        return service.getSuccessResponse(sectionService.getSections());
    }



    @GetMapping()
    public ResponseEntity getSectionCache() throws IOException {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("section.json"))
        {
            Object obj = jsonParser.parse(reader);
            JSONArray sectionList = (JSONArray) obj;
            return service.getSuccessResponse(sectionList);
        } catch (FileNotFoundException | ParseException e) {
            e.printStackTrace();
        }

        return null;
    }



    @PostMapping("/cache")
    public ResponseEntity addSectionCache() throws IOException {

       List<ProductSectionDto> list =  sectionService.getSections();
        JSONArray sectionList = new JSONArray();
        for (ProductSectionDto i : list) {
            JSONObject section = new JSONObject();
            section.put("id" , i.getId());
            section.put("title" , i.getTitle());
            section.put("productList",sectionService.convertIntoJson(i.getProductList()));
            sectionList.add(section);
            try (FileWriter file = new FileWriter("section.json")) {
                file.write(sectionList.toJSONString());
                file.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return service.getSuccessResponse("successfully write!");

    }



    @GetMapping("/jsonn")
    public ResponseEntity getJson(@RequestParam String data) throws IOException {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("section.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray employeeList = (JSONArray) obj;
            System.out.println(employeeList);

            //Iterate over employee array
//          employeeList.forEach( emp ->  {
//
//              parseEmployeeObject( (JSONObject) emp );
//          } );
            return service.getSuccessResponse(employeeList);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteSection(@PathVariable Integer id){


        if(id != null){
            if(sectionRepo.existsById(id)){
                sectionRepo.deleteById(id);
                return service.getSuccessResponse("Deleted successfully!");
            }
        }
        return service.getSuccessResponse("Not found!");
    }



    @PostMapping
    public ResponseEntity addSection(@RequestBody ProductSections pro){

        if(pro.getTitle().isEmpty()){
            return service.getErrorResponse("Enter title!");
        }

        return service.getSuccessResponse(sectionRepo.save(pro));
    }

    @PutMapping
    public ResponseEntity editSection(@RequestBody ProductSections pro){

            if(pro.getId() == null){
                return service.getErrorResponse("Invalid request!");
            }
            ProductSections db = sectionRepo.getOne(pro.getId());
        if(!pro.getTitle().isEmpty()){
            db.setTitle(pro.getTitle());
        }

            return service.getSuccessResponse(sectionRepo.save(db));
        }




    private JSONObject parseEmployeeObject(JSONObject employee)
    {
        //Get employee object within list
        JSONObject employeeObject = (JSONObject) employee.get("employee");

        return employeeObject;
//        //Get employee first name
//        String firstName = (String) employeeObject.get("firstName");
//        System.out.println(firstName);
//
//        //Get employee last name
//        String lastName = (String) employeeObject.get("lastName");
//        System.out.println(lastName);
//
//        //Get employee website name
//        String website = (String) employeeObject.get("website");
//        System.out.println(website);
    }

}
