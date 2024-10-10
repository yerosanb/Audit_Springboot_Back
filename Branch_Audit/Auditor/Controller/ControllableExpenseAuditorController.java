
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

import com.afr.fms.Branch_Audit.Auditor.Service.ControllableExpenseAuditorService;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Common.Service.FunctionalitiesService;
import com.afr.fms.Payload.endpoint.Endpoint;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/branch_audit/auditor/controllable_expense")
@PreAuthorize("hasRole('AUDITOR_BFA')")
public class ControllableExpenseAuditorController {

	@Autowired
	private ControllableExpenseAuditorService suspenseAccountAuditorBranchService;

	@Autowired
	private FunctionalitiesService functionalitiesService;

	@PostMapping("/createAll")
	public ResponseEntity<HttpStatus> createControllableExpenseBranch(@RequestBody BranchFinancialAudit branchFinancialAudit,
			HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request,
		"create_or_update_controllable_expenses_auditor_bfa")) {
		try {
			if (branchFinancialAudit.getId() != null) {
				suspenseAccountAuditorBranchService.updateControllableExpenseBranch(branchFinancialAudit);
			} else {
				suspenseAccountAuditorBranchService.createControllableExpenseBranch(branchFinancialAudit);
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

	@GetMapping("/drafted/{auditor_id}")
	public ResponseEntity<List<BranchFinancialAudit>> getAuditsDraftedFindings(@PathVariable Long auditor_id,
			HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request,
		"get_drafted_controllable_expenses_auditor_bfa")) {
		try {
			return new ResponseEntity<>(suspenseAccountAuditorBranchService.getDraftedAudits(auditor_id), HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/progress/{auditor_id}")
	public ResponseEntity<List<BranchFinancialAudit>> getOnProgressFindings(@PathVariable Long auditor_id,
			HttpServletRequest request) {
		// if (functionalitiesService.verifyPermission(request,
		// "get_ISM_findings_auditor")) {
		try {
			return new ResponseEntity<>(suspenseAccountAuditorBranchService.getOnProgressAudits(auditor_id), HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// } else {
		// return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		// }
	}

	@PostMapping("/pass")
	public ResponseEntity<HttpStatus> passControllableExpenseBranch(@RequestBody BranchFinancialAudit branchFinancialAudit,
			HttpServletRequest request) {
				if (functionalitiesService.verifyPermission(request,
		"pass_controllable_expenses_auditor_bfa")) {
		try {
			suspenseAccountAuditorBranchService.passControllableExpenseBranch(branchFinancialAudit);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

	}

	@PostMapping("/pass/selected")
	public ResponseEntity<HttpStatus> passMultiControllableExpenseBranch(
			@RequestBody List<BranchFinancialAudit> branchFinancialAudit,
			HttpServletRequest request) {
				if (functionalitiesService.verifyPermission(request,
		"pass_selected_controllable_expenses_auditor_bfa")) {
		try {
			suspenseAccountAuditorBranchService.passMultiControllableExpenseBranch(branchFinancialAudit);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

	}

	@GetMapping("/passed/{auditor_id}")
	public ResponseEntity<List<BranchFinancialAudit>> getAuditsForPassedAuditor(@PathVariable Long auditor_id,
			HttpServletRequest request) {
				if (functionalitiesService.verifyPermission(request,
				"get_passed_controllable_expenses_auditor_bfa")) {
		try {
			return new ResponseEntity<>(suspenseAccountAuditorBranchService.getPassedAudits(auditor_id), HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("/back")
	public ResponseEntity<HttpStatus> backpassedFindings(@RequestBody  BranchFinancialAudit branchFinancialAudit,
			HttpServletRequest request) {
				if (functionalitiesService.verifyPermission(request,
				"back_controllable_expenses_auditor_bfa")) {
		try {
			suspenseAccountAuditorBranchService.backPassedControllableExpenseBranch(branchFinancialAudit);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

	}

	@PostMapping("/back/selected")
	public ResponseEntity<HttpStatus> backMultiPassedFindings(@RequestBody List<BranchFinancialAudit> inspectionAudits,
			HttpServletRequest request) {
				if (functionalitiesService.verifyPermission(request,
				"back_selected_controllable_expenses_auditor_bfa")) {
		try {
			suspenseAccountAuditorBranchService.backMultiPassedControllableExpenseBranch(inspectionAudits);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<HttpStatus> deleteOperationalDescripancy(@RequestBody BranchFinancialAudit branchFinancialAudit,
			HttpServletRequest request) {
				if (functionalitiesService.verifyPermission(request,
				"delet_controllable_expenses_auditor_bfa")) {
		try {
			suspenseAccountAuditorBranchService.deleteControllableExpenseBranch(branchFinancialAudit.getId());
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

	}

	@PostMapping("/delete/selected")
	public ResponseEntity<HttpStatus> deletMultiOperationalDescripancy(
			@RequestBody List<BranchFinancialAudit> branchFinancialAudits,
			HttpServletRequest request) {
				if (functionalitiesService.verifyPermission(request,
				"delete_selected_controllable_expenses_auditor_bfa")) {
		try {
			suspenseAccountAuditorBranchService.deleteMultiControllableExpenseBranch(branchFinancialAudits);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

	}

}

