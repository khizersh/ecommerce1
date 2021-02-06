package com.test.service;
import java.util.List;
import com.test.bean.ChildCategory;
import org.springframework.http.ResponseEntity;


public interface ChildCategoryService {

    public List<ChildCategory> getAll();

    public ResponseEntity addCategory(ChildCategory category);

    public ResponseEntity updateCategory(Integer id , ChildCategory cat);

    public Boolean deleteCategory(Integer id);

    public ResponseEntity getById(Integer id);

    public Boolean changeStatus(Integer id);
}
