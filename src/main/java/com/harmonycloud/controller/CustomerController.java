package com.harmonycloud.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.harmonycloud.bean.Message;
import com.harmonycloud.bean.VerifyMessage;
import com.harmonycloud.bean.account.UserListView;
import com.harmonycloud.bean.customer.*;
import com.harmonycloud.bean.document.DocumentPlanneListView;
import com.harmonycloud.bean.document.DocumentRecordListView;
import com.harmonycloud.bean.document.SelectDocumentRecordView;
import com.harmonycloud.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.harmonycloud.util.JsonWebToken.VerifyCode;

/**
 * @author ：frp
 * @date ：Created in 2019/8/1 20:29
 */
@CrossOrigin
@RestController
@Slf4j
@Api(value="客户controller",tags={"客户操作接口"})
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    private HttpServletRequest request;

    /**
     * 返回所有的客户列表（根据权限自动判断）
     * @return message
     */
    @PostMapping("/listCustomer")
    @ApiOperation(value = "返回所有客户信息")
    public Message listCustomer(@RequestBody Map map) throws ParseException {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Integer pageNum = (Integer) map.get("pageNum");
        String selectCustomerName = (String) map.get("selectCustomerName");
        String selectCustomerSource = (String) map.get("selectCustomerSource");
        String selectCustomerIndustry = (String) map.get("selectCustomerIndustry");
        String beginTime1 = (String) map.get("beginTime");
        String endTime1 = (String) map.get("endTime");
        String selectEmployeeName = (String) map.get("selectEmployeeName");
        String sort = (String) map.get("sort");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> data = new HashMap<>();
        Date beginTime = null;
        Date endTime = null;
        if(beginTime1 != null){
            beginTime = df.parse(beginTime1);
        }
        if(endTime1 != null){
            endTime = df.parse(endTime1);
        }
        String employeeGh = res.user.getId();
        String role = res.user.getRole();
        List<CustomerListView> list;
        PageInfo<CustomerListView> pageInfo;
        if (role.contains("Customer_RW_A") || role.contains("Customer_RO")) {
            PageHelper.startPage(pageNum,10);
            list = customerService.pageCustomer(selectCustomerName,selectCustomerSource,selectCustomerIndustry,beginTime,endTime,selectEmployeeName,sort);
            pageInfo = new PageInfo<>(list);
        } else if (role.contains("Customer_RW_S")) {
            PageHelper.startPage(pageNum,10);
            list = customerService.pagePartCustomer(employeeGh,selectCustomerName,selectCustomerSource,selectCustomerIndustry,beginTime,endTime,selectEmployeeName,sort);
            pageInfo = new PageInfo<>(list);
        } else {
            list = customerService.listPartCustomer(employeeGh);
            List<String> listSalesIndustry = customerService.selectSalesIndustry(employeeGh);
            if (listSalesIndustry.size() > 0) {
                log.info("行业负责人负责行业返回成功");
                for (String sales : listSalesIndustry) {
                    List<CustomerListView> list1 = customerService.selectCustomerByIndustry(sales);
                    if (list1 .size() > 0) {
                        log.info("行业负责人客户列表返回成功");
                        list.addAll(list1);
                        for (int i = 0; i < list.size(); i++) {
                            for (int j = list.size() - 1; j > i; j--) {
                                if (list.get(i).getId().equals(list.get(j).getId())) {
                                    list.remove(j);
                                }
                            }
                        }
                    } else {
                        log.warn("行业负责人客户列表返回为空");
                        res.message.setMessage(400, "行业负责人客户列表返回为空");
                    }
                }
                data.put("list", list);
                log.info("部分Customer信息返回成功");
                res.message.setMessage(200, "客户数据返回成功", data);
                return res.message;
            } else {
                log.error("行业负责人负责行业返回为空");
                res.message.setMessage(400, "行业负责人负责行业返回为空");
                return res.message;
            }
        }
        if (pageInfo.getTotal()>0) {
            data.put("pageInfo", pageInfo);
            log.info("部分Customer信息返回成功");
            res.message.setMessage(200, "客户数据返回成功", data);
        } else {
            log.error("部分Customer信息返回为空");
            res.message.setMessage(400, "客户数据为空");
        }
        return res.message;
    }

    /**
     * 获取客户详情（单选多选返回字符串）
     * @param map
     * @return message
     */
    @PostMapping("/getCustomerDetails")
    @ApiOperation(value = "返回部分客户信息")
    public Message getCustomerDetails(@RequestBody Map map) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Integer id = (Integer) map.get("id");
        Map<String, Object> data = new HashMap<>();
        CustomerDetailView customer = customerService.selectCustomerDetails(id);
        if (customer != null) {
            log.info("客户详情返回成功");
            data.put("customer", customer);
            res.message.setMessage(200, "客户详情返回成功", data);
        } else {
            log.error("客户详情返回为空");
            res.message.setMessage(400, "客户详情返回为空");
        }
        return res.message;
    }

    /**
     * 新增客户，同时新增联系人
     * @param listCustomer
     * @return message
     */
    @PostMapping("/insertCustomer")
    @ApiOperation(value = "添加客户")
    public Message insertCustomer(@RequestBody List<Customer> listCustomer) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        for(Customer customer : listCustomer){
            if (customer.getContactsName() != null) {
                String contactsName = customer.getContactsName();
                Integer contactsPosition = customer.getContactsPosition();
                String contactsSpecificPosition = customer.getContactsSpecificPosition();
                String contactsTel = customer.getContactsTel();
                String contactsWechat = customer.getContactsWechat();
                CustomerContacts contact = new CustomerContacts();
                contact.setContactsName(contactsName);
                contact.setContactsPosition(contactsPosition);
                contact.setContactsSpecificPosition(contactsSpecificPosition);
                contact.setContactsTel(contactsTel);
                contact.setContactsWechat(contactsWechat);
                Integer result = customerService.insertCustomerContact(contact);
                if (result > 0) {
                    log.info("客户默认联系人添加成功");
                    Integer id = customerService.selectCustomerContactId(contactsName, contactsPosition, contactsTel);
                    customer.setDefaultContactId(id);
                } else {
                    log.error("客户默认联系人添加失败");
                    res.message.setMessage(403, "客户默认联系人添加失败");
                    return res.message;
                }
            }
            Integer result1 = customerService.insertCustomer(customer);
            if (result1 > 0) {
                log.info("客户添加成功");
                if (customer.getDefaultContactId() != null) {
                    Integer id = customer.getDefaultContactId();
                    String customerName = customer.getCustomerName();
                    Integer fkCustomerId = customerService.selectCustomerId(customerName);
                    if (fkCustomerId != null) {
                        log.info("新增客户id获取成功");
                        Integer result2 = customerService.updateFkCustomerId(fkCustomerId, id);
                        if (result2 > 0) {
                            log.info("联系人关联客户成功");
                            String employeeGh = customer.getDefaultEmployeeGh();
                            Integer result3 = customerService.insertCustomerSalesman(employeeGh, fkCustomerId);
                            if (result3 > 0) {
                                log.info("操作员关联添加成功");
                            } else {
                                log.error("操作员关联添加失败");
                                res.message.setMessage(403, "操作员关联添加失败");
                                return res.message;
                            }
                        } else {
                            log.error("联系人关联客户失败");
                            res.message.setMessage(403, "联系人关联客户失败");
                            return res.message;
                        }
                    } else {
                        log.error("新增客户id获取失败");
                        res.message.setMessage(400, "新增客户id获取失败");
                        return res.message;
                    }
                }
            } else {
                log.error("客户添加失败");
                res.message.setMessage(403, "客户添加成功");
                return res.message;
            }
        }
        res.message.setMessage(200, "新增客户成功");
        return res.message;
    }

    /**
     * 获取客户详情（单选多选返回id）
     * @param map
     * @return message
     */
    @PostMapping("/selectCustomerAfterUpdate")
    @ApiOperation(value = "在修改客户详情前搜索客户信息")
    public Message selectCustomerAfterUpdate(@RequestBody Map map) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Map<String, Object> data = new HashMap<>();
        Integer id = (Integer) map.get("id");
        Customer customer = customerService.selectCustomerAfterUpdate(id);
        if (customer != null) {
            log.info("客户信息返回成功");
            data.put("customer",customer);
            res.message.setMessage(200, "客户信息返回成功", data);
        } else {
            log.error("客户信息返回为空");
            res.message.setMessage(400, "客户信息返回为空");
        }
        return res.message;
    }

    /**
     * 修改客户信息
     * @param customer
     * @return message
     */
    @PostMapping("/updateCustomer")
    @ApiOperation(value = "客户详情修改")
    public Message updateCustomer(@RequestBody Customer customer) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Integer result = customerService.updateCustomer(customer);
        if (result > 0) {
            log.info("客户信息修改成功");
            res.message.setMessage(200, "客户信息修改成功");
        } else {
            log.error("客户信息修改失败");
            res.message.setMessage(403, "客户信息修改失败");
        }
        return res.message;
    }

    /**
     * 删除客户
     * @param map
     * @return message
     */
    @DeleteMapping("/deleteCustomer")
    @ApiOperation(value = "删除客户")
    public Message deleteCustomer(@RequestBody Map map) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Integer id = (Integer) map.get("id");
        Integer result = customerService.deleteCustomer(id);
        if (result > 0) {
            log.info("客户删除成功");
            res.message.setMessage(200, "客户删除成功");
        } else {
            log.error("客户删除失败");
            res.message.setMessage(403, "客户删除失败");
        }
        return res.message;
    }

    /**
     * 获取客户联系人列表
     * @param map
     * @return message
     */
    @PostMapping("/listCustomerContacts")
    @ApiOperation(value = "获取客户联系人列表")
    public Message listCustomerContacts(@RequestBody Map map) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Map<String, Object> data = new HashMap<>();
        Integer customerId = (Integer) map.get("customerId");
        List<CustomerContactsListView> list = customerService.listCustomerContacts(customerId);
        if (list != null) {
            log.info("客户联系人信息返回成功");
            Integer defaultContactId = customerService.selectDefaultContactId(customerId);
            if (defaultContactId != null) {
                log.info("默认客户联系人返回成功");
                data.put("list", list);
                data.put("total", list.size());
                data.put("defaultContactId", defaultContactId);
                res.message.setMessage(200, "客户联系人信息返回成功", data);
            } else {
                log.warn("默认客户联系人为空");
                res.message.setMessage(400, "默认客户联系人为空");
                return res.message;
            }
        } else {
            log.error("客户联系人信息返回为空");
            res.message.setMessage(400, "客户联系人信息返回为空");
            return res.message;
        }
        return res.message;
    }

    /**
     * 修改客户联系人信息
     * @param contact
     * @return message
     */
    @PostMapping("/updateCustomerContact")
    @ApiOperation(value = "客户联系人详情修改")
    public Message updateCustomerContact(@RequestBody CustomerContacts contact) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Integer result = customerService.updateCustomerContact(contact);
        if (result > 0) {
            log.info("客户联系人信息修改成功");
            res.message.setMessage(200, "客户联系人信息修改成功");
        } else {
            log.error("客户联系人信息修改失败");
            res.message.setMessage(403, "客户联系人信息修改失败");
        }
        return res.message;
    }

    /**
     * 新增客户联系人
     * @param listContact
     * @return message
     */
    @PostMapping("/insertCustomerContact")
    @ApiOperation(value = "添加客户联系人")
    public Message insertCustomerContact(@RequestBody List<CustomerContacts> listContact) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        for(CustomerContacts contact : listContact){
            Integer result = customerService.insertCustomerContact(contact);
            if (result > 0) {
                log.info("客户联系人添加成功");
                res.message.setMessage(200, "客户联系人添加成功");
            } else {
                log.error("客户联系人添加失败");
                res.message.setMessage(403, "客户联系人添加失败");
            }
        }
        return res.message;
    }

    /**
     * 获取客户联系人详情信息
     * @param map
     * @return message
     */
    @PostMapping("/selectContactDetails")
    @ApiOperation(value = "获取联系人详情")
    public Message selectContactDetails(@RequestBody Map map) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Map<String, Object> data = new HashMap<>();
        Integer id = (Integer) map.get("id");
        CustomerContacts customerContact = customerService.selectContactDetails(id);
        if (customerContact != null) {
            log.info(id + " 联系人详情返回成功");
            data.put("customerContact", customerContact);
            res.message.setMessage(200, id + " 联系人详情返回成功", data);
        } else {
            log.error(id + " 联系人详情返回失败");
            res.message.setMessage(400, id + " 联系人详情返回失败");
        }
        return res.message;
    }

    /**
     * 删除客户联系人
     * @param map
     * @return message
     */
    @DeleteMapping("/deleteCustomerContact")
    @ApiOperation(value = "删除联系人")
    public Message deleteCustomerContact(@RequestBody Map map) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Integer id = (Integer) map.get("id");
        Integer result = customerService.deleteCustomerContact(id);
        if (result > 0) {
            log.info("客户联系人删除成功");
            res.message.setMessage(200, "客户联系人删除成功");
        } else {
            log.error("客户联系人删除失败");
            res.message.setMessage(403, "客户联系人删除失败");
        }
        return res.message;
    }

    /**
     * 字典表字段查询，获取字典信息
     * @param map
     * @return message
     */
    @PostMapping("/selectDicData")
    @ApiOperation(value = "客户字段字典表查询")
    public Message selectDicData(@RequestBody Map map) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Map<String, Object> data = new HashMap<>();
        String dicName = (String) map.get("dicName");
        List<CustomerDicView> list = customerService.selectDicData(dicName);
        if (list != null) {
            log.info("字典信息返回成功");
            data.put("list", list);
            res.message.setMessage(200, "字典信息返回成功", data);
        } else {
            log.error("字典信息返回为空");
            res.message.setMessage(400, "字典信息返回为空");
        }
        return res.message;
    }

    /**
     * 所有客户字段查询
     * @return message
     */
    @PostMapping("/selectAllDicData")
    @ApiOperation(value = "所有客户字段查询")
    public Message selectAllDicData() {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Map<String, Object> data = new HashMap<>();
        List<CustomerDicView> list = customerService.selectAllDicData();
        if (list != null) {
            log.info("客户字段返回成功");
            data.put("list", list);
            res.message.setMessage(200, "客户字段返回成功", data);
        } else {
            log.error("客户字段返回为空");
            res.message.setMessage(400, "客户字段返回为空");
        }
        return res.message;
    }

    /**
     * 获取所有销售业务员信息
     * @return message
     */
    @GetMapping("/listSales")
    @ApiOperation(value = "获取所有业务员（不包含行业）")
    public Message listSales() {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        List<UserListView> list = customerService.listSales();
        Map<String, Object> data = new HashMap<>();
        if (list != null) {
            log.info("业务员列表返回成功");
            data.put("list", list);
            data.put("total", list.size());
            res.message.setMessage(200, "业务员列表返回成功", data);
        } else {
            log.error("业务员列表返回为空");
            res.message.setMessage(400, "业务员列表返回为空");
        }
        return res.message;
    }

    /**
     * 获取客户跟单记录列表（根据权限自动判断）
     * @return message
     */
    @PostMapping("/listDocumentRecord")
    @ApiOperation(value = "获取跟单记录列表")
    public Message listDocumentRecord(@RequestBody Map map) throws ParseException {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        String employeeGh = res.user.getId();
        String role = res.user.getRole();
        Integer pageNum = (Integer) map.get("pageNum");
        String selectCustomerName = (String) map.get("selectCustomerName");
        String selectDocumentType = (String) map.get("selectDocumentType");
        String beginTime1 = (String) map.get("beginTime");
        String endTime1 = (String) map.get("endTime");
        String selectEmployeeName = (String) map.get("selectEmployeeName");
        String sort = (String) map.get("sort");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date beginTime = null;
        Date endTime = null;
        if(beginTime1 != null){
            beginTime = df.parse(beginTime1);
        }
        if(endTime1 != null){
            endTime = df.parse(endTime1);
        }
        Map<String, Object> data = new HashMap<>();
        List<DocumentRecordListView> list = new ArrayList<>();
        PageInfo<DocumentRecordListView> pageInfo;
        if (role.contains("Customer_RW_A") || role.contains("Customer_RO")) {
            PageHelper.startPage(pageNum,10);
            list = customerService.listDocumentRecord(selectCustomerName,selectDocumentType,beginTime,endTime,selectEmployeeName,sort);
            pageInfo = new PageInfo<>(list);
        } else {
            List<CustomerListView> listCustomer = customerService.listPartCustomer(employeeGh);
            if (listCustomer != null && role.contains("Customer_RW_S")) {
                log.info("客户列表返回成功");
                for (CustomerListView customerListView : listCustomer) {
                    Integer customerId = customerListView.getId();
                    List<DocumentRecordListView> list1 = customerService.listPartDocumentRecord(customerId);
                    List<DocumentRecordListView> listView = customerService.selectPartDocumentRecord(employeeGh);
                    if (list1 != null || listView != null) {
                        list.addAll(list1);
                        list.addAll(listView);
                    } else {
                        log.error("部分跟单记录返回为空");
                        res.message.setMessage(400, "部分跟单记录返回为空");
                        return res.message;
                    }
                }
                for (int i = 0; i < list.size(); i++) {
                    for (int j = list.size() - 1; j > i; j--) {
                        if (list.get(i).getId().equals(list.get(j).getId())) {
                            list.remove(j);
                        }
                    }
                }
                data.put("list",list);
                data.put("total",list.size());
                res.message.setMessage(200,"跟单记录列表返回成功",data);
                return res.message;
            } else if (listCustomer == null) {
                log.error("客户列表返回为空");
                res.message.setMessage(400, "客户列表返回为空");
                return res.message;
            } else {
                List<String> listSalesIndustry = customerService.selectSalesIndustry(employeeGh);
                if (listSalesIndustry != null) {
                    log.info("行业负责人负责行业返回成功");
                    for (String sales : listSalesIndustry) {
                        List<CustomerListView> list2 = customerService.selectCustomerByIndustry(sales);
                        if (list2.size() > 0) {
                            log.info("行业负责人客户列表返回成功");
                            listCustomer.addAll(list2);
                        } else {
                            log.error("部分跟单记录返回为空");
                            res.message.setMessage(400, "部分跟单记录返回为空");
                            return res.message;
                        }
                    }
                    for (int i = 0; i < listCustomer.size(); i++) {
                        for (int j = listCustomer.size() - 1; j > i; j--) {
                            if (listCustomer.get(i).getId().equals(listCustomer.get(j).getId())) {
                                listCustomer.remove(j);
                            }
                        }
                    }
                    for (CustomerListView customerListView : listCustomer) {
                        Integer customerId = customerListView.getId();
                        List<DocumentRecordListView> list3 = customerService.listPartDocumentRecord(customerId);
                        if (list3.size() > 0) {
                            list.addAll(list3);
                        }
                    }
                    List<DocumentRecordListView> listView = customerService.selectPartDocumentRecord(employeeGh);
                    if(listView.size() > 0){
                        list.addAll(listView);
                        for (int i = 0; i < list.size(); i++) {
                            for (int j = list.size() - 1; j > i; j--) {
                                if (list.get(i).getId().equals(list.get(j).getId())) {
                                    list.remove(j);
                                }
                            }
                        }
                    }
                    data.put("list",list);
                    data.put("total",list.size());
                    res.message.setMessage(200,"跟单记录列表返回成功",data);
                    return res.message;
                } else {
                    log.error("行业负责人负责行业返回为空");
                    res.message.setMessage(400, "行业负责人负责行业返回为空");
                    return res.message;
                }
            }
        }
        if (pageInfo.getTotal()>0) {
            log.info("跟单记录列表返回成功");
            data.put("pageInfo", pageInfo);
            res.message.setMessage(200, "跟单记录列表返回成功", data);
        } else {
            log.error("跟单记录列表返回为空");
            res.message.setMessage(400, "跟单记录列表返回为空");
        }
        return res.message;
    }

    /**
     * 获取单个客户的跟单记录列表
     * @param map
     * @return message
     */
    @PostMapping("/listPartDocumentRecord")
    @ApiOperation(value = "获取单个客户的跟单记录列表")
    public Message listPartDocumentRecord(@RequestBody Map map) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Integer customerId = (Integer) map.get("customerId");
        Map<String, Object> data = new HashMap<>();
        List<DocumentRecordListView> list = customerService.listPartDocumentRecord(customerId);
        if (list != null) {
            log.info("客户跟单记录列表返回成功");
            data.put("list", list);
            data.put("total", list.size());
            res.message.setMessage(200, "客户跟单记录列表返回成功", data);
        } else {
            log.error("客户跟单记录列表返回为空");
            res.message.setMessage(400, "客户跟单记录列表返回为空");
        }
        return res.message;
    }

    /**
     * 新增客户跟单记录
     * @param listDocumentRecord
     * @return message
     */
    @PostMapping("/insertDocumentRecord")
    @ApiOperation(value = "增加跟单记录")
    public Message insertDocumentRecord(@RequestBody List<CustomerDocumentRecord> listDocumentRecord){
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        for(CustomerDocumentRecord documentRecord : listDocumentRecord){
            Integer result = customerService.insertDocumentRecord(documentRecord);
            if (result > 0) {
                log.info("跟单记录添加成功");
                res.message.setMessage(200, "跟单记录添加成功");
            } else {
                log.error("跟单记录添加失败");
                res.message.setMessage(403, "跟单记录添加失败");
            }
        }
        return res.message;
    }

    /**
     * 获取客户跟单记录详情
     * @param map
     * @return message
     */
    @PostMapping("/selectDocumentRecordDetails")
    @ApiOperation(value = "获取跟单记录详情")
    public Message selectDocumentRecordDetails(@RequestBody Map map) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Map<String, Object> data = new HashMap<>();
        Integer id = (Integer) map.get("id");
        CustomerDocumentRecord documentRecord = customerService.selectDocumentRecordDetails(id);
        if (documentRecord != null) {
            log.info("获取跟单记录详情成功");
            data.put("documentRecord", documentRecord);
            res.message.setMessage(200, "获取跟单记录详情成功", data);
        } else {
            log.error("获取跟单记录详情为空");
            res.message.setMessage(400, "获取跟单记录详情为空");
        }
        return res.message;
    }

    /**
     * 删除客户跟单记录
     * @param map
     * @return message
     */
    @DeleteMapping("/deleteDocumentRecord")
    @ApiOperation(value = "删除跟单记录")
    public Message deleteDocumentRecord(@RequestBody Map map) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Integer id = (Integer) map.get("id");
        Integer result = customerService.deleteDocumentRecord(id);
        if (result > 0) {
            log.info("删除跟单记录成功");
            res.message.setMessage(200, "删除跟单记录成功");
        } else {
            log.error("删除跟单记录失败");
            res.message.setMessage(403, "删除跟单记录失败");
        }
        return res.message;
    }

    /**
     * 修改客户跟单记录
     * @param documentRecord
     * @return message
     */
    @PostMapping("/updateDocumentRecord")
    @ApiOperation(value = "更新跟单记录")
    public Message updateDocumentRecord(@RequestBody CustomerDocumentRecord documentRecord) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Integer result = customerService.updateDocumentRecord(documentRecord);
        if (result > 0) {
            log.info("更新跟单记录成功");
            res.message.setMessage(200, "更新跟单记录成功");
        } else {
            log.error("更新跟单记录失败");
            res.message.setMessage(403, "更新跟单记录失败");
        }
        return res.message;
    }

    /**
     * 获取客户跟单进度详情
     * @param map
     * @return message
     */
    @PostMapping("/selectDocumentPlanne")
    @ApiOperation(value = "获取客户跟单进度详情")
    public Message selectDocumentPlanne(@RequestBody Map map) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Map<String, Object> data = new HashMap<>();
        Integer customerId = (Integer) map.get("customerId");
        CustomerDocumentPlanne documentPlanne = customerService.selectDocumentPlanne(customerId);
        if (documentPlanne != null) {
            log.info("获取跟单记录成功");
            data.put("documentPlanne", documentPlanne);
            res.message.setMessage(200, "获取跟单记录成功", data);
        } else {
            log.error("获取跟单记录为空");
            res.message.setMessage(400, "获取跟单记录为空");
        }
        return res.message;
    }

    /**
     * 新增客户跟单进度
     * @param listDocumentPlanne
     * @return message
     */
    @PostMapping("/insertDocumentPlanne")
    @ApiOperation(value = "新增跟单进度")
    public Message insertDocumentPlanne(@RequestBody List<CustomerDocumentPlanne> listDocumentPlanne) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        for(CustomerDocumentPlanne documentPlanne : listDocumentPlanne){
            Integer result = customerService.insertDocumentPlanne(documentPlanne);
            if (result > 0) {
                log.info("新增跟单进度成功");
                res.message.setMessage(200, "新增跟单进度成功");
            } else {
                log.error("新增跟单进度失败");
                res.message.setMessage(403, "新增跟单进度失败");
            }
        }
        return res.message;
    }

    /**
     * 修改客户跟单进度
     * @param documentPlanne
     * @return message
     */
    @PostMapping("/updateDocumentPlanne")
    @ApiOperation(value = "修改跟单进度")
    public Message updateDocumentPlanne(@RequestBody CustomerDocumentPlanne documentPlanne) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Integer result = customerService.updateDocumentPlanne(documentPlanne);
        if (result > 0) {
            log.info("修改跟单进度成功");
            res.message.setMessage(200, "修改跟单进度成功");
        } else {
            log.error("修改跟单进度失败");
            res.message.setMessage(403, "修改跟单进度失败");
        }
        return res.message;
    }

    /**
     * 客户筛选
     * @param selectCustomerView
     * @return res.message
     */
    @PostMapping("/selectCustomer")
    @ApiOperation(value = "客户筛选")
    public Message selectCustomer(@RequestBody SelectCustomerView selectCustomerView){
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        String role = res.user.getRole();
        String employeeGh = res.user.getId();
        List<CustomerListView> listCustomer;
        Map<String, Object> data = new HashMap<>();
        List<CustomerDetailView> list = customerService.selectCustomer(selectCustomerView);
        if (role.contains("Customer_RW_A") || role.contains("Customer_RO")) {
            listCustomer = customerService.listCustomer();
        } else if (role.contains("Customer_RW_S")) {
            listCustomer = customerService.listPartCustomer(employeeGh);
        } else {
            listCustomer = customerService.listPartCustomer(employeeGh);
            List<String> listSalesIndustry = customerService.selectSalesIndustry(employeeGh);
            if (listSalesIndustry.size() > 0) {
                log.info("行业负责人负责行业返回成功");
                for (String sales : listSalesIndustry) {
                    List<CustomerListView> list2 = customerService.selectCustomerByIndustry(sales);
                    if (list2 .size() > 0) {
                        log.info("行业负责人客户列表返回成功");
                        listCustomer.addAll(list2);
                        for (int i = 0; i < listCustomer.size(); i++) {
                            for (int j = listCustomer.size() - 1; j > i; j--) {
                                if (listCustomer.get(i).getId().equals(listCustomer.get(j).getId())) {
                                    listCustomer.remove(j);
                                }
                            }
                        }
                    } else {
                        log.warn("行业负责人客户列表返回为空");
                    }
                }
            } else {
                log.error("行业负责人负责行业返回为空");
            }
        }
        List<CustomerDetailView> list1 = new ArrayList<>();
        if(list.size() > 0 && listCustomer.size() > 0){
            log.info("成功筛选客户，返回数据成功");
            for(CustomerDetailView customerDetailView:list){
                for (CustomerListView customerListView:listCustomer){
                    if(customerDetailView.getId().equals(customerListView.getId())){
                        list1.add(customerDetailView);
                    }
                }
            }
            data.put("list",list1);
            data.put("total",list1.size());
            res.message.setMessage(200,"成功筛选客户，返回数据成功",data);
        }else {
            log.error("筛选客户为空");
            res.message.setMessage(400,"筛选客户为空");
        }
        return res.message;
    }

    /**
     * 跟单记录筛选
     * @param selectDocumentRecordView
     * @return res.message
     */
    @PostMapping("/selectDocumentRecord")
    @ApiOperation(value = "跟单记录筛选")
    public Message selectDocumentRecord(@RequestBody SelectDocumentRecordView selectDocumentRecordView){
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Map<String, Object> data = new HashMap<>();
        List<DocumentRecordListView> list = customerService.selectDocumentRecord(selectDocumentRecordView);
        String role = res.user.getRole();
        String employeeGh = res.user.getId();
        List<CustomerListView> listCustomer;
        if (role.contains("Customer_RW_A") || role.contains("Customer_RO")) {
            listCustomer = customerService.listCustomer();
        } else if (role.contains("Customer_RW_S")) {
            listCustomer = customerService.listPartCustomer(employeeGh);
        } else {
            listCustomer = customerService.listPartCustomer(employeeGh);
            List<String> listSalesIndustry = customerService.selectSalesIndustry(employeeGh);
            if (listSalesIndustry.size() > 0) {
                log.info("行业负责人负责行业返回成功");
                for (String sales : listSalesIndustry) {
                    List<CustomerListView> list2 = customerService.selectCustomerByIndustry(sales);
                    if (list2 .size() > 0) {
                        log.info("行业负责人客户列表返回成功");
                        listCustomer.addAll(list2);
                        for (int i = 0; i < listCustomer.size(); i++) {
                            for (int j = listCustomer.size() - 1; j > i; j--) {
                                if (listCustomer.get(i).getId().equals(listCustomer.get(j).getId())) {
                                    listCustomer.remove(j);
                                }
                            }
                        }
                    } else {
                        log.warn("行业负责人客户列表返回为空");
                    }
                }
            } else {
                log.error("行业负责人负责行业返回为空");
            }
        }
        List<DocumentRecordListView> list1 = new ArrayList<>();
        if(list.size()>0&&listCustomer.size()>0){
            log.info("成功筛选跟单记录，返回数据成功");
            for(DocumentRecordListView documentRecordListView:list){
                for (CustomerListView customerListView:listCustomer){
                    if (documentRecordListView.getCustomerId().equals(customerListView.getId())){
                        list1.add(documentRecordListView);
                    }
                }
            }
            data.put("list",list1);
            data.put("total",list1.size());
            res.message.setMessage(200,"成功筛选跟单记录，返回数据成功",data);
        }else {
            log.error("筛选跟单记录为空");
            res.message.setMessage(400,"筛选跟单记录为空");
        }
        return res.message;
    }

    /**
     * 跟单进度导出
     * @return res.message
     */
    @GetMapping("/listDocumentPlanne")
    @ApiOperation(value = "跟单进度导出")
    public Message listDocumentPlanne(){
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Map<String, Object> data = new HashMap<>();
        String role = res.user.getRole();
        String employeeGh = res.user.getId();
        List<DocumentPlanneListView> list = customerService.listDocumentPlanne();
        List<CustomerListView> listCustomer;
        if (role.contains("Customer_RW_A") || role.contains("Customer_RO")) {
            listCustomer = customerService.listCustomer();
        } else if (role.contains("Customer_RW_S")) {
            listCustomer = customerService.listPartCustomer(employeeGh);
        } else {
            listCustomer = customerService.listPartCustomer(employeeGh);
            List<String> listSalesIndustry = customerService.selectSalesIndustry(employeeGh);
            if (listSalesIndustry.size() > 0) {
                log.info("行业负责人负责行业返回成功");
                for (String sales : listSalesIndustry) {
                    List<CustomerListView> list2 = customerService.selectCustomerByIndustry(sales);
                    if (list2 .size() > 0) {
                        log.info("行业负责人客户列表返回成功");
                        listCustomer.addAll(list2);
                        for (int i = 0; i < listCustomer.size(); i++) {
                            for (int j = listCustomer.size() - 1; j > i; j--) {
                                if (listCustomer.get(i).getId().equals(listCustomer.get(j).getId())) {
                                    listCustomer.remove(j);
                                }
                            }
                        }
                    } else {
                        log.warn("行业负责人客户列表返回为空");
                    }
                }
            } else {
                log.error("行业负责人负责行业返回为空");
            }
        }
        List<DocumentPlanneListView> list1 = new ArrayList<>();
        if(list.size()>0 && listCustomer.size()>0){
            log.info("成功筛选跟单进度，返回数据成功");
            for(DocumentPlanneListView documentPlanneListView:list){
                for(CustomerListView customerListView:listCustomer){
                    if(documentPlanneListView.getCustomerId().equals(customerListView.getId())){
                        list1.add(documentPlanneListView);
                    }
                }
            }
            data.put("list",list1);
            data.put("total",list1.size());
            res.message.setMessage(200,"成功筛选跟单进度，返回数据成功",data);
        }else {
            log.error("筛选跟单进度为空");
            res.message.setMessage(400,"筛选跟单进度为空");
        }
        return res.message;
    }

    /**
     * 客户共享：共享给我
     * @return res.message
     */
    @GetMapping("/shareToMe")
    @ApiOperation(value = "客户共享：共享给我")
    public Message shareToMe(){
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        String employeeGh = res.user.getId();
        Map<String, Object> data = new HashMap<>();
        List<CustomerListView> list = customerService.shareToMe(employeeGh);
        if(list.size()>0){
            log.info("客户共享：共享给我返回成功");
            data.put("list",list);
            res.message.setMessage(200,"客户共享：共享给我返回成功",data);
        }else {
            log.error("客户共享：共享给我返回为空");
            res.message.setMessage(400,"客户共享：共享给我返回为空");
        }
        return res.message;
    }

    /**
     * 客户共享：我的共享
     * @return res.message
     */
    @GetMapping("/myShared")
    @ApiOperation(value = "客户共享：我的共享")
    public Message myShared(){
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        String employeeGh = res.user.getId();
        Map<String, Object> data = new HashMap<>();
        List<CustomerListView> list = customerService.myShared(employeeGh);
        if(list.size()>0){
            log.info("客户共享：我的共享返回成功");
            data.put("list",list);
            res.message.setMessage(200,"客户共享：我的共享返回成功",data);
        }else {
            log.error("客户共享：我的共享返回为空");
            res.message.setMessage(400,"客户共享：我的共享返回为空");
        }
        return res.message;
    }

    /**
     * 返回所有的客户名称
     * @return res.message
     */
    @GetMapping("/selectAllCustomerName")
    @ApiOperation(value = "获取所有客户的名字")
    public Message selectAllCustomerName(){
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Map<String, Object> data = new HashMap<>();
        List<String> list = customerService.selectAllCustomerName();
        if(list.size()>0){
            log.info("客户名称返回成功");
            data.put("list",list);
            data.put("total",list.size());
            res.message.setMessage(200,"客户名称返回成功",data);
        }else {
            log.error("客户名称返回为空");
            res.message.setMessage(400,"客户名称返回为空");
        }
        return res.message;
    }

    /**
     * 获取业务员的列表（姓名及行业线）
     * @return res.message
     */
    @GetMapping("/listSaleman")
    @ApiOperation(value = "获取业务员列表")
    public Message listSaleman(){
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Map<String, Object> data = new HashMap<>();
        List<UserListView> list = customerService.listSaleman();
        if(list.size()>0){
            log.info("业务员列表获取成功");
            data.put("list",list);
            data.put("total",list.size());
            res.message.setMessage(200,"业务员列表获取成功",data);
        }else {
            log.error("业务员列表获取为空");
            res.message.setMessage(400,"业务员列表获取为空");
        }
        return res.message;
    }

    /**
     * 客户共享
     * @param map
     * @return res.message
     */
    @PostMapping("/shareCustomer")
    @ApiOperation(value = "客户共享")
    public Message shareCustomer(@RequestBody Map map){
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        String listEmployeeGh = (String) map.get("employeeGh");
        Integer fkCustomerId = (Integer) map.get("fkCustomerId");
        String[] split = listEmployeeGh.split(";");
        if(!listEmployeeGh.equals("")){
            Integer result1 = customerService.deleteSalemans(fkCustomerId);
            for(String employeeGh:split){
                Integer result = customerService.insertIntoCustomerSalesman(employeeGh,fkCustomerId);
                if(result>0){
                    log.info("客户共享成功");
                    res.message.setMessage(200,"客户共享成功");
                }else {
                    log.error("客户共享失败");
                    res.message.setMessage(400,"客户共享失败");
                }
            }
            
        }
        return res.message;
    }

    /**
     * 获取已经共享的业务员
     * @param map
     * @return res.message
     */
    @PostMapping("/getHadShared")
    @ApiOperation(value = "获取已经共享的业务员")
    public Message getHadShared(@RequestBody Map map){
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Map<String, Object> data = new HashMap<>();
        Integer id = (Integer) map.get("id");
        List<String> list = customerService.getHadShared(id);
        if(list.size()>0){
            log.info("获取已经共享的业务员成功");
            data.put("list",list);
            data.put("total",list.size());
            res.message.setMessage(200,"获取已经共享的业务员成功",data);
        }else {
            log.error("获取已经共享的业务员失败");
            res.message.setMessage(400,"获取已经共享的业务员失败");
        }
        return res.message;
    }
}
