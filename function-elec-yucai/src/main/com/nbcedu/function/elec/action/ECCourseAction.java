package com.nbcedu.function.elec.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.nbcedu.function.elec.biz.ECClasshourBiz;
import com.nbcedu.function.elec.biz.ECCourseBiz;
import com.nbcedu.function.elec.biz.ECPlaceBiz;
import com.nbcedu.function.elec.biz.ECSignBiz;
import com.nbcedu.function.elec.biz.ECStudentInfoVoBiz;
import com.nbcedu.function.elec.biz.ECTermBiz;
import com.nbcedu.function.elec.devcore.action.ElecBaseAction;
import com.nbcedu.function.elec.devcore.util.StringUtil;
import com.nbcedu.function.elec.devcore.util.exl.ExlAnnotationUtil;
import com.nbcedu.function.elec.devcore.util.exl.mapping.SheetContent;
import com.nbcedu.function.elec.devcore.util.json.JSONArray;
import com.nbcedu.function.elec.devcore.util.json.JSONObject;
import com.nbcedu.function.elec.model.ECClasshour;
import com.nbcedu.function.elec.model.ECCourse;
import com.nbcedu.function.elec.model.ECPlace;
import com.nbcedu.function.elec.model.ECTerm;
import com.nbcedu.function.elec.util.CommonUtil;
import com.nbcedu.function.elec.util.Constants;
import com.nbcedu.function.elec.util.CourseAttr;
import com.nbcedu.function.elec.util.DateUtil;
import com.nbcedu.function.elec.util.DictionaryUtil;
import com.nbcedu.function.elec.util.Struts2Utils;
import com.nbcedu.function.elec.util.WeekType;
import com.nbcedu.function.elec.vo.ECCourseVO;
import com.nbcedu.function.elec.vo.ECStudentInfoVo;
import com.nbcedu.function.elec.vo.ECType;
import com.nbcedu.function.elec.vo.ECUser;
import com.nbcedu.integration.uc.client.facade.BaseClient;
import com.nbcedu.integration.uc.client.vo.NbcUcEdusys;
import com.nbcedu.integration.uc.client.vo.NbcUcGrade;

/**
 * 课程管理
 * 
 * @author qinyuan
 * @since 2013-3-6
 */
@Controller("ecCourseAction")
@Scope("prototype")
public class ECCourseAction extends ElecBaseAction {
	private static final long serialVersionUID = 1L;
	@Resource
	private ECCourseBiz ecCourseBiz;
	@Resource
	private ECClasshourBiz ecClasshourBiz;
	@Resource
	private ECTermBiz ecTermBiz;
	@Resource
	private BaseClient baseClient;
	@Resource
	private ECPlaceBiz ecPlaceBiz;
	@Resource(name="elecSignBiz")
	private ECSignBiz elecSignBiz;
	@Resource(name="elecStuInfoVoBiz")
	private ECStudentInfoVoBiz elecStuInfoVoBiz;
	
	private ECCourseVO ecCond; // 查询条件、添加、编辑

