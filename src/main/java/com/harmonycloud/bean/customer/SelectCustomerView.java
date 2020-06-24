package com.harmonycloud.bean.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class SelectCustomerView {
    private String customerProvince;
    private String customerCity;
    private Integer customerSource;
    private Integer customerProdSort;
    private String defaultEmployeeGh;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createBeginTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createEndTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateBeginTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateEndTime;

    public String getCustomerProvince() {
        return customerProvince;
    }

    public void setCustomerProvince(String customerProvince) {
        this.customerProvince = customerProvince;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public Integer getCustomerSource() {
        return customerSource;
    }

    public void setCustomerSource(Integer customerSource) {
        this.customerSource = customerSource;
    }

    public Integer getCustomerProdSort() {
        return customerProdSort;
    }

    public void setCustomerProdSort(Integer customerProdSort) {
        this.customerProdSort = customerProdSort;
    }

    public String getDefaultEmployeeGh() {
        return defaultEmployeeGh;
    }

    public void setDefaultEmployeeGh(String defaultEmployeeGh) {
        this.defaultEmployeeGh = defaultEmployeeGh;
    }

    public Date getCreateBeginTime() {
        return createBeginTime;
    }

    public void setCreateBeginTime(Date createBeginTime) {
        this.createBeginTime = createBeginTime;
    }

    public Date getCreateEndTime() {
        return createEndTime;
    }

    public void setCreateEndTime(Date createEndTime) {
        this.createEndTime = createEndTime;
    }

    public Date getUpdateBeginTime() {
        return updateBeginTime;
    }

    public void setUpdateBeginTime(Date updateBeginTime) {
        this.updateBeginTime = updateBeginTime;
    }

    public Date getUpdateEndTime() {
        return updateEndTime;
    }

    public void setUpdateEndTime(Date updateEndTime) {
        this.updateEndTime = updateEndTime;
    }
}
