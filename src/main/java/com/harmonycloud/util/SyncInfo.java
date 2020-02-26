package com.harmonycloud.util;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiDepartmentListRequest;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiUserListRequest;
import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiUserListResponse;
import com.harmonycloud.config.Constant;
import com.harmonycloud.dao.EmployeeDao;
import com.harmonycloud.dao.UserDao;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.harmonycloud.config.URLConstant.*;

/**
 * @author ：wz
 * @date ：Created in 2019/8/12 9:48
 * 从钉钉中获取信息并同步到数据库
 */
@Controller
@Slf4j
public class SyncInfo {
	/**
	 * 静态代理，为不同同步策略预留扩展空间
	 */
	public static void LoadAllUserInfo(){

		if(true){//如果同步条件判断通过
			LoadDeptUserInfo("1",true);
		}
	}

	/**
	 * 获取部门内用户信息并存入数据库
	 */
	private static void LoadDeptUserInfo(String dept_id, Boolean is_fetch_child){
		OapiDepartmentListResponse dept_res=getDepartmentList(dept_id,is_fetch_child);
		if(dept_res==null || dept_res.isSuccess()==false){
			log.error("get department information failed!");
			return ;
		}
		for (OapiDepartmentListResponse.Department dep:dept_res.getDepartment()) {
			OapiUserListResponse userlist_res=getUserList(dep.getId());
			if(userlist_res==null || userlist_res.isSuccess()==false){
				log.error("get user information from "+dep.getName()+" department failed!");
				return ;
			}
			for (OapiUserListResponse.Userlist user:
					userlist_res.getUserlist()) {
				save_data_to_sql(user);
			}

		}
	}

	/**
	 * 获取token
	 */
	private static String getToken (){
		DefaultDingTalkClient client = new
				DefaultDingTalkClient(URL_GET_TOKKEN);
		OapiGettokenRequest request = new OapiGettokenRequest();
		request.setAppkey(Constant.APP_KEY);
		request.setAppsecret(Constant.APP_SECRET);
		request.setHttpMethod("GET");
		try {
			OapiGettokenResponse response = client.execute(request);
			return response.getAccessToken();
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param dept_id 父部门id（如果不传，默认部门为根部门，根部门ID为1）
	 * @param is_fetch_child 是否递归部门的全部子部门
	 */
	private static OapiDepartmentListResponse getDepartmentList(String dept_id,Boolean is_fetch_child){
		DingTalkClient client = new DefaultDingTalkClient(URL_GET_DEPARTMENT_LIST);
		OapiDepartmentListRequest request = new OapiDepartmentListRequest();
		//获取根部门下所有部门列表  根部门的部门id为1
		request.setId(dept_id);
		request.setHttpMethod("GET");
		request.setFetchChild(is_fetch_child);
		try {
			OapiDepartmentListResponse response = client.execute(request, getToken());
			return response;
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param dept_id 目标部门id，从该部门获取用户信息列表
	 */
	private static OapiUserListResponse getUserList(long dept_id){
		DingTalkClient client = new DefaultDingTalkClient(URL_GET_USER_LIST);
		OapiUserListRequest request = new OapiUserListRequest();
		request.setDepartmentId(dept_id);
		request.setHttpMethod("GET");
		try {
			OapiUserListResponse  response = client.execute(request, getToken());
			return response;
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将单个用户信息存入数据库
	 * @param user 待存入数据库的用户信息
	 */
	private static void save_data_to_sql(OapiUserListResponse.Userlist user) {
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://10.1.11.89:3306/harmage?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai",
					"root", "123456");
			String sql = "select employee_gh FROM employee where employee_gh=?;";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, user.getJobnumber());
			ResultSet rs = st.executeQuery();

			String sql2 = "insert into employee(employee_gh, employee_name, employee_dep, employee_job,\n" +
					"employee_workplace, employee_sex, employee_type, create_time, dingding_user_id)\n" +
					"value(?,?,'/',?,?,'无','/',?,?)";

			if (!rs.next()) {
				if(user.getJobnumber()!=null){
					PreparedStatement st2 = con.prepareStatement(sql2);
//					System.out.println(user.getJobnumber());
					st2.setString(1, user.getJobnumber());
					st2.setString(2, user.getName());
					st2.setString(3, user.getPosition());
					st2.setString(4, user.getWorkPlace());
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					if(user.getHiredDate()!=null){
						String creatTime = formatter.format(user.getHiredDate());
						st2.setString(5, creatTime);
					}else {
						st2.setString(5,null);
					}
					st2.setString(6,user.getUserid());
					st2.execute();
					log.info(user.getJobnumber() + "已添加至Employee表");
					st2.close();
				}
			} else {
				log.warn(user.getJobnumber() + "在Employee表中已存在");
			}
			rs.close();
			st.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
