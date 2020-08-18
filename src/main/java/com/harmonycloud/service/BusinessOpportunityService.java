package com.harmonycloud.service;

import com.harmonycloud.bean.customer.BusinessOpportunity;

import java.util.List;

public interface BusinessOpportunityService {
    List<BusinessOpportunity> listBizOpp();

    Integer insertBizOpp(BusinessOpportunity businessOpportunity);

    Integer deleteBizOpp(Integer id);

    Integer updateBizOpp(BusinessOpportunity businessOpportunity);
}
