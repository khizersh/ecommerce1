package com.test.repo;

import com.test.utility.ImageURl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ImageUrlRepo extends JpaRepository<ImageURl , Integer> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from imageurl where product_id = ?1",nativeQuery = true)
    void deleteByProductId(Integer id);


//    @Modifying
//    @Query("delete from imageurl f where f.product_id=:id")
//    List<Integer> deleteProductImages(@Param("id") Integer name);
}
