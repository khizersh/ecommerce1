package com.test.repo;

import com.test.bean.ParentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentCategoryRepo extends JpaRepository<ParentCategory, Integer> {


}
