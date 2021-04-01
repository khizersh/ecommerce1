package com.test.repo;

import com.test.bean.product.AttributeImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface AttributeImageRepo extends JpaRepository<AttributeImages , Integer> {
    @Query(value = "select * from attribute_images where attribute_id =?1 AND product_id=?2",nativeQuery = true)
    public List<AttributeImages> findByAttributeIdAndProductId(Integer aid , Integer pid  );
}
