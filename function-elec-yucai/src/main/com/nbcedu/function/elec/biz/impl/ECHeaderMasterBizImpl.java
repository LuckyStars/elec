package com.nbcedu.function.elec.biz.impl;

import java.io.*;
import java.util.*;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Repository;

import com.nbcedu.function.elec.biz.ECCourseBiz;
import com.nbcedu.function.elec.biz.ECHeaderMasterBiz;
import com.nbcedu.function.elec.dao.ECCourseDao;
import com.nbcedu.function.elec.dao.ECSignDao;
import com.nbcedu.function.elec.devcore.util.StringUtil;
import com.nbcedu.function.elec.model.ECClasshour;
import com.nbcedu.function.elec.model.ECCourse;
import com.nbcedu.function.elec.model.ECSign;
import com.nbcedu.function.elec.util.Constants;
import com.nbcedu.function.elec.util.DictionaryUtil;
import com.nbcedu.function.elec.util.ExcelCellStyleUtils;
import com.nbcedu.function.elec.util.WeekType;
import com.nbcedu.function.elec.vo.ECCourseClass;
import com.nbcedu.function.elec.vo.ECCourseVO;
import com.nbcedu.function.elec.vo.ECStuInfo;
import com.nbcedu.integration.uc.client.facade.BaseClient;
import com.nbcedu.integration.uc.client.vo.NbcUcClass;
import com.nbcedu.integration.uc.client.vo.NbcUcParent;
import com.nbcedu.integration.uc.client.vo.NbcUcStudent;
import com.nbcedu.integration.uc.client.vo.VO;

@Repository("ecHeaderMasterBiz")
public class ECHeaderMasterBizImpl implements ECHeaderMasterBiz {

	@Resource(name = "baseClient")
	private BaseClient baseClient;
	@Resource
	private ECSignDao ecSignDao;
	@Resource
	private ECCourseDao ecCourseDao;
	@Resource
	private ECCourseBiz ecCourseBiz;

	@Override
	public List<ECStuInfo> findStuByHeaderMaster(String uid) {
		List<ECStuInfo> listVO = new ArrayList<ECStuInfo>();
		StringBuffer sb = new StringBuffer();
		String pid = baseClient.queryUidPid(1, uid);
		NbcUcClass c = this.getNbcClass(pid);
		List<VO> listV = this.getStuInfo(c.getId().trim());
		NbcUcStudent ns = new NbcUcStudent();
		for (VO v : listV) {
			ns = (NbcUcStudent) v;
			ECStuInfo stuVo = new ECStuInfo();
			NbcUcStudent stu = baseClient.queryStudent(1, ns.getPid());
			stuVo.setStuNo(stu.getStudentno());
			stuVo.setStuName(stu.getName());
			stuVo.setSex(baseClient.queryDiction(1, stu.getSex()).getName());
			List<NbcUcParent> listP = this.getParent(stu.getStudentno());
			if (!listP.isEmpty()) {
				for (NbcUcParent p : listP) {
					sb.append(p.getTelephone());
					sb.append(",");
				}
				stuVo.setParentsPhone(sb.substring(0, sb.length() - 1));
			}
			// 获取学生uid
			String stuUid = baseClient.queryUidPid(2, ns.getPid());// 得到学生的uid
			stuVo.setStuId(stuUid);
			// 获取报名信息根据学生uid
			List<ECSign> listSign = this.ecSignDao.findByStudentId(stuUid);
			List<ECCourseVO> mainList = new ArrayList<ECCourseVO>();
			sb.delete(0, sb.length());
			for (ECSign ec : listSign) {
				ECCourseVO ecCourse = new ECCourseVO();
				ecCourse.setId(ec.getCourseId());
				// 根据课程Id获取课程信息
				ECCourse ecObj = this.ecCourseDao.get(ec.getCourseId());
				ecCourse.setName(ecObj.getName());
				sb.append(ecObj.getName());
				sb.append(",");
				mainList.add(ecCourse);
				stuVo.setMainList(mainList);
			}
			if (sb.length() > 0) {
				stuVo.setMainName(sb.substring(0, sb.length() - 1));
			}
			sb.delete(0, sb.length());
			listVO.add(stuVo);
		}
		return listVO;
	}

