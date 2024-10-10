package com.afr.fms.Branch_Audit.Core.Controller;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Branch_Audit.Approver.Service.ApproverBranchService;
import com.afr.fms.Branch_Audit.Core.Service.TrialBalanceService;
import com.afr.fms.Branch_Audit.Entity.CompiledRegionalAudit;
import com.afr.fms.Common.RecentActivity.RecentActivity;
import com.afr.fms.Common.RecentActivity.RecentActivityMapper;
import com.afr.fms.Common.Service.FunctionalitiesService;
import com.afr.fms.Payload.endpoint.Endpoint;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/branch_audit/core/")
@PreAuthorize("hasRole('APPROVER_BFA')")
public class TrialBalanceController {

	@Autowired
	private TrialBalanceService auditService;
	@Autowired
	private RecentActivityMapper recentActivityMapper;

	@Autowired
	private FunctionalitiesService functionalitiesService;

	RecentActivity recentActivity = new RecentActivity();

	@GetMapping("pending/{category}")
	public ResponseEntity<List<CompiledRegionalAudit>> getPendingAudits(@PathVariable String category,
			HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request,
				"get_pending_compiled_regional_approver_bfa")) {
			try {
				return new ResponseEntity<>(auditService.getPendingAudits(), HttpStatus.OK);
			} catch (Exception ex) {
				System.out.println(ex);
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

	}

	@PutMapping("update")
	public ResponseEntity<HttpStatus> updateAudit(@RequestBody CompiledRegionalAudit audit,
			HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "update_compiled_regional_approver_bfa")) {
			try {
				auditService.updateCompiledFinding(audit);
				return new ResponseEntity<>(HttpStatus.OK);
			} catch (Exception ex) {
				System.out.println(ex);
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("approved/{approver_id}")
	public ResponseEntity<List<CompiledRegionalAudit>> getReviewedFindings(@PathVariable Long approver_id,
			HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "get_approved_compiled_regional_approver_bfa")) {
			try {
				return new ResponseEntity<>(auditService.getApprovedAudits(approver_id), HttpStatus.OK);
			} catch (Exception ex) {
				System.out.println(ex);
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("drafted/{approver_id}")
	public ResponseEntity<List<CompiledRegionalAudit>> getDraftedFindings(@PathVariable Long approver_id,
			HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "get_drafted_compiled_regional_approver_bfa")) {
			try {
				return new ResponseEntity<>(auditService.getDraftedAudits(approver_id), HttpStatus.OK);
			} catch (Exception ex) {
				System.out.println(ex);
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("close")
	public ResponseEntity<HttpStatus> closeFindings(@RequestBody List<CompiledRegionalAudit> compiledRegionalAudits,
			HttpServletRequest request) {
		// if (functionalitiesService.verifyPermission(request, "close_findings_compiled_regional_approver_bfa")) {
			try {
				for (CompiledRegionalAudit compiledRegionalAudit : compiledRegionalAudits) {
					auditService.closeFinding(compiledRegionalAudit);
				}
				return new ResponseEntity<>(HttpStatus.OK);
			} catch (Exception ex) {
				System.out.println(ex);
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		// } else {
		// 	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		// }
	}

	@PutMapping("approve")
	public ResponseEntity<HttpStatus> approveFinding(@RequestBody List<CompiledRegionalAudit> audits,
			HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "approve_compiled_regional_approver_bfa")) {
			try {
				User approver = audits.get(0).getApprover();
				for (CompiledRegionalAudit compiledRegionalAudit : audits) {
					compiledRegionalAudit.setApprover(approver);
					auditService.approveFinding(compiledRegionalAudit);
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

	@PutMapping("draft")
	public ResponseEntity<HttpStatus> addToDrafting(@RequestBody List<CompiledRegionalAudit> audits,
			HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "draft_compiled_regional_approver_bfa")) {
			try {
				User approver = audits.get(0).getApprover();

				for (CompiledRegionalAudit compiledRegionalAudit : audits) {
					compiledRegionalAudit.setApprover(approver);
					auditService.addToDrafting(compiledRegionalAudit);
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

	@PutMapping("unapprove")
	public ResponseEntity<HttpStatus> unapproveFinding(@RequestBody List<CompiledRegionalAudit> audits,
			HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "unapprove_compiled_regional_approver_bfa")) {
			try {
				User approver = audits.get(0).getApprover();
				for (CompiledRegionalAudit compiledRegionalAudit : audits) {
					compiledRegionalAudit.setApprover(approver);
					auditService.unapproveFinding(compiledRegionalAudit);
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
