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

import com.afr.fms.Branch_Audit.Auditor.Service.LoanAndAdvanceService;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Common.Service.FunctionalitiesService;
import com.afr.fms.Payload.endpoint.Endpoint;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/branch_audit/auditor/loan-and-advance")
@PreAuthorize("hasRole('AUDITOR_BFA')")
public class LoanAndAdvanceController {

    @Autowired
    private LoanAndAdvanceService loanService;

    @Autowired
	private FunctionalitiesService functionalitiesService;

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createBAFinding(@RequestBody BranchFinancialAudit audit,
            HttpServletRequest request) {
        if (functionalitiesService.verifyPermission(request,
        "create_or_update_loan_and_advance_auditor_bfa")) {
        try {
            if (audit.getId() != null) {
                loanService.updateLoanAndAdvance(audit);
            } else {
                loanService.createLoanAndAdvance(audit);
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteBFA(@PathVariable("id") Long id,
            HttpServletRequest request) {
                if (functionalitiesService.verifyPermission(request,
                "delete_loan_and_advance_auditor_bfa")) {
        try {
            loanService.deleteBFA(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } else {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping("delete/selected")
    public ResponseEntity<HttpStatus> deleteSelectedBFA(
            @RequestBody List<BranchFinancialAudit> BranchFinancialAudits,
            HttpServletRequest request) {
                if (functionalitiesService.verifyPermission(request,
                "delete_selected_loan_and_advance_auditor_bfa")) {
        try {
            loanService.deleteSelectedBFA(BranchFinancialAudits);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } else {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }

    @GetMapping("/drafted/{auditor_id}")
    public ResponseEntity<List<BranchFinancialAudit>> getLoanAndAdvanceOnDrafting(@PathVariable Long auditor_id,
            HttpServletRequest request) {
        if (functionalitiesService.verifyPermission(request,
        "get_drafted_loan_and_advance_auditor_bfa")) {
        try {
            return new ResponseEntity<>(loanService.getLoanAndAdvanceOnDrafting(auditor_id), HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        } else {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("pass/{id}")
    public ResponseEntity<HttpStatus> passBFA(@PathVariable("id") Long id,
            HttpServletRequest request) {
                if (functionalitiesService.verifyPermission(request,
        "pass_loan_and_advance_auditor_bfa")) {
        try {
            loanService.passBFA(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } else {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("pass/selected")
    public ResponseEntity<HttpStatus> passSelectedBFA(@RequestBody List<BranchFinancialAudit> BranchFinancialAudits,
            HttpServletRequest request) {
                if (functionalitiesService.verifyPermission(request,
                "pass_selected_loan_and_advance_auditor_bfa")) {
        try {
            loanService.passSelectedBFA(BranchFinancialAudits);
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
    public ResponseEntity<List<BranchFinancialAudit>> getBAFindingsForAuditor(@PathVariable Long auditor_id,
            HttpServletRequest request) {
        if (functionalitiesService.verifyPermission(request,
        "get_passed_loan_and_advance_auditor_bfa")) {
        try {
            return new ResponseEntity<>(loanService.getAuditsForAuditor(auditor_id), HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        } else {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("back/{id}")
    public ResponseEntity<HttpStatus> backBAFinding(@PathVariable("id") Long id,
            HttpServletRequest request) {
                if (functionalitiesService.verifyPermission(request,
        "back_loan_and_advance_auditor_bfa")) {
        try {
            loanService.backBAFinding(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } else {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping("back/selected")
    public ResponseEntity<HttpStatus> backSelectedBAFinding(
            @RequestBody List<BranchFinancialAudit> BranchFinancialAudits,
            HttpServletRequest request) {
                if (functionalitiesService.verifyPermission(request,
        "back_selected_loan_and_advance_auditor_bfa")) {
        try {
            loanService.backSelectedBAFinding(BranchFinancialAudits);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } else {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }


    @GetMapping("/progress/{auditor_id}")
	public ResponseEntity<List<BranchFinancialAudit>> getOnProgressFindings(@PathVariable Long auditor_id,
			HttpServletRequest request) {
		// if (functionalitiesService.verifyPermission(request,
		// "get_ISM_findings_auditor")) {
		try {
			return new ResponseEntity<>(loanService.getOnProgressAudits(auditor_id), HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// } else {
		// return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		// }
	}

}
