package com.test.service;

import com.test.bean.product.AttributeImages;
import com.test.bean.product.ImageModel;
import com.test.bean.product.Product;
import com.test.bean.product_attribute.ProductAttribute;
import com.test.bean.product_attribute.ProductSubAttribute;
import com.test.dto.ChildAttributeDto;
import com.test.dto.ParentAttributeDto;
import com.test.dto.ProductDto;
import com.test.repo.AttributeImageRepo;
import com.test.utility.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private GlobalService service;

    @Autowired
    private AttributeImageRepo attributeImageRepo;

    public ProductDto convertDto(Product pro , Boolean isDetail){
        ProductDto dto = new ProductDto();
        dto.setId(pro.getId());
        dto.setTitle(pro.getTitle());
        if(pro.getCategory() != null){
            dto.setCategoryId(pro.getCategory().getId());
            dto.setCategoryName(pro.getCategory().getCategoryName());
        }
        dto.setDescription(pro.getDescription());
        if(pro.getImageList().size() != 0){

            if(!isDetail){
                List<ImageModel> list = new ArrayList<>();
                ImageModel image = new ImageModel();
                image = pro.getImageList().get(0);
                image.setPicByte(service.decompressByteArray(image.getPicByte()));
                list.add(image);
                dto.setImageList(list);
            }
            else{
                pro.getImageList().forEach(i -> {
                    i.setPicByte(service.decompressByteArray( i.getPicByte()));
                });
                dto.setImageList(pro.getImageList());
                if(pro.getCategory() != null){

                }
            }
        }

        if(isDetail) {
            List<ParentAttributeDto> pList = new ArrayList<>();
            for (ProductAttribute i : pro.getAttributeList()) {
                ParentAttributeDto pa = new ParentAttributeDto();
                pa.setId(i.getParentAttributeId());
                pa.setParentTitle(i.getParentAttributeName());
                pa.setMulti(i.getMultiImage());

                List<ChildAttributeDto> clist = new ArrayList<>();
                for (ProductSubAttribute j : i.getSubAttributeList()) {
                    ChildAttributeDto ca = new ChildAttributeDto();
                    ca.setId(j.getChildAttributeId());
                    ca.setTitle(j.getChildAttributeName());

                    if (i.getMultiImage()) {

                        List<ImageModel> imageList = new ArrayList<>();
                        for (AttributeImages k : attributeImageRepo.findByAttributeIdAndProductId(j.getChildAttributeId(), pro.getId())) {
                            ImageModel image = new ImageModel();
                            image.setId(k.getImage().getId());
                            image.setName(k.getImage().getName());
                            image.setType(k.getImage().getType());
                            image.setPicByte(service.decompressByteArray( k.getImage().getPicByte()));
                            imageList.add(image);

                        }
                        ca.setAttributeImage(imageList);
                    }
                    clist.add(ca);
                }
                pa.setChildAttributeList(clist);
                pList.add(pa);
            }
            dto.setAttributeList(pList);
        }

        return dto;
    }
}
