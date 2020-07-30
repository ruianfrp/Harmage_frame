package com.harmonycloud.service;


import com.harmonycloud.bean.employee.*;
import com.harmonycloud.bean.skill.TestSkillListView;
import org.springframework.scheduling.annotation.Async;

import java.util.Date;
import java.util.List;

/**
 * @author ：lxl
 * @date ：Created in 2019/8/1 20:29
 */
public interface EmployeeService {
    List<EmployeeListView> listEmployee(String selectEmployeeGh, String selectSex, String selectType, String selectEmployeeName, String sort);

    List<EmployeeListView> listAllEmployee();

    List<EmployeeSkillView> selectEmployeeSkill();

    List<TestSkillListView> selectEmployeeTestSkill();

    List<EmployeeSkillView> listEmployeeSkill(String employeeGh);

    List<TestSkillListView> listEmployeeTestSkill(String employeeGh);

    List<EmployeeListView> selectByEmployeeGh(String employeeGh);

    List<EmployeeListView> selectByEmployeeName(String employeeName);

    void deleteEmployee(String employeeGh);

    void updateEmployee(String employeeName, String employeeDep, String employeeJob, String employeeWorkplace, Integer isQuit,
                        Date createTime, Date updateTime, String employeeSex, String employeeType, String employeeGh);

    String findByEmployeeGh(String employeeGh);

    void insertEmployee(String employeeGh, String employeeName, String employeeDep, String employeeJob,
                        String employeeWorkplace, String employeeSex, String employeeType);

    String selectEmployeeNameByEmployeeGh(String employeeGh);

    String findByEmployeeName(String employeeName);

    void deleteEmployeeSkill(String employeeGh);

    void deleteEmployeeSkillTest(String employeeGh);

    void insertEmployeeSkill(String employeeGh,Long id);

    void insertEmployeeSkillTest(String employeeGh,Long skillTestId);

    List<EmployeeExperienceView> listEmployeeExperience(String employeeGh);

    EmployeeCountView MonthInsertPerson(Integer num);

    Integer MonthQuitPerson(Integer num);

    Integer MonthStartPerson(Integer num);

    Integer MonthEndPerson(Integer num);

    List<String> selectEmployeeQuit();

    List<EmployeeListView> selectAllEmployee();

    List<EmployeeNumView> selectEmployeeNum();

}