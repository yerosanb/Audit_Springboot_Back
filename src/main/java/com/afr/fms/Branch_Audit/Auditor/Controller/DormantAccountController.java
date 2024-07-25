
package com.afr.fms.Branch_Audit.Auditor.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afr.fms.Branch_Audit.Auditor.Service.DormantAccountService;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Common.RecentActivity.RecentActivity;
import com.afr.fms.Common.RecentActivity.RecentActivityMapper;
import com.afr.fms.Common.Service.FunctionalitiesService;
import com.afr.fms.Payload.endpoint.Endpoint;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/branch_audit/auditor/dormant_account")
@PreAuthorize("hasRole('AUDITOR_BFA')")
public class DormantAccountController {
	@Autowired
	private DormantAccountService auditService;
	@Autowired
	private RecentActivityMapper recentActivityMapper;

	@Autowired
	private FunctionalitiesService functionalitiesService;

	RecentActivity recentActivity = new RecentActivity();

	@GetMapping("/passedAccount/{auditor_id}")
	public ResponseEntity<List<BranchFinancialAudit>> getBAFindingsForAuditor(@PathVariable Long auditor_id,
			HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request,
		"get_passed_dormant_account_auditor_bfa")) {
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

	@GetMapping("/draft/{auditor_id}")
	public ResponseEntity<List<BranchFinancialAudit>> getAuditsOnDrafting(@PathVariable Long auditor_id,
			HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request,
		"get_drafted_dormant_account_auditor_bfa")) {
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

	@GetMapping("/progress/{auditor_id}")
	public ResponseEntity<List<BranchFinancialAudit>> getAuditsOnProgressForAuditor(@PathVariable Long auditor_id,
			HttpServletRequest request) {
		// if (functionalitiesService.verifyPermission(request,
		// "get_ISM_findings_auditor")) {
		try {
			return new ResponseEntity<>(auditService.getAuditsOnProgressForAuditor(auditor_id), HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// } else {
		// return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		// }
	}

	@PostMapping("/create")
	public ResponseEntity<HttpStatus> createBAFinding(@RequestBody BranchFinancialAudit audit, HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request,
		"create_or_update_dormant_account_auditor_bfa")) {
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

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<HttpStatus> deleteBAFinding(@PathVariable("id") Long id,
			HttpServletRequest request) {
				if (functionalitiesService.verifyPermission(request,
		"delete_dormant_account_auditor_bfa")) {
		try {
			auditService.deleteBAFinding(id);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

	}

	@PostMapping("delete/selected")
	public ResponseEntity<HttpStatus> deleteSelectedBAFinding(@RequestBody List<BranchFinancialAudit> BranchFinancialAudits,
			HttpServletRequest request) {
				if (functionalitiesService.verifyPermission(request,
		"delete_selected_dormant_account_auditor_bfa")) {
		try {
			auditService.deleteSelectedBAFinding(BranchFinancialAudits);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

	}

	@GetMapping("/pass/{id}")
	public ResponseEntity<HttpStatus> passISMFinding(@PathVariable("id") Long id,
			HttpServletRequest request) {
				if (functionalitiesService.verifyPermission(request,
		"pass_dormant_account_auditor_bfa")) {
		try {
			auditService.passBAFinding(id);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

	}

	@PostMapping("pass/selected")
	public ResponseEntity<HttpStatus> passSelectedBAFinding(@RequestBody List<BranchFinancialAudit> BranchFinancialAudits,
			HttpServletRequest request) {
				if (functionalitiesService.verifyPermission(request,
		"pass_selected_dormant_account_auditor_bfa")) {
		try {
			auditService.passSelectedBAFinding(BranchFinancialAudits);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

	}

	@GetMapping("/back/{id}")
	public ResponseEntity<HttpStatus> backBAFinding(@PathVariable("id") Long id,
			HttpServletRequest request) {
				if (functionalitiesService.verifyPermission(request,
		"back_dormant_account_auditor_bfa")) {
		try {
			auditService.backBAFinding(id);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

	}

	@PostMapping("back/selected")
	public ResponseEntity<HttpStatus> backSelectedBAFinding(@RequestBody List<BranchFinancialAudit> BranchFinancialAudits,
			HttpServletRequest request) {
				if (functionalitiesService.verifyPermission(request,
		"back_selected_dormant_account_auditor_bfa")) {
		try {
			auditService.backSelectedBAFinding(BranchFinancialAudits);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

	}


	@GetMapping("/bfa")
	public ResponseEntity<List<BranchFinancialAudit>> getAuditFindings(HttpServletRequest request) {
		// if (functionalitiesService.verifyPermission(request,
		// "get_ISM_findings_auditor")) {
		try {
			return new ResponseEntity<>(auditService.getAuditFindings(), HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// } else {
		// return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		// }
	}

}

