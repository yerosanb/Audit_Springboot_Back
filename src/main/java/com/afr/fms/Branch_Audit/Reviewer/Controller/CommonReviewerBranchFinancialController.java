package com.afr.fms.Branch_Audit.Reviewer.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afr.fms.Branch_Audit.Common.Audit_Remark.RemarkBranchAudit;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Reviewer.Service.CommonReviewerBranchFinancialService;
import com.afr.fms.Common.RecentActivity.RecentActivity;
import com.afr.fms.Common.RecentActivity.RecentActivityMapper;
import com.afr.fms.Common.Service.FunctionalitiesService;
import com.afr.fms.Payload.endpoint.Endpoint;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/branch_audit/reviewer/common/")
@PreAuthorize("hasRole('REVIEWER_BFA')")
public class CommonReviewerBranchFinancialController {

	@Autowired
	private CommonReviewerBranchFinancialService auditService;
	@Autowired
	private RecentActivityMapper recentActivityMapper;

	@Autowired
	private FunctionalitiesService functionalitiesService;

	RecentActivity recentActivity = new RecentActivity();

	@GetMapping("/progress/{reviewer_id}")
	public ResponseEntity<List<BranchFinancialAudit>> getReviewedFindingsStatus(@PathVariable Long reviewer_id) {
		// if (functionalitiesService.verifyPermission(request, "get_ISM_findings")) {

		try {
			return new ResponseEntity<>(auditService.getAuditsOnProgressAudits(reviewer_id), HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// } else {
		// return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		// }
	}

	@PutMapping("/review")
	public ResponseEntity<HttpStatus> reviewFindings(@RequestBody BranchFinancialAudit audit,
			HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "review_reviewer_bfa")) {
		try {
			auditService.reviewFindings(audit);
			return new ResponseEntity<>(HttpStatus.OK);

		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@PutMapping("/cancel")
	public ResponseEntity<HttpStatus> cancelFinding(@RequestBody RemarkBranchAudit remark, HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "cancel_reviewer_bfa")) {

		try {
			auditService.cancelFinding(remark);
			return new ResponseEntity<>(HttpStatus.OK);

		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@PutMapping("/review/selected")
	public ResponseEntity<HttpStatus> reviewMultipleFindings(@RequestBody List<BranchFinancialAudit> audit,
			HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "review_selected_reviewer_bfa")) {
		try {
			auditService.multiReviewFindings(audit);
			return new ResponseEntity<>(HttpStatus.OK);

		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@PutMapping("/unreview")
	public ResponseEntity<HttpStatus> unReviewFinding(@RequestBody BranchFinancialAudit audit,
			HttpServletRequest request) {
				if (functionalitiesService.verifyPermission(request, "unreview_reviewer_bfa")) {
		try {
			auditService.unReviewFinding(audit);
			return new ResponseEntity<>(HttpStatus.OK);

		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

	}

	@PutMapping("/unreview/selected")

	public ResponseEntity<HttpStatus> unReviewMultipleFindings(@RequestBody List<BranchFinancialAudit> audit,
			HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "unreview_selected_reviewer_bfa")) {
		try {
			auditService.unReviewMultipleFindings(audit);
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
