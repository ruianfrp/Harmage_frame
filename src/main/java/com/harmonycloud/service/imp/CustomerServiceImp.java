package com.harmonycloud.service.imp;


import com.harmonycloud.bean.account.UserListView;
import com.harmonycloud.bean.customer.*;
import com.harmonycloud.bean.document.DocumentPlanneListView;
import com.harmonycloud.bean.document.DocumentRecordListView;
import com.harmonycloud.bean.document.SelectDocumentRecordView;
import com.harmonycloud.dao.CustomerDao;
import com.harmonycloud.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author ：lxl
 * @date ：Created in 2019/8/1 20:29
 */
@Service
public class CustomerServiceImp implements CustomerService {


    @Autowired
    CustomerDao customerDao;

    @Override
    public Long selectByName(String customerName) {
        return customerDao.selectByName(customerName);
    }

    @Override
    public int updateByPrimaryKeySelective(Customer customer) {
        return customerDao.updateByPrimaryKeySelective(customer);
    }

    @Override
    public List<CustomerListView> pageCustomer(String selectCustomerName, String selectCustomerSource, String selectCustomerIndustry,
                                               Date beginTime, Date endTime, String selectEmployeeName, String sort){
        return customerDao.pageCustomer(selectCustomerName, selectCustomerSource, selectCustomerIndustry, beginTime, endTime, selectEmployeeName, sort);
    }

    @Override
    public List<CustomerListView> listCustomer(){
        return customerDao.listCustomer();
    }

    @Override
    public List<CustomerListView> pagePartCustomer(String employeeGh,String selectCustomerName, String selectCustomerSource, String selectCustomerIndustry,
                                            Date beginTime, Date endTime, String selectEmployeeName, String sort){
        return customerDao.pagePartCustomer(employeeGh, selectCustomerName, selectCustomerSource, selectCustomerIndustry, beginTime, endTime, selectEmployeeName, sort);
    }

    @Override
    public List<CustomerListView> listPartCustomer(String employeeGh){
        return customerDao.listPartCustomer(employeeGh);
    }

    @Override
    public CustomerDetailView selectCustomerDetails(Integer id){
        return customerDao.selectCustomerDetails(id);
    }

    @Override
    public Integer insertCustomerContact(CustomerContacts contact){
        return customerDao.insertCustomerContact(contact);
    }

    @Override
    public Integer selectCustomerContactId(String contactsName,Integer contactsPosition,String contactsTel){
        return customerDao.selectCustomerContactId(contactsName, contactsPosition, contactsTel);
    }

    @Override
    public Integer insertCustomer(Customer customer){
        return customerDao.insertCustomer(customer);
    }

    @Override
    public Integer updateFkCustomerId(Integer fkCustomerId,Integer id){
        return customerDao.updateFkCustomerId(fkCustomerId, id);
    }

    @Override
    public Integer selectCustomerId(String customerName){
        return customerDao.selectCustomerId(customerName);
    }

    @Override
    public Integer insertCustomerSalesman(String employeeGh,Integer fkCustomerId){
        return customerDao.insertCustomerSalesman(employeeGh,fkCustomerId);
    }

    @Override
    public List<CustomerContactsListView> listCustomerContacts(Integer customerId){
        return customerDao.listCustomerContacts(customerId);
    }

    @Override
    public Integer selectDefaultContactId(Integer customerId){
        return customerDao.selectDefaultContactId(customerId);
    }

    @Override
    public Integer updateCustomer(Customer customer){
        return customerDao.updateCustomer(customer);
    }

    @Override
    public Integer updateCustomerContact(CustomerContacts contact){
        return customerDao.updateCustomerContact(contact);
    }

    @Override
    public CustomerContacts selectContactDetails(Integer id){
        return customerDao.selectContactDetails(id);
    }

    @Override
    public Integer deleteCustomerContact(Integer id){
        return customerDao.deleteCustomerContact(id);
    }

