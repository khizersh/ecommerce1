package com.test.repo;

import com.test.bean.product_attribute.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductAttributeRepo extends JpaRepository<ProductAttribute , Integer> {

    @Query(value = "select * from product_attribute where p_id = ?1",nativeQuery = true)
    List<ProductAttribute> findByPid(int pid);
}
