package com.afr.fms.Admin.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afr.fms.Admin.Entity.Role;
import com.afr.fms.Admin.Service.RoleService;
import com.afr.fms.Common.Service.FunctionalitiesService;
import com.afr.fms.Payload.endpoint.Endpoint;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")

@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('ADMIN')")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private FunctionalitiesService functionalitiesService;

	@PostMapping("/role")
	public ResponseEntity<?> createRole(@RequestBody @Validated Role role, HttpServletRequest request) {

		if (functionalitiesService.verifyPermission(request, "create_role")) {
			try {
				roleService.createRole(role);
				return new ResponseEntity<>(HttpStatus.CREATED);
			} catch (Exception e) {
				System.out.println(e);
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/role")
	public ResponseEntity<List<Role>> getRoles(HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "get_roles")) {
			try {
				return new ResponseEntity<>(roleService.getRoles(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/role/{id}")
	public ResponseEntity<Role> getRoleById(@PathVariable("id") Long id, HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "get_role")) {
			try {
				return new ResponseEntity<>(roleService.getRoleById(id), HttpStatus.OK);
			} catch (Exception e) {
				System.out.println(e);
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("role/delete")
	public ResponseEntity<HttpStatus> deleteRole(@RequestBody List<Role> roles, HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "deactivate_role")) {
		try {
			for (Role role : roles) {
				roleService.deactivate_role(role.getId());
			}
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception exception) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("role/activate")
	public ResponseEntity<HttpStatus> activateRole(@RequestBody List<Role> roles, HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "activate_role")) {
		try {
			for (Role role : roles) {
				roleService.activate_role(role.getId());
			}
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception exception) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/role/activate/{id}")
	public ResponseEntity<Boolean> activate_role(@PathVariable Long id, HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "activate_role")) {
			try {
				roleService.activate_role(id);
				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			} catch (Exception exception) {
				System.out.println(exception);
				return new ResponseEntity<Boolean>(false, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		}
	}

	@GetMapping("role/deactivate/{id}")
	public ResponseEntity<Boolean> deactivate_role(@PathVariable Long id, HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "deactivate_role")) {

			try {
				roleService.deactivate_role(id);
				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			} catch (Exception exception) {
				System.out.println(exception);
				return new ResponseEntity<Boolean>(false, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		}
	}

}
