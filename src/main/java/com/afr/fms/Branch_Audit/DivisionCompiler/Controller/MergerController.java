package com.afr.fms.Branch_Audit.DivisionCompiler.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afr.fms.Branch_Audit.DivisionCompiler.Service.MergerService;
import com.afr.fms.Branch_Audit.Entity.CompiledRegionalAudit;
import com.afr.fms.Common.RecentActivity.RecentActivity;
import com.afr.fms.Common.RecentActivity.RecentActivityMapper;
import com.afr.fms.Common.Service.FunctionalitiesService;
import com.afr.fms.Payload.endpoint.Endpoint;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/branch_audit/division_compiler/")
@PreAuthorize("hasRole('COMPILER_BFA')")
public class MergerController {

	@Autowired
	private MergerService auditService;
	@Autowired
	private RecentActivityMapper recentActivityMapper;

	@Autowired
	private FunctionalitiesService functionalitiesService;

	RecentActivity recentActivity = new RecentActivity();

	@GetMapping("compiled/{compiler_id}")
	public ResponseEntity<List<CompiledRegionalAudit>> getCompiledAudits(@PathVariable Long compiler_id, HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "get_compiled_compiler_bfa")) {
		try {
			return new ResponseEntity<>(auditService.getCompiledFindings(compiler_id), HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

	}

	@GetMapping("progress/{compiler_id}")
	public ResponseEntity<List<CompiledRegionalAudit>> getSubmittedCompiledAudits(@PathVariable Long compiler_id, HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "get_submitted_compiler_bfa")) {
		try {
			return new ResponseEntity<>(auditService.getSubmittedCompiledFindings(compiler_id), HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

	}

	@PutMapping("compile")
	public ResponseEntity<HttpStatus> compileFindings(@RequestBody CompiledRegionalAudit audit,
			HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "compile_compiler_bfa")) {

		try {
			auditService.compileFindings(audit);
			return new ResponseEntity<>(HttpStatus.OK);

		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@PutMapping("submit")
	public ResponseEntity<HttpStatus> submitCompiledFindings(@RequestBody CompiledRegionalAudit audit,
			HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "submit_compiler_bfa")) {
		try {
			auditService.submitCompiledFindings(audit);
			return new ResponseEntity<>(HttpStatus.OK);

		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@PutMapping("submit/selected")
	public ResponseEntity<HttpStatus> submitMultipleCompiledFindings(@RequestBody List<CompiledRegionalAudit> audits,
			HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "submit_selected_compiler_bfa")) {
		try {
			for (CompiledRegionalAudit compiledRegionalAudit : audits) {
				auditService.submitCompiledFindings(compiledRegionalAudit);

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

	@PutMapping("decompile")
	public ResponseEntity<HttpStatus> decompileFindings(@RequestBody CompiledRegionalAudit compiledRegionalAudit,
			HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "decompile_compiler_bfa")) {
		try {
			auditService.decompileFindings(compiledRegionalAudit);
			return new ResponseEntity<>(HttpStatus.OK);

		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

	}

	@PutMapping("decompile/selected")
	public ResponseEntity<HttpStatus> decompileSelectedFindings(
			@RequestBody List<CompiledRegionalAudit> compiledRegionalAudits,
			HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "decompile_selected_compiler_bfa")) {
		try {
			for (CompiledRegionalAudit compiledRegionalAudit : compiledRegionalAudits) {
				auditService.decompileFindings(compiledRegionalAudit);
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