    @Override
    public Integer deleteCustomer(Integer id){
        return customerDao.deleteCustomer(id);
    }

    @Override
    public List<CustomerDicView> selectDicData(String dicName){
        return customerDao.selectDicData(dicName);
    }

    @Override
    public Customer selectCustomerAfterUpdate(Integer id){
        return customerDao.selectCustomerAfterUpdate(id);
    }

    @Override
    public List<DocumentRecordListView> listDocumentRecord(String selectCustomerName,String selectDocumentType,Date beginTime,Date endTime,String selectEmployeeName,String sort){
        return customerDao.listDocumentRecord(selectCustomerName, selectDocumentType, beginTime, endTime, selectEmployeeName, sort);
    }

    @Override
    public List<DocumentRecordListView> listPartDocumentRecord(Integer customerId){
        return customerDao.listPartDocumentRecord(customerId);
    }

    @Override
    public List<DocumentRecordListView> selectPartDocumentRecord(String employeeGh){
        return customerDao.selectPartDocumentRecord(employeeGh);
    }

    @Override
    public Integer insertDocumentRecord(CustomerDocumentRecord documentRecord){
        return customerDao.insertDocumentRecord(documentRecord);
    }

    @Override
    public CustomerDocumentRecord selectDocumentRecordDetails(Integer id){
        return customerDao.selectDocumentRecordDetails(id);
    }

    @Override
    public Integer deleteDocumentRecord(Integer id){
        return customerDao.deleteDocumentRecord(id);
    }

    @Override
    public Integer updateDocumentRecord(CustomerDocumentRecord documentRecord){
        return customerDao.updateDocumentRecord(documentRecord);
    }

    @Override
    public List<UserListView> listSales(){
        return customerDao.listSales();
    }

    @Override
    public CustomerDocumentPlanne selectDocumentPlanne(Integer customerId){
        return customerDao.selectDocumentPlanne(customerId);
    }

    @Override
    public Integer insertDocumentPlanne(CustomerDocumentPlanne documentPlanne){
        return customerDao.insertDocumentPlanne(documentPlanne);
    }

    @Override
    public Integer updateDocumentPlanne(CustomerDocumentPlanne documentPlanne){
        return customerDao.updateDocumentPlanne(documentPlanne);
    }

    @Override
    public List<String> selectSalesIndustry(String employeeGh){
        return customerDao.selectSalesIndustry(employeeGh);
    }

    @Override
    public List<CustomerListView> selectCustomerByIndustry(String sales){
        return customerDao.selectCustomerByIndustry(sales);
    }

    @Override
    public List<CustomerDetailView> selectCustomer(SelectCustomerView selectCustomerView){
        return customerDao.selectCustomer(selectCustomerView);
    }

    @Override
    public List<DocumentRecordListView> selectDocumentRecord(SelectDocumentRecordView selectDocumentRecordView){
        return customerDao.selectDocumentRecord(selectDocumentRecordView);
    }

    @Override
    public List<DocumentPlanneListView> listDocumentPlanne(){
        return customerDao.listDocumentPlanne();
    }

    @Override
    public List<CustomerListView> shareToMe(String employeeGh){
        return customerDao.shareToMe(employeeGh);
    }

    @Override
    public List<CustomerListView> myShared(String employeeGh){
        return customerDao.myShared(employeeGh);
    }

    @Override
    public List<String> selectAllCustomerName(){
        return customerDao.selectAllCustomerName();
    }

    @Override
    public List<UserListView> listSaleman(){
        return customerDao.listSaleman();
    }

    @Override
    public Integer deleteSalemans(Integer fkCustomerId){
        return customerDao.deleteSalemans(fkCustomerId);
    }

    @Override
    public Integer insertIntoCustomerSalesman(String employeeGh,Integer fkCustomerId){
        return customerDao.insertIntoCustomerSalesman(employeeGh,fkCustomerId);
    }

    @Override
    public List<String> getHadShared(Integer id){
        return customerDao.getHadShared(id);
    }
}
