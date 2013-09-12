package com.nbcedu.function.elec.util;

import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * 常量
 * 
 * @author qinyuan
 * @since 2013-2-26
 */
public class Constants {
	/** 后台管理员uid */
	public static final String ADMIN_UID="1";
	/** 后台管理员角色ID */
	public static final String ROLE_ID_ADMIN = "ELEC_ADMIN";
	/** 教务老师角色ID */
	public static final String ROLE_ID_MANAGER = "ELEC_MANAGER";
	/** 任课老师角色ID */
	public static final String ROLE_ID_COURSE_TEACHER = "ELEC_COURSE_TEACHER";
	/** SESSION中当前用户对象的key */
	public static final String SESSION_USER_ID = "elec_init";
	/**删除状态为 已删除 **/
	public static final int DEL_STATE_REMOVED = 1;
	/**删除状态为 可用 **/
	public static final int DEL_STATE_ENABLED = 0;

	/**开启**/
	public static final String OPEN = "open";
	/**关闭**/
	public static final String CLOSE = "close";
	
	/**是否俱乐部：是 **/
	public static final Integer CLUB_STATE_YES = 1;
	/**是否俱乐部：否 **/
	public static final Integer CLUB_STATE_NO = 0;
	/**报名方式**/
	public static final String SIGN_TYPE = ConfigHolder.getSignType();
	/**session中报名方式key**/
	public static final String SESSION_SIGN_TYPE_KEY = "signType";
	/**文件上传目录**/
	public static final String COMMON_UPLOAD = "upload";
	/**type 教师**/
	public static final String PERSON_TYPE_TEACHER = "3022100";
	/**type校长 **/
	public static final String PERSON_TYPE_SCHOOL_MASTER = "3022101";
	/**type班主任 **/
	public static final String PERSON_TYPE_CLASS_MASTER = "3022102";
	/**type学生 **/
	public static final String PERSON_TYPE_STUDENT = "3022200";
	/**type家长 **/
	public static final String PERSON_TYPE_PARENT = "3022300";
	/**报名规则史家**/
	public static final String SIGN_TYPE_SHIJIA = "shijia";
	/**报名规则育才**/
	public static final String SIGN_TYPE_YUCAI = "yucai";
	
	/**i18n年级范围**/
	public static final String GRADE_NAMES = ResHolder.getPropertie("i18nGradeNames");
	
	
	private Constants(){}
	
	private static class ConfigHolder{
		static Properties config = new Properties();
		static{
			try {
				config.load(Thread.currentThread().getContextClassLoader().
						getResourceAsStream("config.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		static final String getPropertie(String key){
			return config.getProperty(key);
		}
		static final String getSignType(){
			return getPropertie("signtype");
		} 
	}
	
	public static class ResHolder{
		static ResourceBundle bundler = ResourceBundle.getBundle("message." + SIGN_TYPE);

		static final String getPropertie(String key){
			return bundler.getString(key);
		}
	}
}
