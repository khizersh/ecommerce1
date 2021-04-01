package com.test.repo;

import com.test.bean.category.ChildCategory;
import com.test.bean.category.ParentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface ChildCategoryRepo extends JpaRepository<ChildCategory , Integer> {

    public List<ChildCategory> findByParentCategory(ParentCategory p);
}
