package com.harmonycloud.controller;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.Region;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.harmonycloud.bean.Message;
import com.harmonycloud.bean.VerifyMessage;
import com.harmonycloud.bean.account.UserListView;
import com.harmonycloud.bean.contract.*;
import com.harmonycloud.bean.customer.*;
import com.harmonycloud.bean.document.DocumentPlanneListView;
import com.harmonycloud.bean.document.DocumentRecordListView;
import com.harmonycloud.bean.document.SelectDocumentRecordView;
import com.harmonycloud.bean.project.ProjectListView;
import com.harmonycloud.service.ContractService;
import com.harmonycloud.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${frame.ip}")
    private String frame;

    @Autowired
    CustomerService customerService;

    @Autowired
    ContractService contractService;

    @Autowired
    private HttpServletRequest request;

    /**
     * 返回所有的客户列表（根据权限自动判断）
     *
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
        if (beginTime1 != null) {
            beginTime = df.parse(beginTime1);
        }
        if (endTime1 != null) {
            endTime = df.parse(endTime1);
        }
        String employeeGh = res.user.getId();
        String role = res.user.getRole();
        List<CustomerListView> list;
        PageInfo<CustomerListView> pageInfo;
        if (role.contains("Customer_RW_A") || role.contains("Customer_RO")) {
            PageHelper.startPage(pageNum, 10);
            list = customerService.pageCustomer(selectCustomerName, selectCustomerSource, selectCustomerIndustry, beginTime, endTime, selectEmployeeName, sort);
            pageInfo = new PageInfo<>(list);
        } else if (role.contains("Customer_RW_S")) {
            PageHelper.startPage(pageNum, 10);
            list = customerService.pagePartCustomer(employeeGh, selectCustomerName, selectCustomerSource, selectCustomerIndustry, beginTime, endTime, selectEmployeeName, sort);
            pageInfo = new PageInfo<>(list);
        } else {
            list = customerService.listPartCustomer(employeeGh);
            List<String> listSalesIndustry = customerService.selectSalesIndustry(employeeGh);
            if (listSalesIndustry.size() > 0) {
                log.info("行业负责人负责行业返回成功");
                for (String sales : listSalesIndustry) {
                    List<CustomerListView> list1 = customerService.selectCustomerByIndustry(sales);
                    if (list1.size() > 0) {
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
        if (pageInfo.getTotal() > 0) {
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
     *
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
     *
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
        for (Customer customer : listCustomer) {
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
     *
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
            data.put("customer", customer);
            res.message.setMessage(200, "客户信息返回成功", data);
        } else {
            log.error("客户信息返回为空");
            res.message.setMessage(400, "客户信息返回为空");
        }
        return res.message;
    }

    /**
     * 修改客户信息
     *
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
     *
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
     *
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
     *
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
     *
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
        for (CustomerContacts contact : listContact) {
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
        if (beginTime1 != null) {
            beginTime = df.parse(beginTime1);
        }
        if (endTime1 != null) {
            endTime = df.parse(endTime1);
        }
        Map<String, Object> data = new HashMap<>();
        List<DocumentRecordListView> list = new ArrayList<>();
        PageInfo<DocumentRecordListView> pageInfo;
        if (role.contains("Customer_RW_A") || role.contains("Customer_RO")) {
            PageHelper.startPage(pageNum, 10);
            list = customerService.listDocumentRecord(selectCustomerName, selectDocumentType, beginTime, endTime, selectEmployeeName, sort);
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
                data.put("list", list);
                data.put("total", list.size());
                res.message.setMessage(200, "跟单记录列表返回成功", data);
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
                    if (listView.size() > 0) {
                        list.addAll(listView);
                        for (int i = 0; i < list.size(); i++) {
                            for (int j = list.size() - 1; j > i; j--) {
                                if (list.get(i).getId().equals(list.get(j).getId())) {
                                    list.remove(j);
                                }
                            }
                        }
                    }
                    data.put("list", list);
                    data.put("total", list.size());
                    res.message.setMessage(200, "跟单记录列表返回成功", data);
                    return res.message;
                } else {
                    log.error("行业负责人负责行业返回为空");
                    res.message.setMessage(400, "行业负责人负责行业返回为空");
                    return res.message;
                }
            }
        }
        if (pageInfo.getTotal() > 0) {
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
     *
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
     *
     * @param listDocumentRecord
     * @return message
     */
    @PostMapping("/insertDocumentRecord")
    @ApiOperation(value = "增加跟单记录")
    public Message insertDocumentRecord(@RequestBody List<CustomerDocumentRecord> listDocumentRecord) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        for (CustomerDocumentRecord documentRecord : listDocumentRecord) {
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
     *
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
     *
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
     *
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
     *
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
     *
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
        for (CustomerDocumentPlanne documentPlanne : listDocumentPlanne) {
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
     *
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
     *
     * @param selectCustomerView
     * @return res.message
     */
    @PostMapping("/selectCustomer")
    @ApiOperation(value = "客户筛选")
    public Message selectCustomer(@RequestBody SelectCustomerView selectCustomerView) {
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
                    if (list2.size() > 0) {
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
        if (list.size() > 0 && listCustomer.size() > 0) {
            log.info("成功筛选客户，返回数据成功");
            for (CustomerDetailView customerDetailView : list) {
                for (CustomerListView customerListView : listCustomer) {
                    if (customerDetailView.getId().equals(customerListView.getId())) {
                        list1.add(customerDetailView);
                    }
                }
            }
            data.put("list", list1);
            data.put("total", list1.size());
            res.message.setMessage(200, "成功筛选客户，返回数据成功", data);
        } else {
            log.error("筛选客户为空");
            res.message.setMessage(400, "筛选客户为空");
        }
        return res.message;
    }

    /**
     * 跟单记录筛选
     *
     * @param selectDocumentRecordView
     * @return res.message
     */
    @PostMapping("/selectDocumentRecord")
    @ApiOperation(value = "跟单记录筛选")
    public Message selectDocumentRecord(@RequestBody SelectDocumentRecordView selectDocumentRecordView) {
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
                    if (list2.size() > 0) {
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
        if (list.size() > 0 && listCustomer.size() > 0) {
            log.info("成功筛选跟单记录，返回数据成功");
            for (DocumentRecordListView documentRecordListView : list) {
                for (CustomerListView customerListView : listCustomer) {
                    if (documentRecordListView.getCustomerId().equals(customerListView.getId())) {
                        list1.add(documentRecordListView);
                    }
                }
            }
            data.put("list", list1);
            data.put("total", list1.size());
            res.message.setMessage(200, "成功筛选跟单记录，返回数据成功", data);
        } else {
            log.error("筛选跟单记录为空");
            res.message.setMessage(400, "筛选跟单记录为空");
        }
        return res.message;
    }

    /**
     * 跟单进度导出
     *
     * @return res.message
     */
    @GetMapping("/listDocumentPlanne")
    @ApiOperation(value = "跟单进度导出")
    public Message listDocumentPlanne() {
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
                    if (list2.size() > 0) {
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
        if (list.size() > 0 && listCustomer.size() > 0) {
            log.info("成功筛选跟单进度，返回数据成功");
            for (DocumentPlanneListView documentPlanneListView : list) {
                for (CustomerListView customerListView : listCustomer) {
                    if (documentPlanneListView.getCustomerId().equals(customerListView.getId())) {
                        list1.add(documentPlanneListView);
                    }
                }
            }
            data.put("list", list1);
            data.put("total", list1.size());
            res.message.setMessage(200, "成功筛选跟单进度，返回数据成功", data);
        } else {
            log.error("筛选跟单进度为空");
            res.message.setMessage(400, "筛选跟单进度为空");
        }
        return res.message;
    }

    /**
     * 客户共享：共享给我
     *
     * @return res.message
     */
    @GetMapping("/shareToMe")
    @ApiOperation(value = "客户共享：共享给我")
    public Message shareToMe() {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        String employeeGh = res.user.getId();
        Map<String, Object> data = new HashMap<>();
        List<CustomerListView> list = customerService.shareToMe(employeeGh);
        if (list.size() > 0) {
            log.info("客户共享：共享给我返回成功");
            data.put("list", list);
            res.message.setMessage(200, "客户共享：共享给我返回成功", data);
        } else {
            log.error("客户共享：共享给我返回为空");
            res.message.setMessage(400, "客户共享：共享给我返回为空");
        }
        return res.message;
    }

    /**
     * 客户共享：我的共享
     *
     * @return res.message
     */
    @GetMapping("/myShared")
    @ApiOperation(value = "客户共享：我的共享")
    public Message myShared() {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        String employeeGh = res.user.getId();
        Map<String, Object> data = new HashMap<>();
        List<CustomerListView> list = customerService.myShared(employeeGh);
        if (list.size() > 0) {
            log.info("客户共享：我的共享返回成功");
            data.put("list", list);
            res.message.setMessage(200, "客户共享：我的共享返回成功", data);
        } else {
            log.error("客户共享：我的共享返回为空");
            res.message.setMessage(400, "客户共享：我的共享返回为空");
        }
        return res.message;
    }

    /**
     * 返回所有的客户名称
     *
     * @return res.message
     */
    @GetMapping("/selectAllCustomerName")
    @ApiOperation(value = "获取所有客户的名字")
    public Message selectAllCustomerName() {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Map<String, Object> data = new HashMap<>();
        List<String> list = customerService.selectAllCustomerName();
        if (list.size() > 0) {
            log.info("客户名称返回成功");
            data.put("list", list);
            data.put("total", list.size());
            res.message.setMessage(200, "客户名称返回成功", data);
        } else {
            log.error("客户名称返回为空");
            res.message.setMessage(400, "客户名称返回为空");
        }
        return res.message;
    }

    /**
     * 获取所有客户信息
     *
     * @return res.message
     */
    @GetMapping("/listAllCustomer")
    @ApiOperation(value = "获取所有客户信息")
    public Message listAllCustomer() {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Map<String, Object> data = new HashMap<>();
        List<Customer> list = customerService.listAllCustomer();
        if (list.size() > 0) {
            log.info("所有客户信息返回成功");
            data.put("list", list);
            data.put("total", list.size());
            res.message.setMessage(200, "所有客户信息返回成功", data);
        } else {
            log.error("所有客户信息返回为空");
            res.message.setMessage(400, "所有客户信息返回为空");
        }
        return res.message;
    }

    /**
     * 获取业务员的列表（姓名及行业线）
     *
     * @return res.message
     */
    @GetMapping("/listSaleman")
    @ApiOperation(value = "获取业务员列表")
    public Message listSaleman() {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Map<String, Object> data = new HashMap<>();
        List<UserListView> list = customerService.listSaleman();
        if (list.size() > 0) {
            log.info("业务员列表获取成功");
            data.put("list", list);
            data.put("total", list.size());
            res.message.setMessage(200, "业务员列表获取成功", data);
        } else {
            log.error("业务员列表获取为空");
            res.message.setMessage(400, "业务员列表获取为空");
        }
        return res.message;
    }

    /**
     * 客户共享
     *
     * @param map
     * @return res.message
     */
    @PostMapping("/shareCustomer")
    @ApiOperation(value = "客户共享")
    public Message shareCustomer(@RequestBody Map map) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        String listEmployeeGh = (String) map.get("employeeGh");
        Integer fkCustomerId = (Integer) map.get("fkCustomerId");
        String[] split = listEmployeeGh.split(";");
        if (!listEmployeeGh.equals("")) {
            Integer result1 = customerService.deleteSalemans(fkCustomerId);
            for (String employeeGh : split) {
                Integer result = customerService.insertIntoCustomerSalesman(employeeGh, fkCustomerId);
                if (result > 0) {
                    log.info("客户共享成功");
                    res.message.setMessage(200, "客户共享成功");
                } else {
                    log.error("客户共享失败");
                    res.message.setMessage(400, "客户共享失败");
                }
            }

        }
        return res.message;
    }

    /**
     * 获取已经共享的业务员
     *
     * @param map
     * @return res.message
     */
    @PostMapping("/getHadShared")
    @ApiOperation(value = "获取已经共享的业务员")
    public Message getHadShared(@RequestBody Map map) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Map<String, Object> data = new HashMap<>();
        Integer id = (Integer) map.get("id");
        List<String> list = customerService.getHadShared(id);
        if (list.size() > 0) {
            log.info("获取已经共享的业务员成功");
            data.put("list", list);
            data.put("total", list.size());
            res.message.setMessage(200, "获取已经共享的业务员成功", data);
        } else {
            log.error("获取已经共享的业务员失败");
            res.message.setMessage(400, "获取已经共享的业务员失败");
        }
        return res.message;
    }

    /**
     * 获取客户的所有项目
     *
     * @param customerId
     * @return res.message
     */
    @PostMapping("/getProjByCustomer")
    @ApiOperation(value = "获取客户的所有项目")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "customerId", value = "客户Id", required = true)
    })
    public Message getProjByCustomer(Integer customerId) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Map<String, Object> data = new HashMap<>();
        List<ProjectListView> list = customerService.listProjectByCustomer(customerId);
        log.info("客户项目获取成功");
        data.put("list", list);
        data.put("total", list.size());
        res.message.setMessage(200, "客户项目获取成功", data);
        return res.message;
    }

    /**
     * 获取合同列表
     *
     * @return res.message
     */
    @GetMapping("/listContract")
    @ApiOperation(value = "获取合同列表")
    public Message listContract() {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Map<String, Object> data = new HashMap<>();
        String employeeGh = res.user.getId();
        String authority = res.user.getRole();
        if (authority.contains("Contract_RW_A") || authority.contains("Contract_RO")) {
            List<ContractListView> list1 = contractService.listAllContract();
            List<ContractReceivedView> list2 = contractService.selectReceived();
            if (list1.size() > 0) {
                for (ContractListView contractListView : list1) {
                    for (ContractReceivedView contractReceivedView : list2) {
                        if (contractListView.getId() == contractReceivedView.getFkContractId()) {
                            contractListView.setReceived(contractReceivedView.getContractReceived());
                            contractListView.setUncollected(contractListView.getContractAmount() - contractReceivedView.getContractReceived());
                        }
                    }
                    ContractFileView contractFileView = contractService.selectContractFile(contractListView.getId());
                    contractListView.setContractFile(contractFileView);
                }
                data.put("list", list1);
                data.put("total", list1.size());
                log.info("合同列表获取成功");
                res.message.setMessage(200, "合同列表获取成功", data);
            } else {
                log.warn("合同列表获取为空");
                res.message.setMessage(200, "合同列表获取为空");
            }
        }
        if (authority.contains("Contract_RW_S")) {
            List<ContractListView> list1 = contractService.listContract(employeeGh);
            List<ContractReceivedView> list2 = contractService.selectReceived();
            if (list1.size() > 0) {
                for (ContractListView contractListView : list1) {
                    for (ContractReceivedView contractReceivedView : list2) {
                        if (contractListView.getId() == contractReceivedView.getFkContractId()) {
                            contractListView.setReceived(contractReceivedView.getContractReceived());
                            contractListView.setUncollected(contractListView.getContractAmount() - contractReceivedView.getContractReceived());
                        }
                    }
                    ContractFileView contractFileView = contractService.selectContractFile(contractListView.getId());
                    contractListView.setContractFile(contractFileView);
                }
                data.put("list", list1);
                data.put("total", list1.size());
                log.info("合同列表获取成功");
                res.message.setMessage(200, "合同列表获取成功", data);
            } else {
                log.warn("合同列表获取为空");
                res.message.setMessage(200, "合同列表获取为空");
            }
        }
        if (authority.contains("Contract_RW_M_I")) {
            List<ContractListView> list = contractService.listContract(employeeGh);
            List<String> listSalesIndustry = customerService.selectSalesIndustry(employeeGh);
            if (listSalesIndustry.size() > 0) {
                log.info("行业负责人负责行业返回成功");
                for (String sales : listSalesIndustry) {
                    List<ContractListView> list1 = contractService.selectContractByIndustry(sales);
                    if (list1.size() > 0) {
                        log.info("行业负责人合同列表返回成功");
                        list.addAll(list1);
                        for (int i = 0; i < list.size(); i++) {
                            for (int j = list.size() - 1; j > i; j--) {
                                if (list.get(i).getId().equals(list.get(j).getId())) {
                                    list.remove(j);
                                }
                            }
                        }
                    } else {
                        log.warn("行业负责人合同列表返回为空");
                        res.message.setMessage(400, "行业负责人合同列表返回为空");
                    }
                }
                List<ContractReceivedView> list2 = contractService.selectReceived();
                if (list.size() > 0) {
                    for (ContractListView contractListView : list) {
                        for (ContractReceivedView contractReceivedView : list2) {
                            if (contractListView.getId() == contractReceivedView.getFkContractId()) {
                                contractListView.setReceived(contractReceivedView.getContractReceived());
                                contractListView.setUncollected(contractListView.getContractAmount() - contractReceivedView.getContractReceived());
                            }
                        }
                        ContractFileView contractFileView = contractService.selectContractFile(contractListView.getId());
                        contractListView.setContractFile(contractFileView);
                    }
                    data.put("list", list);
                    data.put("total", list.size());
                    log.info("合同列表获取成功");
                    res.message.setMessage(200, "合同列表获取成功", data);
                } else {
                    log.warn("合同列表获取为空");
                    res.message.setMessage(200, "合同列表获取为空");
                }
            } else {
                log.error("行业负责人负责行业返回为空");
                res.message.setMessage(400, "行业负责人负责行业返回为空");
            }
        }
        return res.message;
    }

    /**
     * 获取项目对应的合同阶段
     *
     * @param projId 项目id
     * @return res.message
     */
    @PostMapping("/listContractStep")
    @ApiOperation(value = "获取项目对应的合同阶段")
    public Message listContractStep(@RequestParam("projId") Integer projId) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Map<String, Object> data = new HashMap<>();
        ContractFileView contractFileView = contractService.selectContractFileByProjId(projId);
        List<ContractStep> list = contractService.listContractStep(projId);
        if (list.size() > 0) {
            log.info("获取项目对应的合同阶段成功");
            for (ContractStep contractStep : list) {
                ContractFileView contractFileView1 = contractService.selectContractStepFile(contractStep.getId(), "验收报告");
                ContractFileView contractFileView2 = contractService.selectContractStepFile(contractStep.getId(), "开票证明");
                ContractFileView contractFileView3 = contractService.selectContractStepFile(contractStep.getId(), "回款证明");
                contractStep.setAcceptanceFile(contractFileView1);
                contractStep.setInvoiceFile(contractFileView2);
                contractStep.setPaymentFile(contractFileView3);
            }
            data.put("list", list);
            data.put("total", list.size());
            data.put("contractFileView", contractFileView);
            res.message.setMessage(200, "获取项目对应的合同阶段成功", data);
        } else {
            log.error("获取项目对应的合同阶段为空");
            res.message.setMessage(400, "获取项目对应的合同阶段为空");
        }
        return res.message;
    }

    /**
     * 新增合同
     *
     * @param contract 合同信息
     * @return res.message
     */
    @PostMapping("/insertContract")
    @ApiOperation(value = "新增合同阶段信息")
    public Message insertContract(@RequestBody Contract contract) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Map<String, Object> data = new HashMap<>();
        Integer result = contractService.insertContract(contract);
        if (result != 0) {
            log.info("合同添加成功");
            data.put("id", contract.getId());
            res.message.setMessage(200, "合同添加成功", data);
        } else {
            log.error("合同添加失败");
            res.message.setMessage(400, "合同添加失败");
        }
        return res.message;
    }

    /**
     * 添加合同阶段
     *
     * @param contractSteps 合同阶段信息
     * @return res.message
     */
    @PostMapping("/insertContractStep")
    @ApiOperation(value = "添加合同阶段")
    public Message insertContractStep(@RequestBody List<ContractStep> contractSteps) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Integer result = contractService.insertContractStep(contractSteps);
        if (result > 0) {
            log.info("添加合同阶段成功");
            res.message.setMessage(200, "添加合同阶段成功");
        } else {
            log.error("添加合同阶段失败");
            res.message.setMessage(400, "添加合同阶段失败");
        }
        return res.message;
    }

    /**
     * 删除合同阶段信息
     *
     * @param id 合同阶段id
     * @return res.message
     */
    @PostMapping("/deleteContract")
    @ApiOperation(value = "删除合同信息")
    public Message deleteContract(@RequestParam("id") Integer id) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Integer result = contractService.deleteContract(id);
        if (result > 0) {
            res.message.setMessage(200, "删除合同信息成功");
        } else {
            res.message.setMessage(400, "删除合同信息失败");
        }
        return res.message;
    }

    /**
     * 删除合同阶段信息
     *
     * @param id 合同阶段id
     * @return res.message
     */
    @PostMapping("/deleteContractStep")
    @ApiOperation(value = "删除合同阶段信息")
    public Message deleteContractStep(@RequestParam("id") Integer id) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Integer result = contractService.deleteContractStep(id);
        if (result > 0) {
            res.message.setMessage(200, "删除合同阶段信息成功");
        } else {
            res.message.setMessage(400, "删除合同阶段信息失败");
        }
        return res.message;
    }

    /**
     * 修改合同
     *
     * @param contract 修改的合同信息
     * @return res.message
     */
    @PostMapping("/updateContract")
    @ApiOperation(value = "修改合同")
    public Message updateContract(@RequestBody Contract contract) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Integer result = contractService.updateContract(contract);
        if (result > 0) {
            res.message.setMessage(200, "合同修改成功");
        } else {
            res.message.setMessage(400, "合同修改失败");
        }
        return res.message;
    }

    /**
     * 修改合同阶段
     *
     * @param contractStep 修改的合同阶段信息
     * @return res.message
     */
    @PostMapping("/updateContractStep")
    @ApiOperation(value = "修改合同阶段信息")
    public Message updateContractStep(@RequestBody ContractStep contractStep) {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        Integer result = contractService.updateContractStep(contractStep);
        if (result > 0) {
            res.message.setMessage(200, "合同阶段信息修改成功");
        } else {
            res.message.setMessage(400, "合同阶段信息修改失败");
        }
        return res.message;
    }


    /**
     * 合同数据导出
     *
     * @return res.message
     */
    @GetMapping("/exportContract")
    @ApiOperation(value = "合同数据导出")
    public Message exportContract() {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        try {
            short num = 0;
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("合同报表");
            // 样式
            // 表头标题
            HSSFCellStyle styleTitel = wb.createCellStyle();
            styleTitel.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            styleTitel.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            styleTitel.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
            styleTitel.setBottomBorderColor(HSSFColor.BLACK.index);
            styleTitel.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
            styleTitel.setLeftBorderColor(HSSFColor.BLACK.index);
            styleTitel.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
            styleTitel.setRightBorderColor(HSSFColor.BLACK.index);
            styleTitel.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
            styleTitel.setTopBorderColor(HSSFColor.BLACK.index);
            styleTitel.setDataFormat(wb.createDataFormat().getFormat("@"));
            styleTitel.setWrapText(true);
            //普通样式
            HSSFCellStyle style = wb.createCellStyle();
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style.setDataFormat(wb.createDataFormat().getFormat("@"));
            style.setWrapText(true);
            //日期样式
            HSSFCellStyle styleDate = wb.createCellStyle();
            styleDate.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            styleDate.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            styleDate.setDataFormat(wb.createDataFormat().getFormat("yyyy-MM-dd"));
            //货币样式（万元）
            HSSFCellStyle styleDouble = wb.createCellStyle();
            styleDouble.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            styleDouble.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            styleDouble.setDataFormat(wb.createDataFormat().getFormat("¥###,###,###"));
            //已完成样式
            HSSFCellStyle styleDone = wb.createCellStyle();
            styleDone.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            styleDone.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            styleDone.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            styleDone.setFillForegroundColor(HSSFColor.BRIGHT_GREEN.index);
            //未完成样式
            HSSFCellStyle styleNotDone = wb.createCellStyle();
            styleNotDone.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            styleNotDone.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            styleNotDone.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            styleNotDone.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);

            Map<Integer, Integer> maxWidth = new HashMap<Integer, Integer>();
            HSSFRow row1 = sheet.createRow(num);
            num += 1;
            HSSFCell cell;
            String[] tableHead = new String[]{"合同编号", "产品线", "项目名称", "行业", "客户名称", "销售合同金额（万元）", "合同签署日期", "负责人", "合同阶段", "比例", "金额（万元）", "验收标准", "预计开票时间", "验收报告", "上传时间", "开票证明", "上传时间", "回款证明", "上传时间", "子负责人", "备注"};
            for (short i = 0; i < tableHead.length; i++) {
                cell = row1.createCell(i);
                cell.setCellValue(tableHead[i]);
                cell.setCellStyle(styleTitel);
                maxWidth.put((int) i, cell.getStringCellValue().getBytes().length * 256 + 200);
            }
            for (int i = 0; i < tableHead.length; i++) {
                sheet.setColumnWidth(i, maxWidth.get(i));
            }
            List<ExcelContractView> excelContractViews = contractService.selectAllContract();
            if (excelContractViews.size() > 0) {
                for (ExcelContractView excelContractView : excelContractViews) {
                    List<ExcelContractStepView> excelContractStepViews = contractService.selectAllContractStep(excelContractView.getId());
                    if (excelContractStepViews.size() == 0) {
                        HSSFRow row = sheet.createRow(num);
                        cell = row.createCell((short) 0);
                        cell.setCellValue(excelContractView.getContractNo());
                        cell.setCellStyle(style);

                        cell = row.createCell((short) 1);
                        cell.setCellValue(excelContractView.getProjLine());
                        cell.setCellStyle(style);

                        cell = row.createCell((short) 2);
                        cell.setCellValue(excelContractView.getProjName());
                        cell.setCellStyle(style);

                        if (excelContractView.getCustomerIndustry() != null) {
                            cell = row.createCell((short) 3);
                            cell.setCellValue(excelContractView.getCustomerIndustry());
                            cell.setCellStyle(style);
                        }

                        if (excelContractView.getCustomerName() != null) {
                            cell = row.createCell((short) 4);
                            cell.setCellValue(excelContractView.getCustomerName());
                            cell.setCellStyle(style);
                        }

                        cell = row.createCell((short) 5);
                        cell.setCellValue(excelContractView.getContractMoney() * 10000);
                        cell.setCellStyle(styleDouble);

                        cell = row.createCell((short) 6);
                        cell.setCellValue(excelContractView.getContractDate());
                        cell.setCellStyle(styleDate);

                        cell = row.createCell((short) 7);
                        cell.setCellValue(excelContractView.getEmployeeName());
                        cell.setCellStyle(style);
                        num += 1;
                    } else {
                        HSSFRow row = sheet.createRow(num);
                        sheet.addMergedRegion(new Region(num, (short) 0, num + excelContractStepViews.size() - 1, (short) 0));
                        cell = row.createCell((short) 0);
                        cell.setCellValue(excelContractView.getContractNo());
                        cell.setCellStyle(style);

                        sheet.addMergedRegion(new Region(num, (short) 1, num + excelContractStepViews.size() - 1, (short) 1));
                        cell = row.createCell((short) 1);
                        cell.setCellValue(excelContractView.getProjLine());
                        cell.setCellStyle(style);

                        sheet.addMergedRegion(new Region(num, (short) 2, num + excelContractStepViews.size() - 1, (short) 2));
                        cell = row.createCell((short) 2);
                        cell.setCellValue(excelContractView.getProjName());
                        cell.setCellStyle(style);

                        if (excelContractView.getCustomerIndustry() != null) {
                            sheet.addMergedRegion(new Region(num, (short) 3, num + excelContractStepViews.size() - 1, (short) 3));
                            cell = row.createCell((short) 3);
                            cell.setCellValue(excelContractView.getCustomerIndustry());
                            cell.setCellStyle(style);
                        }

                        if (excelContractView.getCustomerName() != null) {
                            sheet.addMergedRegion(new Region(num, (short) 4, num + excelContractStepViews.size() - 1, (short) 4));
                            cell = row.createCell((short) 4);
                            cell.setCellValue(excelContractView.getCustomerName());
                            cell.setCellStyle(style);
                        }

                        sheet.addMergedRegion(new Region(num, (short) 5, num + excelContractStepViews.size() - 1, (short) 5));
                        cell = row.createCell((short) 5);
                        cell.setCellValue(excelContractView.getContractMoney() * 10000);
                        cell.setCellStyle(styleDouble);

                        sheet.addMergedRegion(new Region(num, (short) 6, num + excelContractStepViews.size() - 1, (short) 6));
                        cell = row.createCell((short) 6);
                        cell.setCellValue(excelContractView.getContractDate());
                        cell.setCellStyle(styleDate);

                        sheet.addMergedRegion(new Region(num, (short) 7, num + excelContractStepViews.size() - 1, (short) 7));
                        cell = row.createCell((short) 7);
                        cell.setCellValue(excelContractView.getEmployeeName());
                        cell.setCellStyle(style);

                        cell = row.createCell((short) 8);
                        cell.setCellValue(excelContractStepViews.get(0).getContractStage());
                        cell.setCellStyle(style);

                        cell = row.createCell((short) 9);
                        cell.setCellValue(excelContractStepViews.get(0).getContractProportion());
                        cell.setCellStyle(style);

                        cell = row.createCell((short) 10);
                        cell.setCellValue(excelContractStepViews.get(0).getContractAmount() * 10000);
                        cell.setCellStyle(styleDouble);

                        cell = row.createCell((short) 11);
                        cell.setCellValue(excelContractStepViews.get(0).getContractStandard());
                        cell.setCellStyle(style);

                        cell = row.createCell((short) 12);
                        cell.setCellValue(excelContractStepViews.get(0).getContractTime());
                        cell.setCellStyle(styleDate);

                        cell = row.createCell((short) 13);
                        cell.setCellValue(excelContractStepViews.get(0).getAcceptanceDone());
                        if (excelContractStepViews.get(0).getAcceptanceDone().equals("已上传")) {
                            cell.setCellStyle(styleDone);
                        } else {
                            cell.setCellStyle(styleNotDone);
                        }

                        if (excelContractStepViews.get(0).getAcceptanceTime() != null) {
                            cell = row.createCell((short) 14);
                            cell.setCellValue(excelContractStepViews.get(0).getAcceptanceTime());
                            cell.setCellStyle(styleDate);
                        }

                        cell = row.createCell((short) 15);
                        cell.setCellValue(excelContractStepViews.get(0).getInvoiceDone());
                        if (excelContractStepViews.get(0).getInvoiceDone().equals("已上传")) {
                            cell.setCellStyle(styleDone);
                        } else {
                            cell.setCellStyle(styleNotDone);
                        }

                        if (excelContractStepViews.get(0).getInvoiceTime() != null) {
                            cell = row.createCell((short) 16);
                            cell.setCellValue(excelContractStepViews.get(0).getInvoiceTime());
                            cell.setCellStyle(styleDate);
                        }

                        cell = row.createCell((short) 17);
                        cell.setCellValue(excelContractStepViews.get(0).getPaymentDone());
                        if (excelContractStepViews.get(0).getPaymentDone().equals("已上传")) {
                            cell.setCellStyle(styleDone);
                        } else {
                            cell.setCellStyle(styleNotDone);
                        }

                        if (excelContractStepViews.get(0).getPaymentTime() != null) {
                            cell = row.createCell((short) 18);
                            cell.setCellValue(excelContractStepViews.get(0).getPaymentTime());
                            cell.setCellStyle(styleDate);
                        }

                        cell = row.createCell((short) 19);
                        cell.setCellValue(excelContractStepViews.get(0).getEmployeeName());
                        cell.setCellStyle(style);

                        cell = row.createCell((short) 20);
                        cell.setCellValue(excelContractStepViews.get(0).getContractRemark());
                        cell.setCellStyle(style);
                        num += 1;
                        if (excelContractStepViews.size() > 1) {
                            for (short z = 1; z < excelContractStepViews.size(); z++) {
                                row = sheet.createRow(num);
                                cell = row.createCell((short) 8);
                                cell.setCellValue(excelContractStepViews.get(z).getContractStage());
                                cell.setCellStyle(style);

                                cell = row.createCell((short) 9);
                                cell.setCellValue(excelContractStepViews.get(z).getContractProportion());
                                cell.setCellStyle(style);

                                cell = row.createCell((short) 10);
                                cell.setCellValue(excelContractStepViews.get(z).getContractAmount() * 10000);
                                cell.setCellStyle(styleDouble);

                                cell = row.createCell((short) 11);
                                cell.setCellValue(excelContractStepViews.get(z).getContractStandard());
                                cell.setCellStyle(style);

                                cell = row.createCell((short) 12);
                                cell.setCellValue(excelContractStepViews.get(z).getContractTime());
                                cell.setCellStyle(styleDate);

                                cell = row.createCell((short) 13);
                                cell.setCellValue(excelContractStepViews.get(z).getAcceptanceDone());
                                if (excelContractStepViews.get(z).getAcceptanceDone().equals("已上传")) {
                                    cell.setCellStyle(styleDone);
                                } else {
                                    cell.setCellStyle(styleNotDone);
                                }

                                if (excelContractStepViews.get(z).getAcceptanceTime() != null) {
                                    cell = row.createCell((short) 14);
                                    cell.setCellValue(excelContractStepViews.get(z).getAcceptanceTime());
                                    cell.setCellStyle(styleDate);
                                }

                                cell = row.createCell((short) 15);
                                cell.setCellValue(excelContractStepViews.get(z).getInvoiceDone());
                                if (excelContractStepViews.get(z).getInvoiceDone().equals("已上传")) {
                                    cell.setCellStyle(styleDone);
                                } else {
                                    cell.setCellStyle(styleNotDone);
                                }

                                if (excelContractStepViews.get(z).getInvoiceTime() != null) {
                                    cell = row.createCell((short) 16);
                                    cell.setCellValue(excelContractStepViews.get(z).getInvoiceTime());
                                    cell.setCellStyle(styleDate);
                                }

                                cell = row.createCell((short) 17);
                                cell.setCellValue(excelContractStepViews.get(z).getPaymentDone());
                                if (excelContractStepViews.get(z).getPaymentDone().equals("已上传")) {
                                    cell.setCellStyle(styleDone);
                                } else {
                                    cell.setCellStyle(styleNotDone);
                                }

                                if (excelContractStepViews.get(z).getPaymentTime() != null) {
                                    cell = row.createCell((short) 18);
                                    cell.setCellValue(excelContractStepViews.get(z).getPaymentTime());
                                    cell.setCellStyle(styleDate);
                                }

                                cell = row.createCell((short) 19);
                                cell.setCellValue(excelContractStepViews.get(z).getEmployeeName());
                                cell.setCellStyle(style);

                                cell = row.createCell((short) 20);
                                cell.setCellValue(excelContractStepViews.get(z).getContractRemark());
                                cell.setCellStyle(style);
                                num += 1;
                            }
                        }
                    }
                }
            }
            FileOutputStream fileOut = new FileOutputStream("/var/upload/Excel/Contract.xls");
            wb.write(fileOut);
            fileOut.close();
            log.info("Excel文件已成功导出!");
            Map<String, Object> data = new HashMap<>();
            data.put("url", frame + "/Excel/Contract.xls");
            res.message.setMessage(200, "excel导出成功", data);
        } catch (Exception ex) {
            ex.printStackTrace();
            res.message.setMessage(400, "excel导出失败");
        }
        return res.message;
    }

    @GetMapping("/exportCustomer")
    @ApiOperation(value = "客户数据导出")
    public Message exportCustomer() {
        VerifyMessage res = VerifyCode(request.getHeader("Authorization"));
        if (res.message.getCode() == 401) {
            log.error("Authorization参数校验失败");
            return res.message;
        }
        try {
            short num = 0;
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("客户信息");
            // 样式
            // 表头标题
            HSSFCellStyle styleTitel = wb.createCellStyle();
            styleTitel.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            styleTitel.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            styleTitel.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
            styleTitel.setBottomBorderColor(HSSFColor.BLACK.index);
            styleTitel.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
            styleTitel.setLeftBorderColor(HSSFColor.BLACK.index);
            styleTitel.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
            styleTitel.setRightBorderColor(HSSFColor.BLACK.index);
            styleTitel.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
            styleTitel.setTopBorderColor(HSSFColor.BLACK.index);
            styleTitel.setDataFormat(wb.createDataFormat().getFormat("@"));
            styleTitel.setWrapText(true);
            //普通样式
            HSSFCellStyle style = wb.createCellStyle();
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style.setDataFormat(wb.createDataFormat().getFormat("@"));
            style.setWrapText(true);
            //日期样式
            HSSFCellStyle styleDate = wb.createCellStyle();
            styleDate.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            styleDate.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            styleDate.setDataFormat(wb.createDataFormat().getFormat("yyyy-MM-dd"));
            Map<Integer, Integer> maxWidth = new HashMap<Integer, Integer>();
            HSSFRow row1 = sheet.createRow(num);
            num += 1;
            HSSFCell cell;
            String[] tableHead = new String[]{"客户ID", "客户名称", "省", "市", "详细地址", "邮编", "官网", "邮箱", "联系人姓名", "联系人职位", "联系人具体职位",
                    "联系人手机", "联系人微信", "产品分类", "客户来源", "客户行业", "单位类型", "技术部门总规格", "外包人员规模", "服务器规模", "主要服务器类型",
                    "机房网络封闭程度", "IT运维发展阶段", "软件架构发展阶段", "主流技术框架", "IT监控的发展阶段", "现有痛点", "主要诉求", "未来1～2年关注的技术方向",
                    "关心的技术点", "用了什么中间件", "技术人员工资平均水平", "年度IT建设总预算", "哪些架构在朝分布式方向转型", "备注其他", "录入时间"};
            for (short i = 0; i < tableHead.length; i++) {
                cell = row1.createCell(i);
                cell.setCellValue(tableHead[i]);
                cell.setCellStyle(styleTitel);
                maxWidth.put((int) i, cell.getStringCellValue().getBytes().length * 256 + 200);
            }
            for (int i = 0; i < tableHead.length; i++) {
                sheet.setColumnWidth(i, maxWidth.get(i));
            }
            List<CustomerDetailView> customerDetailViews = customerService.selectAllCustomerDetails();
            if (customerDetailViews.size() > 0) {
                for (CustomerDetailView customerDetailView : customerDetailViews) {
                    HSSFRow row = sheet.createRow(num);

                    cell = row.createCell((short) 0);
                    cell.setCellValue(customerDetailView.getId());
                    cell.setCellStyle(style);

                    cell = row.createCell((short) 1);
                    cell.setCellValue(customerDetailView.getCustomerName());
                    cell.setCellStyle(style);

                    if (customerDetailView.getCustomerProvince() != null) {
                        cell = row.createCell((short) 2);
                        cell.setCellValue(customerDetailView.getCustomerProvince());
                        cell.setCellStyle(style);
                    }
                    if (customerDetailView.getCustomerCity() != null) {
                        cell = row.createCell((short) 3);
                        cell.setCellValue(customerDetailView.getCustomerCity());
                        cell.setCellStyle(style);
                    }
                    if (customerDetailView.getCustomerPlace() != null) {
                        cell = row.createCell((short) 4);
                        cell.setCellValue(customerDetailView.getCustomerPlace());
                        cell.setCellStyle(style);
                    }
                    if (customerDetailView.getCustomerCode() != null) {
                        cell = row.createCell((short) 5);
                        cell.setCellValue(customerDetailView.getCustomerCode());
                        cell.setCellStyle(style);
                    }
                    if (customerDetailView.getCustomerWeb() != null) {
                        cell = row.createCell((short) 6);
                        cell.setCellValue(customerDetailView.getCustomerWeb());
                        cell.setCellStyle(style);
                    }
                    if (customerDetailView.getCustomerEmail() != null) {
                        cell = row.createCell((short) 7);
                        cell.setCellValue(customerDetailView.getCustomerEmail());
                        cell.setCellStyle(style);
                    }
                    if (customerDetailView.getContactsName() != null) {
                        cell = row.createCell((short) 8);
                        cell.setCellValue(customerDetailView.getContactsName());
                        cell.setCellStyle(style);
                    }
                    if (customerDetailView.getContactsPosition() != null) {
                        cell = row.createCell((short) 9);
                        cell.setCellValue(customerDetailView.getContactsPosition());
                        cell.setCellStyle(style);
                    }
                    if (customerDetailView.getContactsSpecificPosition() != null) {
                        cell = row.createCell((short) 10);
                        cell.setCellValue(customerDetailView.getContactsSpecificPosition());
                        cell.setCellStyle(style);
                    }
                    if (customerDetailView.getContactsTel() != null) {
                        cell = row.createCell((short) 11);
                        cell.setCellValue(customerDetailView.getContactsTel());
                        cell.setCellStyle(style);
                    }
                    if (customerDetailView.getContactsWechat() != null) {
                        cell = row.createCell((short) 12);
                        cell.setCellValue(customerDetailView.getContactsWechat());
                        cell.setCellStyle(style);
                    }
                    if (customerDetailView.getCustomerProdSort() != null) {
                        cell = row.createCell((short) 13);
                        if (customerDetailView.getCustomerProdText() != null) {
                            cell.setCellValue(customerDetailView.getCustomerIndustry() + "  " + customerDetailView.getCustomerProdText());
                        } else {
                            cell.setCellValue(customerDetailView.getCustomerIndustry());
                        }
                        cell.setCellStyle(style);
                    }
                    if (customerDetailView.getCustomerSourceSort() != null) {
                        cell = row.createCell((short) 14);
                        if (customerDetailView.getCustomerSourceText() != null) {
                            cell.setCellValue(customerDetailView.getCustomerIndustry() + "  " + customerDetailView.getCustomerSourceText());
                        } else {
                            cell.setCellValue(customerDetailView.getCustomerSourceSort());
                        }
                        cell.setCellStyle(style);
                    }
                    if (customerDetailView.getCustomerIndustry() != null) {
                        cell = row.createCell((short) 15);
                        cell.setCellValue(customerDetailView.getCustomerIndustry());
                        cell.setCellStyle(style);
                    }
                    if (customerDetailView.getCustomerType() != null) {
                        cell = row.createCell((short) 16);
                        cell.setCellValue(customerDetailView.getCustomerType());
                        cell.setCellStyle(style);
                    }
                    if (customerDetailView.getTeamTotal() != null) {
                        cell = row.createCell((short) 17);
                        cell.setCellValue(customerDetailView.getTeamTotal());
                        cell.setCellStyle(style);
                    }
                    if (customerDetailView.getTeamOutsource() != null) {
                        cell = row.createCell((short) 18);
                        cell.setCellValue(customerDetailView.getTeamOutsource());
                        cell.setCellStyle(style);
                    }
                    if (customerDetailView.getServerCount() != null) {
                        cell = row.createCell((short) 19);
                        cell.setCellValue(customerDetailView.getServerCount());
                        cell.setCellStyle(style);
                    }
                    if (customerDetailView.getServerType() != null) {
                        cell = row.createCell((short) 20);
                        cell.setCellValue(customerDetailView.getServerType());
                        cell.setCellStyle(style);
                    }
                    if (customerDetailView.getServerNetwork() != null) {
                        cell = row.createCell((short) 21);
                        cell.setCellValue(customerDetailView.getServerNetwork());
                        cell.setCellStyle(style);
                    }
                    if (customerDetailView.getOperationStage() != null) {
                        cell = row.createCell((short) 22);
                        cell.setCellValue(customerDetailView.getOperationStage());
                        cell.setCellStyle(style);
                    }
                    if (customerDetailView.getSorftStage() != null) {
                        cell = row.createCell((short) 23);
                        cell.setCellValue(customerDetailView.getSorftStage());
                        cell.setCellStyle(style);
                    }
                    if (customerDetailView.getTechnologyStage() != null) {
                        cell = row.createCell((short) 24);
                        cell.setCellValue(customerDetailView.getTechnologyStage());
                        cell.setCellStyle(style);
                    }
                    if (customerDetailView.getMonitorStage() != null) {
                        cell = row.createCell((short) 25);
                        cell.setCellValue(customerDetailView.getMonitorStage());
                        cell.setCellStyle(style);
                    }
                    if (customerDetailView.getExistingPain() != null) {
                        cell = row.createCell((short) 26);
                        cell.setCellValue(customerDetailView.getExistingPain());
                        cell.setCellStyle(style);
                    }
                    if (customerDetailView.getPetition() != null) {
                        cell = row.createCell((short) 27);
                        cell.setCellValue(customerDetailView.getPetition());
                        cell.setCellStyle(style);
                    }
                    if (customerDetailView.getDirection() != null) {
                        cell = row.createCell((short) 28);
                        cell.setCellValue(customerDetailView.getDirection());
                        cell.setCellStyle(style);
                    }
                    if (customerDetailView.getFollowPoint() != null) {
                        cell = row.createCell((short) 29);
                        cell.setCellValue(customerDetailView.getFollowPoint());
                        cell.setCellStyle(style);
                    }
                    if (customerDetailView.getMiddleware() != null) {
                        cell = row.createCell((short) 30);
                        cell.setCellValue(customerDetailView.getMiddleware());
                        cell.setCellStyle(style);
                    }
                    if (customerDetailView.getWages() != null) {
                        cell = row.createCell((short) 31);
                        cell.setCellValue(customerDetailView.getWages());
                        cell.setCellStyle(style);
                    }
                    if (customerDetailView.getBudgetTotal() != null) {
                        cell = row.createCell((short) 32);
                        cell.setCellValue(customerDetailView.getBudgetTotal());
                        cell.setCellStyle(style);
                    }
                    if (customerDetailView.getTransformation() != null) {
                        cell = row.createCell((short) 33);
                        cell.setCellValue(customerDetailView.getTransformation());
                        cell.setCellStyle(style);
                    }
                    if (customerDetailView.getCustomerRemark() != null) {
                        cell = row.createCell((short) 34);
                        cell.setCellValue(customerDetailView.getCustomerRemark());
                        cell.setCellStyle(style);
                    }
                    if (customerDetailView.getCreateTime() != null) {
                        cell = row.createCell((short) 35);
                        cell.setCellValue(customerDetailView.getCreateTime());
                        cell.setCellStyle(styleDate);
                    }
                    num += 1;
                }
            }
            FileOutputStream fileOut = new FileOutputStream("/var/upload/Excel/Customer.xls");
            wb.write(fileOut);
            fileOut.close();
            log.info("Excel文件已成功导出!");
            Map<String, Object> data = new HashMap<>();
            data.put("url", frame + "/Excel/Customer.xls");
            res.message.setMessage(200, "excel导出成功", data);

        } catch (Exception ex) {
            ex.printStackTrace();
            res.message.setMessage(400, "excel导出失败");
        }
        return res.message;
    }
}