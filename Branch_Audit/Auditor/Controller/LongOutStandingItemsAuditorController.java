package com.afr.fms.Branch_Audit.Auditor.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afr.fms.Branch_Audit.Auditor.Service.LongOutStandingItemsAuditorService;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Common.Service.FunctionalitiesService;
import com.afr.fms.Payload.endpoint.Endpoint;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/branch_audit/auditor/long_outstanding_items/")
@PreAuthorize("hasRole('AUDITOR_BFA')")
public class LongOutStandingItemsAuditorController {
    @Autowired
    private LongOutStandingItemsAuditorService service;

	@Autowired
	private FunctionalitiesService functionalitiesService;

    @PostMapping("create")
	public ResponseEntity<HttpStatus> saveLongOutstandingItems(@RequestBody BranchFinancialAudit audit,
			HttpServletRequest request) {
				if (functionalitiesService.verifyPermission(request,
		"create_or_update_long_outstanding_items_auditor_bfa")) {
		try {
			if (audit.getId() != null) {
				service.updateLongOutstandingItems(audit);
			} else {
				service.saveLongOutstandingItems(audit);
			}
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("pending/{id}")
	public ResponseEntity<List<BranchFinancialAudit>> getPendingOutstandingItems(@PathVariable Long id,HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request,
		"get_drafted_long_outstanding_items_auditor_bfa")) {
		try {
			return new ResponseEntity<>(service.getPendingOutstandingItems(id), HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("passed/{auditor_id}")
	public ResponseEntity<List<BranchFinancialAudit>> getPassedLongOutstandingItems(@PathVariable Long auditor_id,
			HttpServletRequest request) {
				if (functionalitiesService.verifyPermission(request,
		"get_passed_long_outstanding_items_auditor_bfa")) {
		try {
			return new ResponseEntity<>(service.getPassedLongOutstandingItems(auditor_id), HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
	
}
