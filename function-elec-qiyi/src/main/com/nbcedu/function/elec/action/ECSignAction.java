package com.nbcedu.function.elec.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.nbcedu.function.elec.biz.ECCourseBiz;
import com.nbcedu.function.elec.biz.ECSignBiz;
import com.nbcedu.function.elec.biz.ECTermBiz;
import com.nbcedu.function.elec.devcore.action.ElecBaseAction;
import com.nbcedu.function.elec.devcore.util.StringUtil;
import com.nbcedu.function.elec.devcore.util.Struts2Utils;
import com.nbcedu.function.elec.model.ECClasshour;
import com.nbcedu.function.elec.model.ECSign;
import com.nbcedu.function.elec.model.ECTerm;
import com.nbcedu.function.elec.util.CommonUtil;
import com.nbcedu.function.elec.util.Constants;
import com.nbcedu.function.elec.util.DateUtil;
import com.nbcedu.function.elec.util.DictionaryUtil;
import com.nbcedu.function.elec.util.WeekType;
import com.nbcedu.function.elec.vo.ECCourseVO;
import com.nbcedu.function.elec.vo.ECSignCourseVo;
import com.nbcedu.function.elec.vo.ECType;
import com.nbcedu.function.elec.vo.ECUser;
import com.opensymphony.xwork2.ActionContext;
@Controller("ecsignAction")
@Scope("prototype")
public class ECSignAction  extends ElecBaseAction {
	private static final long serialVersionUID = 1L;
	@Resource
	private ECCourseBiz ecCourseBiz;
	@Resource(name="elecTermBiz")
	private ECTermBiz termBiz;
	@Resource(name="elecSignBiz")
	private ECSignBiz elecSignBiz;
	private ECTerm term;
	private ECCourseVO ecCourseVO=new ECCourseVO();
	private List<ECSignCourseVo> ecSignCourseVos;
	private List<ECType> ecTypes;
	private ECSign ecSign;
	private Integer flag;
	private Integer locationGo;//返回按键 返回页面的值
	private Integer clubState;//是否是俱乐部
	
	
	/**
	 * 报名首页
	 * @return
	 */
	public String index(){
		term=termBiz.findCurrentTerm();
		if (term==null) {
			Struts2Utils.OutWrite("<script>alert('对不起,系统还没有开始使用或没有有效使用数据!');history.go(-1);</script>");
			return null;
		}
		long nowTime=new Date().getTime();
		long starTime=term.getSignDateStart().getTime()-nowTime;//距报名开始时间
		long endTime=term.getSignDateEnd().getTime()-nowTime;//距报名结束时间
		ECUser uEcUser=CommonUtil.getCurrentUser();
		if (starTime<=0) {
			starTime=0;
		}
		if (endTime<=0) {
			endTime=0;
		}
		ActionContext.getContext().put("uEcUser", uEcUser);
		ActionContext.getContext().put("starTime", starTime);//距报名开始时间
		ActionContext.getContext().put("endTime", endTime);//距报名结束时间
		ActionContext.getContext().put("openSign", 
				term.getOpenDateStart().getTime()-nowTime>0
				||term.getOpenDateEnd().getTime()-nowTime<0?0:1);//未开放报名为0,开放为1
		return "index";
	}
	/**
	 * 报名类型
	 * @return
	 */
	public String ecsignType(){
		
		if (Constants.SIGN_TYPE.equals(Constants.SIGN_TYPE_YUCAI)) {
			return index();
		}else {
			return "ecsignType";
		}
	}
	
