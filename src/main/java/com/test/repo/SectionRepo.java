package com.test.repo;

import com.test.bean.sections.ProductSections;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepo extends JpaRepository<ProductSections , Integer> {
}
