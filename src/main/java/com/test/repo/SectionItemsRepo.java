package com.test.repo;

import com.test.bean.sections.ProductSections;
import com.test.bean.sections.SectionItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface SectionItemsRepo extends JpaRepository<SectionItems, Integer> {

    public List<SectionItems> findBySectionId(Integer id);
}
