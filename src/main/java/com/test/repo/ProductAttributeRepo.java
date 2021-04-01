package com.test.repo;

import com.test.bean.product_attribute.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductAttributeRepo extends JpaRepository<ProductAttribute , Integer> {
}
