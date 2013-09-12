package com.nbcedu.function.elec.devcore.util.exl.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 声明一个类为可用于导出EXL的模型<br>
 * 如如下代码:
 * <pre> @ExlModel(viewTypes={0,1,2,3})
 * public class PersonModel {
 * }
 * </pre>
 * 如果导出EXL的类不加上此注解则会抛出<tt>IllegalArgumentException</tt>
 * @author xuechong
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)  
@Documented 
public @interface ExlModel {
	
	/**
	 * EXL字段显示类型 <br>
	 * 填入的值应为从0开始连续的正整数
	 * @return
	 * @author xuechong
	 */
	int[] viewTypes() default 0; 
}
