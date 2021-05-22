package com.test.controller.review;

import com.test.bean.User;
import com.test.bean.product.Points;
import com.test.bean.review.ProductReview;
import com.test.repo.PointsRepo;
import com.test.repo.ReviewRepo;
import com.test.repo.UserRepository;
import com.test.utility.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private GlobalService service;

    @Autowired
    private ReviewRepo reviewRepo;
    @Autowired
    private UserRepository userRepo;


    @GetMapping()
    public ResponseEntity getAll(){
        List<ProductReview> list = reviewRepo.findByOrderByIdAsc();
        return service.getSuccessResponse(list);

    }

    @GetMapping("/product/{id}")
    public ResponseEntity getByProduct(@PathVariable Integer id){
        if(id == null){
            return service.getErrorResponse("Invalid request!");
        }
        List<ProductReview> list = reviewRepo.findByProductId(id);
        return service.getSuccessResponse(list);

    }

    @PostMapping
    public ResponseEntity addReview(@RequestBody ProductReview review){

        if(review.getReview().isEmpty()){
            return service.getErrorResponse("Enter review!");
        }
        if(review.getProductId() == null){
            return service.getErrorResponse("Invalid request!");
        }
        if(Double.compare(review.getReviewCount() , 0)  < 0 || Double.compare(review.getReviewCount() , 5)  > 0  ){
            return service.getErrorResponse("Invalid review!");
        }
        if(review.getUserId() == null  ){
            return service.getErrorResponse("Invalid request!");
        }

        User user = userRepo.getOne(review.getUserId());

        if(user == null  ){
            return service.getErrorResponse("Invalid request!");
        }

        List<ProductReview> list;
        list = reviewRepo.findByUserAndProduct( review.getProductId(),review.getUserId());
        if(list.size() > 0 ){
            return service.getErrorResponse("You can review this product only once!");
        }

        review.setUserId(user.getId());
        review.setUserName(user.getFullName());
        review.setUserImage(user.getUserImage());
        review.setDate(new Date());

        return service.getSuccessResponse(reviewRepo.save(review));

    }

//    @PostMapping("/edit")
//    public ResponseEntity edit(@RequestBody Points point){
//        if(point.getId() == null){
//            return service.getErrorResponse("Invalid request!");
//        }
//        Points db = pointsRepo.getOne(point.getId());
//
//        if(db == null){
//            return service.getErrorResponse("Invalid request!");
//        }
//        if(point.getPoint() != null){
//            db.setPoint(point.getPoint());
//        }
//
//        return service.getSuccessResponse(pointsRepo.save(db));
//
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        if(id == null){
            return service.getErrorResponse("Invalid request!");
        }
        if(!reviewRepo.existsById(id)){
            return service.getErrorResponse("Invalid request!");
        }
        reviewRepo.deleteById(id);
        return service.getSuccessResponse("Delete Successfully!");

    }
}
