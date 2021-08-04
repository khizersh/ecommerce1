package com.test.repo;

import com.test.bean.sections.ProductSections;
import com.test.bean.sections.SectionItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.*;

@Repository
public interface SectionItemsRepo extends JpaRepository<SectionItems, Integer> {

    public List<SectionItems> findBySectionId(Integer id);
    public List<SectionItems> findByProductId(Integer id);
    @Query(value = "select * from section_item where section_id=?1 AND sequence=?2" ,nativeQuery = true)
    public List<SectionItems> findBySectionIdAndSequence(Integer sId , Integer seq);

    public List<SectionItems> findBySequence(Integer id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "delete  from section_item where product_id=?1",nativeQuery = true)
    void deleteItemByProductId(Integer pid);

}
