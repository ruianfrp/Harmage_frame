package com.harmonycloud.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Customer {
    private Integer id;
    private String customerName;
    private String customerProvince;
    private String customerCity;
    private String customerPlace;
    private String customerCode;
    private String customerWeb;
    private String customerEmail;
    private Integer defaultContactId;
    private Integer customerProdSort;
    private Integer customerSource;
    private String customerSourceText;
    private Integer customerIndustry;
    private Integer customerType;
    private Integer teamTotal;
    private Integer teamOutsource;
    private Integer serverCount;
    private Integer serverType;
    private Integer serverNetwork;
    private Integer operationStage;
    private Integer sorftStage;
    private Integer technologyStage;
    private Integer monitorStage;
    private String existingPain;
    private String petition;
    private String direction;
    private String followPoint;
    private String wages;
    private Integer budgetTotal;
    private Integer transformation;
    private String middleware;
    private String customerRemark;
    private String contactsName;
    private Integer contactsPosition;
    private String contactsSpecificPosition;
    private String contactsTel;
    private String contactsWechat;
    private String defaultEmployeeGh;
    private Date createTime;
    private Date updateTime;

    public Customer(){}

    public Customer(Integer id, String customerName, String customerProvince, String customerCity, String customerPlace, String customerCode, String customerWeb, String customerEmail, Integer defaultContactId, Integer customerProdSort, Integer customerSource, String customerSourceText, Integer customerIndustry, Integer customerType, Integer teamTotal, Integer teamOutsource, Integer serverCount, Integer serverType, Integer serverNetwork, Integer operationStage, Integer sorftStage, Integer technologyStage, Integer monitorStage, String existingPain, String petition, String direction, String followPoint, String wages, Integer budgetTotal, Integer transformation, String middleware, String customerRemark, String contactsName, Integer contactsPosition, String contactsSpecificPosition, String contactsTel, String contactsWechat, String defaultEmployeeGh, Date createTime, Date updateTime) {
        this.id = id;
        this.customerName = customerName;
        this.customerProvince = customerProvince;
        this.customerCity = customerCity;
        this.customerPlace = customerPlace;
        this.customerCode = customerCode;
        this.customerWeb = customerWeb;
        this.customerEmail = customerEmail;
        this.defaultContactId = defaultContactId;
        this.customerProdSort = customerProdSort;
        this.customerSource = customerSource;
        this.customerSourceText = customerSourceText;
        this.customerIndustry = customerIndustry;
        this.customerType = customerType;
        this.teamTotal = teamTotal;
        this.teamOutsource = teamOutsource;
        this.serverCount = serverCount;
        this.serverType = serverType;
        this.serverNetwork = serverNetwork;
        this.operationStage = operationStage;
        this.sorftStage = sorftStage;
        this.technologyStage = technologyStage;
        this.monitorStage = monitorStage;
        this.existingPain = existingPain;
        this.petition = petition;
        this.direction = direction;
        this.followPoint = followPoint;
        this.wages = wages;
        this.budgetTotal = budgetTotal;
        this.transformation = transformation;
        this.middleware = middleware;
        this.customerRemark = customerRemark;
        this.contactsName = contactsName;
        this.contactsPosition = contactsPosition;
        this.contactsSpecificPosition = contactsSpecificPosition;
        this.contactsTel = contactsTel;
        this.contactsWechat = contactsWechat;
        this.defaultEmployeeGh = defaultEmployeeGh;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getTransformation() {
        return transformation;
    }

    public void setTransformation(Integer transformation) {
        this.transformation = transformation;
    }

    public String getMiddleware() {
        return middleware;
    }

    public void setMiddleware(String middleware) {
        this.middleware = middleware;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

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

    public String getCustomerPlace() {
        return customerPlace;
    }

    public void setCustomerPlace(String customerPlace) {
        this.customerPlace = customerPlace;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerWeb() {
        return customerWeb;
    }

    public void setCustomerWeb(String customerWeb) {
        this.customerWeb = customerWeb;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Integer getDefaultContactId() {
        return defaultContactId;
    }

    public void setDefaultContactId(Integer defaultContactId) {
        this.defaultContactId = defaultContactId;
    }

    public Integer getCustomerProdSort() {
        return customerProdSort;
    }

    public void setCustomerProdSort(Integer customerProdSort) {
        this.customerProdSort = customerProdSort;
    }

    public Integer getCustomerSource() {
        return customerSource;
    }

    public void setCustomerSource(Integer customerSource) {
        this.customerSource = customerSource;
    }

    public String getCustomerSourceText() {
        return customerSourceText;
    }

    public void setCustomerSourceText(String customerSourceText) {
        this.customerSourceText = customerSourceText;
    }

    public Integer getCustomerIndustry() {
        return customerIndustry;
    }

    public void setCustomerIndustry(Integer customerIndustry) {
        this.customerIndustry = customerIndustry;
    }

    public Integer getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }

    public Integer getTeamTotal() {
        return teamTotal;
    }

    public void setTeamTotal(Integer teamTotal) {
        this.teamTotal = teamTotal;
    }

    public Integer getTeamOutsource() {
        return teamOutsource;
    }

    public void setTeamOutsource(Integer teamOutsource) {
        this.teamOutsource = teamOutsource;
    }

    public Integer getServerCount() {
        return serverCount;
    }

    public void setServerCount(Integer serverCount) {
        this.serverCount = serverCount;
    }

    public Integer getServerType() {
        return serverType;
    }

    public void setServerType(Integer serverType) {
        this.serverType = serverType;
    }

    public Integer getServerNetwork() {
        return serverNetwork;
    }

    public void setServerNetwork(Integer serverNetwork) {
        this.serverNetwork = serverNetwork;
    }

    public Integer getOperationStage() {
        return operationStage;
    }

    public void setOperationStage(Integer operationStage) {
        this.operationStage = operationStage;
    }

    public Integer getSorftStage() {
        return sorftStage;
    }

    public void setSorftStage(Integer sorftStage) {
        this.sorftStage = sorftStage;
    }

    public Integer getTechnologyStage() {
        return technologyStage;
    }

    public void setTechnologyStage(Integer technologyStage) {
        this.technologyStage = technologyStage;
    }

    public Integer getMonitorStage() {
        return monitorStage;
    }

    public void setMonitorStage(Integer monitorStage) {
        this.monitorStage = monitorStage;
    }

    public String getExistingPain() {
        return existingPain;
    }

    public void setExistingPain(String existingPain) {
        this.existingPain = existingPain;
    }

    public String getPetition() {
        return petition;
    }

    public void setPetition(String petition) {
        this.petition = petition;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getFollowPoint() {
        return followPoint;
    }

    public void setFollowPoint(String followPoint) {
        this.followPoint = followPoint;
    }

    public String getWages() {
        return wages;
    }

    public void setWages(String wages) {
        this.wages = wages;
    }

    public Integer getBudgetTotal() {
        return budgetTotal;
    }

    public void setBudgetTotal(Integer budgetTotal) {
        this.budgetTotal = budgetTotal;
    }

    public String getCustomerRemark() {
        return customerRemark;
    }

    public void setCustomerRemark(String customerRemark) {
        this.customerRemark = customerRemark;
    }

    public String getContactsName() {
        return contactsName;
    }

    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }

    public Integer getContactsPosition() {
        return contactsPosition;
    }

    public void setContactsPosition(Integer contactsPosition) {
        this.contactsPosition = contactsPosition;
    }

    public String getContactsSpecificPosition() {
        return contactsSpecificPosition;
    }

    public void setContactsSpecificPosition(String contactsSpecificPosition) {
        this.contactsSpecificPosition = contactsSpecificPosition;
    }

    public String getContactsTel() {
        return contactsTel;
    }

    public void setContactsTel(String contactsTel) {
        this.contactsTel = contactsTel;
    }

    public String getContactsWechat() {
        return contactsWechat;
    }

    public void setContactsWechat(String contactsWechat) {
        this.contactsWechat = contactsWechat;
    }

    public String getDefaultEmployeeGh() {
        return defaultEmployeeGh;
    }

    public void setDefaultEmployeeGh(String defaultEmployeeGh) {
        this.defaultEmployeeGh = defaultEmployeeGh;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}