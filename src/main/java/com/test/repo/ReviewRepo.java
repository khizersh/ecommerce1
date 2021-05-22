package com.test.repo;

import com.test.bean.User;
import com.test.bean.review.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepo extends JpaRepository<ProductReview, Long> {

    List<ProductReview> findByProductId(Integer id);

    List<ProductReview> findByOrderByIdAsc();

    @Query("SELECT u FROM ProductReview u WHERE u.productId = ?1 AND u.userId = ?2")
    public List<ProductReview> findByUserAndProduct(Integer productId , Long userId);
}
