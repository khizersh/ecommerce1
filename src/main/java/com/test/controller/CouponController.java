package com.test.controller;

import com.test.bean.coupon.Coupon;
import com.test.repo.CouponRepo;
import com.test.utility.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/coupon")
public class CouponController {

    @Autowired
    private CouponRepo couponRepo;
    @Autowired
    private GlobalService service;

    @GetMapping
    public ResponseEntity getAll(){
        return service.getSuccessResponse(couponRepo.findAll());
    }

    @PostMapping
    public ResponseEntity addCoupon(@RequestBody Coupon coupon) throws ParseException {

        if(coupon.getPercentageOff() <= 0){
            return service.getErrorResponse("Invalid percentage!");
        }
        if(coupon.getTitle().isEmpty()){
            return service.getErrorResponse("Enter title!");
        }
        if(coupon.getExpiryDate() == null){
            return service.getErrorResponse("Invalid date!");
        }
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        Date expiry = sdformat.parse(coupon.getExpiryDate());
        coupon.setStartingDate(sdformat.format(currentDate));


        String code = generateRandomDigits(7);
        coupon.setCode(code);


        if(currentDate.compareTo(expiry) > 0) {
          return service.getErrorResponse("Invalid date");
        }

        coupon.setExpiryDate(coupon.getExpiryDate());
        coupon.setExpired(false);


        return service.getSuccessResponse( couponRepo.save(coupon));

    }



    @PostMapping("/edit")
    public ResponseEntity editCoupon(@RequestBody Coupon coupon) throws ParseException {
        Coupon db = couponRepo.getOne(coupon.getId());
        if(db == null){
            return service.getErrorResponse("Invalid request!");
        }
        if(coupon.getTitle() != null){
           db.setTitle(coupon.getTitle());
        }
        if(coupon.getPercentageOff() != null){
        if(coupon.getPercentageOff() > 0){
            db.setPercentageOff(coupon.getPercentageOff());
        }
        }

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();

        if(coupon.getExpiryDate() != null){
            Date expiry = sdformat.parse(coupon.getExpiryDate());
            if(currentDate.compareTo(expiry) < 0) {
                db.setExpiryDate(coupon.getExpiryDate());
            }
        }


        if(coupon.getExpired() != null){
            db.setExpired(coupon.getExpired());
        }





        return service.getSuccessResponse( couponRepo.save(db));

    }


    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Integer id){
        if(id == null){
            return service.getErrorResponse("Invalid request!");
        }
        Coupon coupon = couponRepo.getOne(id);
        if(coupon == null){
            return service.getErrorResponse("invalid request!");
        }
        return service.getSuccessResponse(coupon);
    }

  @PostMapping("/validate/{code}")
    public ResponseEntity validateCoupon(@PathVariable String code) throws ParseException {
        if(code == null){
            return service.getErrorResponse("Invalid request!");
        }

        Coupon coupon = null;

      List<Coupon> list = couponRepo.findAll();
      for (Coupon coupon1 : list) {
          if(coupon1.getCode().equals(code)){
              coupon = coupon1;
          }
      }
        if(coupon == null){
            return service.getErrorResponse("coupon not found!");
        }
      SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
      Date expiry = sdformat.parse(coupon.getExpiryDate());
      Date currentDate = new Date();
      if(coupon.getExpired()){
          return service.getErrorResponse("Expired");
      }
      if(currentDate.compareTo(expiry) > 0) {
          coupon.setExpired(true);
          couponRepo.save(coupon);
          return service.getErrorResponse("Expired");
      }

        return service.getSuccessResponse(coupon);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Integer id){
        if(id == null){
            return service.getErrorResponse("Invalid request!");
        }
        Coupon coupon = couponRepo.getOne(id);
        if(!couponRepo.existsById(id)){
            return service.getErrorResponse("invalid request!");
        }
        couponRepo.deleteById(id);
        return service.getSuccessResponse("Success");
    }


    public  String generateRandomDigits(int n) {
        int m = (int) Math.pow(10, n - 1);
        int code = m + new Random().nextInt(9 * m);

        if(couponRepo.findByCode( "COUP-" + code) != null){
            generateRandomDigits(n);
        }
        return  "COU-" + code;
    }

}
