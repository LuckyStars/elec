package com.nbcedu.function.elec.util;
/**
 * 定时器任务操作
 * @author libin
 */

import java.util.TimerTask;
import javax.annotation.Resource;
import com.nbcedu.function.elec.biz.ECSignBiz;

public class ECSignTimerDevice extends TimerTask{
	@Resource(name="elecSignBiz")
	private ECSignBiz ecSignBiz;
	private Long signCreateTime;
	private Boolean onOff;
	
	@Override   
    public void run() {  
		if (onOff&&Constants.SIGN_TYPE.equals(Constants.SIGN_TYPE_SHIJIA)) {//判断报名规则和开启状态
			ecSignBiz.removeECSignTimer(signCreateTime);
		}else {
			this.cancel();//结束定时器
		}
		
     }

	public Long getSignCreateTime() {
		return signCreateTime;
	}

	public void setSignCreateTime(Long signCreateTime) {
		this.signCreateTime = signCreateTime;
	}

	public Boolean getOnOff() {
		return onOff;
	}

	public void setOnOff(Boolean onOff) {
		this.onOff = onOff;
	}
}
