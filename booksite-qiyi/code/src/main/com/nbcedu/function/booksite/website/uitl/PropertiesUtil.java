package com.nbcedu.function.booksite.website.uitl;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 属性文件的读取   
 * @author       作者 黎青春
 * @version      版本 0.01
 * @filename     文件名 PropertiesUtil.java
 * @date         创建日期 Dec 23, 2009
 * @description  描述
 */
public class PropertiesUtil {


		private static Properties properties =new Properties();
	
       static{
			try {
				//  加载  backup.properties
				properties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream("push.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		/**
		 * 加载属性文件
		 * 方法名称:load
		 * 作者:黎青春
		 * 创建日期:May 21, 2010
		 * 方法描述:  
		 * @param filename void
		 */
		public static void load(String filename){
			try {
				//  加载  backup.properties
				properties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(filename));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		/**
		 * 得到文件路径
		 * 方法名称:geturl
		 * 作者:黎青春
		 * 创建日期:Dec 30, 2009
		 * 方法描述:  
		 * @param filename
		 * @return String
		 */
		public static String getPathURL(String filename){
			
			return PropertiesUtil.class.getClassLoader().getResource(filename).toString().substring(6);
			
		}
	
		/**
		 * 读取 backup.properties 文件中的值
		 * 方法名称:readerValue
		 * 作者:黎青春
		 * 创建日期:Dec 15, 2009
		 * 方法描述:  
		 * @param key
		 * @return String
		 */
		 public static String readValue(String key){
		     return (String)properties.get(key);
		 } 
		/**
		 * 更新修改属性文件
		 * 方法名称:SaveOrModifyPropertie
		 * 作者:黎青春
		 * 创建日期:Dec 30, 2009
		 * 方法描述:  
		 * @param name
		 * @param value void
		 */
		public static void SaveOrModifyPropertie(String key,Object value){
			properties.put(key, value.toString());
		}
		/**
		 * 删除属性文件
		 * 方法名称:delPropertie
		 * 作者:黎青春
		 * 创建日期:Dec 30, 2009
		 * 方法描述:  
		 * @param key void
		 */
		public static void delPropertie(String key){
			properties.remove(key);
		}
	 
		 /**
		    *修改对应KEY的属性值
		    *@param 路径path String
		    *@param 关键字propertyName String
		    *@param 关键字对应的属性值 String
		    *@return void
		    */
		    public static void setPorpertyValue(String porpertyName,
		                                       String porpertyValue,String filepath){
		       Properties prop = new Properties();
		       try{
		           FileInputStream in = new FileInputStream(filepath);
		           prop.load(in);
		           prop.setProperty(porpertyName,porpertyValue);
		           in.close();
		           FileOutputStream out = new FileOutputStream(filepath);
		           prop.store(out,null);
		           out.close();
		       }
		       catch(FileNotFoundException e){
		    	   //System.out.println("没有提供的属性文件！");
		           e.printStackTrace();
		       }
		       catch(IOException e){
		    	   //System.out.println("存取属性文件有误，请检查属性文件！");
		           e.printStackTrace();
		       }
		    }
		    /**
		     *取得对应KEY的属性值
		     *@param 路径path String
		     *@param 关键字propertyName String
		     *@return 关键字对应的属性值 String
		     */
		     public static String getPropertyValue(String propertyName,String filepath){
		        String propertyValue = "";
		        Properties prop = new Properties();
			    //System.out.println(path);
		        try{
		            FileInputStream in = new FileInputStream(filepath);
		            prop.load(in);
		            propertyValue = prop.getProperty(propertyName);
		            in.close();
		        }
		        catch(FileNotFoundException e){
		        	//System.out.println("没有提供的属性文件！");
		            e.printStackTrace();
		        }
		        catch(IOException e){
		        	//System.out.println("读取属性文件有误，请检查属性文件！");
		            e.printStackTrace();
		        }
		        return propertyValue;
		     }
		     
}
   