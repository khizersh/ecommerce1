package com.test.dto;

import java.util.List;

public class ParentAttributeInner {


        Integer parentId;
        Boolean multi;
        List<ChildAttribute> childList;

        public Integer getParentId() {
            return parentId;
        }

        public void setParentId(Integer parentId) {
            this.parentId = parentId;
        }

        public Boolean getMulti() {
            return multi;
        }

        public void setMulti(Boolean multi) {
            this.multi = multi;
        }

        public List<ChildAttribute> getChildList() {
            return childList;
        }

        public void setChildList(List<ChildAttribute> childList) {
            this.childList = childList;
        }

}
