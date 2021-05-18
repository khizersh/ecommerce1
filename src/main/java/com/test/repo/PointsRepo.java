package com.test.repo;

import com.test.bean.product.Points;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PointsRepo extends JpaRepository<Points, Integer> {

    List<Points> findByProductId(Integer id);
}
