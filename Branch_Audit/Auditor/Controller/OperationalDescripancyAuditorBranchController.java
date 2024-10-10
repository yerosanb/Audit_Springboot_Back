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

import com.afr.fms.Branch_Audit.Auditor.Service.OperationalDescripancyAuditorBranchService;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Common.Service.FunctionalitiesService;
import com.afr.fms.Payload.endpoint.Endpoint;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/branch/auditor/operational_descripancy_branch")
@PreAuthorize("hasRole('AUDITOR_BFA')")
public class OperationalDescripancyAuditorBranchController {

	@Autowired
	private OperationalDescripancyAuditorBranchService operationalDescripancyAuditorBranchService;

	@Autowired
	private FunctionalitiesService functionalitiesService;

	@PostMapping("/create")
	public ResponseEntity<HttpStatus> createOperationalDescripancyBranch(@RequestBody BranchFinancialAudit branchFinancialAudit,
			HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request,
		"create_or_update_operational_descripancy_auditor_bfa")) {
		try {
			if (branchFinancialAudit.getId() != null) {
				operationalDescripancyAuditorBranchService.updateoperationalDescripancy(branchFinancialAudit);
			} else {
				operationalDescripancyAuditorBranchService.createoperationalDescripancy(branchFinancialAudit);
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
		"get_drafted_operational_descripancy_auditor_bfa")) {
		try {
			return new ResponseEntity<>(operationalDescripancyAuditorBranchService.getDraftedAudits(auditor_id), HttpStatus.OK);
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
			return new ResponseEntity<>(operationalDescripancyAuditorBranchService.getOnProgressAudits(auditor_id), HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// } else {
		// return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		// }
	}

	@PostMapping("/pass")
	public ResponseEntity<HttpStatus> passAbnormalBalance(@RequestBody BranchFinancialAudit branchFinancialAudit,
			HttpServletRequest request) {
				if (functionalitiesService.verifyPermission(request,
		"pass_operational_descripancy_auditor_bfa")) {
		try {
			operationalDescripancyAuditorBranchService.passoperationalDescripancy(branchFinancialAudit);
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
	public ResponseEntity<HttpStatus> passMultiAbnormalBalance(
			@RequestBody List<BranchFinancialAudit> branchFinancialAudit,
			HttpServletRequest request) {
				if (functionalitiesService.verifyPermission(request,
		"pass_selected_operational_descripancy_auditor_bfa")) {
		try {
			operationalDescripancyAuditorBranchService.passMultioperationalDescripancy(branchFinancialAudit);
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
		"get_passed_operational_descripancy_auditor_bfa")) {
		try {
			return new ResponseEntity<>(operationalDescripancyAuditorBranchService.getPassedAudits(auditor_id), HttpStatus.OK);
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
		"back_operational_descripancy_auditor_bfa")) {
		try {
			operationalDescripancyAuditorBranchService.backPassedoperationalDescripancy(branchFinancialAudit);
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
		"back_selected_operational_descripancy_auditor_bfa")) {
		try {
			operationalDescripancyAuditorBranchService.backMultiPassedoperationalDescripancy(inspectionAudits);
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
		"delete_operational_descripancy_auditor_bfa")) {
		try {
			operationalDescripancyAuditorBranchService.deleteoperationalDescripancy(branchFinancialAudit.getId());
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
		"delete_selected_operational_descripancy_auditor_bfa")) {
		try {
			operationalDescripancyAuditorBranchService.deleteMultioperationalDescripancy(branchFinancialAudits);
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
