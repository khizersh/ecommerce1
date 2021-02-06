package com.test.service;

import com.test.bean.ParentCategory;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ParentCategoryService {

    public List<ParentCategory> getAll();

    public ParentCategory getById(Integer id);

    public ResponseEntity add(ParentCategory p);

    public ResponseEntity update(Integer id , ParentCategory p);

    public Boolean delete(Integer id );
}
