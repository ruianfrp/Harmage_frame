package com.harmonycloud.dao;

import com.harmonycloud.bean.customer.BusinessOpportunity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessOpportunityDao {
    List<BusinessOpportunity> listBizOpp();

    Integer insertBizOpp(BusinessOpportunity businessOpportunity);

    Integer deleteBizOpp(Integer id);

    Integer updateBizOpp(BusinessOpportunity businessOpportunity);
}
