package com.afr.fms.Admin.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afr.fms.Admin.Entity.JobPosition;
import com.afr.fms.Admin.Entity.JobPositionRole;
import com.afr.fms.Admin.Entity.Role;
import com.afr.fms.Admin.Service.JobPositionService;
import com.afr.fms.Common.Service.FunctionalitiesService;
import com.afr.fms.Payload.endpoint.Endpoint;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")

@RestController
@RequestMapping("/api")
// @PreAuthorize("hasRole('ADMIN')")
public class JobPositionController {

	@Autowired
	private JobPositionService jobPositionService;

	@Autowired
	private FunctionalitiesService functionalitiesService;

	@GetMapping("/job_position/byRole")
	public ResponseEntity<JobPositionRole> getJobPositionsByRole(HttpServletRequest request, @RequestBody Role role) {
		if (functionalitiesService.verifyPermission(request,
				"fetching_job_positions_by_role")) {
			try {
				JobPositionRole jobPositionsByRole = jobPositionService.getJobPositionsByRole(role);
				return new ResponseEntity<>(jobPositionsByRole, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("/jobPosition/manageJobPositions")
	public ResponseEntity<?> manageJobPositions(HttpServletRequest request,
			@RequestBody JobPositionRole jobPositionRole) {
		if (functionalitiesService.verifyPermission(request,
				"job_position_management")) {
			try {
				jobPositionService.manageJobPositions(jobPositionRole);
				return new ResponseEntity<>(HttpStatus.CREATED);
			} catch (Exception e) {
				System.out.println(e);
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/selected_job_position")
	public ResponseEntity<List<JobPosition>> getRoleJobPositions(HttpServletRequest request) {
		try {
			return new ResponseEntity<>(jobPositionService.getJobPositions(), HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/jobPositions")
	public ResponseEntity<List<JobPosition>> getMappedJobPositions(HttpServletRequest request) {
		try {
			return new ResponseEntity<>(jobPositionService.getMappedJobPositions(), HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}	
	
	@GetMapping("/total_job_positions")
	public ResponseEntity<List<JobPosition>> getTotalJobPositions(HttpServletRequest request) {
		try {
			return new ResponseEntity<>(jobPositionService.getTotalJobPositions(), HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/job_positions_admin")
	public ResponseEntity<List<JobPosition>> getJobPositions(HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request,
				"job_positions_not_mapped_role")) {
			try {
				return new ResponseEntity<>(jobPositionService.getAllJobPositions(), HttpStatus.OK);
			} catch (Exception e) {
				System.out.println(e);
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

}
