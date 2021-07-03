package com.test.repo;

import com.test.bean.category.ChildCategory;
import com.test.bean.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface ProductRepo extends JpaRepository<Product , Integer> {


    @Query(value = "SELECT DISTINCT * FROM product WHERE LOWER(keywords) LIKE LOWER('%?1%')",nativeQuery = true)
    public List<Product> findByKeyword(String  key);

    List<Product> findByKeywordsContainingIgnoreCase(String keyword);


    public List<Product> findProductByCategory(ChildCategory  cat);
    public List<Product> findByOrderByIdAsc();

}
