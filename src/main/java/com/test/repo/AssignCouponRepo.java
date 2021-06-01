package com.test.repo;

import com.test.bean.coupon.AssignCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignCouponRepo extends JpaRepository<AssignCoupon, Long> {

    List<AssignCoupon> findByUserId(Long id);
}
