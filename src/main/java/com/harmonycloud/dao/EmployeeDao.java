package com.harmonycloud.dao;

import com.harmonycloud.view.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EmployeeDao {
    List<EmployeeListView> listEmployee(String selectEmployeeGh,String selectSex,String selectType,String selectEmployeeName,String sort);

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

    void insertEmployeeSkill(String employeeGh, Long id);

    void insertEmployeeSkillTest(String employeeGh, Long skillTestId);

    List<EmployeeExperienceView> listEmployeeExperience(String employeeGh);

    EmployeeCountView MonthInsertPerson(Integer num);

    Integer MonthQuitPerson(Integer num);

    Integer MonthStartPerson(Integer num);

    Integer MonthEndPerson(Integer num);

    List<String> selectEmployeeQuit();

    List<EmployeeListView> selectAllEmployee();

    List<EmployeeNumView> selectEmployeeNum();
}