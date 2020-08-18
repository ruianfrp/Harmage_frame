package com.harmonycloud.service.imp;

import com.harmonycloud.bean.customer.BusinessOpportunity;
import com.harmonycloud.dao.BusinessOpportunityDao;
import com.harmonycloud.service.BusinessOpportunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessOpportunityServiceImp implements BusinessOpportunityService {
    @Autowired
    BusinessOpportunityDao businessOpportunityDao;

    @Override
    public List<BusinessOpportunity> listBizOpp() {
        return businessOpportunityDao.listBizOpp();
    }

    @Override
    public Integer insertBizOpp(BusinessOpportunity businessOpportunity) {
        return businessOpportunityDao.insertBizOpp(businessOpportunity);
    }

    @Override
    public Integer deleteBizOpp(Integer id) {
        return businessOpportunityDao.deleteBizOpp(id);
    }

    @Override
    public Integer updateBizOpp(BusinessOpportunity businessOpportunity) {
        return businessOpportunityDao.updateBizOpp(businessOpportunity);
    }
}
