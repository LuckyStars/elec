package com.nbcedu.function.booksite.biz.impl;

import java.util.HashMap;
import java.util.Map;

import com.nbcedu.core.framework.filter.ServiceInfoLoader;
import com.nbcedu.core.privilege.model.User;
import com.nbcedu.integration.uc.client.facade.BaseClient;
import com.nbcedu.integration.uc.client.vo.NbcUcPerson;
/***
 * 场馆预定Loader
 * @author windy
 * @date 2011-6-26
 */
public class BookSiteLoaderImpl implements ServiceInfoLoader {
	
	public Object load(Map<?, ?> paramMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String curUseruid = (String)paramMap.get("uid");
		User loginUser = new User();
		if (curUseruid.equals("1")) {
			loginUser.setName("admin");
			loginUser.setUserType("100");
			loginUser.setUid(curUseruid);
		} else {
			BaseClient client = new BaseClient();
			NbcUcPerson person = client.queryPerson(1, curUseruid);
			String uname = person.getName();
			loginUser.setName(uname);
			loginUser.setUserType("101");
			loginUser.setUid(curUseruid);
		}
		resultMap.put("user",loginUser);
		return resultMap;
	}
	
}
