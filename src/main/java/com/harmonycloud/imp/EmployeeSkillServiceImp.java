package com.harmonycloud.imp;


import com.harmonycloud.dao.EmployeeSkillDao;
import com.harmonycloud.service.EmployeeSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：lxl
 * @date ：Created in 2019/8/1 20:29
 */
@Service
public class EmployeeSkillServiceImp implements EmployeeSkillService {


    @Autowired
    EmployeeSkillDao employeeSkillDao;
}
