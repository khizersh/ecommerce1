package com.test.repo;

import com.test.bean.product_attribute.ProductSubAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ProductSubAtrributeRepo extends JpaRepository<ProductSubAttribute , Integer> {

    @Query(value = "select * from product_sub_attribute where  parentid = ?1",nativeQuery = true)
    List<ProductSubAttribute> findByParentId(Integer id);


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "delete  from product_sub_attribute where child_attribute_id =?1 AND parentid = ?2",nativeQuery = true)
    void deleteSub(Integer cid , Integer pid);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "delete  from product_sub_attribute where parentid = ?1",nativeQuery = true)
    void deleteSubAttributeByParentId( Integer pid);
}
