package com.harmonycloud.service.imp;

import com.harmonycloud.bean.Threads.SynchroThread;
import com.harmonycloud.bean.employee.*;
import com.harmonycloud.bean.skill.TestSkillListView;
import com.harmonycloud.dao.EmployeeDao;
import com.harmonycloud.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author ：lxl
 * @date ：Created in 2019/8/1 20:29
 */
@Service
public class EmployeeServiceImp implements EmployeeService {


    @Autowired
    EmployeeDao employeeDao;

    @Override
    public List<EmployeeListView> listEmployee(String selectEmployeeGh,String selectSex,String selectType,String selectEmployeeName,String sort) {
        return employeeDao.listEmployee(selectEmployeeGh, selectSex, selectType, selectEmployeeName, sort);
    }

    @Override
    public List<EmployeeListView> listAllEmployee(){
        return employeeDao.listAllEmployee();
    }

    @Override
    public List<EmployeeSkillView> selectEmployeeSkill(){
        return employeeDao.selectEmployeeSkill();
    }

    @Override
    public List<TestSkillListView> selectEmployeeTestSkill(){
        return employeeDao.selectEmployeeTestSkill();
    }

    @Override
    public List<EmployeeSkillView> listEmployeeSkill(String employeeGh) {
        return employeeDao.listEmployeeSkill(employeeGh);
    }

    @Override
    public List<TestSkillListView> listEmployeeTestSkill(String employeeGh){
        return employeeDao.listEmployeeTestSkill(employeeGh);
    }

    @Override
    public void deleteEmployee(String employeeGh) {
        employeeDao.deleteEmployee(employeeGh);
    }

    @Override
    public void updateEmployee(String employeeName, String employeeDep, String employeeJob, String employeeWorkplace, Integer isQuit,
                               Date createTime, Date updateTime, String employeeSex, String employeeType, String employeeGh) {
        employeeDao.updateEmployee(employeeName, employeeDep, employeeJob, employeeWorkplace, isQuit,
                createTime, updateTime, employeeSex, employeeType, employeeGh);

    }

    @Override
    public String findByEmployeeGh(String employeeGh) {
        return employeeDao.findByEmployeeGh(employeeGh);
    }

    @Override
    public List<EmployeeListView> selectByEmployeeGh(String employeeGh) {
        return employeeDao.selectByEmployeeGh(employeeGh);
    }

    @Override
    public List<EmployeeListView> selectByEmployeeName(String employeeName) {
        return employeeDao.selectByEmployeeName(employeeName);
    }

    @Override
    public void insertEmployee(String employeeGh, String employeeName, String employeeDep, String employeeJob,
                               String employeeWorkplace, String employeeSex, String employeeType) {
        employeeDao.insertEmployee(employeeGh, employeeName, employeeDep, employeeJob, employeeWorkplace, employeeSex, employeeType);
    }

    @Override
    public String selectEmployeeNameByEmployeeGh(String employeeGh) {
        return employeeDao.selectEmployeeNameByEmployeeGh(employeeGh);
    }

    @Override
    public String findByEmployeeName(String employeeName) {
        return employeeDao.findByEmployeeName(employeeName);
    }

    @Override
    public void deleteEmployeeSkill(String employeeGh){
        employeeDao.deleteEmployeeSkill(employeeGh);
    }

    @Override
    public void deleteEmployeeSkillTest(String employeeGh){
        employeeDao.deleteEmployeeSkillTest(employeeGh);
    }

    @Override
    public void insertEmployeeSkill(String employeeGh,Long id){
        employeeDao.insertEmployeeSkill(employeeGh, id);
    }

    @Override
    public void insertEmployeeSkillTest(String employeeGh,Long skillTestId){
        employeeDao.insertEmployeeSkillTest(employeeGh, skillTestId);
    }

    @Override
    public List<EmployeeExperienceView> listEmployeeExperience(String employeeGh){
        return employeeDao.listEmployeeExperience(employeeGh);
    }

    @Override
    public EmployeeCountView MonthInsertPerson(Integer num){
        return employeeDao.MonthInsertPerson(num);
    }

    @Override
    public Integer MonthQuitPerson(Integer num){
        return employeeDao.MonthQuitPerson(num);
    }

    @Override
    public Integer MonthStartPerson(Integer num){
        return employeeDao.MonthStartPerson(num);
    }

    @Override
    public Integer MonthEndPerson(Integer num){
        return employeeDao.MonthEndPerson(num);
    }

    @Override
    public List<String> selectEmployeeQuit(){
        return employeeDao.selectEmployeeQuit();
    }

    @Override
    public List<EmployeeListView> selectAllEmployee(){
        return employeeDao.selectAllEmployee();
    }

    @Override
    public List<EmployeeNumView> selectEmployeeNum(){
        return employeeDao.selectEmployeeNum();
    }

}

