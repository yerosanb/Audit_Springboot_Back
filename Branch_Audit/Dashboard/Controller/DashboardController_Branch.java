package com.afr.fms.Branch_Audit.Dashboard.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afr.fms.Branch_Audit.Dashboard.Model.DashboardBranchRequest;
import com.afr.fms.Branch_Audit.Dashboard.Service.DashboardService_Branch;
import com.afr.fms.Payload.endpoint.Endpoint;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/branch/dashboard/")
public class DashboardController_Branch {

	@Autowired
	DashboardService_Branch dashboardService;

	@PostMapping("fetchDashboard")
	public ResponseEntity<?> fetchDashboard(@RequestBody() DashboardBranchRequest dashboard_request) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(dashboardService.fetchDashboard(dashboard_request));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("fetchDashboardOne")
	public ResponseEntity<?> fetchDashboardDataOne(@RequestBody() DashboardBranchRequest dashboard_request) {

		try {
			return ResponseEntity.status(HttpStatus.OK).body(dashboardService.fetchDashboardDataOne(dashboard_request));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("fetchDashboardTwo")
	public ResponseEntity<?> fetchDashboardTwo(@RequestBody() DashboardBranchRequest dashboard_request) {

		try {
			return ResponseEntity.status(HttpStatus.OK).body(dashboardService.fetchDashboardDataTwo(dashboard_request));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("fetchDashboardThree")
	public ResponseEntity<?> fetchDashboardThree(@RequestBody() DashboardBranchRequest dashboard_request) {

		try {
			return ResponseEntity.status(HttpStatus.OK).body(dashboardService.fetchDashboardDataThree(dashboard_request));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping("fetchDashboardFour")
	public ResponseEntity<?> fetchDashboardFour(@RequestBody() DashboardBranchRequest dashboard_request) {

		try {
			return ResponseEntity.status(HttpStatus.OK).body(dashboardService.fetchDashboardDataFour(dashboard_request));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping("fetchDashboardFive")
	public ResponseEntity<?> fetchDashboardFive(@RequestBody() DashboardBranchRequest dashboard_request) {

		try {
			return ResponseEntity.status(HttpStatus.OK).body(dashboardService.fetchDashboardDataFive(dashboard_request));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}