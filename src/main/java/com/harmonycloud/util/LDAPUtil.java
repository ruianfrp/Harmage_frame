package com.harmonycloud.util;

import com.unboundid.ldap.sdk.*;
import com.unboundid.ldap.sdk.controls.SubentriesRequestControl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class LDAPUtil {
    private static String ldapHost = "47.92.161.179";
    private static int ldapPort = 389;
    private static String ldapBindDN = "cn=Manager,dc=harmonycloud,dc=com";
    private static String ldapPassword = "ldappassword";
    private static LDAPConnection connection = null;

    private static void openConnection() {
        if (connection == null) {
            try {
                connection = new LDAPConnection(ldapHost, ldapPort, ldapBindDN, ldapPassword);
                System.out.println("认证成功");
                System.out.println(connection);
            } catch (Exception e) {
                System.out.println("连接LDAP出现错误：\n" + e.getMessage());
            }
        }
    }

    public static String[] insert(String[] arr, String str) {
        int size = arr.length;  //获取数组长度
        String[] tmp = new String[size + 1];
        for (int i = 0; i < size; i++){
            tmp[i] = arr[i];
        }
        tmp[size] = str;  //在最后添加上需要追加的数据
        return tmp;  //返回拼接完成的字符串数组
    }

    /** 修改用户权限 */
    public static void modifyPermission(String requestDN, String uid) {
        try {
            // 连接LDAP
            openConnection();

            SearchResultEntry entry = connection.getEntry(requestDN);
            if (entry == null) {
                System.out.println(requestDN + "请求的DN:" + requestDN + "不存在");
                return;
            }
            System.out.println( 1+ "\t" + entry.getDN() + "\t");
            String[] unique = entry.getAttributeValues("uniqueMember");
            String insertUser = "uid="+uid+",ou=devops,ou=People,dc=harmonycloud,dc=com";
            unique=insert(unique,insertUser);
            Map<String, String[]> maps = new HashMap<>();
            maps.put("uniqueMember", unique);

            // 修改信息
            ArrayList<Modification> md = new ArrayList<Modification>();
            for(String key : maps.keySet()) {
                md.add(new Modification(ModificationType.REPLACE, key, maps.get(key)));
            }
            connection.modify(requestDN, md);
            System.out.println("增加用户权限成功！");
        } catch (Exception e) {
            System.out.println("增加用户权限错误！\n" + e.getMessage());
        }
    }


    /** 创建用户 */
    public static void createUser(String baseDN, String uid,String displayName) {
        String entryDN = "uid=" + uid + "," + baseDN;
        String cn=displayName.substring(0,1);
        String sn=displayName.substring(1);
        try {
            // 连接LDAP
            openConnection();

            SearchResultEntry entry = connection.getEntry(entryDN);
            if (entry == null) {
                // 不存在则创建
                ArrayList<Attribute> attributes = new ArrayList<Attribute>();
                attributes.add(new Attribute("objectClass", "top", "person","organizationalPerson","inetOrgPerson"));
                attributes.add(new Attribute("uid", uid));
                attributes.add(new Attribute("cn", cn));
                attributes.add(new Attribute("sn", sn));
                attributes.add(new Attribute("displayname",displayName));
                attributes.add(new Attribute("mail",uid+"@harmonycloud.cn"));
                attributes.add(new Attribute("userpassword","Ab123456"));
                connection.add(entryDN, attributes);
                System.out.println("创建用户:" + entryDN + "成功！");
            } else {
                System.out.println("用户" + entryDN + "已存在！");
            }
        } catch (Exception e) {
            System.out.println("创建用户出现错误：\n" + e.getMessage());
        }
    }
}
