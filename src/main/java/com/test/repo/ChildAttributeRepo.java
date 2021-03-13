package com.test.repo;

import java.util.*;
import com.test.bean.ChildAttribute;
import com.test.bean.ParentAttributes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildAttributeRepo extends JpaRepository<ChildAttribute , Integer> {

    public List<ChildAttribute> findByParentAttributes(ParentAttributes p);
}
