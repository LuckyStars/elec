package com.nbcedu.function.booksite.util;

import java.util.Comparator;
import java.util.List;

import com.nbcedu.core.privilege.model.Role;
import com.nbcedu.core.privilege.model.User;

@SuppressWarnings("unchecked")
public class UserCompare implements Comparator{	


	@Override
	public int compare(Object o1, Object o2) {
		User u1 = (User)o1;
		User u2 = (User)o2;
		String str1 = "zzero";
		String str2 = "zzero";
		List<Role> roles1 = u1.getRoles();
		List<Role> roles2 = u2.getRoles();
		for (Role r1 : roles1) {
			if(r1.getName().equals("BOOKSITE_ADMIN") || r1.getName().equals("BOOKSITE_COMMON")) {
				for (Role r2 : roles2) {
					if (r2.getName().equals("BOOKSITE_ADMIN") || r2.getName().equals("BOOKSITE_COMMON")) {
						str1 = r1.getName();
						str2 = r2.getName();
						break;
					}
				}
				break;
			}
		}
		return str1.compareTo(str2);
	}
}

