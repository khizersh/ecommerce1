package com.test.repo;

import com.test.bean.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order , Integer> {

    List<Order> findByUserId(Long id);

    List<Order> findByOrderByIdDesc();

    Order findByCheckoutId(Integer id);
}
