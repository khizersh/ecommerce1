package com.test.repo;

import java.util.*;
import com.test.bean.product.AttributePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributePriceRepo extends JpaRepository<AttributePrice, Integer> {
    public List<AttributePrice> findByProductId(Integer id);
}
