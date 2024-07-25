package com.afr.fms.Branch_Audit.Reviewer.Controller;

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

import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Reviewer.Service.GeneralObservationReviewerService;
import com.afr.fms.Common.Service.FunctionalitiesService;
import com.afr.fms.Payload.endpoint.Endpoint;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/branch/reviewer/general-observation")
@PreAuthorize("hasRole('REVIEWER_BFA')")
public class GeneralObservationReviewerController {

    @Autowired
    private GeneralObservationReviewerService reviewerService;


    @Autowired
	private FunctionalitiesService functionalitiesService;

  
	@PostMapping("/pending")
    public ResponseEntity<List<BranchFinancialAudit>> getPendingGeneralObservation(@RequestBody User user,
            HttpServletRequest request) {
        if (functionalitiesService.verifyPermission(request,
        "get_pending_general_observation_reviewer_bfa")) {
        try {
            return new ResponseEntity<>(reviewerService.getPendingGeneralObservation(user), HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        } else {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


    @GetMapping("/reviewed/{reviewer_id}")
    public ResponseEntity<List<BranchFinancialAudit>> getReviewedGeneralObservationForReviewer(
            @PathVariable Long reviewer_id,
            HttpServletRequest request) {
        if (functionalitiesService.verifyPermission(request,
        "get_reviewed_general_observation_reviewer_bfa")) {
        try {
            return new ResponseEntity<>(reviewerService.getReviewedGeneralObservationForReviewer(reviewer_id),
                    HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        } else {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


}
