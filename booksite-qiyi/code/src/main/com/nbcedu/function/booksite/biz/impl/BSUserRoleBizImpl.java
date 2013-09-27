package com.nbcedu.function.booksite.biz.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.nbcedu.function.booksite.biz.BSUserRoleBiz;
import com.nbcedu.function.booksite.dao.BSUserRoleDao;
import com.nbcedu.function.booksite.model.BSUserRole;
import com.nbcedu.function.booksite.util.BookSiteUtil;
import com.nbcedu.function.booksite.website.biz.impl.WsBaseBizImpl;
import com.nbcedu.function.booksite.website.pager.PagerModel;
import com.nbcedu.function.booksite.website.uitl.PinYinUtil;
import com.nbcedu.integration.uc.client.facade.BaseClient;
import com.nbcedu.integration.uc.client.vo.NbcUcTreeNode;

public class BSUserRoleBizImpl extends WsBaseBizImpl<BSUserRole> implements BSUserRoleBiz{

	private BSUserRoleDao userRoleDao;

	public void setUserRoleDao(BSUserRoleDao userRoleDao) {
		super.setDao(userRoleDao);
		this.userRoleDao = userRoleDao;
	}

	@Override
	public PagerModel list() {
		return userRoleDao.searchPaginated("from BSUserRole where deleteFlag='1' order by roleArray,sort ");
	}
	
	@Override
	public PagerModel search(String userName) {
		Criteria criteria = userRoleDao.createCriteria(Restrictions.eq("deleteFlag", "1"),Restrictions.like("userName", "%"+userName.trim()+"%"));
		criteria.addOrder(Order.desc("roleArray"));
		criteria.addOrder(Order.desc("sort"));
		return userRoleDao.searchPaginated(criteria);
	}

	@Override
	public String getPersonJsonString() {
		class CloumnTree {
			BaseClient client = new BaseClient();
			StringBuffer jsonString = new StringBuffer();
			public String getPersonTreeXmlString() {
				jsonString.append("[");
				List<NbcUcTreeNode> list = getTreeNode("root");
				for (NbcUcTreeNode faultPlace : list) {
					disk_NbcUcTreeNode(faultPlace);
				}
				jsonString.append("]");
				return jsonString.toString();
			}

			private void disk_NbcUcTreeNode(NbcUcTreeNode nbcUcTreeNode) {
				List<NbcUcTreeNode> list = getTreeNode(nbcUcTreeNode.getId());
				jsonString.append("{");
				jsonString.append("\"id\":");
				jsonString.append("\"");
				jsonString.append(nbcUcTreeNode.getId());
				jsonString.append("\"");
				jsonString.append(",\"text\":");
				jsonString.append("\"");
				jsonString.append(nbcUcTreeNode.getTitle());
				jsonString.append("\"");
				if(list!=null && list.size()>0){
					jsonString.append(",\"children\":[");
					for (NbcUcTreeNode tNode : list) {
						disk_NbcUcTreeNode(tNode);
					}
					jsonString.append("]");
				}
				jsonString.append("},");
			}

			private List<NbcUcTreeNode> getTreeNode(String pid) {
				return client.queryDepartTree(pid, 3);
			}
		}
		return new CloumnTree().getPersonTreeXmlString();
	}

	@Override
	public void add(String userIdArray, String roleArray) {
		add(userIdArray, roleArray, null);
	}

	
	public void add(String userIdArray, String roleArray,String type) {
		if(userIdArray!=null && !"".equals(userIdArray)){
			String[] userIdList = userIdArray.split(",");
			List<BSUserRole> userRoleList = new ArrayList<BSUserRole>();
			for(String userid : userIdList){
				if(userid!=null && !"".equals(userid.trim())){
					userid = userid.replaceAll("u\\|", "");
					Map<String,String> userInfo = BookSiteUtil.findUserInfo(userid.trim());
					if(userInfo!=null && userInfo.get("UID")!=null && !"".equals(userInfo.get("UID"))){
						if(userInfo.get("USERNAME")!=null && !"".equals(userInfo.get("USERNAME").trim())){
							BSUserRole subUserRole = new BSUserRole();
							subUserRole.setUserId(userInfo.get("UID"));//userId
							subUserRole.setUserName(userInfo.get("USERNAME"));//USERNAME
							subUserRole.setTel(userInfo.get("TEL"));//TEL
							subUserRole.setEmail(userInfo.get("EMAIL"));//EMAIL
							subUserRole.setSort(PinYinUtil.getFirst(userInfo.get("USERNAME")));
							subUserRole.setRoleArray(roleArray);
							subUserRole.setType(type);
							userRoleList.add(subUserRole);
						}
					}
				}
			}
			addORmodify(userRoleList);
		}
	}
	
	
	public void addORmodify(List<BSUserRole> userRoleList) {
		for (BSUserRole userRole : userRoleList) {
			BSUserRole sur = findUserRoleByUserId(userRole.getUserId());
			if(sur!=null){
				if(sur.getDeleteFlag()!=null && "1".equals(sur.getDeleteFlag())){
					sur.setRoleArray(userRole.getRoleArray());
					sur.setUserName(userRole.getUserName());
					sur.setEmail(userRole.getEmail());
					sur.setTel(userRole.getTel());
					sur.setDeleteFlag("1");
					userRoleDao.merge(sur);
				}
			}else{
				userRoleDao.save(userRole);
			}
		}
	}
	
	public BSUserRole findUserRoleByUserId(String userId) {
		return	(BSUserRole) this.userRoleDao.createQuery("from BSUserRole where deleteFlag='1' and userId=?",userId).uniqueResult();
	}

	@Override
	public void remove(String id) {
		BSUserRole userRole = findById(id);
		if(userRole!=null){
			userRole.setDeleteFlag("0");
			update(userRole);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BSUserRole> findAllAdmins() {
		return	(List<BSUserRole>) this.userRoleDao.createQuery("from BSUserRole where deleteFlag='1' ").list();
	}

}
