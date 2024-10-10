package com.afr.fms.Branch_Audit.Auditor.Rectification.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afr.fms.Branch_Audit.Auditor.Rectification.Service.AuditRectificationService;
import com.afr.fms.Branch_Audit.Common.RectificationTracker.RectificationTrackerService;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Common.Service.FilesStorageService;
import com.afr.fms.Payload.endpoint.Endpoint;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/auditor/auditee_response/")
// @PreAuthorize("hasRole('AUDITOR')")
public class AuditRectificationController {

	@Autowired
	FilesStorageService storageService;
	@Autowired
	private AuditRectificationService auditRectificationService;

	@Autowired
	private RectificationTrackerService rectificationTrackerService;

	@PostMapping("add")
	public ResponseEntity<HttpStatus> add_response(@RequestBody List<BranchFinancialAudit> auditee_response,
			HttpServletRequest request) {
		// if (functionalitiesService.verifyPermission(request,
		// "add_auditee_response")) {

		try {

			auditRectificationService.add_response(auditee_response);
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

	@PostMapping("rectify")
	public ResponseEntity<HttpStatus> rectifyAuditeeResponse(
			@RequestBody List<BranchFinancialAudit> branchFinancialAudits,
			HttpServletRequest request) {
		// if (functionalitiesService.verifyPermission(request,
		// "rectify_auditee_response")) {

		try {
			for (BranchFinancialAudit branchFinancialAudit : branchFinancialAudits) {
				auditRectificationService.rectifyAuditeeResponse(branchFinancialAudit);
			}
			rectificationTrackerService.addRectificationTracker(branchFinancialAudits);
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