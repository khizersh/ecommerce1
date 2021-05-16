package com.test.repo;

import com.test.bean.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepo extends JpaRepository<Coupon , Integer> {

     @Query(value = "select from coupon where code = ?1",nativeQuery = true)
     Coupon findByCode(String code);
}
