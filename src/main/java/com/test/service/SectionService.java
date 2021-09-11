package com.test.service;

import com.test.bean.product.AttributeImages;
import com.test.bean.product.Points;
import com.test.bean.product.Product;
import com.test.bean.sections.ProductSections;
import com.test.bean.sections.SectionItems;
import com.test.bean.sections.SortBySequence;
import com.test.dto.ChildAttributeDto;
import com.test.dto.ParentAttributeDto;
import com.test.dto.ProductDto;
import com.test.dto.ProductSectionDto;
import com.test.repo.ProductRepo;
import com.test.repo.SectionItemsRepo;
import com.test.repo.SectionRepo;
import com.test.utility.ImageURl;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.cache.annotation.Cacheable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SectionService {

    @Autowired
    private SectionRepo sectionRepo;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private SectionItemsRepo itemsRepo;

    @Cacheable("section")
    public List<ProductSectionDto> getSections() {
        List<ProductSectionDto> list = new ArrayList<>();
        for (ProductSections i : sectionRepo.findAll()) {
            ProductSectionDto dto = new ProductSectionDto();
            dto.setId(i.getId());
            dto.setTitle(i.getTitle());
            List<ProductDto> productList = new ArrayList<>();

            List<SectionItems> listItems = itemsRepo.findBySectionId(i.getId());
            Collections.sort(listItems, new SortBySequence());
            for (SectionItems j : listItems) {
                Product product = productRepo.getOne(j.getProductId());
                if (product != null) {
                    productList.add(productService.productDetailFull(product));
                }
            }
            dto.setProductList(productList);
            list.add(dto);
        }

        return list;
    }

    public JSONArray convertIntoJson(List<ProductDto> list) {
        JSONArray productList = new JSONArray();

        for (ProductDto pro : list) {
            productList.add(convertProductIntoJson(pro));

        }
            return productList;


    }

    public JSONObject convertProductIntoJson (ProductDto pro){
            JSONObject p = new JSONObject();
            p.put("id" ,pro.getId());
            p.put("title" ,pro.getTitle());
            p.put("priceSet" ,pro.getPriceSet());
            p.put("description" ,pro.getDescription());
            p.put("categoryId" ,pro.getCategoryId());
            p.put("categoryName" ,pro.getCategoryName());
            p.put("sequence" ,pro.getSequence());
            p.put("range" ,pro.getRange());
            p.put("rangeCad" ,pro.getRangeCad());
            p.put("rangeEuro" ,pro.getRangeEuro());
            p.put("review" ,pro.getReview());
            p.put("reviewCount" ,pro.getReviewCount());
            p.put("keywords" ,pro.getKeywords());
            p.put("priceSet" ,pro.getPriceSet());
            p.put("discount" ,pro.getDiscount());
            if(pro.getGender() != null){
            p.put("gender" ,pro.getGender().toString());
            }else{
            p.put("gender" ,"Male");
            }

            JSONArray imageList = new JSONArray();
            for (ImageURl image : pro.getImageList()) {
                JSONObject img = new JSONObject();
                img.put("id" ,image.getId());
                img.put("image" ,image.getImage());
                imageList.add(img);
            }
            p.put("imageList" , imageList);

            JSONArray pList = new JSONArray();
            for (ParentAttributeDto attrib : pro.getAttributeList()) {
                JSONObject att = new JSONObject();
                att.put("id" ,attrib.getId());
                att.put("parentTitle" ,attrib.getParentTitle());
                att.put("parentAttributeId" ,attrib.getParentAttributeId());
                att.put("multi" ,attrib.getMulti());
                att.put("productId" ,attrib.getProductId());

                JSONArray cList = new JSONArray();
                for (ChildAttributeDto c : attrib.getChildAttributeList()) {
                    JSONObject ca = new JSONObject();
                    ca.put("id", c.getId());
                    ca.put("title", c.getTitle());
                    ca.put("parentId", c.getParentId());

                    JSONArray ccList = new JSONArray();
                    if(c.getAttributeImageFull().size() > 0){
                        for (AttributeImages i: c.getAttributeImageFull()) {
                            JSONObject cb = new JSONObject();
                            cb.put("id",i.getId());
                            cb.put("attributeId",i.getAttributeId());
                            cb.put("productId",i.getProductId());
                            cb.put("image",i.getImage());
                            ccList.add(cb);

                        }
                    }else{
                        for (String img: c.getAttributeImage()) {
                            JSONObject cb = new JSONObject();
                            cb.put("id","");
                            cb.put("attributeId","");
                            cb.put("productId","");
                            cb.put("image",img);
                            ccList.add(cb);

                        }
                    }

                    ca.put("attributeImageFull", ccList);
                    cList.add(ca);

                }
                att.put("childAttributeList",cList);
                pList.add(att);
            }
            p.put("attributeList" , pList);

            JSONArray pointList = new JSONArray();
            for (Points points: pro.getBulletList()) {
                JSONObject po = new JSONObject();

                po.put("id" , points.getId());
                po.put("point" , points.getPoint());
                po.put("productId" , points.getProductId());
                pointList.add(po);

            }
            p.put("bulletList",pointList);


          return p;
        }
        

}