	/**
	 * 分页
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String list() {
		// 兴趣班列表
		List<ECTerm> termList = ecTermBiz.findAll();
		request.setAttribute("termList", termList);

		// 课程类别
		List<ECType> typeList = DictionaryUtil.getTypeList();
		request.setAttribute("typeList", typeList);
		
		// 年级范围
		List<NbcUcEdusys> ucEdusyslist = baseClient.queryEdusysList(1, null);
		if (ucEdusyslist != null && ucEdusyslist.size() > 0) {
			NbcUcEdusys edusys = ucEdusyslist.get(0);
			List<NbcUcGrade> ucGradeList = baseClient.queryGrades(edusys.getId());
			request.setAttribute("ucGradeList", ucGradeList);
		}

		ECTerm ecTerm = null;
		if (termList != null && termList.size() > 0) {
			for (ECTerm term : termList) {
				if (term.isCurrentTerm()) {
					ecTerm = term;//获取当前学期
					break;
				}
			}
		}
		if (ecTerm != null) {
			String currentTermId = ecTerm.getId();

			// 分页
			if (ecCond == null) {
				ecCond = new ECCourseVO();
			} else {
				if (!StringUtils.isBlank(ecCond.getTermId()) && !currentTermId.equals(ecCond.getTermId())) {
					if (termList != null && termList.size() > 0) {
						for (ECTerm term : termList) {
							if (term.getId().equals(ecCond.getTermId())) {
								ecTerm = term;
								break;
							}
						}
					}
				}

				// 年级
				if (!StringUtils.isBlank(ecCond.getGradeIds())) {
					ecCond.setCondGradeIds(ecCond.getGradeIds().split(","));
				}
				// 教师
				if (!StringUtils.isBlank(ecCond.getTeacherIds())) {
					ecCond.setTeacherIdsArr(ecCond.getTeacherIds().split(","));
				}
			}
			
			ECUser user = CommonUtil.getCurrentUser();
			if (user!=null && user.isCourseTeacherViewable()) {//可查看权限的教师
				ecCond.setTeacherIdsArr(new String[]{user.getUid()});
			}
			ecCond.setTermId(ecTerm.getId());
			ecCourseBiz.findPage(ecCond, page);

			// 是否出现：编辑、删除
			List<ECCourseVO> list = (List<ECCourseVO>) page.getDatas();
			for (ECCourseVO vo : list) {
				vo.setEdit(true);
				if (ecTerm.getOpenDateStart().getTime() <= new Date().getTime() || !vo.getTermId().equals(currentTermId)) {
					vo.setEdit(false);
				}
			}
			
			boolean btnBool = true;
			if (ecTerm.getOpenDateStart().getTime() <= new Date().getTime() || !ecTerm.getId().equals(currentTermId)) {
				btnBool = false;
			}
			request.setAttribute("btnBool", btnBool);
		}
		request.setAttribute("ecTerm", ecTerm);

		return LIST;
	}
	
	
	/**
	 * 导出所有课程列表
	 */
	public String exportExl(){
		ECUser user = CommonUtil.getCurrentUser();
		if (user!=null && user.isCourseTeacherViewable()) {//可查看权限的教师
			ecCond.setTeacherIdsArr(new String[]{user.getUid()});
		}
		
		ECTerm term = ecTermBiz.findById(ecCond.getTermId());
		List<ECCourseVO> voList = ecCourseBiz.findPage(ecCond, null);
		
		if(voList!=null && voList.size()>0){
			StringBuilder sb = null;
			for(ECCourseVO vo : voList){
				List<ECClasshour> chList = vo.getClasshourList();
				sb = new StringBuilder();
				int len = chList.size();
				for(int i=0; i<len; i++){
					sb.append(WeekType.getById(chList.get(i).getWeekIndex()).getName() + " ")
						.append(DateUtil.date2Str(chList.get(i).getWeekStartTime(), "HH:mm:ss") + " - ")
						.append(DateUtil.date2Str(chList.get(i).getWeekEndTime(), "HH:mm:ss"));
					if(i != len-1) sb.append("\r\n");
				}
				vo.setClasshoursListStr(sb.toString());
			}
		}
		
		ExlAnnotationUtil.export(term.getName()+"课程列表", voList);
		return null;
	}
	
	
	/**
	 * 导出所有课程学生选课列表
	 */
	public String exportExlWithStuInfo(){
		ECUser user = CommonUtil.getCurrentUser();
		if (user!=null && user.isCourseTeacherViewable()) {//可查看权限的教师
			ecCond.setTeacherIdsArr(new String[]{user.getUid()});
		}
		
		ECTerm term = ecTermBiz.findById(ecCond.getTermId());
		List<SheetContent> sheetList = new ArrayList<SheetContent>(); 
		List<ECCourseVO> voList = ecCourseBiz.findPage(ecCond, null);
		if(voList!=null && voList.size()>0){
			sheetList.clear();
			SheetContent sheet = null;
			String head = ""; //课程名称
			List<String> conditions = null; //excel条件
			List<ECStudentInfoVo> dataList = null; //excel数据
			StringBuilder sb = null;
			for(ECCourseVO vo : voList){
				head = vo.getName();
				conditions = new ArrayList<String>();
				
				List<ECClasshour> chList = vo.getClasshourList();
				sb = new StringBuilder();
				int len = chList.size();
				for(int i=0; i<len; i++){
					sb.append(WeekType.getById(chList.get(i).getWeekIndex()).getName() + " ")
						.append(DateUtil.date2Str(chList.get(i).getWeekStartTime(), "HH:mm:ss") + " - ")
						.append(DateUtil.date2Str(chList.get(i).getWeekEndTime(), "HH:mm:ss"));
					if(i != len-1) sb.append("\r\n");
				}
				conditions.add("上课时间：" + sb.toString());
				conditions.add("上课地点：" + vo.getStartPlaceName());
				conditions.add("限定人数：" + vo.getMaxStudentNum());
				conditions.add("已选人数：" + vo.getCurrentStudentNum());
				conditions.add("任课老师 ：" + vo.getTeacherNames());
				conditions.add("年级范围 ：" + vo.getGradeNames());
				
				dataList = elecStuInfoVoBiz.encapeList(elecSignBiz.findByCourseId(vo.getId()));
				
				sheet = new SheetContent(head, conditions, dataList, null);
				sheetList.add(sheet);
			}
		}
		
		ExlAnnotationUtil.export(term.getName()+"选课学生列表", sheetList.toArray(new SheetContent[sheetList.size()]));
		return null;
	}
	
	
	/**
	 * 查看
	 */
	public String view() {
		String id = request.getParameter("id");
		String flag = request.getParameter("flag"); //区分跳转页面：1-列表   2-班主任全部课程   3-班主任本班已选学生
		request.setAttribute("ec", ecCourseBiz.getCourseById(id));
		request.setAttribute("flag", flag);
		return "view";
	}
	
	
	/**
	 * 单个、批量删除
	 */
	public String remove() {
		String ids = request.getParameter("ids");
		String[] idArr = ids.split(",");
		this.ecCourseBiz.removeALL(Arrays.asList(idArr));
		return RELOAD;
	}
	/**
	 * 添加、更新UI
	 * 
	 * @return
	 */
	public String addUI() {
		String flag = request.getParameter("flag"); // 编辑
		ECCourseVO vo = null;
		if (!StringUtils.isEmpty(flag)) {
			String id = request.getParameter("id");
			vo = ecCourseBiz.getCourseById(id);
			vo.setCourseContent(StringUtil.toHtml(vo.getCourseContent()));
			vo.setCourseRequire(StringUtil.toHtml(vo.getCourseRequire()));
			vo.setCourseNote(StringUtil.toHtml(vo.getCourseNote()));

			// 上课地点
			List<ECPlace> startPlaceList = ecPlaceBiz.findAllPlace(vo.getTypeId());
			request.setAttribute("startPlaceList", startPlaceList);

			request.setAttribute("ec", vo);
		}

		// 课程类别
		List<ECType> typeList = DictionaryUtil.getTypeList();
		request.setAttribute("typeList", typeList);
		
		//课程属性(育才)
		if(Constants.SIGN_TYPE.equals(Constants.SIGN_TYPE_YUCAI)){
			request.setAttribute("courseAttrs", Arrays.asList(CourseAttr.values()));
		}
		
		// 年级
		List<NbcUcEdusys> ucEdusyslist = baseClient.queryEdusysList(1, null);
		if (ucEdusyslist != null && ucEdusyslist.size() > 0) {
			NbcUcEdusys edusys = ucEdusyslist.get(0);
			List<NbcUcGrade> ucGradeList = baseClient.queryGrades(edusys.getId());
			request.setAttribute("ucGradeList", ucGradeList);
		}

		// 兴趣班
		ECTerm currentTerm = null;
		if (!StringUtils.isEmpty(flag)) {
			currentTerm = vo.getEcTerm();
		} else {
			currentTerm = ecTermBiz.findCurrentTerm();
		}
		request.setAttribute("currentTerm", currentTerm);

		// 星期
		List<WeekType> weekList = Arrays.asList(WeekType.values());
		request.setAttribute("weekList", weekList);

		// 放学地点
		List<ECPlace> placeList = ecPlaceBiz.findAllPlace("");
		request.setAttribute("placeList", placeList);

		if (!StringUtils.isEmpty(flag)) {
			return "editUI";
		}
		return "addUI";
	}
	
	
	/**
	 * 添加、更新
	 * 
	 * @return
	 * @throws Throwable
	 */
	public String add() throws Throwable {
		ECCourse entity = new ECCourse();
		Date operatorDate = null;
		if (!StringUtils.isBlank(ecCond.getId())) { // 更新
			entity = ecCourseBiz.findById(ecCond.getId());
			List<ECClasshour> chList = entity.getClasshourList();
			operatorDate = entity.getOperatorDate();
			for (ECClasshour ech : chList) {
				ech.seteCCourse(null);
			}
			entity.setClasshourList(new ArrayList<ECClasshour>());
			ecClasshourBiz.removeAll(chList);
		}

		BeanUtils.copyProperties(ecCond, entity);
		entity.setName(StringUtil.toHtml(entity.getName()));
		if(StringUtils.isNotBlank(entity.getCourseComment())){
			entity.setCourseComment(StringUtil.toHtml(entity.getCourseComment()));
		}
		entity.setSignStartDate(DateUtil.str2Date(ecCond.getSignStartDateStr(), "yyyy-MM-dd HH:mm:ss"));
		entity.setSignEndDate(DateUtil.str2Date(ecCond.getSignEndDateStr(), "yyyy-MM-dd HH:mm:ss"));

		entity.setClassStartDate(DateUtil.str2Date(ecCond.getClassStartDateStr(), "yyyy-MM-dd"));
		entity.setClassEndDate(DateUtil.str2Date(ecCond.getClassEndDateStr(), "yyyy-MM-dd"));

		String[] weekIndexs = request.getParameterValues("ecCond_real_weekIndexs");
		String[] weekStartTimes = request.getParameterValues("ecCond_real_weekStartTimes");
		String[] weekEndTimes = request.getParameterValues("ecCond_real_weekEndTimes");
		List<ECClasshour> classhourList = new ArrayList<ECClasshour>();
		ECClasshour ch = null;
		if(Constants.SIGN_TYPE.equals(Constants.SIGN_TYPE_SHIJIA)){
		for (int i = 0; i < weekIndexs.length; i++) {
			String weekIndex = weekIndexs[i];
			String weekStartTime = weekStartTimes[i];
			String weekEndTime = weekEndTimes[i];

			ch = new ECClasshour();
			ch.seteCCourse(entity);
			ch.setWeekIndex(Integer.parseInt(weekIndex));
			ch.setWeekStartTime(DateUtil.str2Date(weekStartTime, "HH:mm:ss"));
			ch.setWeekEndTime(DateUtil.str2Date(weekEndTime, "HH:mm:ss"));

			classhourList.add(ch);
		}
		entity.setClasshourList(classhourList);
		}
		ECPlace startPlace = new ECPlace();
		startPlace.setId(ecCond.getStartPlaceId());
		entity.setStartPlace(startPlace);
		
		if (!StringUtils.isBlank(ecCond.getEndPlaceId())) {
			ECPlace endPlace = new ECPlace();
			endPlace.setId(ecCond.getEndPlaceId());
			entity.setEndPlace(endPlace);
		}
		
		// 更新
		if (StringUtils.isBlank(entity.getId())) {
			entity.setOperatorDate(new Date());
		}else{
			entity.setOperatorDate(operatorDate);
		}

		ecCourseBiz.addorUpdate(entity);
		return RELOAD;
	}
	
	
	/**
	 * 校验课程名是否重复
	 * 
	 * @return
	 * @throws Exception
	 */
	public String checkName() throws Exception {
		String name = request.getParameter("name"); // 课程名
		String termId = request.getParameter("termId"); // 兴趣班ID
		String courseID = request.getParameter("courseID"); // 主键ID

		boolean bool = false;
		if (StringUtils.isEmpty(courseID)) {
			bool = ecCourseBiz.checkName(name, termId);
		} else {
			bool = ecCourseBiz.checkName(name, termId, courseID);
		}

		JSONObject jo = new JSONObject();
		jo.put("success", bool ? 0 : 1); // 0-重复 1- 不重复
		Struts2Utils.renderJson(jo.toString());
		return null;
	}
	
	
	/**
	 * 根据课程分类Ajax查询上课地点
	 * 
	 * @return
	 */
	public String getStartPlaces() {
		String typeId = request.getParameter("typeId"); // 课程分类

		List<ECPlace> placeList = ecPlaceBiz.findAllPlace(typeId);
		JSONArray jArray = new JSONArray();
		if (placeList != null && placeList.size() > 0) {
			for (ECPlace place : placeList) {
				JSONObject jObject = new JSONObject(place);
				jArray.put(jObject);
			}
		}
		Struts2Utils.renderJson(jArray.toString());
		return null;
	}
	
	
	/**
	 * 校验课时
	 */
	public String checkClasshour() throws Exception {
		String teacherIds = request.getParameter("teacherIds");// 教师ID集合
		String teacherNames = request.getParameter("teacherNames");// 教师name集合
		String startPlaceId = request.getParameter("startPlaceId"); // 上课地点ID
		String termId = request.getParameter("termId"); // 学期ID
		String classStartDateStr = request.getParameter("classStartDateStr"); // 开课日期
		String classEndDateStr = request.getParameter("classEndDateStr"); // 结课日期
		String[] weekIndexs = request.getParameterValues("ecCond_weekIndexs");
		String[] weekStartTimes = request.getParameterValues("ecCond_weekStartTimes");
		String[] weekEndTimes = request.getParameterValues("ecCond_weekEndTimes");
		String courseID = request.getParameter("courseID");

		Map<String, Object> resultMap = ecClasshourBiz.checkClassHour(teacherIds, teacherNames, startPlaceId, termId,
				classStartDateStr, classEndDateStr, weekIndexs, weekStartTimes, weekEndTimes, courseID);

		JSONObject jo = new JSONObject(resultMap);
		Struts2Utils.renderJson(jo.toString());
		return null;
	}

	public ECCourseVO getEcCond() {
		return ecCond;
	}

	public void setEcCond(ECCourseVO ecCond) {
		this.ecCond = ecCond;
	}

}
