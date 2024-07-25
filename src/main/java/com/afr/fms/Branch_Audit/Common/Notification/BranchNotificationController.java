package com.afr.fms.Branch_Audit.Common.Notification;

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
import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Entity.CompiledBranchAudit;
import com.afr.fms.Common.Service.FunctionalitiesService;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")

@RestController
@RequestMapping("/api/branch_audit/audit_notification/")
// @PreAuthorize("hasAnyRole('MAKER','APPROVER')")
public class BranchNotificationController {

    @Autowired
    BranchNotificationService rectificationTrackerService;

    @Autowired
    private FunctionalitiesService functionalitiesService;

    @PostMapping("/notification_r_m")
    private ResponseEntity<List<BranchFinancialAudit>> getPendingAuditsNotification(
            @RequestBody User user, HttpServletRequest request) {
        // if (functionalitiesService.verifyPermission(request, "view_unseen_remarks"))
        // {

        try {
            return new ResponseEntity<>(
                    rectificationTrackerService.getPendingAuditsNotification(user), HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // } else {
        // return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        // }

    }


    @PostMapping("/remark_notification")
    private ResponseEntity<List<BranchFinancialAudit>> getBranchRemarkAuditsNotification(
            @RequestBody User user, HttpServletRequest request) {
        // if (functionalitiesService.verifyPermission(request, "view_unseen_remarks"))
        // {

        try {
            return new ResponseEntity<>(
                    rectificationTrackerService.getBranchRemarkAuditsNotification(user), HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // } else {
        // return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        // }

    }

    @PostMapping("/notification_compiler")
    private ResponseEntity<List<CompiledBranchAudit>> getPendingAuditsNotificationCompiler(
            @RequestBody User user, HttpServletRequest request) {
        // if (functionalitiesService.verifyPermission(request, "view_unseen_remarks"))
        // {

        try {
            return new ResponseEntity<>(
                    rectificationTrackerService.getPendingAuditsNotificationCompiler(user), HttpStatus.OK);
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
