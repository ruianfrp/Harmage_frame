package com.harmonycloud.bean.customer;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class CustomerDetailView {
    private Integer id;
    private String customerName;
    private String customerProvince;
    private String customerCity;
    private String customerPlace;
    private String customerCode;
    private String customerWeb;
    private String customerEmail;
    private String customerProdSort;
    private String customerProdText;
    private String customerSourceSort;
    private String customerSourceText;
    private String customerIndustry;
    private String customerType;
    private String teamTotal;
    private String teamOutsource;
    private String serverCount;
    private String serverType;
    private String serverNetwork;
    private String operationStage;
    private String sorftStage;
    private String technologyStage;
    private String monitorStage;
    private String existingPain;
    private String petition;
    private String direction;
    private String followPoint;
    private String wages;
    private String budgetTotal;
    private String transformation;
    private String middleware;
    private String customerRemark;
    private String employeeName;
    private String contactsName;
    private String contactsPosition;
    private String contactsSpecificPosition;
    private String contactsTel;
    private String contactsWechat;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getTransformation() {
        return transformation;
    }

    public void setTransformation(String transformation) {
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

    public String getCustomerProdSort() {
        return customerProdSort;
    }

    public void setCustomerProdSort(String customerProdSort) {
        this.customerProdSort = customerProdSort;
    }

    public String getCustomerProdText() {
        return customerProdText;
    }

    public void setCustomerProdText(String customerProdText) {
        this.customerProdText = customerProdText;
    }

    public String getCustomerSourceSort() {
        return customerSourceSort;
    }

    public void setCustomerSourceSort(String customerSourceSort) {
        this.customerSourceSort = customerSourceSort;
    }

    public String getCustomerSourceText() {
        return customerSourceText;
    }

    public void setCustomerSourceText(String customerSourceText) {
        this.customerSourceText = customerSourceText;
    }

    public String getCustomerIndustry() {
        return customerIndustry;
    }

    public void setCustomerIndustry(String customerIndustry) {
        this.customerIndustry = customerIndustry;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getTeamTotal() {
        return teamTotal;
    }

    public void setTeamTotal(String teamTotal) {
        this.teamTotal = teamTotal;
    }

    public String getTeamOutsource() {
        return teamOutsource;
    }

    public void setTeamOutsource(String teamOutsource) {
        this.teamOutsource = teamOutsource;
    }

    public String getServerCount() {
        return serverCount;
    }

    public void setServerCount(String serverCount) {
        this.serverCount = serverCount;
    }

    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    public String getServerNetwork() {
        return serverNetwork;
    }

    public void setServerNetwork(String serverNetwork) {
        this.serverNetwork = serverNetwork;
    }

    public String getOperationStage() {
        return operationStage;
    }

    public void setOperationStage(String operationStage) {
        this.operationStage = operationStage;
    }

    public String getSorftStage() {
        return sorftStage;
    }

    public void setSorftStage(String sorftStage) {
        this.sorftStage = sorftStage;
    }

    public String getTechnologyStage() {
        return technologyStage;
    }

    public void setTechnologyStage(String technologyStage) {
        this.technologyStage = technologyStage;
    }

    public String getMonitorStage() {
        return monitorStage;
    }

    public void setMonitorStage(String monitorStage) {
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

    public String getBudgetTotal() {
        return budgetTotal;
    }

    public void setBudgetTotal(String budgetTotal) {
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

    public String getContactsPosition() {
        return contactsPosition;
    }

    public void setContactsPosition(String contactsPosition) {
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
