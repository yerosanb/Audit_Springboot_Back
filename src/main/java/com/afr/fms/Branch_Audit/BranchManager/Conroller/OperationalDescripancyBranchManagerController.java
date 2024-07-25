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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afr.fms.Branch_Audit.BranchManager.Service.OperationalDescripancyBranchManagerService;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Common.Service.FunctionalitiesService;
import com.afr.fms.Payload.endpoint.Endpoint;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/branch/branch_manager/operational_descripancy_branch")
@PreAuthorize("hasRole('BRANCHM_BFA')")
public class OperationalDescripancyBranchManagerController {
	@Autowired
	private OperationalDescripancyBranchManagerService operationalDescripancyBranchManagerService;

	@Autowired
	private FunctionalitiesService functionalitiesService;

	@GetMapping("/pending/{branch_id}")
	public ResponseEntity<List<BranchFinancialAudit>> getPendingAudits(@PathVariable Long branch_id,
			HttpServletRequest request) {
		// if (functionalitiesService.verifyPermission(request,
		// 		"get_pending_operational_descripancy_branch_manager_bfa")) {
			try {

				return new ResponseEntity<>(operationalDescripancyBranchManagerService.getPendingAudits(branch_id),
						HttpStatus.OK);
			} catch (Exception ex) {
				System.out.println(ex);
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		// } else {
			
		// 	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		// }
	}

	@GetMapping("/rectified/{auditee_id}")
	public ResponseEntity<List<BranchFinancialAudit>> getRectifiedAudis(@PathVariable Long auditee_id,
			HttpServletRequest request) {
		// if (functionalitiesService.verifyPermission(request,
		// "get_ISM_findings_auditor")) {
		try {

			return new ResponseEntity<>(operationalDescripancyBranchManagerService.getRectifiedAudits(auditee_id),
					HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// } else {
		// return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		// }
	}

	@GetMapping("/responded/{auditee_id}")
	public ResponseEntity<List<BranchFinancialAudit>> getRespondedAudits(@PathVariable Long auditee_id,
			HttpServletRequest request) {
		// if (functionalitiesService.verifyPermission(request,
		// "get_ISM_findings_auditor")) {
		try {

			return new ResponseEntity<>(operationalDescripancyBranchManagerService.getRespondedAudits(auditee_id),
					HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// } else {
		// return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		// }
	}

	@PostMapping("/give_response")
	public ResponseEntity<HttpStatus> add_response(@RequestBody BranchFinancialAudit auditee_response,
			HttpServletRequest request) {
		// if (functionalitiesService.verifyPermission(request,
		// "create_or_update_ISM_auditee_response")) {

		try {

			operationalDescripancyBranchManagerService.branchManagerGiveResponse(auditee_response);
			// }
			// recentActivity.setMessage(audite.getName() + " branch is created ");
			// user.setId(branch.getUser_id());
			// recentActivity.setUser(user);
			// recentActivityMapper.addRecentActivity(recentActivity);

			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// } else {
		// return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		// }
	}

	@PostMapping("/give_response/selected")
	public ResponseEntity<HttpStatus> giveRespnseSelected(@RequestBody List<BranchFinancialAudit> auditee_response,
			HttpServletRequest request) {
		// if (functionalitiesService.verifyPermission(request,
		// "create_or_update_ISM_auditee_response")) {

		try {

			operationalDescripancyBranchManagerService.branchManagerGiveResponseSelcted(auditee_response);
			// }
			// recentActivity.setMessage(audite.getName() + " branch is created ");
			// user.setId(branch.getUser_id());
			// recentActivity.setUser(user);
			// recentActivityMapper.addRecentActivity(recentActivity);

			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// } else {
		// return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		// }
	}

}
