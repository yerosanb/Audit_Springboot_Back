package com.afr.fms.Branch_Audit.BranchManager.Conroller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afr.fms.Branch_Audit.BranchManager.Service.StatusOfAuditBranchManagerService;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Common.Service.FunctionalitiesService;
import com.afr.fms.Payload.endpoint.Endpoint;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/branch/branch_manager/status_of_audit")
@PreAuthorize("hasRole('BRANCHM_BFA')")
public class StatusOfAuditBranchManagerController {
    @Autowired
    private StatusOfAuditBranchManagerService service;

	@Autowired
	private FunctionalitiesService functionalitiesService;
    
    @GetMapping("/pending/{branch_id}")
	public ResponseEntity<List<BranchFinancialAudit>> getPendingAudits(@PathVariable Long branch_id,
			HttpServletRequest request) {
				if (functionalitiesService.verifyPermission(request,
		"get_pending_status_of_audit_branch_manager_bfa")) {
                try {
			return new ResponseEntity<>(service.getPendingAudits(branch_id), HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
    }

}
