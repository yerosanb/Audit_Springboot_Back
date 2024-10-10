package com.afr.fms.Branch_Audit.Common.Audit_Remark;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Common.Service.FunctionalitiesService;
import com.afr.fms.Payload.endpoint.Endpoint;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/remark_branch_audit")
public class RemarkBranchAuditController {

    @Autowired
    RemarkBranchAuditService remarkService;

    @Autowired
    private FunctionalitiesService functionalitiesService;

    @PostMapping("/getRemarks")
    private ResponseEntity<List<RemarkBranchAudit>> getRemarks(@RequestBody RemarkBranchAudit remark, HttpServletRequest request) {
        if (functionalitiesService.verifyPermission(request, "get_remarks_branch_audit_bfa")) {
            try {
                return new ResponseEntity<>(remarkService.getRemarks(remark), HttpStatus.OK);
            } catch (Exception ex) {
                System.out.println(ex);
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        }
    } 
    
    @PostMapping("/getAllUnseenRemarks")
    private ResponseEntity<List<RemarkBranchAudit>> getAllUnseenRemarks(@RequestBody RemarkBranchAudit remark, HttpServletRequest request) {
        // if (functionalitiesService.verifyPermission(request, "view_remarks")) {

            try {
                return new ResponseEntity<>(remarkService.getAllUnseenRemarks(remark), HttpStatus.OK);
            } catch (Exception ex) {
                System.out.println(ex);
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        // } else {
        //     return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        // }
    }

    @GetMapping("/user/{category}")
    private ResponseEntity<List<User>> getUsersByCategory(@PathVariable String category, HttpServletRequest request) {
        if (functionalitiesService.verifyPermission(request, "get_user_branch_audit_bfa")) {
        try {
            return new ResponseEntity<>(remarkService.getUserByCategory(category), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        }else{
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    }

    @PostMapping("/getUnseenRemarks")
    private ResponseEntity<List<RemarkBranchAudit>> getUnseenRemarks(@RequestBody RemarkBranchAudit remark, HttpServletRequest request) {
        if (functionalitiesService.verifyPermission(request, "get_unseen_remarks_branch_audit_bfa")) {
            try {
                return new ResponseEntity<>(remarkService.getUnseenRemarks(remark), HttpStatus.OK);
            } catch (Exception ex) {
                System.out.println(ex);
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    } 
    

    @PostMapping("/getUnseenRemarksUnassignedApprover")
    private ResponseEntity<List<RemarkBranchAudit>> getUnseenRemarksUnassignedApprover(@RequestBody RemarkBranchAudit remark,
            HttpServletRequest request) {
        if (functionalitiesService.verifyPermission(request, "get_unseen_remarks_approver_branch_audit_bfa")) {
            try {
                return new ResponseEntity<>(remarkService.getUnseenRemarksUnassignedApprover(remark), HttpStatus.OK);
            } catch (Exception ex) {
                System.out.println(ex);
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        }

    }

    @PostMapping("/seenRemark")
    private ResponseEntity<HttpStatus> seenRemark(@RequestBody RemarkBranchAudit remark, HttpServletRequest request) {
        if (functionalitiesService.verifyPermission(request, "seen_remark_branch_audit_bfa")) {
        try {
            remarkService.seenRemark(remark);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } else {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    }

    @PostMapping("/setReciever")
    private ResponseEntity<HttpStatus> setReciever(@RequestBody List<RemarkBranchAudit> remarks, HttpServletRequest request) {
        if (functionalitiesService.verifyPermission(request, "set_reciever_branch_audit_bfa")) {
        try {
            remarkService.setReciever(remarks);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } else {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    }

    @PostMapping("/addRemark")
    private ResponseEntity<HttpStatus> addRemark(@RequestBody RemarkBranchAudit remark, HttpServletRequest request) {
        if (functionalitiesService.verifyPermission(request, "add_remarks_branch_audit_bfa")) {
            try {
                remarkService.addRemark(remark);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception ex) {
                System.out.println(ex);
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

   
}
