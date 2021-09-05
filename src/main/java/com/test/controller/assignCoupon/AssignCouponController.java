package com.test.controller.assignCoupon;

import com.test.bean.User;
import com.test.bean.coupon.AssignCoupon;
import com.test.bean.coupon.Coupon;
import com.test.repo.AssignCouponRepo;
import com.test.repo.CouponRepo;
import com.test.repo.UserRepository;
import com.test.utility.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assign-coupon")
public class AssignCouponController {

    @Autowired
    private GlobalService service;
    @Autowired
    private AssignCouponRepo assignRepo;
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CouponRepo couponRepo;

    @GetMapping("/user/{id}")
    public ResponseEntity getByUserId(@PathVariable Long id){
        if(id == null){
            return service.getErrorResponse("Invalid response");
        }
        try {
       return service.getSuccessResponse(assignRepo.findByUserId(id));

        }catch (Exception e){
            return service.getErrorResponse("Something went wrong!");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){

        try{

        if(id == null){
            return service.getErrorResponse("Invalid response");
        }
        assignRepo.deleteById(id);
       return service.getSuccessResponse("Delete successfully!");
        }catch (Exception e){
            e.printStackTrace();
        }
        return service.getErrorResponse("Something went wrong!");
    }

    @PostMapping("/user/assign")
    public ResponseEntity assignCoupon(@RequestBody AssignCoupon coup){

        if(coup.getUserId() == null){
            return service.getErrorResponse("Invalid request");
        }
        if(coup.getCouponId() == null){
            return service.getErrorResponse("Invalid response");
        }

        User user = userRepo.getOne(coup.getUserId());
        if(user == null){
            return service.getErrorResponse("Invalid request");
        }
        Coupon coupon = couponRepo.getOne(coup.getCouponId());
        if(coupon == null){
            return service.getErrorResponse("Invalid request");
        }

        coup.setCouponTitle(coupon.getTitle());
        coup.setUserName(user.getFullName());
        coup.setUsed(false);
        try{
        coup.setPercentageOff(coupon.getPercentageOff());
       return service.getSuccessResponse(assignRepo.save(coup));

        }catch (Exception e){
            e.printStackTrace();
        }
        return service.getErrorResponse("Something went wrong!");
    }


}
