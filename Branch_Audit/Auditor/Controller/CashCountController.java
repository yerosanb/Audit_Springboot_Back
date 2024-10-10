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

import com.afr.fms.Branch_Audit.Auditor.Service.CashCountService;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Common.Service.FunctionalitiesService;
import com.afr.fms.Payload.endpoint.Endpoint;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/branch_audit/auditor/cash_count")
@PreAuthorize("hasRole('AUDITOR_BFA')")
public class CashCountController {
	@Autowired
	private CashCountService service;

	@Autowired
	private FunctionalitiesService functionalitiesService;

	@GetMapping("/getCashCount/{id}")
	public ResponseEntity<List<BranchFinancialAudit>> getCashCount(@PathVariable Long id, HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request,
		"get_cash_count_auditor_bfa")) {
		try {
			return new ResponseEntity<>(service.getCashCount(id), HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("/create")
	public ResponseEntity<HttpStatus> addCashCount(@RequestBody BranchFinancialAudit audit,
			HttpServletRequest request) {
				if (functionalitiesService.verifyPermission(request,
		"create_or_update_cash_count_auditor_bfa")) {
		try {
			if (audit.getId() != null) {
				service.editCashCount(audit);
			} else {
				service.addCashCount(audit);
			}
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/getAuditsOnDrafting/{auditor_id}")
	public ResponseEntity<List<BranchFinancialAudit>> getAuditsOnDrafting(@PathVariable Long auditor_id,
			HttpServletRequest request) {
				if (functionalitiesService.verifyPermission(request,
		"get_drafted_cash_count_auditor_bfa")) {
		try {
			return new ResponseEntity<>(service.getAuditsOnDrafting(auditor_id), HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("/delete/selected")
	public ResponseEntity<HttpStatus> deleteSelectedCashCount(
			@RequestBody List<BranchFinancialAudit> BranchFinancialAudits,
			HttpServletRequest request) {
				if (functionalitiesService.verifyPermission(request,
		"delete_selected_cash_count_auditor_bfa")) {
		try {
			service.deleteSelectedCashCount(BranchFinancialAudits);
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
	public ResponseEntity<HttpStatus> passSelectedCashCount(
			@RequestBody List<BranchFinancialAudit> BranchFinancialAudits,
			HttpServletRequest request) {
				if (functionalitiesService.verifyPermission(request,
		"pass_selected_cash_count_auditor_bfa")) {
		try {
			service.passSelectedCashCount(BranchFinancialAudits);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<HttpStatus> deleteCashCount(@PathVariable("id") Long id,
			HttpServletRequest request) {
				if (functionalitiesService.verifyPermission(request,
		"delete_cash_count_auditor_bfa")) {
		try {
			service.deleteCashCount(id);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("pass/{id}")
	public ResponseEntity<HttpStatus> passCashCount(@PathVariable("id") Long id,
			HttpServletRequest request) {
				if (functionalitiesService.verifyPermission(request,
		"pass_cash_count_auditor_bfa")) {
		try {
			service.passCashCount(id);
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
	public ResponseEntity<List<BranchFinancialAudit>> getPassedCashCount(@PathVariable Long auditor_id,
			HttpServletRequest request) {
				if (functionalitiesService.verifyPermission(request,
		"get_passed_cash_count_auditor_bfa")) {
		try {
			return new ResponseEntity<>(service.getPassedCashCount(auditor_id), HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("back/selected")
	public ResponseEntity<HttpStatus> backSelectedCashCount(
			@RequestBody List<BranchFinancialAudit> BranchFinancialAudits,
			HttpServletRequest request) {
				if (functionalitiesService.verifyPermission(request,
		"back_selected_cash_count_auditor_bfa")) {
		try {
			service.backSelectedCashCount(BranchFinancialAudits);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("/edit/passed")
	public ResponseEntity<HttpStatus> editPassedCashCount(@RequestBody BranchFinancialAudit audit,
			HttpServletRequest request) {
		try {
			service.editPassedCashCount(audit);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("back/{id}")
	public ResponseEntity<HttpStatus> backPassedCashCount(@PathVariable("id") Long id,
			HttpServletRequest request) {
				if (functionalitiesService.verifyPermission(request,
		"back_cash_count_auditor_bfa")) {
		try {
			service.backPassedCashCount(id);
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
