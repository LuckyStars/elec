package com.nbcedu.function.elec.action;

import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nbcedu.function.elec.biz.ECPlaceBiz;
import com.nbcedu.function.elec.devcore.action.ElecBaseAction;
import com.nbcedu.function.elec.model.ECPlace;
import com.nbcedu.function.elec.util.CommonUtil;
import com.nbcedu.function.elec.util.DictionaryUtil;
import com.nbcedu.function.elec.vo.ECType;

@Component("ecPlaceAction")
@Scope("prototype")
public class ECPlaceAction  extends ElecBaseAction{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ECPlaceAction.class);
	
	private List<ECPlace> placeList = new ArrayList<ECPlace>() ;
	//地点
	private ECPlace ecPlace;
	//类别
	private List<ECType> ecTypelist = new ArrayList<ECType>();
	//类别
	private String [] typeName;
	
	@Resource
	private ECPlaceBiz ecPlaceBiz;
	
	/**
	 * 查询所有地点
	 * @return
	 */
	public String findAll(){
		this.placeList = this.ecPlaceBiz.findAllPlace(this.page );
		this.ecTypelist = this.getEcType();
		return LIST;
	}

	
	/**
	 * 查询单个地点
	 * @return
	 */

	public String findPlace(){
		this.ecPlace = this.ecPlaceBiz.findPlace(this.ecPlace);
		return SUCCESS;
	}
	
	/**
	 * 编辑地点
	 * @return
	 */
	public String updatePlace(){
		String tempname=replaceName(typeName);
		this.ecPlace.setTypeName(tempname);
		this.ecPlaceBiz.updatePlace(this.ecPlace);
		return findAll();
	}
	
	/**
	 * 新增地点
	 * @return
	 */
	public String addPlace(){
		String tempname=replaceName(typeName);
		this.ecPlace.setTypeName(tempname);
		this.ecPlaceBiz.addPlace(ecPlace);
		return findAll();
	}
	
	public void editPlace(){
		this.ecPlace = this.ecPlaceBiz.findPlace(this.ecPlace);
		String result = CommonUtil.getGson().toJson(ecPlace);
		response.setContentType("text/html;charset=UTF-8");
		try {
			response.getWriter().write(result);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.toString());
		}
	}
	
	/**
	 * 判断地点名是否重复
	 * @throws IOException
	 */
	public void checkPlaceName() throws IOException{
		boolean b = Boolean.FALSE;
		//判断是添加还是编辑的标志位  flag=("add":添加；"edit":编辑)
		String flag = request.getParameter("flag");
		b = this.ecPlaceBiz.checkPlaceName(ecPlace , flag);
		this.response.setContentType("text/html;charset=UTF-8");
		this.response.getWriter().write(String.valueOf(b).trim());
	}
	
	/**
	 * 关闭地点  ecPlace.del_state状态值改为1（关闭）
	 * @throws Exception
	 */
	public void close()throws Exception{
		boolean b = Boolean.FALSE; 
		//判断开启和关闭的标志位  "open"开启 ， "close"关闭
		String flag = request.getParameter("flag");
		b = this.ecPlaceBiz.modifyPlace(ecPlace.getId() , flag);
		this.response.setContentType("text/html;charset=UTF-8");
		this.response.getWriter().write(String.valueOf(b).trim());
	}
	/**
	 * 启用该地点  ecPlace.del_state状态值改为0（开启）
	 * @throws Exception
	 */
	public void open()throws Exception{
		boolean b = Boolean.FALSE;
		//判断开启和关闭的标志位   "open"开启 ， "close"关闭
		String flag = request.getParameter("flag");
		b = this.ecPlaceBiz.modifyPlace(ecPlace.getId() , flag);
		this.response.setContentType("text/html;charset=UTF-8");
		this.response.getWriter().write(String.valueOf(b).trim());
	}
	
	/**
	 * 相关课程名称转化成字符串
	 * @param name
	 * @return
	 */
	public String replaceName(String []name){ 
		StringBuffer tempname= new StringBuffer(name.length*33);
		for(int i=0;i<name.length;i++){
			tempname.append(name[i].trim());
			tempname.append(",");
		}
		return tempname.substring(0, tempname.length()-1);
	}
	
	/**
	 * 查询类别
	 * @return
	 */
	public List<ECType> getEcType(){
		List <ECType> list = DictionaryUtil.getTypeList();
		return list;
	}
	
	//////////////////////////
	/////GETTERS&SETTERS//////
	//////////////////////////
	public List<ECType> getEcTypelist() {
		return ecTypelist;
	}
	public void setEcTypelist(List<ECType> ecTypelist) {
		this.ecTypelist = ecTypelist;
	}
	public List<ECPlace> getPlaceList() {
		return placeList;
	}
	public void setPlaceList(List<ECPlace> placeList) {
		this.placeList = placeList;
	}
	public String[] getTypeName() {
		return typeName;
	}
	public void setTypeName(String[] typeName) {
		this.typeName = typeName;
	}
	public ECPlace getEcPlace() {
		return ecPlace;
	}
	public void setEcPlace(ECPlace ecPlace) {
		this.ecPlace = ecPlace;
	}
	
}
