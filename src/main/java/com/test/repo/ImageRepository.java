package com.test.repo;

import com.test.bean.ChildCategory;
import com.test.bean.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<ImageModel , Integer> {
    Optional<ImageModel> findByName(String name);

    List<ImageModel> findByCategory(ChildCategory cat);
//    Optional<ImageModel> findById(Long id);
}
