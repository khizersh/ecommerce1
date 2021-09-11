package com.test.service;

import com.test.bean.product.AttributeImages;
import com.test.bean.product.ImageModel;
import com.test.bean.product.Points;
import com.test.bean.product.Product;
import com.test.bean.product_attribute.ProductAttribute;
import com.test.bean.product_attribute.ProductSubAttribute;
import com.test.bean.sections.SectionItems;
import com.test.dto.ChildAttributeDto;
import com.test.dto.ParentAttributeDto;
import com.test.dto.ProductDto;
import com.test.repo.*;
import com.test.utility.GlobalService;
import com.test.utility.ImageURl;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
    private SectionItemsRepo itemsRepo;

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

//    for dashboard edit attribute and now for all
    public ProductDto productDetailFull(Product pro){
        ProductDto dto = new ProductDto();
        dto.setId(pro.getId());
        dto.setTitle(pro.getTitle());
        dto.setRange(pro.getPriceRange());
        dto.setRangeEuro(pro.getPriceRangeEuro());
        dto.setRangeCad(pro.getPriceRangeCad());

        dto.setSequence(pro.getSequence());
        if(pro.getSequence() != null){
        dto.setSequence(pro.getSequence());
        }  if(pro.getGender() != null){
        dto.setGender(pro.getGender());
        }

        if(pro.getDiscount() != null){
        dto.setDiscount(true);
        }else{
        dto.setDiscount(false);
        }
        if(pro.getPriceSet() != null && pro.getPriceSet() == true){
        dto.setPriceSet(true);
        }else{
        dto.setPriceSet(false);
        }
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
        dto.setImageList(pro.getImageList());
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
         Product product = productRepo.getOne(id);

        for (ImageURl imageURl : product.getImageList()) {
//            amazonClient.deleteFileFromS3Bucket(imageURl.getImage());
            imageUrlRepo.deleteById(imageURl.getId());

        }
        for (SectionItems sectionItems : itemsRepo.findByProductId(id)) {
                itemsRepo.deleteById(sectionItems.getId());
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


    public JSONArray getProductListFromFile() throws IOException {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("product.json"))
        {
            Object obj = jsonParser.parse(reader);
            JSONArray productList = (JSONArray) obj;
            return (productList);
        } catch (FileNotFoundException | ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
