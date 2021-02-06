package com.test.controller;

import com.test.bean.Employee;
import com.test.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeRepo employeeRepo;


    @GetMapping
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    public ResponseEntity getAll() {
        return ResponseEntity.ok( employeeRepo.findAll());
    }
    @PostMapping
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    public ResponseEntity addEmployee(@RequestBody Employee employee) {
        return ResponseEntity.ok( employeeRepo.save(employee));
    }

    @PutMapping("/{id}")
    public ResponseEntity editEmployee(@PathVariable Integer id , @RequestBody Employee employee){
        if(id == null){
            return new ResponseEntity("Enter id ..", HttpStatus.BAD_GATEWAY);
        }

        Employee employeeDB = employeeRepo.getOne(id);
        if(employee.getEmail() != null){
            employeeDB.setEmail(employee.getEmail());
        }if(employee.getFisrtName() != null){
            employeeDB.setFisrtName(employee.getFisrtName());
        }if(employee.getLastName() != null){
            employeeDB.setLastName(employee.getLastName());
        }

        return ResponseEntity.ok(employeeRepo.save(employeeDB));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteEmployee(@PathVariable Integer id){
        if(id == null){
            return new ResponseEntity("Enter id ..", HttpStatus.BAD_GATEWAY);
        }
        employeeRepo.deleteById(id);

        return ResponseEntity.ok().build();
//        return new ResponseEntity("Delete successfully!",HttpStatus.OK);
    }

}
