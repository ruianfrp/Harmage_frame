package com.harmonycloud.util;


import com.alibaba.fastjson.JSONObject;
import com.harmonycloud.bean.Message;
import com.harmonycloud.bean.VerifyMessage;
import com.harmonycloud.bean.account.LoginInfoView;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * @author ：wz
 * @date ：Created in 2019/8/8 21:28
 */
public class JsonWebToken {
	private static final String JWT_SECRET = "1AsdadSAS123daXX1AsdadSAS123daXX1AsdadSAS123daXX";//48位

	/**
	 * 完成Unicode到String格式转换
	 * @param unicode 待转换字符串
	 */
	public static String unicodeToString(String unicode) {
		StringBuffer string = new StringBuffer();
		String[] hex = unicode.split("\\\\u");
		for (int i = 1; i < hex.length; i++) {
			int data = Integer.parseInt(hex[i], 16);// 转换出每一个代码点
			string.append((char) data);// 追加成string
		}
		return string.toString();
	}

	/**
	 * 生成项目密匙
	 */
	private static SecretKey generalKey() {
		String stringKey =JWT_SECRET;
		byte[] encodedKey = Base64.decodeBase64(stringKey);
		SecretKey key = Keys.hmacShaKeyFor(encodedKey);
		return key;
	}

	public static String CreateToken(LoginInfoView item)throws Exception{
		return CreateToken(item,60*60*24*1000); //默认token有效时间为一天
	}

	/**
	 *根据请求登录的信息生成令牌
	 * @param item 登录请求相关信息，同时也是令牌解密所需验证信息
	 * @param ttlMillis 令牌有效时间
	 * @return 返还生成的令牌
	 */
	public static String CreateToken(LoginInfoView item,long ttlMillis) throws Exception {
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		JwtBuilder builder = Jwts.builder()
				.setId(item.getId())
				.setSubject(item.getName())
				.claim("role",item.getRole())
				.claim("unionid_dingding",item.getUnionid_dingding())
				.setIssuedAt(now)
				.signWith(generalKey());
		//if it has been specified, let's add the expiration
		if (ttlMillis >= 0) {
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}
		//Builds the JWT and serializes it to a compact, URL-safe string
		return builder.compact();
	}

	/**
	 * 将前端发送过来的请求所附带的信息转换为可识别形式。并发送给验证令牌的函数
	 * @param js 前端发送请求时所附带的String格式的json文件
	 */
	public static LoginInfoView ParseToken(String js) throws JwtException{
		if(js==null||js.equals("")) return null;
		JSONObject jsStr = JSONObject.parseObject(js);
		String token=jsStr.getString("token");
		String id=jsStr.getString("id");
		String name=jsStr.getString("name");
		if(name.contains("%")){//如果姓名包含%则认为其为前端格式的unicode字符串，转换为UTF-8格式
			name=name.replace('%','\\');
			name=unicodeToString(name);
		}
		String role=jsStr.getString("role");
		return ParseToken_(token,new LoginInfoView(id,name,role));
	}

	/**
	 * 验证令牌，成功则返还令牌所携带的信息
	 */
	private static LoginInfoView ParseToken_(String token,LoginInfoView info) throws JwtException{
		Jws<Claims> jws;
		try {
			jws = Jwts.parser()
					.setAllowedClockSkewSeconds(3*60)//This ensures that clock differences between the machines can be ignored.
					.requireSubject(info.getName())
					.requireId(info.getId())
					.require("role",info.getRole())
					.setSigningKey(generalKey())
					.parseClaimsJws(token);
			}
		catch (JwtException ex) {
				System.err.println("token parsing failed!");
				return null;
			}
			Claims res=jws.getBody();
			LoginInfoView userinfo=new LoginInfoView(
					res.getId(),
					res.getSubject(),
					(String)res.get("role"),
					(String)res.get("unionid_dingding")
			);
			return userinfo;
			}

	public static VerifyMessage VerifyCode(String auth_code){
		Message message = new Message();
		LoginInfoView userinfo= null;
		try {
			userinfo = JsonWebToken.ParseToken(auth_code);
		} catch (JwtException e) {
			e.printStackTrace();
		}

		if(auth_code==null||auth_code==""||userinfo==null){
			message.setMessage(401,"Authorization参数校验失败",null);
		}
		return new VerifyMessage(message,userinfo);
	}

	public static void main(String[] args) {

/*		ParseToken("{'id':'110','name':'%u738B%u4F50','role':'PMO','token':'eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMTAiLCJzdWIiOiLnjovkvZAiLCJyb2xlIjoiUE1PIiwiaWF0IjoxNTY1MzQxNjMzLCJleHAiOjE1NjU0MjgwMzN9.dLDH1wa08WY9eZjMkry45AjCoVMUhKo0QrZT5EDfwYo'}");

		String id="testid",name="王佐",role="PMO";
		LoginInfoView it=new LoginInfoView(id,name,role);
		String token=CreateToken(it);
		System.out.println("JsonWebToken = "+token+"\n");*/
	}
}





