package com.nbcedu.function.elec.biz.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nbcedu.core.page.Page;
import com.nbcedu.function.elec.biz.ECPlaceBiz;
import com.nbcedu.function.elec.dao.ECCourseDao;
import com.nbcedu.function.elec.dao.ECPlaceDao;
import com.nbcedu.function.elec.devcore.biz.ElecBaseBizImpl;
import com.nbcedu.function.elec.devcore.util.StringUtil;
import com.nbcedu.function.elec.model.ECPlace;
import com.nbcedu.function.elec.util.Constants;
import com.nbcedu.function.elec.util.DictionaryUtil;

@Service
public class ECPlaceBizImpl extends ElecBaseBizImpl<ECPlace> implements ECPlaceBiz  {
	
	@Resource
	private ECPlaceDao ecPlaceDao;
	
	@Resource
	private ECCourseDao ecCourseDao;

	@Override
	public void addPlace(ECPlace ecPlace) {
		ecPlace.setDelState(Constants.DEL_STATE_ENABLED);
		ecPlace.setCreateTime(new Date());
		ecPlace.setName(StringUtil.toHtml(ecPlace.getName().trim()).trim());
		this.ecPlaceDao.addPlace(ecPlace);
	}

	/**
	 * 判断地点名是否重复
	 * @param name
	 * @param flag  ("add":添加地点   ； "edit":编辑地点)
	 * @return
	 */
	@Override
	public boolean checkPlaceName(ECPlace ep , String flag) {
		boolean b = false;
		ep.setName(StringUtil.toHtml(ep.getName()));
		List<ECPlace> list = this.ecPlaceDao.findByName(ep.getName().trim());
		if(list!=null&&list.size()>0){
			if("add".equals(flag)){//"add":添加地点的判断
				b=true;
			}else if("edit".equals(flag)){// "edit":编辑地点的判断（排除自身）
				for(ECPlace ecp : list){
					if(!ep.getId().equals(ecp.getId())){
						b=true;
						break;
					}
				}
			}
		}
		return  b;
	}

	@Override
	public List<ECPlace> findAllPlace(Page page) {
		List<ECPlace> list = this.ecPlaceDao.findAllPlace( page );
		try{
		for(int i=0;i<list.size();i++){
			StringBuffer sb = new StringBuffer();
			if(list.get(i).getTypeName()!=null){
				String []temp1  = list.get(i).getTypeName().split(",");
				for(int j=0;j<temp1.length;j++){
					sb.append(DictionaryUtil.getTypeById(temp1[j].trim()).getName());
					sb.append(",");
				}
			}
			list.get(i).setName(list.get(i).getName());
			list.get(i).setTypeName(sb.substring(0, sb.length()-1));
		}
		}catch(Exception e){
			e.getStackTrace();
		}
		return list;
	}

	@Override
	public List<ECPlace> findAllPlace(String typeId) {
		List<ECPlace> list = this.ecPlaceDao.findPlace(typeId);
		if(list.size()>0&&list!=null){
			for(int i=0;i<list.size();i++){
				list.get(i).setName(list.get(i).getName());
			}
			return list;
		}
		return  null;
	}
	
	
	
	@Override
	public ECPlace updatePlace(ECPlace ecPlace) {
		ECPlace ep = this.ecPlaceDao.findPlace(ecPlace);
		ep.setId(ecPlace.getId());
		ep.setName(StringUtil.toHtml(ecPlace.getName().trim()).trim());
		ep.setCreateTime(new Date());
		ep.setTypeName(ecPlace.getTypeName());
		this.ecPlaceDao.updatePlace(ep);
		return null;
	}

	@Override
	public ECPlace findPlace(ECPlace ecPlace) {
		ECPlace ep = this.ecPlaceDao.findPlace(ecPlace);
		ep.setName(ep.getName());
		return ep;
	}

	@Override
	public boolean modifyPlace(String id, String flag) {
		boolean b = false;
		if(Constants.OPEN.equals(flag)){
			b = this.ecPlaceDao.modifyPlace(id , Constants.DEL_STATE_ENABLED);
		}else if(Constants.CLOSE.equals(flag)){
			b = this.ecCourseDao.checkPlaceUsed(id);
			if(b){//b==true  表示地点未被占用
				//改变地点状态
				this.ecPlaceDao.modifyPlace(id , Constants.DEL_STATE_REMOVED);
			}
		}
		return b;
	}
}
