package com.test.dto;
import java.util.List;


public class EditAttributeRequest {

    Integer productId;
    List<ParentAttributeInner> parentList;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public List<ParentAttributeInner> getParentList() {
        return parentList;
    }

    public void setParentList(List<ParentAttributeInner> parentList) {
        this.parentList = parentList;
    }
}
