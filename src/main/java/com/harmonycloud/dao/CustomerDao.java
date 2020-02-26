package com.harmonycloud.dao;

import com.harmonycloud.entity.Customer;
import com.harmonycloud.entity.CustomerContacts;
import com.harmonycloud.entity.CustomerDocumentPlanne;
import com.harmonycloud.entity.CustomerDocumentRecord;
import com.harmonycloud.view.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CustomerDao {
    Long selectByName(String customerName);

    int updateByPrimaryKeySelective(Customer customer);

    List<CustomerListView> pageCustomer(String selectCustomerName, String selectCustomerSource, String selectCustomerIndustry,
                                        Date beginTime, Date endTime, String selectEmployeeName, String sort);

    List<CustomerListView> listCustomer();

    List<CustomerListView> pagePartCustomer(String employeeGh,String selectCustomerName, String selectCustomerSource, String selectCustomerIndustry,
                                            Date beginTime, Date endTime, String selectEmployeeName, String sort);

    List<CustomerListView> listPartCustomer(String employeeGh);

    CustomerDetailView selectCustomerDetails(Integer id);

    Integer insertCustomerContact(CustomerContacts contact);

    Integer selectCustomerContactId(String contactsName,Integer contactsPosition,String contactsTel);

    Integer insertCustomer(Customer customer);

    Integer updateFkCustomerId(Integer fkCustomerId,Integer id);

    Integer selectCustomerId(String customerName);

    Integer insertCustomerSalesman(String employeeGh,Integer fkCustomerId);

    List<CustomerContactsListView> listCustomerContacts(Integer customerId);

    Integer selectDefaultContactId(Integer customerId);

    Integer updateCustomer(Customer customer);

    Integer updateCustomerContact(CustomerContacts contact);

    CustomerContacts selectContactDetails(Integer id);

    Integer deleteCustomerContact(Integer id);

    Integer deleteCustomer(Integer id);

    List<CustomerDicView> selectDicData(String dicName);

    Customer selectCustomerAfterUpdate(Integer id);

    List<DocumentRecordListView> listDocumentRecord(String selectCustomerName,String selectDocumentType,Date beginTime,Date endTime,String selectEmployeeName,String sort);

    List<DocumentRecordListView> selectPartDocumentRecord(String employeeGh);

    List<DocumentRecordListView> listPartDocumentRecord(Integer customerId);

    Integer insertDocumentRecord(CustomerDocumentRecord documentRecord);

    CustomerDocumentRecord selectDocumentRecordDetails(Integer id);

    Integer deleteDocumentRecord(Integer id);

    Integer updateDocumentRecord(CustomerDocumentRecord documentRecord);

    List<UserListView> listSales();

    CustomerDocumentPlanne selectDocumentPlanne(Integer customerId);

    Integer insertDocumentPlanne(CustomerDocumentPlanne documentPlanne);

    Integer updateDocumentPlanne(CustomerDocumentPlanne documentPlanne);

    List<String> selectSalesIndustry(String employeeGh);

    List<CustomerListView> selectCustomerByIndustry(String sales);

    List<CustomerDetailView> selectCustomer(SelectCustomerView selectCustomerView);

    List<DocumentRecordListView> selectDocumentRecord(SelectDocumentRecordView selectDocumentRecordView);

    List<DocumentPlanneListView> listDocumentPlanne();

    List<CustomerListView> shareToMe(String employeeGh);

    List<CustomerListView> myShared(String employeeGh);

    List<String> selectAllCustomerName();

    List<UserListView> listSaleman();

    Integer deleteSalemans(Integer fkCustomerId);

    Integer insertIntoCustomerSalesman(String employeeGh,Integer fkCustomerId);

    List<String> getHadShared(Integer id);
}