	/**
	 * 查看课程页面
	 * @return
	 */
	public String ecsignDetails(){
		ECSignCourseVo ecSignCourseVo=new ECSignCourseVo();
		term=termBiz.findCurrentTerm();
		ECUser uEcUser=CommonUtil.getCurrentUser();
		ecCourseVO=ecCourseBiz.getCourseById(ecCourseVO.getId());
		ecSign=elecSignBiz.findByStudentIdAndCourseId(uEcUser.getUid(), ecCourseVO.getId());
		ecSignCourseVo.setEcSign(ecSign);
		//本课程此学生是否报名
		ecSignCourseVo.setSignUpState(ecSign!=null);
		long nowTime=new Date().getTime();
		//此课程是否开始报名或者结束报名!
		ecSignCourseVo.setSignStarState(
			ecCourseVO.getSignStartDate().getTime()-nowTime<0
			||ecCourseVO.getSignEndDate().getTime()-nowTime>0);
		if (ecSign!=null&&Constants.SIGN_TYPE.equals(Constants.SIGN_TYPE_SHIJIA)) {//此课程是否选时间 是否是史家报名规则
			ecSignCourseVo.setClassDateState(!StringUtil.isEmpty(ecSign.getClasshourIds()));
		}
		if (ecSign!=null&&ecSign.getClasshourIds()!=null) {
			String[] clsshours=ecSign.getClasshourIds().split(",");
			List<ECClasshour> ecClasshours=new ArrayList<ECClasshour>();
			for (String string : clsshours) {
				chr:for (ECClasshour ch : ecCourseVO.getClasshourList()) {
					if (ch.getId().equals(string)) {
						ecClasshours.add(ch);
						break chr;
					}
				}
			}
			ecSignCourseVo.setEcClasshours(ecClasshours);
		}
		ActionContext.getContext().put("ecSignCourseVo", ecSignCourseVo);
		ActionContext.getContext().put("courseCount", 
				elecSignBiz.findByStudentIdAndTermId(uEcUser.getUid(), term.getId()));
		ActionContext.getContext().put("ecsignType", 
				Constants.SIGN_TYPE.equals(Constants.SIGN_TYPE_YUCAI)?0:1);//育才报名0,其他为1
		return "ecsignDetails";
	}
	
