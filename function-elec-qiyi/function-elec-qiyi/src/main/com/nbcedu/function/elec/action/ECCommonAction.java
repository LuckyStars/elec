package com.nbcedu.function.elec.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.json.simple.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.nbcedu.function.elec.devcore.action.ElecBaseAction;
import com.nbcedu.function.elec.util.Constants;
import com.nbcedu.function.elec.util.Struts2Utils;

/**
 * 公共Action
 * 
 * @author qinyuan
 * @since 2013-3-6
 */
@Controller("ecCommonAction")
@Scope("prototype")
@SuppressWarnings("unchecked")
public class ECCommonAction extends ElecBaseAction {
	private static final long serialVersionUID = 1L;

	/**
	 * 文件上传
	 * 
	 * @return
	 * @throws Exception
	 */
	public String upload() {
		String savePath = (this.servletContext.getRealPath("/") + Constants.COMMON_UPLOAD + "/");
		String saveUrl = request.getContextPath() + "/" + Constants.COMMON_UPLOAD + "/";

		// 定义允许上传的文件扩展名
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");

		// 最大文件大小
		long maxSize = 1000000;

		if (!ServletFileUpload.isMultipartContent(request)) {
			Struts2Utils.renderJson(getError("请选择文件。"));
			return null;
		}
		// 检查目录
		File uploadDir = new File(savePath);
		if (!uploadDir.isDirectory()) {
			Struts2Utils.renderJson(getError("上传目录不存在。"));
			return null;
		}
		// 检查目录写权限
		if (!uploadDir.canWrite()) {
			Struts2Utils.renderJson(getError("上传目录没有写权限。"));
			return null;
		}

		String dirName = request.getParameter("dir");
		if (dirName == null) {
			dirName = "image";
		}
		if (!extMap.containsKey(dirName)) {
			Struts2Utils.renderJson(getError("目录名不正确。"));
			return null;
		}
		// 创建文件夹
		savePath += dirName + "/";
		saveUrl += dirName + "/";
		File saveDirFile = new File(savePath);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		savePath += ymd + "/";
		saveUrl += ymd + "/";
		File dirFile = new File(savePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}

		// Struts2 请求 包装过滤器
		MultiPartRequestWrapper wrapper = (MultiPartRequestWrapper) ServletActionContext.getRequest();
		// 获得上传的文件名
		String fileName = wrapper.getFileNames("imgFile")[0];// imgFile,imgFile,imgFile
		// 获得文件过滤器
		File file = wrapper.getFiles("imgFile")[0];

		// 检查扩展名
		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		if (!Arrays.<String> asList(extMap.get(dirName).split(",")).contains(fileExt)) {
			Struts2Utils.renderJson(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
			return null;
		}
		// 检查文件大小
		if (file.length() > maxSize) {
			Struts2Utils.renderJson(getError("上传文件大小超过限制。"));
			return null;
		}

		// 重构上传图片的名称
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String newImgName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
		byte[] buffer = new byte[1024];
		// 获取文件输出流
		FileOutputStream fos = null;
		// 获取内存中当前文件输入流
		InputStream in = null;
		try {
			fos = new FileOutputStream(savePath + "/" + newImgName);
			in = new FileInputStream(file);
			
			int num = 0;
			while ((num = in.read(buffer)) > 0) {
				fos.write(buffer, 0, num);
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
			
			Struts2Utils.renderJson(getError("上传出错。"));
			return null;
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
				Struts2Utils.renderJson(getError("上传出错。"));
				return null;
			}
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
				Struts2Utils.renderJson(getError("上传出错。"));
				return null;
			}
		}

		JSONObject obj = new JSONObject();
		obj.put("error", 0);
		obj.put("url", saveUrl + "/" + newImgName);
		Struts2Utils.renderJson(obj.toJSONString());
		return null;
	}
	
	/**
	 * 出错Json
	 * @param message
	 * @return
	 */
	private String getError(String message) {
		JSONObject obj = new JSONObject();
		obj.put("error", 1);
		obj.put("message", message);
		return obj.toJSONString();
	}

}
