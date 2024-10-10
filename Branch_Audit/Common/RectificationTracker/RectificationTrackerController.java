package com.afr.fms.Branch_Audit.Common.RectificationTracker;

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
import com.afr.fms.Payload.endpoint.Endpoint;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Common.Service.FunctionalitiesService;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")

@RestController
@RequestMapping("/api/branch_audit/rectification_tracker/")
// @PreAuthorize("hasAnyRole('MAKER','APPROVER')")
public class RectificationTrackerController {

    @Autowired
    RectificationTrackerService rectificationTrackerService;

    @Autowired
    private FunctionalitiesService functionalitiesService;

    @PostMapping("/unseenRectifiedAudits")
    private ResponseEntity<List<BranchFinancialAudit>> getUseenRectifiedAudits(
            @RequestBody BranchFinancialAudit branchFinancialAudit, HttpServletRequest request) {
        // if (functionalitiesService.verifyPermission(request, "view_unseen_remarks"))
        // {

        try {
            return new ResponseEntity<>(
                    rectificationTrackerService.getUnseenRectificationTrackers(branchFinancialAudit), HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // } else {
        // return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        // }

    }

    @PostMapping("/seenRectifiedAudits")
    private ResponseEntity<HttpStatus> seenRemark(@RequestBody List<BranchFinancialAudit> branchFinancialAudits,
            HttpServletRequest request) {
        try {
            rectificationTrackerService.seenRectificationTracker(branchFinancialAudits);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addRemark")
    private ResponseEntity<HttpStatus> addRectificationTracker(
            @RequestBody List<BranchFinancialAudit> branchFinancialAudits, HttpServletRequest request) {
        // if (functionalitiesService.verifyPermission(request, "add_remarks")) {

        try {
            rectificationTrackerService.addRectificationTracker(branchFinancialAudits);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // } else {
        // return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        // }
    }

}
