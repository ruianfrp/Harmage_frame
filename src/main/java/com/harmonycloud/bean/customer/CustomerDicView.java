package com.harmonycloud.bean.customer;

public class CustomerDicView {
    private Integer id;
    private String dicType;
    private String dicDataSort;
    private String dicDataText;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDicType() {
        return dicType;
    }

    public void setDicType(String dicType) {
        this.dicType = dicType;
    }

    public String getDicDataSort() {
        return dicDataSort;
    }

    public void setDicDataSort(String dicDataSort) {
        this.dicDataSort = dicDataSort;
    }

    public String getDicDataText() {
        return dicDataText;
    }

    public void setDicDataText(String dicDataText) {
        this.dicDataText = dicDataText;
    }
}