	/**
	 * 课时加载判断
	 */
	@SuppressWarnings("unchecked")
	public void classDate(){
		ecCourseVO=ecCourseBiz.getCourseById(id);
		if (ecCourseVO!=null) {
			List<ECClasshour> ecClasshours=ecCourseVO.getClasshourList();
			Integer courseCount=(ecCourseVO.getMaxStudentNum()*
					ecCourseVO.getClasshourRequire())/ecCourseVO.getClasshourNum();//每节课最多报多少人
			JSONArray jsonArray=new JSONArray();
			if(ecClasshours!=null&&ecClasshours.size()>0){
				JSONObject jsonObject=null;
				for (ECClasshour ecClasshour : ecClasshours) {
					jsonObject=new JSONObject();
					Long count=elecSignBiz.findByClassDateCount(ecClasshour.getId());//本节课当前报名数
					jsonObject.put("id", ecClasshour.getId());
					jsonObject.put("name", ecClasshour.getWeekIndexStr()
							+DateUtil.date2Str(ecClasshour.getWeekStartTime(), "     HH:mm")+
							" - "
							+DateUtil.date2Str(ecClasshour.getWeekEndTime(), "HH:mm"));
					jsonObject.put("state", count>=courseCount);//此课程是否报满
					jsonArray.add(jsonObject);
				}
			}
			Struts2Utils.OutWrite(jsonArray.toString());
		}
		
	}
	
	
	
	
	/**
	 * 学生报名页面
	 * @return
	 */
	public String ecsignMy(){
		term=termBiz.findCurrentTerm();
		ECUser uEcUser=CommonUtil.getCurrentUser();
		ecSignCourseVos=new ArrayList<ECSignCourseVo>();
		long nowTime=new Date().getTime();
		List<ECSign> ecSigns=elecSignBiz.findByStudentId(uEcUser.getUid());
		if (ecSigns!=null&&ecSigns.size()>0) {
			ECSignCourseVo ecSignCourseVo=null;
			for (ECSign ecSign : ecSigns) {
				ECCourseVO ecCourseVO=ecCourseBiz.getCourseById(ecSign.getCourseId());
				ecSignCourseVo=new ECSignCourseVo();
				ecSignCourseVo.setEcCourseVO(ecCourseVO);
				ecSignCourseVo.setEcSign(ecSign);
				//此课程是否开始报名或者结束报名!
				ecSignCourseVo.setSignStarState(
					ecCourseVO.getSignStartDate().getTime()-nowTime<0
					||ecCourseVO.getSignEndDate().getTime()-nowTime>0);
				if (Constants.SIGN_TYPE.equals(Constants.SIGN_TYPE_SHIJIA)) {//此课程是否选时间 是否是史家报名规则
					ecSignCourseVo.setClassDateState(!StringUtil.isEmpty(ecSign.getClasshourIds()));
				}
				
				if (ecSign.getClasshourIds()!=null) {
					String[] clsshours=ecSign.getClasshourIds().split(",");
					List<ECClasshour> ecClasshours=new ArrayList<ECClasshour>();
					for (String string : clsshours) {
						chr:for (ECClasshour ch : ecCourseVO.getClasshourList()) {
							if (ch.getId().equals(string)) {
								ecClasshours.add(ch);
								break chr;
							}
						}
					}
					ecSignCourseVo.setEcClasshours(ecClasshours);
				}
				ecSignCourseVos.add(ecSignCourseVo);
			}
		}
		ActionContext.getContext().put("uEcUser", uEcUser);
		ActionContext.getContext().put("ecsignType", 
				Constants.SIGN_TYPE.equals(Constants.SIGN_TYPE_YUCAI)?0:1);//育才报名0,其他为1
		ActionContext.getContext().put("courseCount", 
				ecSignCourseVos.size());
		ActionContext.getContext().put("openSign", 
				term.getOpenDateStart().getTime()-nowTime>0
				||term.getOpenDateEnd().getTime()-nowTime<0?0:1);//未开放报名为0,开放为1
		return "ecsignMy";
	}
	
	/**
	 *报名页面
	 */
	public  String ecsign(){
		term=termBiz.findCurrentTerm();
		long nowTime=new Date().getTime();
		if (term.getOpenDateStart().getTime()-nowTime>0
		||term.getOpenDateEnd().getTime()-nowTime<0) {//报名是否开放
			return index();
		}
		ECUser uEcUser=CommonUtil.getCurrentUser();
		Map<Integer, String> weekMap=WeekType.getEnumMap();
		ecCourseVO.setTermId(term.getId());
		ecCourseVO.setClubState(clubState);
		ecCourseVO.setCondGradeIds(uEcUser.getGradeId().split(","));
		ecTypes=DictionaryUtil.getTypeList();
		List<ECCourseVO> ecCourseVOs=ecCourseBiz.findPage(ecCourseVO, null);
		if (ecCourseVOs!=null&&ecCourseVOs.size()>0) {
			ecSignCourseVos=new ArrayList<ECSignCourseVo>();
			ECSignCourseVo ecSignCourseVo=null;
			for (ECCourseVO ecCourseVO : ecCourseVOs) {
				ECSign ecSign=elecSignBiz.findByStudentIdAndCourseId(uEcUser.getUid(), ecCourseVO.getId());
				ecSignCourseVo=new ECSignCourseVo();
				ecSignCourseVo.setEcCourseVO(ecCourseVO);
				ecSignCourseVo.setEcSign(ecSign);
				//此课程是否开始报名或者结束报名!
				ecSignCourseVo.setSignStarState(
					ecCourseVO.getSignStartDate().getTime()-nowTime<0
					&&ecCourseVO.getSignEndDate().getTime()-nowTime>0);
				//本课程此学生是否报名
				ecSignCourseVo.setSignUpState(ecSign!=null);
				if (ecSign!=null&&Constants.SIGN_TYPE.equals(Constants.SIGN_TYPE_SHIJIA)) {
					//此课程是否选时间 是否是史家报名规则
					ecSignCourseVo.setClassDateState(!StringUtil.isEmpty(ecSign.getClasshourIds()));
				}
				ecSignCourseVos.add(ecSignCourseVo);
			}
		}
		ActionContext.getContext().put("weekMap", weekMap);
		ActionContext.getContext().put("uEcUser", uEcUser);
		ActionContext.getContext().put("courseCount", 
				elecSignBiz.findByStudentIdAndTermId(uEcUser.getUid(), term.getId()));
		ActionContext.getContext().put("ecsignType", 
				Constants.SIGN_TYPE.equals(Constants.SIGN_TYPE_YUCAI)?0:1);//育才报名0,其他为1
		return "ecsign";
	}
	