	@Override
	public List<ECStuInfo> findToSearchStu(String uid, ECCourseVO cond) {
		List<ECStuInfo> listVO = new ArrayList<ECStuInfo>();
		StringBuffer sb = new StringBuffer();
		String pid = baseClient.queryUidPid(1, uid);// 根据当前用户的uid查询用户的pid
		NbcUcClass c = this.getNbcClass(pid);// 根据pid查询所带领的班级
		List<VO> listV = this.getStuInfo(c.getId().trim());// 获取当前班级内的学生信息
		NbcUcStudent ns = new NbcUcStudent();
		// 符合搜索条件的所有课程
		List<ECCourseVO> ecObjList = this.ecCourseBiz.findPage(cond, null);
		for (VO v : listV) {
			ns = (NbcUcStudent) v;
			// 获取学生uid
			String stuUid = baseClient.queryUidPid(2, ns.getPid());// 得到学生的uid
			// 获取报名信息根据学生uid(获取当前学生报名的课程)
			List<ECSign> listSign = this.ecSignDao.findByStudentId(stuUid);
			ECStuInfo stuVo = null;
			if (!ecObjList.isEmpty()) {
				stuVo = new ECStuInfo();
				List<ECCourseVO> mainSet = new ArrayList<ECCourseVO>();
				for (ECSign ec : listSign) {
					for (ECCourseVO course : ecObjList) {
						if (ec.getCourseId().equals(course.getId())) {
							stuVo.setStuId(stuUid);
							NbcUcStudent stu = baseClient.queryStudent(1, ns.getPid());
							stuVo.setStuNo(stu.getStudentno());
							stuVo.setStuName(stu.getName());
							stuVo.setSex(baseClient.queryDiction(1, stu.getSex()).getName());
							List<NbcUcParent> listP = this.getParent(stu.getStudentno());
							if (!listP.isEmpty()) {
								for (NbcUcParent p : listP) {
									sb.append(p.getTelephone());
									sb.append(",");
								}
								stuVo.setParentsPhone(sb.substring(0, sb.length() - 1));
							}
							sb.delete(0, sb.length());
							ECCourseVO ecCourse = new ECCourseVO();
							ecCourse.setId(ec.getCourseId());
							// 根据课程Id获取课程信息
							ECCourse ecObj = this.ecCourseDao.get(ec.getCourseId());
							ecCourse.setName(ecObj.getName());
							mainSet.add(ecCourse);
							stuVo.setMainList(mainSet);
						}
					}
				}
				if (stuVo != null && !stuVo.getMainList().isEmpty()) {
					listVO.add(stuVo);
				}
			}

		}
		return listVO;
	}

	@Override
	public List<ECCourseClass> findAllOurClass(String termId, String uid, String mainName) {
		StringBuffer sb = new StringBuffer();
		List<ECCourseClass> classList = new ArrayList<ECCourseClass>();
		String pid = baseClient.queryUidPid(1, uid);
		NbcUcClass c = this.getNbcClass(pid);
		List<VO> listV = this.getStuInfo(c.getId().trim());
		List<ECCourse> courseList = new ArrayList<ECCourse>();
		if (StringUtil.isEmpty(mainName)) {
			courseList = this.ecCourseDao.find(
					"from ECCourse e where 1=1 and e.termId = ?  and FIND_IN_SET( ? , e.gradeIds)!= 0",
					new Object[] {termId, c.getGradeNum()});
		} else {
			courseList = this.ecCourseDao
					.find(
							"from ECCourse e where 1=1 and e.termId = ?  and e.name like ?  and  FIND_IN_SET( ? , e.gradeIds)!= 0",
							new Object[] {termId, "%" + mainName + "%", c.getGradeNum()});
		}
		if (!courseList.isEmpty()) {
			for (ECCourse course : courseList) {
				NbcUcStudent ns = new NbcUcStudent();
				int count = 0;
				ECCourseClass cclass = new ECCourseClass();
				for (VO v : listV) {
					ns = (NbcUcStudent) v;
					// 获取学生uid
					String stuUid = baseClient.queryUidPid(2, ns.getPid());// 得到学生的uid
					if (this.ecSignDao.find("from ECSign e where 1=1 and e.courseId = ? and e.stuId = ?",
							new Object[] {course.getId(), stuUid}).size() != 0) {
						++count;
					}
				}
				if (count != 0) {
					cclass.setCoursrId(course.getId());
					cclass.setCourseName(course.getName());
					cclass.setTeacherName(course.getTeacherNames());
					cclass.setTypeId(course.getTypeId());
					cclass.setTypeName(DictionaryUtil.getTypeById(cclass.getTypeId().trim()).getName());
					cclass.setStartTime(course.getClassStartDate());
					cclass.setEndTime(course.getClassEndDate());
					List<ECClasshour> classhourList = course.getClasshourList();
					List<String> timeList = new ArrayList<String>();
					for (ECClasshour ecHourse : classhourList) {
						sb.append(WeekType.getById(ecHourse.getWeekIndex()).getName());
						sb.append("  ");
						sb.append(ecHourse.getWeekStartTime());
						sb.append("--");
						sb.append(ecHourse.getWeekEndTime());
						timeList.add(sb.toString());
						sb.delete(0, sb.length());
					}
					cclass.setClassTime(timeList);
					cclass.setLessonPlace(course.getStartPlace().getName());
					cclass.setTotolNum(course.getMaxStudentNum());
					cclass.setSelectedNum(count);
					classList.add(cclass);
				}

			}
		}
		return classList;
	}

