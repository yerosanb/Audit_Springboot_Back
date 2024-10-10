package com.afr.fms.Branch_Audit.Reviewer.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Branch_Audit.Entity.CompiledBranchAudit;
import com.afr.fms.Branch_Audit.Reviewer.Service.CompilerService;
import com.afr.fms.Common.RecentActivity.RecentActivity;
import com.afr.fms.Common.RecentActivity.RecentActivityMapper;
import com.afr.fms.Common.Service.FunctionalitiesService;
import com.afr.fms.Payload.endpoint.Endpoint;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")

@RestController
@RequestMapping("/api/branch_audit/regional_compiler/")

// @PreAuthorize("hasRole('ADMIN')")
public class CompilerController {

	@Autowired
	private CompilerService auditService;
	@Autowired
	private RecentActivityMapper recentActivityMapper;

	@Autowired
	private FunctionalitiesService functionalitiesService;

	RecentActivity recentActivity = new RecentActivity();

	@GetMapping("compiled/{compiler_id}")
	public ResponseEntity<List<CompiledBranchAudit>> getCompiledAudits(@PathVariable Long compiler_id) {
		// if (functionalitiesService.verifyPermission(request, "get_ISM_findings")) {
		try {
			// System.out.println(auditService.getCompiledFindings(compiler_id));
			return new ResponseEntity<>(auditService.getCompiledFindings(compiler_id), HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("progress")
	public ResponseEntity<List<CompiledBranchAudit>> getSubmittedCompiledAudits(@RequestBody User user) {
		// if (functionalitiesService.verifyPermission(request, "get_ISM_findings")) {
		try {
			// System.out.println(auditService.getCompiledFindings(compiler_id));
			return new ResponseEntity<>(auditService.getSubmittedCompiledFindings(user), HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PutMapping("compile")
	public ResponseEntity<HttpStatus> compileFindings(@RequestBody CompiledBranchAudit audit,
			HttpServletRequest request) {
		// if (functionalitiesService.verifyPermission(request, "get_ISM_findings")) {

		try {
			auditService.compileFindings(audit);
			return new ResponseEntity<>(HttpStatus.OK);

		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// } else {
		// return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		// }
	}

	@PutMapping("submit")
	public ResponseEntity<HttpStatus> submitCompiledFindings(@RequestBody CompiledBranchAudit audit,
			HttpServletRequest request) {
		// if (functionalitiesService.verifyPermission(request, "get_ISM_findings")) {
		try {
			auditService.submitCompiledFindings(audit);
			return new ResponseEntity<>(HttpStatus.OK);

		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// } else {
		// return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		// }
	}

	@PutMapping("submit/selected")
	public ResponseEntity<HttpStatus> submitMultipleCompiledFindings(@RequestBody List<CompiledBranchAudit> audits,
			HttpServletRequest request) {
		// if (functionalitiesService.verifyPermission(request, "get_ISM_findings")) {
		try {
			for (CompiledBranchAudit compiledBranchAudit : audits) {
				auditService.submitCompiledFindings(compiledBranchAudit);

			}
			return new ResponseEntity<>(HttpStatus.OK);

		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// } else {
		// return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		// }
	}

	@PutMapping("decompile")
	public ResponseEntity<HttpStatus> decompileFindings(@RequestBody CompiledBranchAudit compiledBranchAudit,
			HttpServletRequest request) {
		try {
			auditService.decompileFindings(compiledBranchAudit);
			return new ResponseEntity<>(HttpStatus.OK);

		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PutMapping("decompile/selected")
	public ResponseEntity<HttpStatus> decompileSelectedFindings(
			@RequestBody List<CompiledBranchAudit> compiledBranchAudit,
			HttpServletRequest request) {
		try {
			for (CompiledBranchAudit compiledBranchAudit2 : compiledBranchAudit) {
				auditService.decompileFindings(compiledBranchAudit2);
			}
			return new ResponseEntity<>(HttpStatus.OK);

		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
