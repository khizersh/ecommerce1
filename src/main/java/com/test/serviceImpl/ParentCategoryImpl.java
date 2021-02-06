package com.test.serviceImpl;

import com.test.bean.ParentCategory;
import com.test.repo.ParentCategoryRepo;
import com.test.service.ParentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParentCategoryImpl implements ParentCategoryService {

    @Autowired
    ParentCategoryRepo parentCategoryRepo;

    @Override
    public List<ParentCategory> getAll() {
        return parentCategoryRepo.findAll();
    }

    @Override
    public ParentCategory getById(Integer id) {

        if(!parentCategoryRepo.existsById(id)){
        return null;
        }
            return parentCategoryRepo.getOne(id);
    }

    @Override
    public ResponseEntity add(ParentCategory p) {
        if(p.getCategoryName() == null){
            return new ResponseEntity("Enter Category name!",HttpStatus.BAD_REQUEST);
        }
        if(p.getActive() == null){
           p.setActive(true);
        }
       return ResponseEntity.ok(parentCategoryRepo.save(p));

    }


    @Override
    public ResponseEntity update(Integer id, ParentCategory p) {

        ParentCategory category = parentCategoryRepo.getOne(id);
        if(category == null){
            return new ResponseEntity("Not Found!", HttpStatus.NOT_FOUND);
        }
        if(p.getCategoryName() != null){
            category.setCategoryName(p.getCategoryName());
        }
        if(p.getActive() != null){
            category.setActive(p.getActive());
        }
        return ResponseEntity.ok(parentCategoryRepo.save(category));
    }

    @Override
    public Boolean delete(Integer id) {
        boolean flag = false;
        if(!parentCategoryRepo.existsById(id)){
            flag = false;
        }else{
            parentCategoryRepo.deleteById(id);
            flag = true;
        }

        return flag;
    }




}