	@Override
	public List<ECStuInfo> findOneMainStu(String uid, String courseId) {
		List<ECStuInfo> listVO = new ArrayList<ECStuInfo>();
		StringBuffer sb = new StringBuffer();
		String pid = baseClient.queryUidPid(1, uid);
		NbcUcClass c = this.getNbcClass(pid);
		List<VO> listV = this.getStuInfo(c.getId().trim());
		NbcUcStudent ns = new NbcUcStudent();
		for (VO v : listV) {
			ns = (NbcUcStudent) v;
			// 获取学生uid
			String stuUid = baseClient.queryUidPid(2, ns.getPid());// 得到学生的uid
			List<ECSign> listSign = this.ecSignDao.find(
					"from ECSign e where 1=1 and e.courseId = ? and e.stuId = ? ", new Object[] {courseId,
							stuUid});
			if (!listSign.isEmpty()) {
				ECStuInfo ecstu = new ECStuInfo();
				NbcUcStudent stu = baseClient.queryStudent(1, ns.getPid());
				ecstu.setStuNo(stu.getStudentno());
				ecstu.setStuId(stuUid);
				ecstu.setStuName(stu.getName());
				ecstu.setSex(baseClient.queryDiction(1, stu.getSex()).getName());
				List<NbcUcParent> listP = this.getParent(stu.getStudentno());
				if (!listP.isEmpty()) {
					for (NbcUcParent p : listP) {
						sb.append(p.getTelephone());
						sb.append(",");
					}
					ecstu.setParentsPhone(sb.substring(0, sb.length() - 1));
					sb.delete(0, sb.length());
				}
				listVO.add(ecstu);
			}
		}
		return listVO;
	}

