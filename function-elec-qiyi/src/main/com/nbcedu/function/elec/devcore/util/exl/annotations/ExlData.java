package com.nbcedu.function.elec.devcore.util.exl.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 声明为需要导出的数据列
 * 
 *<pre>@ExlModel(viewTypes={0,1,2,3})//标题数应与此处的显示类型数保持一致
 *public class PersonModel {
 *	@ExlData(sortId=1,title={"名字","名字","","名字"})//如果在某种显示模式下不想导出此字段则可以将对应标题设置为空字符
 *	private String name;
 *	@ExlData(sortId=2,title={"年龄","age","age",""})
 *	private Integer age;
 *	@ExlData(sortId=3,title={"姓","姓","familyName","姓"})
 *	private String familyName;
 *	@ExlData(sortId=4,title={"身份证","证件号","IDCODE","IDCODE"})
 *	private String idCode;
 *}
 *</pre>
 * annotation on getters is support since 2013.3.15
 * @author xuechong
 */
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)  
@Documented 
public @interface ExlData {

	/**
	 * 显示时的标题<br>
	 * 标题的数量应与头部viewTypes的数量相对应<br>
	 * 如果想要此字段在某个viewTypes中不显示,则应让对应的标题值为空字符串""
	 * @return
	 * @author xuechong
	 */
	String[] title();
	
	/**
	 * 显示时的序号
	 * 例如@ExlData(sortId=1)<br>
	 * 在生成EXL时会根据此处的id进行排序<br>
	 * 此id应为正整数且不可重复<br>
	 * 
	 * @return
	 * @author xuechong
	 */
	int sortId();

}
