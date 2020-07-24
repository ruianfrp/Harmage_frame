package com.harmonycloud.bean.contract;

public class ContractReceivedView {
    private Integer id;
    private Integer fkContractId;
    private double contractReceived;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFkContractId() {
        return fkContractId;
    }

    public void setFkContractId(Integer fkContractId) {
        this.fkContractId = fkContractId;
    }

    public double getContractReceived() {
        return contractReceived;
    }

    public void setContractReceived(double contractReceived) {
        this.contractReceived = contractReceived;
    }
}
