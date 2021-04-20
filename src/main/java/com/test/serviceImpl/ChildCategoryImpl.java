package com.test.serviceImpl;

import com.test.bean.category.ChildCategory;
import com.test.bean.category.ParentCategory;
import com.test.repo.ChildCategoryRepo;
import com.test.repo.ParentCategoryRepo;
import com.test.service.ChildCategoryService;
import com.test.utility.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChildCategoryImpl  implements ChildCategoryService {

    @Autowired
    private ChildCategoryRepo repo;

    @Autowired
    private ParentCategoryRepo parentCategoryRepo;

    @Autowired
    private GlobalService service;

    @Override
    public List<ChildCategory> getAll() {
        return repo.findAll();
    }


    @Override
    public ResponseEntity getById(Integer id) {

        ChildCategory cat = repo.getOne(id);

        return ResponseEntity.ok(service.convertChildCategoryDto(cat) );
    }

    @Override
    public Boolean changeStatus(Integer id) {
        boolean flag = false;
        if (!repo.existsById(id)){
            flag = false;
        }else{
            try {
            repo.deleteByid(id);
            flag = true;

            }catch (Exception e){
                return false;
            }
        }
        return flag;
    }

    @Override
    public ResponseEntity addCategory(ChildCategory category) {
        if(category.getCategoryName() == null){
            return new ResponseEntity("Enter name!", HttpStatus.BAD_REQUEST);
        }
        if(category.getActive() == null){
            category.setActive(true);
        }
        if(category.getParentId() == null){
            return new ResponseEntity("Enter Parent Category! ", HttpStatus.BAD_REQUEST);
        }

        ParentCategory parentCategory = parentCategoryRepo.getOne(category.getParentId());

        if (parentCategory == null) {
            return new ResponseEntity("Enter Parent Category! ", HttpStatus.BAD_REQUEST);
        }
        category.setParentCategory(parentCategory);


        return ResponseEntity.ok(repo.save(category));
    }

    @Override
    public ResponseEntity updateCategory(Integer id, ChildCategory cat) {

        ChildCategory catDb = repo.getOne(id);
        if(catDb == null){
            return new ResponseEntity("Not Found!",HttpStatus.NOT_FOUND);
        }

        if(cat.getParentId() != null) {
            ParentCategory p = parentCategoryRepo.getOne(cat.getParentId());
            catDb.setParentCategory(p);
        }
        if (cat.getCategoryName() != null) {
            catDb.setCategoryName(cat.getCategoryName());
        }
        if(cat.getActive() != null){
            catDb.setActive(cat.getActive());
        }


        return ResponseEntity.ok(repo.save(catDb));
    }

    @Override
    public Boolean deleteCategory(Integer id) {
        boolean flag = false;
        if (!repo.existsById(id) ) {
            flag = false;
        }else{
            repo.deleteByid(id);
            flag = true;
        }

        return flag;
    }

}
