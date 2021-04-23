package com.test.service;
import java.io.IOException;
import java.util.List;
import com.test.bean.category.ChildCategory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;


public interface ChildCategoryService {

    public List<ChildCategory> getAll();

    public ResponseEntity addCategory(ChildCategory category);

    public ResponseEntity updateCategory(ChildCategory cat , MultipartFile image , MultipartFile banner) throws IOException;

    public Boolean deleteCategory(Integer id);

    public ResponseEntity getById(Integer id);

    public Boolean changeStatus(Integer id);

}
