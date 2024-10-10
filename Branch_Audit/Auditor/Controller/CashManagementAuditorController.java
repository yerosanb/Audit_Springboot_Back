
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

import com.afr.fms.Branch_Audit.Auditor.Service.CashManagementAuditorService;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Common.RecentActivity.RecentActivity;
import com.afr.fms.Common.RecentActivity.RecentActivityMapper;
import com.afr.fms.Common.Service.FunctionalitiesService;
import com.afr.fms.Payload.endpoint.Endpoint;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/branch_audit/auditor/cash_management")
@PreAuthorize("hasRole('AUDITOR_BFA')")
public class CashManagementAuditorController {

	@Autowired
	private CashManagementAuditorService auditService;
	@Autowired
	private RecentActivityMapper recentActivityMapper;

	@Autowired
	private FunctionalitiesService functionalitiesService;

	RecentActivity recentActivity = new RecentActivity();

	@GetMapping("/passed/{auditor_id}")
	public ResponseEntity<List<BranchFinancialAudit>> getBAFindingsForAuditor(@PathVariable Long auditor_id,
			HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request,
		"get_passed_cash_management_auditor_bfa")) {
		try {
			return new ResponseEntity<>(auditService.getAuditsForAuditor(auditor_id), HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/drafted/{auditor_id}")
	public ResponseEntity<List<BranchFinancialAudit>> getAuditsOnDrafting(@PathVariable Long auditor_id,
			HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request,
		"get_drafted_cash_management_auditor_bfa")) {
		try {
			return new ResponseEntity<>(auditService.getAuditsOnDrafting(auditor_id), HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("/create")
	public ResponseEntity<HttpStatus> createBAFinding(@RequestBody BranchFinancialAudit audit,
			HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request,
		"create_or_update_cash_management")) {
		try {
			if (audit.getId() != null) {
				auditService.updateBAFinding(audit);
			} else {
				auditService.createISMFinding(audit);
			}
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

	}

}
