package com.nbcedu.function.elec.vo;

import com.nbcedu.function.elec.model.ECUserPrivilege;

public class ECUserPrivilegeVo {
	private ECUserPrivilege ecUserPrivilege;
	private String[] names;
	public ECUserPrivilege getEcUserPrivilege() {
		return ecUserPrivilege;
	}
	public String[] getNames() {
		return names;
	}
	public void setEcUserPrivilege(ECUserPrivilege ecUserPrivilege) {
		this.ecUserPrivilege = ecUserPrivilege;
	}
	public void setNames(String[] names) {
		this.names = names;
	}
}
