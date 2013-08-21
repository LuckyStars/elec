
package com.nbcedu.function.elec.biz.impl;
import java.util.List;

import javax.annotation.Resource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;

import com.nbcedu.function.elec.biz.ECUserPrivilegeTreeBiz;
import com.nbcedu.integration.uc.client.facade.BaseClient;
import com.nbcedu.integration.uc.client.vo.NbcUcTreeNode;

/** 
 * <p>TreeBiz实现类</p>
 * 
 * @author libin
 */
@Repository("ecUserPrivilegeTreeBiz")
public class ECUserPrivilegeTreeBizImpl implements ECUserPrivilegeTreeBiz {
	
	/**
	 * UC客户端查询接口对象
	 */
	@Resource(name="baseClient")
	private BaseClient baseClient;
	@SuppressWarnings("unchecked")
	@Override
	public JSONArray findNodes() {
		JSONArray nodeJsonArray = new JSONArray();
		List<NbcUcTreeNode> nodeList = baseClient.queryDepartTree("root", 3);
		JSONObject nodeJsonObjs = new JSONObject();
		nodeJsonObjs.put("id", "ne|00");
		nodeJsonObjs.put("text", "所有教师");
		JSONArray ny = new JSONArray();
		for (NbcUcTreeNode node : nodeList) {
			JSONObject nodeJsonObj = new JSONObject();
			nodeJsonObj.put("id", node.getId());
			nodeJsonObj.put("text", node.getTitle());
			List<NbcUcTreeNode> subNodeList = baseClient.queryDepartTree(node.getId(), 3);
			if (subNodeList.size()>0) {//如果含有子节点
				nodeJsonObj.put("state", "closed");
			}
			nodeJsonObj.put("children", buildSubNodes(node));
			ny.add(nodeJsonObj);
		}
		nodeJsonObjs.put("children", ny);
		nodeJsonArray.add(nodeJsonObjs);
		return nodeJsonArray;
	}
	/** 
	 * 递归构造树节点
	 * @param node 当前添加的树节点
	 * @return 符合JSON格式的列表
	 */ 
	@SuppressWarnings("unchecked")
	private JSONArray buildSubNodes(NbcUcTreeNode node) {
		
		JSONArray subNodeJsonArray = new JSONArray();
		List<NbcUcTreeNode> subNodeList = baseClient.queryDepartTree(node.getId(), 3);
		for (NbcUcTreeNode subNode : subNodeList) {
			JSONObject subNodeJsonObj = new JSONObject();
			int sindex=subNode.getId().indexOf("|");//id是否有|标示
			if (subNode.isLeaf()&&sindex>=0) {//如果节点是人进行id去字母标示
					subNodeJsonObj.put("id", subNode.getId().substring(sindex + 1));
			}else {
				subNodeJsonObj.put("id",subNode.getId());
			}
			subNodeJsonObj.put("text", subNode.getTitle());
			JSONArray jyArray=buildSubNodes(subNode);//查询字节点
			subNodeJsonObj.put("children", jyArray);
			if (jyArray.size()>0) {//如果含有子节点
				subNodeJsonObj.put("state", "closed");
			}
			subNodeJsonArray.add(subNodeJsonObj);
		}
		return subNodeJsonArray;
	}
	
	
}
