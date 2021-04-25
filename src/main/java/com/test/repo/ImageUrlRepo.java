package com.test.repo;

import com.test.utility.ImageURl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageUrlRepo extends JpaRepository<ImageURl , Integer> {
}
