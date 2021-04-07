package com.test.repo;

import com.test.bean.category.ChildCategory;
import com.test.bean.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface ProductRepo extends JpaRepository<Product , Integer> {

    public List<Product> findProductByCategory(ChildCategory  cat);

}
