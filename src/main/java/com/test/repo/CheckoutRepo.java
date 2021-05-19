package com.test.repo;

import com.test.bean.checkout.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckoutRepo extends JpaRepository<Checkout , Integer> {
}
