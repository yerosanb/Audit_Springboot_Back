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

import com.afr.fms.Branch_Audit.Auditor.Service.StatusOfAuditService;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Common.Service.FunctionalitiesService;
import com.afr.fms.Payload.endpoint.Endpoint;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/branch_audit/auditor/status_of_audit") 
@PreAuthorize("hasRole('AUDITOR_BFA')")
public class StatusOfAuditController {
	@Autowired
	private StatusOfAuditService service;

	@Autowired
	private FunctionalitiesService functionalitiesService;
	
	@GetMapping("/getStatusOfAudit/{auditor_id}")
	public ResponseEntity<List<BranchFinancialAudit>> getStatusOfAudit(@PathVariable Long auditor_id,
			HttpServletRequest request) {
				if (functionalitiesService.verifyPermission(request,
		"get_drafted_status_of_audit_auditor_bfa")) {
		try {
			return new ResponseEntity<List<BranchFinancialAudit>>(service.getStatusOfAudit(auditor_id), HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	
	@PostMapping("/saveStatusOfAudit")
	public ResponseEntity<HttpStatus> saveStatusOfAudit(@RequestBody BranchFinancialAudit audit, HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request,
		"create_or_update_status_of_audit_auditor_bfa")) {
		try {
			if (audit.getId() != null) {
				
				service.updateStatusOfAudit(audit);
			} else {
				service.saveStatusOfAudit(audit);
			}
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("/deleteSelecteStatusofdAudits")
	public ResponseEntity<HttpStatus> deleteSelecteStatusofdAudits(@RequestBody List<BranchFinancialAudit> BranchFinancialAudits,
			HttpServletRequest request) {
				if (functionalitiesService.verifyPermission(request,
		"delete_selected_status_of_audit_auditor_bfa")) {
		try {
			service.deleteSelecteStatusofdAudits(BranchFinancialAudits);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}


	@DeleteMapping("/deleteStatusOfAudit/{id}")
	public ResponseEntity<HttpStatus> deleteStatusOfAudit(@PathVariable("id") Long id,
			HttpServletRequest request) {
				if (functionalitiesService.verifyPermission(request,
		"delete_status_of_audit_auditor_bfa")) {
		try {
			service.deleteStatusOfAudit(id);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("pass/selected")
	public ResponseEntity<HttpStatus> passSelecteStatusOfdAudits(@RequestBody List<BranchFinancialAudit> BranchFinancialAudits,
			HttpServletRequest request) {
				if (functionalitiesService.verifyPermission(request,
		"pass_selected_status_of_audit_auditor_bfa")) {
		try {
			service.passSelecteStatusOfdAudits(BranchFinancialAudits);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("pass/{id}")
	public ResponseEntity<HttpStatus> passStatusOfAudit(@PathVariable("id") Long id,
			HttpServletRequest request) {
				if (functionalitiesService.verifyPermission(request,
		"pass_status_of_audit_auditor_bfa")) {
		try {
			service.passStatusOfAudit(id);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/getPassedStatusOfAudit/{auditor_id}")
	public ResponseEntity<List<BranchFinancialAudit>> getPassedStatusOfAudit(@PathVariable Long auditor_id,
			HttpServletRequest request) {
				if (functionalitiesService.verifyPermission(request,
		"get_passed_status_of_audit_auditor_bfa")) {
		try {
			return new ResponseEntity<List<BranchFinancialAudit>>(service.getPassedStatusOfAudit(auditor_id), HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("back/selected")
	public ResponseEntity<HttpStatus> backSelectedStatusOfdAudits(@RequestBody List<BranchFinancialAudit> BranchFinancialAudits,
			HttpServletRequest request) {
				if (functionalitiesService.verifyPermission(request,
		"back_selected_status_of_audit_auditor_bfa")) {
		try {
			service.backSelectedStatusOfdAudits(BranchFinancialAudits);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

	}

	@GetMapping("back/{id}")
	public ResponseEntity<HttpStatus> backStatusOfAudit(@PathVariable("id") Long id,
			HttpServletRequest request) {
				if (functionalitiesService.verifyPermission(request,
		"back_status_of_audit_auditor_bfa")) {
		try {
			service.backStatusOfAudit(id);
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
