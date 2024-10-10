package com.afr.fms.Branch_Audit.Auditor.Controller;

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

import com.afr.fms.Branch_Audit.Auditor.Service.GeneralObservationAndCommentService;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Common.Service.FunctionalitiesService;
import com.afr.fms.Payload.endpoint.Endpoint;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/branch_audit/auditor/general_observation")
@PreAuthorize("hasRole('AUDITOR_BFA')")
public class GeneralObservationAndCommentController {

    @Autowired
    private GeneralObservationAndCommentService generalOBService;

    @Autowired
	private FunctionalitiesService functionalitiesService;

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createGeneralObservationAndComment(
            @RequestBody BranchFinancialAudit generalObservation, HttpServletRequest request) {
        if (functionalitiesService.verifyPermission(request,
        "create_or_update_general_observation_auditor_bfa")) {
        try {
            if (generalObservation.getId() != null) {
                generalOBService.updateGeneralObservationAndComment(generalObservation);
            } else {

                generalOBService.createGeneralObservationAndComment(generalObservation);
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

    @GetMapping("/drafted/{auditor_id}")
    public ResponseEntity<List<BranchFinancialAudit>> getDraftedGeneralObservationAndComment(
            @PathVariable Long auditor_id,
            HttpServletRequest request) {
        if (functionalitiesService.verifyPermission(request,
        "get_drafted_general_observation_auditor_bfa")) {
        try {
            return new ResponseEntity<>(generalOBService.getDraftedGeneralObservationAndComment(auditor_id),
                    HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        } else {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<HttpStatus> deleteSingleGeneralObservationAndComment(
            @RequestBody BranchFinancialAudit branchFinancialAudit,
            HttpServletRequest request) {
                if (functionalitiesService.verifyPermission(request,
                "delete_general_observation_auditor_bfa")) {
        try {
            generalOBService.deleteSingleGeneralObservationAndComment(branchFinancialAudit.getId());
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
    public ResponseEntity<HttpStatus> deleteSelectedGeneralObservation(
            @RequestBody List<BranchFinancialAudit> branchFinancialAudits,
            HttpServletRequest request) {
                if (functionalitiesService.verifyPermission(request,
                "delete_selected_general_observation_auditor_bfa")) {
        try {
            generalOBService.deleteSelectedGeneralObservation(branchFinancialAudits);
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
    public ResponseEntity<HttpStatus> passSelectedGeneralObservation(
            @RequestBody List<BranchFinancialAudit> branchFinancialAudit,
            HttpServletRequest request) {
                if (functionalitiesService.verifyPermission(request,
                "pass_selected_general_observation_auditor_bfa")) {
        try {
            generalOBService.passSelectedGeneralObservation(branchFinancialAudit);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } else {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping("/pass")
    public ResponseEntity<HttpStatus> passSingleGeneralObservation(
            @RequestBody BranchFinancialAudit branchFinancialAudit,
            HttpServletRequest request) {
                if (functionalitiesService.verifyPermission(request,
                "pass_general_observation_auditor_bfa")) {
        try {
            generalOBService.passSingleGeneralObservation(branchFinancialAudit);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } else {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping("/back")
    public ResponseEntity<HttpStatus> backPassedGeneralObservation(
            @RequestBody BranchFinancialAudit branchFinancialAudit,
            HttpServletRequest request) {
                if (functionalitiesService.verifyPermission(request,
                "back_general_observation_auditor_bfa")) {
        try {
            generalOBService.backPassedGeneralObservation(branchFinancialAudit.getId());
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } else {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping("/back/selected")
    public ResponseEntity<HttpStatus> backMultiPassedGeneralObservation(
            @RequestBody List<BranchFinancialAudit> inspectionAudits,
            HttpServletRequest request) {
                if (functionalitiesService.verifyPermission(request,
                "back_selected_general_observation_auditor_bfa")) {
        try {
            generalOBService.backMultiPassedGeneralObservation(inspectionAudits);
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
    public ResponseEntity<List<BranchFinancialAudit>> getPassedGeneralObservation(@PathVariable Long auditor_id,
            HttpServletRequest request) {
        // if (functionalitiesService.verifyPermission(request,
        // "get_ISM_findings_auditor")) {
        try {
            return new ResponseEntity<>(generalOBService.getPassedGeneralObservation(auditor_id), HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // } else {
        // return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        // }
    }

}
