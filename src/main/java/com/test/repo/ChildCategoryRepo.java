package com.test.repo;

import com.test.bean.ChildCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildCategoryRepo extends JpaRepository<ChildCategory , Integer> {
}
