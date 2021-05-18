package com.test.controller;

import com.test.bean.product.Points;
import com.test.repo.PointsRepo;
import com.test.utility.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/points")
public class BulletPointsController {

    @Autowired
    private GlobalService service;

    @Autowired
    private PointsRepo pointsRepo;


    @GetMapping("/product/{id}")
    public ResponseEntity getByProduct(@PathVariable Integer id){
        if(id == null){
            return service.getErrorResponse("Invalid request!");
        }
        return service.getSuccessResponse(pointsRepo.findByProductId(id));

    }

    @PostMapping
    public ResponseEntity addPoint(@RequestBody List<Points> point){

        for (Points points : point) {
            if(points.getPoint() == null){
                return service.getErrorResponse("Invalid request!");
            }
            if(points.getProductId() == null){
                return service.getErrorResponse("Invalid request!");
            }
            pointsRepo.save(points);
        }



        return service.getSuccessResponse("Successfully added!");

    }

    @PostMapping("/edit")
    public ResponseEntity edit(@RequestBody Points point){
        if(point.getId() == null){
            return service.getErrorResponse("Invalid request!");
        }
        Points db = pointsRepo.getOne(point.getId());

        if(db == null){
            return service.getErrorResponse("Invalid request!");
        }
        if(point.getPoint() != null){
            db.setPoint(point.getPoint());
        }

        return service.getSuccessResponse(pointsRepo.save(db));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity edit(@PathVariable Integer id){
        if(id == null){
            return service.getErrorResponse("Invalid request!");
        }


        if(!pointsRepo.existsById(id)){
            return service.getErrorResponse("Invalid request!");
        }


        pointsRepo.deleteById(id);
        return service.getSuccessResponse("Delete Successfully!");

    }
}
