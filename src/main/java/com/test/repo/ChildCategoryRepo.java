package com.test.repo;

import com.test.bean.category.ChildCategory;
import com.test.bean.category.ParentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.*;

@Repository
public interface ChildCategoryRepo extends JpaRepository<ChildCategory , Integer> {

    public List<ChildCategory> findByParentCategory(ParentCategory p);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "delete  from child_category where id=?1" , nativeQuery = true)
    public void deleteByid(Integer id);
}
