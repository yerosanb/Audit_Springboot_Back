
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

import com.afr.fms.Branch_Audit.Auditor.Service.ControllableExpenseTypeAuditorService;
import com.afr.fms.Branch_Audit.Entity.ControllableExpenseType;
import com.afr.fms.Common.RecentActivity.RecentActivity;
import com.afr.fms.Common.RecentActivity.RecentActivityMapper;
import com.afr.fms.Common.Service.FunctionalitiesService;
import com.afr.fms.Payload.endpoint.Endpoint;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/branch_audit/auditor/controllable_expense")
// @PreAuthorize("hasRole('COMPILER_BFA')")
public class ControllableExpenseTypeAuditorController {
    @Autowired
    private ControllableExpenseTypeAuditorService auditService;
    @Autowired
    private RecentActivityMapper recentActivityMapper;
    @Autowired
    private FunctionalitiesService functionalitiesService;
    RecentActivity recentActivity = new RecentActivity();

    @GetMapping("/controllable_type")
    public ResponseEntity<List<ControllableExpenseType>> getAuditsOnDrafting(HttpServletRequest request) {

        try {
            return new ResponseEntity<>(auditService.getControllableExpenseType(), HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createControllableExpenseType(@RequestBody ControllableExpenseType audit,
            HttpServletRequest request) {
        try {
            if (audit.getId() != null) {
                auditService.updateControllableExpenseType(audit);
            } else {
                auditService.createControllableExpenseType(audit);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteBAFinding(@PathVariable("id") Long id,
            HttpServletRequest request) {
        try {
            auditService.deleteBAFinding(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
