package com.test.service;

import com.test.bean.product.AttributeImages;
import com.test.bean.product.ImageModel;
import com.test.bean.product.Points;
import com.test.bean.product.Product;
import com.test.bean.product_attribute.ProductAttribute;
import com.test.bean.product_attribute.ProductSubAttribute;
import com.test.dto.ChildAttributeDto;
import com.test.dto.ParentAttributeDto;
import com.test.dto.ProductDto;
import com.test.repo.*;
import com.test.utility.GlobalService;
import com.test.utility.ImageURl;
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

    @Autowired
    private PointsRepo pointsRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ImageUrlRepo imageUrlRepo;

    @Autowired
    private AmazonClient amazonClient;

    @Autowired
    private ProductAttributeRepo productAttributeRepo;
    @Autowired
    private ProductSubAtrributeRepo productSubAttributeRepo;

    public ProductDto convertDto(Product pro , Boolean isDetail){
        ProductDto dto = new ProductDto();
        dto.setId(pro.getId());
        dto.setTitle(pro.getTitle());
        dto.setRange(pro.getPriceRange());
        dto.setRangeCad(pro.getPriceRangeCad());
        dto.setRangeEuro(pro.getPriceRangeEuro());
        if(pro.getKeywords() != null){

        dto.setKeywords(pro.getKeywords());
        }
        if(pro.getCategory() != null){
            dto.setCategoryId(pro.getCategory().getId());
            dto.setCategoryName(pro.getCategory().getCategoryName());
        }
        if(pro.getReview() != null){
            dto.setReview(pro.getReview());
            dto.setReviewCount(pro.getReviewCount());
        }
        dto.setDescription(pro.getDescription());
        if(pro.getImageList().size() != 0){

            if(!isDetail){
                List<ImageURl> list = new ArrayList<>();
                ImageURl image = new ImageURl();
                image = pro.getImageList().get(0);
                list.add(image);
                dto.setImageList(list);
            }
            else{
                dto.setImageList(pro.getImageList());
            }
        }

        if(isDetail) {
            List<ParentAttributeDto> pList = new ArrayList<>();
            for (ProductAttribute i : productAttributeRepo.findByPid(pro.getId())) {
                ParentAttributeDto pa = new ParentAttributeDto();
                pa.setId(i.getId());
                pa.setProductId(pro.getId());
                pa.setParentTitle(i.getParentAttributeName());
                pa.setMulti(i.getMultiImage());

                List<ChildAttributeDto> clist = new ArrayList<>();
                for (ProductSubAttribute j : productSubAttributeRepo.findByParentId(i.getId())) {
                    ChildAttributeDto ca = new ChildAttributeDto();
                    ca.setId(j.getChildAttributeId());
                    ca.setParentId(j.getParentID());
                    ca.setTitle(j.getChildAttributeName());

                    if (i.getMultiImage() != null &&  i.getMultiImage() == true) {

                        List<String> imageList = new ArrayList<>();
                        for (AttributeImages k : attributeImageRepo.findByAttributeIdAndProductId(j.getChildAttributeId(), pro.getId())) {
                            imageList.add(k.getImage());
                        }
                        ca.setAttributeImage(imageList);
                    }
                    clist.add(ca);
                }
                pa.setChildAttributeList(clist);
                pList.add(pa);
            }
            dto.setAttributeList(pList);


            List<Points> bulletList = new ArrayList<>();
            for (Points points : pointsRepo.findByProductId(pro.getId())) {
                bulletList.add(points);
            }
            dto.setBulletList(bulletList);
        }

        return dto;
    }

//    for dashboard edit attribute
    public ProductDto productDetailFull(Product pro ){
        ProductDto dto = new ProductDto();
        dto.setId(pro.getId());
        dto.setTitle(pro.getTitle());
        dto.setRange(pro.getPriceRange());
        if(pro.getKeywords() != null){
            dto.setKeywords(pro.getKeywords());
        }
        if(pro.getCategory() != null){
            dto.setCategoryId(pro.getCategory().getId());
            dto.setCategoryName(pro.getCategory().getCategoryName());
        }
        if(pro.getReview() != null){
            dto.setReview(pro.getReview());
            dto.setReviewCount(pro.getReviewCount());
        }
        dto.setDescription(pro.getDescription());
        if(pro.getImageList().size() != 0) {

            List<ParentAttributeDto> pList = new ArrayList<>();
            for (ProductAttribute i : productAttributeRepo.findByPid(pro.getId())) {
                ParentAttributeDto pa = new ParentAttributeDto();
                pa.setId(i.getId());
                pa.setParentAttributeId(i.getParentAttributeId());
                pa.setProductId(pro.getId());
                pa.setParentTitle(i.getParentAttributeName());
                pa.setMulti(i.getMultiImage());

                List<ChildAttributeDto> clist = new ArrayList<>();
                for (ProductSubAttribute j : productSubAttributeRepo.findByParentId(i.getId())) {
                    ChildAttributeDto ca = new ChildAttributeDto();
                    ca.setId(j.getChildAttributeId());
                    ca.setTitle(j.getChildAttributeName());
                    ca.setParentId(j.getParentID());

                    if (i.getMultiImage() != null && i.getMultiImage() == true) {

                        List<AttributeImages> imageList = new ArrayList<>();
                        for (AttributeImages k : attributeImageRepo.findByAttributeIdAndProductId(j.getChildAttributeId(), pro.getId())) {

                            imageList.add(k);

                        }
                        ca.setAttributeImageFull(imageList);
                    }
                    clist.add(ca);
                }
                pa.setChildAttributeList(clist);
                pList.add(pa);
            }
            dto.setAttributeList(pList);

        }


        List<Points> bulletList = new ArrayList<>();
        for (Points points : pointsRepo.findByProductId(pro.getId())) {
            bulletList.add(points);
        }
        dto.setBulletList(bulletList);
        return dto;
    }


    public Boolean deleteProduct(Integer id){
        try{
         imageUrlRepo.deleteByProductId(id);
         Product product = productRepo.getOne(id);

        for (ImageURl imageURl : product.getImageList()) {
            amazonClient.deleteFileFromS3Bucket(imageURl.getImage());
        }

            for (ProductAttribute productAttribute : product.getAttributeList()) {
                productSubAttributeRepo.deleteSubAttributeByParentId(productAttribute.getParentAttributeId());
                productAttributeRepo.deleteById(productAttribute.getId());

            }
            productRepo.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }

    }
}
