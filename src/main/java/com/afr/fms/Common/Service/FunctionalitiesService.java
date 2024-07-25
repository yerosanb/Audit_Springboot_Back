package com.afr.fms.Common.Service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Admin.Entity.Role;
import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Admin.Mapper.RoleMapper;
import com.afr.fms.Admin.Mapper.UserMapper;
import com.afr.fms.Common.Entity.Functionalities;
import com.afr.fms.Common.Mapper.FunctionalitiesMapper;
import com.afr.fms.Security.jwt.JwtUtils;

@Service
public class FunctionalitiesService {

	@Autowired
	private RoleMapper userRoleMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private FunctionalitiesMapper functionalitiesMapper;

	@Autowired
	JwtUtils jwtUtils;

	public boolean verifyPermission(HttpServletRequest request, String functionality_name) {
		String jwt = jwtUtils.getJwtFromCookies(request);
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			String username = jwtUtils.getUserNameFromJwtToken(jwt);
			return processVerfyingPermission(username, functionality_name);
		} else {
			return false;
		}
	}

	public User getUserFromHttpRequest(HttpServletRequest request) {
		String jwt = jwtUtils.getJwtFromCookies(request);
		User user = new User();
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			String username = jwtUtils.getUserNameFromJwtToken(jwt);
			user = userMapper.findByEmail(username);
			return user;
		}
		return null;
	}

	public boolean processVerfyingPermission(String username, String functionality_name) {
		for (Role role : userRoleMapper.getRolesByUsername(username)) {
			for (Functionalities functionalities : role.getFunctionalities()) {
				if (functionalities.getName().equals(functionality_name)) {
					return true;
				}
			}
		}
		return false;
	}

	public List<Functionalities> getAllRoleFunctionalities() {

		return functionalitiesMapper.getAllFunctionalities();
	}

	public List<Functionalities> getAllFunctionalitiesByRole(Long id) {

		return functionalitiesMapper.getAllFunctionalitiesByRole(id);
	}

	public Boolean updateRoleFunctionalityStatus(String role_id, String functionality_status_string) {
		try {
			JSONObject functionality_status = new JSONObject(functionality_status_string);
			for (int i = 0; i < functionality_status.names().length(); i++) {

				functionalitiesMapper.updateRoleFunctionalitiesById(Long.parseLong(role_id),
						Long.parseLong(functionality_status.names().getString(i)),
						((Boolean) functionality_status.get(functionality_status.names().getString(i)) == true)
								? 1
								: 0);

			}
			return true;
		} catch (Exception e) {
			System.out.println(e);

			return false;
		}
	}

	public Boolean updateFunctionalityStatus(String functionality_status_string) {
		try {
			JSONObject functionality_status = new JSONObject(functionality_status_string);
			for (int i = 0; i < functionality_status.names().length(); i++) {

				functionalitiesMapper.updateFunctionalitiesById(
						Long.parseLong(functionality_status.names().getString(i)),
						((Boolean) functionality_status.get(functionality_status.names().getString(i)) == true)
								? 1
								: 0);

			}
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

}
