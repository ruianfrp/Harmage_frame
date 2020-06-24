package com.harmonycloud.service.imp;


import com.harmonycloud.dao.MilestoneBacklogDao;
import com.harmonycloud.service.MilestoneBacklogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：lxl
 * @date ：Created in 2019/8/1 20:29
 */
@Service
public class MilestoneBacklogServiceImp implements MilestoneBacklogService {


    @Autowired
    MilestoneBacklogDao milestoneBacklogDao;
}
