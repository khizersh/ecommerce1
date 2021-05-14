package com.test.repo;

import java.util.*;
import com.test.bean.product.AttributePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface AttributePriceRepo extends JpaRepository<AttributePrice, Integer> {
    public List<AttributePrice> findByProductId(Integer id);


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from attribute_price where product_id = ?1",nativeQuery = true)
     void deleteByProductId(Integer id);
}
