package com.afr.fms.Common.RecentActivity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afr.fms.Common.Service.FunctionalitiesService;
import com.afr.fms.Payload.endpoint.Endpoint;
import com.afr.fms.Common.Entity.Report;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api")
@PreAuthorize("hasAnyRole('MAKER','APPROVER','ADMIN','HO')")
public class RecentActivityController {

	@Autowired
	private RecentActivityService recentActivityService;

	@Autowired
	private FunctionalitiesService functionalitiesService;

	@PostMapping("/addRecentActivity")
	public ResponseEntity<HttpStatus> addRecentActivity(@RequestBody RecentActivity ra, HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "add_recent_activity")) {

			try {
				recentActivityService.addRecentActivity(ra);
				return new ResponseEntity<>(HttpStatus.OK);
			} catch (Exception ex) {
				System.out.println(ex);
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/getRecentActivity/{id}")
	public ResponseEntity<List<RecentActivity>> getAllLoans(@PathVariable Long id, HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "view_recent_activity")) {

			try {
				return new ResponseEntity<>(recentActivityService.getRecentActivityByUserId(id), HttpStatus.OK);
			} catch (Exception ex) {
				System.out.println(ex);
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		}
	}

	@PostMapping("/getRecentActivityAdmin")
	public ResponseEntity<List<RecentActivity>> getRecentActivityAdmin(@RequestBody Report report,
			HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "view_recent_activity_admin")) {
			try {
				return new ResponseEntity<>(recentActivityService.getRecentActivityAdmin(report), HttpStatus.OK);
			} catch (Exception ex) {
				System.out.println(ex);
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		}
	}

	@PostMapping("/getAllRecentActivityByUserId")
	public ResponseEntity<List<RecentActivity>> getAllRecentActivityByUserId(@RequestBody Report report,
			HttpServletRequest request) {
				System.out.println("recentv 1");
		if (functionalitiesService.verifyPermission(request, "get_recent_activity_by_user")) {
				System.out.println("recentv 2");

			try {
				return new ResponseEntity<>(recentActivityService.getAllRecentActivityByUserId(report), HttpStatus.OK);
			} catch (Exception ex) {
				System.out.println(ex);
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		}
	}

}
