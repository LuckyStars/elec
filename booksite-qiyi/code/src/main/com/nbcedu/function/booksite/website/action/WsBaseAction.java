package com.nbcedu.function.booksite.website.action;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.nbcedu.function.booksite.website.pager.Pager;
import com.nbcedu.function.booksite.website.pager.PagerModel;
import com.nbcedu.function.functionsupport.core.FunctionSupportUtil;
import com.nbcedu.function.functionsupport.core.SupportManager;
import com.nbcedu.function.functionsupport.mapping.PortalMessage;
import com.opensymphony.xwork2.ActionSupport;

/**
 * <p></p>
 * @author 黎青春
 * Create at:2012-2-21 上午08:49:19
 */
@SuppressWarnings("serial")
public class WsBaseAction extends ActionSupport {

	public static Logger logger = Logger.getLogger(WsBaseAction.class);
	/**
	 * 默认的分页对象
	 */
	protected Pager pager = new Pager();
	
	protected PagerModel pm = new PagerModel();
	
	protected String id;
	protected List<String> idList = new ArrayList<String>();
	protected FunctionSupportUtil functionSupport = SupportManager.getFunctionSupportUtil();

	/**
	 * 进行增删改操作后,以redirect方式重新打开action默认页的result名
	 */
	public static final String RELOAD = "reload";
	
	/**
	 * 显示列表
	 */
	public static final String LIST = "list";
	
	/**
	 * 查看详细
	 */
	public static final String LOOK = "look";
	
	/**
	 * 排序
	 */
	protected String ORDERBYID ="ORDER BY id DESC";
	
	public PagerModel getPm() {
		return pm;
	}
	public void setPm(PagerModel pm) {
		this.pm = pm;
	}
	
	public Pager getPager() {
		return pager;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @param pager the pager to set
	 */
	public void setPager(Pager pager) {
		this.pager = pager;
	}
	public List<String> getIdList() {
		return idList;
	}
	public void setIdList(List<String> idList) {
		this.idList = idList;
	}
	
	
	/**
	 * 协同NBC推送消息
	 * @author 黎青春
	 * @param sendUId 发起消息用户uid
	 * @param toUId 收到消息用户uid
	 * @param message 消息内容
	 */
	protected void sendNBCMessage(String sendUId, String toUId, String message) {
		if (sendUId == null || toUId == null) {
			return;
		}
		try {
			this.functionSupport.sendOAMessage(sendUId, toUId, message);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("消息推送失败！");
		}
	}

	/**
	 * 协同NBC推送消息（群发）
	 * @author 黎青春
	 * @param sendUId 发起消息用户uid
	 * @param toUIds 收到消息用户uid列表
	 * @param message 消息内容
	 */
	protected void sendNBCMessage(String sendUId, List<String> toUIds, String message) {
		this.functionSupport.sendOAMessage(sendUId, toUIds, message);
	}
	
	
	/** 
	 * 推送应用消息到门户
	 * @author 黎青春
	 * @param id 所推送的信息的唯一标识
	 * @param title 消息的标题
	 * @param summary 消息的摘要
	 * @param url 接收用户访问该信息的URL
	 * @param moduleName 当前应用的中文名称
	 * @param uids 接收人的UID数组
	 * @return 响应标识
	 * @throws Exception 如果请求出现异常
	 */ 
//	protected void push(String id, String title, String summary, String url, String moduleName, String[] uids){
//		try {
//			 
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("门户信息推送失败！");
//		}
//	}
	
	/** 
	 * 删除 推送应用消息到门户
	 * @author 黎青春
	 * @param id 所推送的信息的唯一标识
	 * @param moduleName 当前应用的中文名称
	 * @throws Exception 如果请求出现异常
	 */ 
	protected void delPush(String id, String moduleName){
		try {
			  PortalMessage message = new PortalMessage();
			  message.setMessageId(id);
			  message.setModuleName(moduleName);
			  this.functionSupport.removeSingleMessage(message, null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("删除 门户信息推送失败！");
		}
	}
	
	protected void writeStr(String str){
		PrintWriter out = null;
		try {
			out = ServletActionContext.getResponse().getWriter();
			out.write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{if(out!=null){out.close();}}
	}
}