	/**
	 *报名
	 */
	public  String ecsignAdd(){
		ecSign.setStuId(CommonUtil.getCurrentUser().getUid());
		this.message=elecSignBiz.addSignPro(ecSign);
		if (flag==1) {//跳转到报名页面
			 return ecsign();
		}else if (flag==2) {//跳转到个人报名页面
			 return ecsignMy();
		}else {//跳转到详细报名页面
			return ecsignDetails();
		}
	}
	/**
	 * 退课
	 * @return
	 */
	public String ecsignDel(){
		ECCourseVO CVO=ecCourseBiz.getCourseById(elecSignBiz.findById(id).getCourseId());
		if (CVO.getSignStartDate().getTime()-new Date().getTime()<0
				&&CVO.getSignEndDate().getTime()-new Date().getTime()>0) {
			this.message=elecSignBiz.removeSign(id)?"退课成功!":"退课失败!";
		}else {
			this.message="报名未开始，不能进行退课!";
		}
		if (flag==1) {
			 return ecsign();
		}else if (flag==2) {
			 return ecsignMy();
		}else {
			return ecsignDetails();
		}
	}
	/**
	 * 退课时
	 * @return
	 */
	public String ecsignUpdate(){
		ECCourseVO CVO=ecCourseBiz.getCourseById(elecSignBiz.findById(id).getCourseId());
		if (CVO.getSignStartDate().getTime()-new Date().getTime()<0
				&&CVO.getSignEndDate().getTime()-new Date().getTime()>0) {
			this.message=elecSignBiz.updateSign(id)?"退课时成功!":"退课时失败!";
		}else {
			this.message="报名未开始，不能进行退课时!";
		}
		if (flag==1) {
			 return ecsign();
		}else if (flag==2) {
			 return ecsignMy();
		}else {
			return ecsignDetails();
		}
	}
	//get&&set
	public List<ECType> getEcTypes() {
		return ecTypes;
	}

	public void setEcTypes(List<ECType> ecTypes) {
		this.ecTypes = ecTypes;
	}


	public ECCourseVO getEcCourseVO() {
		return ecCourseVO;
	}

	public void setEcCourseVO(ECCourseVO ecCourseVO) {
		this.ecCourseVO = ecCourseVO;
	}

	public ECTerm getTerm() {
		return term;
	}

	public void setTerm(ECTerm term) {
		this.term = term;
	}
	public List<ECSignCourseVo> getEcSignCourseVos() {
		return ecSignCourseVos;
	}
	public void setEcSignCourseVos(List<ECSignCourseVo> ecSignCourseVos) {
		this.ecSignCourseVos = ecSignCourseVos;
	}
	public ECSign getEcSign() {
		return ecSign;
	}
	public void setEcSign(ECSign ecSign) {
		this.ecSign = ecSign;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public Integer getLocationGo() {
		return locationGo;
	}
	public void setLocationGo(Integer locationGo) {
		this.locationGo = locationGo;
	}
	public Integer getClubState() {
		return clubState;
	}
	public void setClubState(Integer clubState) {
		this.clubState = clubState;
	}
}
