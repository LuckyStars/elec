package com.nbcedu.function.elec.vo;

import java.util.List;

import com.nbcedu.function.elec.model.ECClasshour;
import com.nbcedu.function.elec.model.ECSign;



public class ECSignCourseVo {
	private ECSign ecSign;
	private ECCourseVO ecCourseVO;
	private boolean signStarState;//此课程是否开始报名 开始为true 
	private boolean signUpState;//此课程此学生是否报名 报名为true
	private boolean classDateState;//此课程此学生是否报抢时间 抢了为true
	private List<ECClasshour> ecClasshours;
	
	public boolean isClassDateState() {
		return classDateState;
	}

	public void setClassDateState(boolean classDateState) {
		this.classDateState = classDateState;
	}

	public ECCourseVO getEcCourseVO() {
		return ecCourseVO;
	}

	public void setEcCourseVO(ECCourseVO ecCourseVO) {
		this.ecCourseVO = ecCourseVO;
	}

	public boolean isSignStarState() {
		return signStarState;
	}

	public void setSignStarState(boolean signStarState) {
		this.signStarState = signStarState;
	}

	public boolean isSignUpState() {
		return signUpState;
	}

	public void setSignUpState(boolean signUpState) {
		this.signUpState = signUpState;
	}

	public ECSign getEcSign() {
		return ecSign;
	}

	public void setEcSign(ECSign ecSign) {
		this.ecSign = ecSign;
	}

	public List<ECClasshour> getEcClasshours() {
		return ecClasshours;
	}

	public void setEcClasshours(List<ECClasshour> ecClasshours) {
		this.ecClasshours = ecClasshours;
	}
	
}
