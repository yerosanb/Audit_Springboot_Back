package com.afr.fms.Branch_Audit.DivisionCompiler.Controller;

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

import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Branch_Audit.Common.Audit_Remark.RemarkBranchAudit;
import com.afr.fms.Branch_Audit.DivisionCompiler.Service.DivisionCompilerService;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Entity.CompiledBranchAudit;
import com.afr.fms.Common.RecentActivity.RecentActivity;
import com.afr.fms.Common.RecentActivity.RecentActivityMapper;
import com.afr.fms.Common.Service.FunctionalitiesService;
import com.afr.fms.Payload.endpoint.Endpoint;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")

@RestController
@RequestMapping("/api/branch_audit/division/")
@PreAuthorize("hasRole('COMPILER_BFA')")
public class DivisionCompilerController {

	@Autowired
	private DivisionCompilerService auditService;
	@Autowired
	private RecentActivityMapper recentActivityMapper;

	@Autowired
	private FunctionalitiesService functionalitiesService;

	RecentActivity recentActivity = new RecentActivity();

	@GetMapping("pending/{parameters}")
	public ResponseEntity<List<CompiledBranchAudit>> getPendingAudits(@PathVariable List<Object> parameters,HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "get_pending_compiler_bfa")) {
		try {
			return new ResponseEntity<>(auditService.getPendingAudits(parameters), HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

	}

	@GetMapping("reviewed/{parameters}")
	public ResponseEntity<List<CompiledBranchAudit>> getReviewedFindings(@PathVariable List<Object> parameters, HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "get_reviewed_compiler_bfa")) {
		try {
			return new ResponseEntity<>(auditService.getReviewedAudits(parameters), HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/rejected/{reviewer_id}")
	public ResponseEntity<List<BranchFinancialAudit>> getRejectedFindings(@PathVariable Long reviewer_id, HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "get_rejected_compiler_bfa")) {

		try {
			return new ResponseEntity<>(auditService.getRejectedFindings(reviewer_id), HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/reviewedStatus/{reviewer_id}")
	public ResponseEntity<List<BranchFinancialAudit>> getReviewedFindingsStatus(@PathVariable Long reviewer_id) {
		// if (functionalitiesService.verifyPermission(request, "get_ISM_findings")) {

		try {
			return new ResponseEntity<>(auditService.getReviewedFindingsStatus(reviewer_id), HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// } else {
		// return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		// }
	}

	@PutMapping("/cancel")
	public ResponseEntity<HttpStatus> cancelFinding(@RequestBody RemarkBranchAudit remark, HttpServletRequest request) {
		// if (functionalitiesService.verifyPermission(request, "get_ISM_findings")) {

		try {
			// auditService.cancelFinding(remark);
			return new ResponseEntity<>(HttpStatus.OK);

		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// } else {
		// return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		// }
	}

	@PutMapping("review")
	public ResponseEntity<HttpStatus> reviewFinding(@RequestBody List<CompiledBranchAudit> audits,
			HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "review_compiler_bfa")) {
		try {
			User compiler = audits.get(0).getCompiler();

			for (CompiledBranchAudit compiledBranchAudit : audits) {
				compiledBranchAudit.setCompiler(compiler);

				auditService.reviewFinding(compiledBranchAudit);
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

	@PutMapping("unreview")
	public ResponseEntity<HttpStatus> unreviewFinding(@RequestBody List<CompiledBranchAudit> audits,
			HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "unreview_compiler_bfa")) {
		try {
			User compiler = audits.get(0).getCompiler();
			for (CompiledBranchAudit compiledBranchAudit : audits) {
				compiledBranchAudit.setCompiler(compiler);
				auditService.unreviewFinding(compiledBranchAudit);
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
