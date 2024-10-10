package com.afr.fms.Branch_Audit.Common.OperationalDescripancyPooledData;

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
@RequestMapping("/api/branch_audit/data_pooling/")
// @PreAuthorize("hasAnyRole('MAKER','APPROVER')")
public class OperationalDescripancyPooledDataController {

    @Autowired
    OperationalDescripancyPooledDataService oDescripancyPooledDataService;

    @Autowired
    private FunctionalitiesService functionalitiesService;

    @PostMapping("/getPooledData")
    private ResponseEntity<List<BranchFinancialAudit>> getPooledData(
            @RequestBody OperationalDescripancyPooledData pooler, HttpServletRequest request) {
        // if (functionalitiesService.verifyPermission(request, "view_unseen_remarks"))
        // {

        try {
            return new ResponseEntity<>(
                    oDescripancyPooledDataService.getPooledData(pooler), HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // } else {
        // return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        // }

    }

    @PostMapping("/deletePooledData")
    private ResponseEntity<HttpStatus> deletePooledData(@RequestBody List<BranchFinancialAudit> branchFinancialAudits,
            HttpServletRequest request) {
        try {
            oDescripancyPooledDataService.deletePooledData(branchFinancialAudits);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addToPool")
    private ResponseEntity<HttpStatus> addToPool(
            @RequestBody OperationalDescripancyPooledData poolData, HttpServletRequest request) {
        // if (functionalitiesService.verifyPermission(request, "add_remarks")) {

        try {
            oDescripancyPooledDataService.addToPool(poolData);
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
