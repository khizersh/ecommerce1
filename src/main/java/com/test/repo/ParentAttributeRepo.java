package com.test.repo;

import com.test.bean.ParentAttributes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentAttributeRepo extends JpaRepository<ParentAttributes , Integer> {
}
