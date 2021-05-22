package com.test.repo;

import com.test.bean.checkout.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckoutRepo extends JpaRepository<Checkout , Integer> {
    List<Checkout> findByOrderByIdAsc();
}
