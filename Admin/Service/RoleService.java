package com.afr.fms.Admin.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.afr.fms.Admin.Entity.Role;
import com.afr.fms.Admin.Mapper.RoleMapper;

@Service
public class RoleService {

	@Autowired
	private RoleMapper roleMapper;

	public List<Role> getRoles() {

		return roleMapper.getRoles();
	}

	public void activate_role(Long id) {
		roleMapper.activate_role(id);
	}

	public void deactivate_role(Long id) {
		roleMapper.deactivate_role(id);
	}

	public Role getRoleById(Long id) {
		return roleMapper.getRoleById(id);
	}

	public Role getRoleByCode(String code) {
		return roleMapper.getRoleByCode(code);
	}

	public void createRole(Role role) {
		if (role.getId() == null) {
			roleMapper.createRole(role);
		} else {
			roleMapper.updateRole(role);
		}
	}

}
