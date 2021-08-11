package com.test.controller.review;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.bean.User;
import com.test.bean.banner.HomePageBanner;
import com.test.bean.product.Points;
import com.test.bean.product.Product;
import com.test.bean.review.ProductReview;
import com.test.repo.PointsRepo;
import com.test.repo.ProductRepo;
import com.test.repo.ReviewRepo;
import com.test.repo.UserRepository;
import com.test.service.AmazonClient;
import com.test.utility.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private AmazonClient amazonClient;


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
    public ResponseEntity addReview(@RequestParam String reviewString ,  @RequestParam(value = "file" , required = false) MultipartFile file) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        ProductReview review = mapper.readValue(reviewString, ProductReview.class);
        if(review.getReview().isEmpty()){
            return service.getErrorResponse("Enter review!");
        }
        if(file != null){
            review.setReviewImage(amazonClient.uploadFile(file));
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
        Product pro = productRepo.getOne(review.getProductId());
        if(pro == null){
            return service.getErrorResponse("Invalid request!");
        }
        List<ProductReview> list = reviewRepo.findByProductId(pro.getId());
        Double totalReview = 0.0 , avg = 0.0;
        for (ProductReview i : list) {
            totalReview += i.getReviewCount();
        }
        if(list.size() > 0){
        avg = Math.ceil(totalReview / list.size());
        pro.setReview(avg);
        pro.setReviewCount(list.size() + 1);
        }else{
            avg = Math.ceil(totalReview / list.size());
            pro.setReview(review.getReviewCount());
            pro.setReviewCount(1);
        }
        productRepo.save(pro);


        if(reviewRepo.findByUserAndProduct( review.getProductId(),review.getUserId()).size() > 0 ){
            return service.getErrorResponse("You can review this product only once!");
        }

        review.setProductImage(pro.getImageList().get(0).getImage());
        review.setUserId(user.getId());
        review.setUserName(user.getFullName());
        review.setUserImage(user.getUserImage());
        review.setDate(new Date());

        return service.getSuccessResponse(reviewRepo.save(review));

    }

    @PostMapping("/edit")
    public ResponseEntity editReview(@RequestParam String reviewString ,  @RequestParam(value = "file" , required = false) MultipartFile file) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        ProductReview review = mapper.readValue(reviewString, ProductReview.class);

        if(review.getId() == null){
            return service.getErrorResponse("Enter review!");
        }

        ProductReview db = reviewRepo.getOne(review.getId());
        if(db == null){
            return service.getErrorResponse("Invalid review!");
        }
        if(file != null){
            db.setReviewImage(amazonClient.uploadFile(file));
        }
        if(!review.getReview().isEmpty()){
           db.setReview(review.getReview());
        }

        if(review.getReviewCount() > 0){
            if(Double.compare(review.getReviewCount() , 0)  < 0 || Double.compare(review.getReviewCount() , 5)  > 0  ){
                return service.getErrorResponse("Invalid review!");
            }else{

           db.setReviewCount(review.getReviewCount());
            }
        }
        if(review.getProductId() == null){
            return service.getErrorResponse("Invalid request!");
        }


        if(review.getUserId() == null  ){
            return service.getErrorResponse("Invalid request!");
        }

        Product pro = productRepo.getOne(review.getProductId());
        if(pro == null){
            return service.getErrorResponse("Invalid request!");
        }

        db.setProductImage(pro.getImageList().get(0).getImage());
        reviewRepo.save(db);


        List<ProductReview> list = reviewRepo.findByProductId(pro.getId());
        Double totalReview = 0.0 , avg = 0.0;
        for (ProductReview i : list) {
            totalReview += i.getReviewCount();
        }

            avg = Math.ceil(totalReview / list.size());
            pro.setReview(avg);
            pro.setReviewCount(list.size());

        productRepo.save(pro);


        return service.getSuccessResponse("edit successfully!");


    }


  @PostMapping("/admin")
    public ResponseEntity addByAdmin(@RequestParam String reviewString  ,@RequestParam MultipartFile image ,@RequestParam(value = "file" , required = false) MultipartFile file) throws JsonProcessingException {



      ObjectMapper mapper = new ObjectMapper();
      ProductReview review = mapper.readValue(reviewString, ProductReview.class);

      if(review.getReview().isEmpty()){
          return service.getErrorResponse("Enter review!");
      }
      if(review.getDate() == null){
          return service.getErrorResponse("Enter date!");
      }
      if(file != null){
          review.setReviewImage(amazonClient.uploadFile(file));
      }
      if(review.getProductId() == null){
          return service.getErrorResponse("Invalid request!");
      }
      if(review.getProductId() == null){
          return service.getErrorResponse("Invalid request!");
      }
      if(Double.compare(review.getReviewCount() , 0)  < 0 || Double.compare(review.getReviewCount() , 5)  > 0  ){
          return service.getErrorResponse("Invalid review!");
      }
      if(review.getUserName().isEmpty()){
          return service.getErrorResponse("Invalid name!");
      }
      String img = amazonClient.uploadFile(image);
      review.setUserImage(img);

      Product pro = productRepo.getOne(review.getProductId());
      if(pro == null){
          return service.getErrorResponse("Invalid request!");
      }
      List<ProductReview> list = reviewRepo.findByProductId(pro.getId());
      Double totalReview = 0.0 , avg = 0.0;
      for (ProductReview i : list) {
          totalReview += i.getReviewCount();
      }
      if(list.size() > 0){
          avg = Math.ceil(totalReview / list.size());
          pro.setReview(avg);
          pro.setReviewCount(list.size() + 1);
      }else{
          avg = Math.ceil(totalReview / list.size());
          pro.setReview(review.getReviewCount());
          pro.setReviewCount(1);
      }
      productRepo.save(pro);


      long id = 0;
      review.setProductImage(pro.getImageList().get(0).getImage());
      review.setUserId(id);
      review.setUserName(review.getUserName());
      review.setDate(review.getDate());

      return service.getSuccessResponse(reviewRepo.save(review));


    }

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
