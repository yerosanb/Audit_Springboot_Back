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
import com.afr.fms.Branch_Audit.Reviewer.Service.NegotiableInstrumentReviewerService;
import com.afr.fms.Common.Service.FunctionalitiesService;
import com.afr.fms.Payload.endpoint.Endpoint;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/branch/reviewer/negotiable_instrument")
@PreAuthorize("hasRole('REVIEWER_BFA')")
public class NegotiableInstrumentReviewerController {

	@Autowired
	private NegotiableInstrumentReviewerService negotiableInstrumentReviewerService;

	@Autowired
	private FunctionalitiesService functionalitiesService;


	@PostMapping("/pending")
	public ResponseEntity<List<BranchFinancialAudit>> getPendingAudits(@RequestBody User user,
			HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request,
		"get_pending_negotiable_instrument_reviewer_bfa")) {
		try {
			return new ResponseEntity<>(negotiableInstrumentReviewerService.getPendingAudits(user), HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		} else {
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/reviewed/{reviewer_id}")
	public ResponseEntity<List<BranchFinancialAudit>> getReviewedFindingsForReviewer(@PathVariable Long reviewer_id,
			HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request,
		"get_reviewed_negotiable_instrument_reviewer_bfa")) {
		try {
			return new ResponseEntity<>(negotiableInstrumentReviewerService.getReviewedAudits(reviewer_id),
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
