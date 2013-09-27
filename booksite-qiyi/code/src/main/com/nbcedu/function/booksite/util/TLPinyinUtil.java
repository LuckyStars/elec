package com.nbcedu.function.booksite.util;

import java.text.Collator;
import java.util.Comparator;

import com.nbcedu.core.privilege.model.User;

@SuppressWarnings("unchecked")
public class TLPinyinUtil implements Comparator{

	@Override
	public int compare(Object o1, Object o2) {
		User u1 = (User)o1;
		User u2 = (User)o2;
		Collator myCollator = Collator.getInstance(java.util.Locale.CHINA);
		if (myCollator.compare(u1.getName(), u2.getName()) < 0)  
            return -1;  
        else if (myCollator.compare(u1.getName(), u2.getName()) > 0)  
            return 1;  
        else  
            return 0; 
	}
	
}
