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

import com.afr.fms.Branch_Audit.Auditor.Service.AtmCardAuditorService;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Common.Service.FunctionalitiesService;
import com.afr.fms.Payload.endpoint.Endpoint;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/branch/auditor/atm")
@PreAuthorize("hasRole('AUDITOR_BFA')")
public class AtmCardAuditorController {
    @Autowired
    private AtmCardAuditorService atmCardService;

    @Autowired
	private FunctionalitiesService functionalitiesService;

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createATMFinding(@RequestBody BranchFinancialAudit audit,
            HttpServletRequest request) {
                if (functionalitiesService.verifyPermission(request,
                "create_or_update_atm_card_auditor_bfa")) {  
        try {
            if (audit.getId() != null) {
                atmCardService.updateATMFinding(audit);
            } else {
                atmCardService.createATMFinding(audit);
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

    @GetMapping("/drafting/{auditor_id}")
    public ResponseEntity<List<BranchFinancialAudit>> getATMAuditsOnDraftingBranch(@PathVariable Long auditor_id,
            HttpServletRequest request) {
                if (functionalitiesService.verifyPermission(request,
		"get_drafted_atm_card_auditor_bfa")) {
        try {
            return new ResponseEntity<>(atmCardService.getATMAuditsOnDraftingBranch(auditor_id), HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteAtmFinding(@PathVariable("id") Long id, HttpServletRequest request) {
        if (functionalitiesService.verifyPermission(request,
		"delete_atm_card_auditor_bfa")) {
        try {
            atmCardService.deleteAtmFinding(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

    }

    @PostMapping("/delete/selected")
    public ResponseEntity<HttpStatus> deleteSelectedAtmFinding(
            @RequestBody List<BranchFinancialAudit> branchFinancialAudits,
            HttpServletRequest request) {
                if (functionalitiesService.verifyPermission(request,
		"delete_selected_atm_card_auditor_bfa")) {
        try {
            for (BranchFinancialAudit branchFinancialAudit : branchFinancialAudits) {
                atmCardService.deleteAtmFinding(branchFinancialAudit.getId());
            }
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

    }

    @GetMapping("/pass/{id}")
    public ResponseEntity<HttpStatus> passATMFinding(@PathVariable("id") Long id,
            HttpServletRequest request) {
                if (functionalitiesService.verifyPermission(request,
		"pass_atm_card_auditor_bfa")) {
        try {
            atmCardService.passATMFinding(id);
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
    public ResponseEntity<HttpStatus> passSelectedATMFinding(
            @RequestBody List<BranchFinancialAudit> branchFinancialAudits,
            HttpServletRequest request) {
                if (functionalitiesService.verifyPermission(request,
                "pass_selected_atm_card_auditor_bfa")) {
        try {
            for (BranchFinancialAudit branchFinancialAudit : branchFinancialAudits) {
                atmCardService.passATMFinding(branchFinancialAudit.getId());
            }
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

    }

    @GetMapping("back/{id}")
    public ResponseEntity<HttpStatus> backATMFinding(@PathVariable("id") Long id,
            HttpServletRequest request) {
                if (functionalitiesService.verifyPermission(request,
                "back_atm_card_auditor_bfa")) {
        try {
            atmCardService.backATMFinding(id);
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
    public ResponseEntity<HttpStatus> backSelectedATMFinding(
            @RequestBody List<BranchFinancialAudit> branchFinancialAudits,
            HttpServletRequest request) {
                if (functionalitiesService.verifyPermission(request,
                "back_selected_atm_card_auditor_bfa")) {
        try {
            for (BranchFinancialAudit branchFinancialAudit : branchFinancialAudits) {
                atmCardService.backATMFinding(branchFinancialAudit.getId());
            }
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
    public ResponseEntity<List<BranchFinancialAudit>> getATMPassedAuditsForAuditor(@PathVariable Long auditor_id,
            HttpServletRequest request) {
                if (functionalitiesService.verifyPermission(request,
                "get_passed_atm_card_auditor_bfa")) {
        try {
            return new ResponseEntity<>(atmCardService.getATMPassedAuditsForAuditor(auditor_id), HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

}
}