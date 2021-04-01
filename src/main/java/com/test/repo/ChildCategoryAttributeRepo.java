package com.test.repo;

import com.test.bean.category.ChildCategoryAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildCategoryAttributeRepo extends JpaRepository<ChildCategoryAttribute, Integer> {
}
