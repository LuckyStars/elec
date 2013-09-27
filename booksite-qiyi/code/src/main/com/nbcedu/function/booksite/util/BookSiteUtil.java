package com.nbcedu.function.booksite.util;

import java.util.HashMap;
import java.util.Map;

import com.nbcedu.integration.uc.client.facade.BaseClient;
import com.nbcedu.integration.uc.client.vo.NbcUcPerson;
import com.nbcedu.integration.uc.client.vo.NbcUcTeacher;

public class BookSiteUtil {

	public static Map<String, String> findUserInfo(String uid){
		Map<String, String> userInfo = new HashMap<String, String>();
		if(uid=="1"){
			userInfo.put("UID", uid);
			userInfo.put("USERNAME", "系统管理员");
		}else{
			BaseClient client =new BaseClient();
			NbcUcPerson  person = client.queryPerson(1,uid);
			if(person!=null){
				userInfo.put("UID", uid);
				String strPid = client.queryUidPid(1, uid);  // 查询 PID 
				NbcUcTeacher nus =  client.queryTeacher(1, strPid);
				if(nus!=null){
					String userName = nus.getName();
					if(userName!=null && !"".equals(userName)){  // 填充 姓名
						userInfo.put("USERNAME",userName);  // 姓名
					}else{
						userInfo.put("USERNAME","");  
					}
					String tel =  nus.getTelephone();
					if(tel!=null && !"".equals(tel)){  // 填充 电话
						userInfo.put("TEL",tel);  // 电话
					}else{
						userInfo.put("TEL","");  
					}
					String email = nus.getEmail();
					if(email!=null && !"".equals(email)){  // 填充 邮箱
						userInfo.put("EMAIL",email);  // 邮箱
					}else{
						userInfo.put("EMAIL","");  
					}
				}
			}else{  // 用户中心 出现 脏数据
				return userInfo;
			}
		}
		return userInfo;	
	}
	
}