	@SuppressWarnings("static-access")
	@Override
	public String exportByHeaderMaster(String termId, String uid) {
		String fileName = this.getHeadText(uid, "course");
		// 创建新的Excel 工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		ExcelCellStyleUtils es = new ExcelCellStyleUtils(workbook);
		// 在Excel工作簿中建一工作表
		HSSFSheet sheet = workbook.createSheet(fileName);
		int i = 0;
		int j = 0;
		// 课程名称
		HSSFRow row = sheet.createRow(i);
		sheet.addMergedRegion(new CellRangeAddress(0, (short) 0, 0, (short) 8));// 合并单元格
		row.createCell(0).setCellStyle(es.titleStyle); // 设置标题样式
		row.createCell(0).setCellValue(fileName);
		List<ECCourseClass> listClass = this.findAllOurClass(termId, uid, null);
		if (!listClass.isEmpty()) {
			for (ECCourseClass ec : listClass) {
				row = sheet.createRow(++i);
				row.createCell(0).setCellStyle(es.titleStyle); // 设置标题样式
				row.createCell(0).setCellValue(++j);
				row.createCell(1).setCellStyle(es.titleStyle); // 设置标题样式
				row.createCell(1).setCellValue("课程类别");
				row.createCell(2).setCellStyle(es.titleStyle); // 设置标题样式
				row.createCell(2).setCellValue("课程名称");
				row.createCell(3).setCellStyle(es.titleStyle); // 设置标题样式
				row.createCell(3).setCellValue("任课老师");
				row.createCell(4).setCellStyle(es.titleStyle); // 设置标题样式
				row.createCell(4).setCellValue("开结课日期");
				row.createCell(5).setCellStyle(es.titleStyle); // 设置标题样式
				row.createCell(5).setCellValue("上课时间");
				row.createCell(6).setCellStyle(es.titleStyle); // 设置标题样式
				row.createCell(6).setCellValue("上课地点");
				row.createCell(7).setCellStyle(es.titleStyle); // 设置标题样式
				row.createCell(7).setCellValue("限定人数");
				row.createCell(8).setCellStyle(es.titleStyle); // 设置标题样式
				row.createCell(8).setCellValue("本班报名人数");
				row = sheet.createRow(++i);
				row.createCell(1).setCellStyle(es.titleStyle); // 设置标题样式
				row.createCell(1).setCellValue(ec.getTypeName());
				row.createCell(2).setCellStyle(es.titleStyle); // 设置标题样式
				row.createCell(2).setCellValue(ec.getCourseName());
				row.createCell(3).setCellStyle(es.titleStyle); // 设置标题样式
				row.createCell(3).setCellValue(ec.getTeacherName());
				row.createCell(4).setCellStyle(es.titleStyle); // 设置标题样式
				row.createCell(4).setCellValue(ec.getStartTime() + "至" + ec.getEndTime());
				row.createCell(5).setCellStyle(es.titleStyle); // 设置标题样式
				String time = "";
				for (String str : ec.getClassTime()) {
					time += str + ",";
				}
				row.createCell(5).setCellValue(time.substring(0, time.length() - 1));
				row.createCell(6).setCellStyle(es.titleStyle); // 设置标题样式
				row.createCell(6).setCellValue(ec.getLessonPlace());
				row.createCell(7).setCellStyle(es.titleStyle); // 设置标题样式
				row.createCell(7).setCellValue(ec.getTotolNum());
				row.createCell(8).setCellStyle(es.titleStyle); // 设置标题样式
				row.createCell(8).setCellValue(ec.getSelectedNum());
				row = sheet.createRow(++i);
				row = sheet.createRow(++i);
				row.createCell(1).setCellStyle(es.titleStyle); // 设置标题样式
				row.createCell(1).setCellValue("学号");
				row.createCell(2).setCellStyle(es.titleStyle); // 设置标题样式
				row.createCell(2).setCellValue("姓名");
				row.createCell(3).setCellStyle(es.titleStyle); // 设置标题样式
				row.createCell(3).setCellValue("家长电话");
				for (int k = 0; k < ec.getSelectedNum(); k++) {
					List<ECStuInfo> listVO = findOneMainStu(uid, ec.getCoursrId());
					if (!listVO.isEmpty()) {
						for (ECStuInfo stu : listVO) {
							row = sheet.createRow(++i);
							row.createCell(1).setCellStyle(es.titleStyle);
							row.createCell(1).setCellValue(stu.getStuNo());
							row.createCell(2).setCellStyle(es.titleStyle);
							row.createCell(2).setCellValue(stu.getStuName());
							row.createCell(3).setCellStyle(es.titleStyle);
							row.createCell(3).setCellValue(stu.getParentsPhone());
						}
					}
				}
				row = sheet.createRow(++i);
			}
		} else {
			row = sheet.createRow(++i);
			sheet.addMergedRegion(new CellRangeAddress(i, (short) i, 0, (short) 8));// 合并单元格
			row.createCell(0).setCellStyle(es.titleStyle); // 设置标题样式
			row.createCell(0).setCellValue("暂时无数据");
		}
		File file = new File(ServletActionContext.getServletContext().getRealPath("/") + "excel/" + fileName
				+ ".xls");
		// 新建一输出文件流
		OutputStream os;
		try {
			os = new FileOutputStream(file);
			workbook.write(os);
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileName;
	}

	@Override
	public String getHeadText(String uid, String flag) {
		String name = "";
		String pid = baseClient.queryUidPid(1, uid);
		NbcUcClass c = this.getNbcClass(pid);
		if ("stu".equals(flag)) {
			name = c.getGradeNum() + "年" + c.getClassNum() + "班" + "学生选课信息";
		}
		if ("course".equals(flag)) {
			name = c.getGradeNum() + "年" + c.getClassNum() + "班" + "选课信息";
		}
		return name;
	}

	/**
	 * 获取班级
	 * 
	 * @param pid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public NbcUcClass getNbcClass(String pid) {
		NbcUcClass nc = new NbcUcClass();
		Map map = new HashMap();
		map.put("teacherId", pid);
		map.put("typeCode", Constants.PERSON_TYPE_CLASS_MASTER);
		nc = baseClient.queryClass(2, map);
		return nc;
	}

	/**
	 * 获取学生信息
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<VO> getStuInfo(String id) {
		Map map = new HashMap();
		map.put("classId", id);
		List<VO> list = baseClient.queryStudentList(2, 1, map);
		return list;
	}

	/**
	 * 获取家长信息
	 * 
	 * @param num
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<NbcUcParent> getParent(String num) {
		Map map = new HashMap();
		map.put("studentNo", num);
		List<NbcUcParent> list = baseClient.queryParentList(2, 2, map);
		return list;
	}
}
