package com.afr.fms.Common.FraudCase;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afr.fms.Common.Service.FunctionalitiesService;
import com.afr.fms.Payload.endpoint.Endpoint;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class FraudCaseController {

	@Autowired
	FraudCaseService fraudCaseService;

	@Autowired
	private FunctionalitiesService functionalitiesService;

	@PostMapping("/fraudcase")
	public ResponseEntity<HttpStatus> createFraudCase(@RequestBody FraudCase fraudcase, HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "create_Fraud_Case")) {
			try {

				if (fraudcase.getId() != null && fraudcase.getCreated_date() != null) {
					fraudCaseService.updateFraudCase(fraudcase);
				} else {
					fraudCaseService.createFraudCase(fraudcase);
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

	@GetMapping("/fraudcase/{initial}")
	public ResponseEntity<FraudCase> getFraudCase(HttpServletRequest request, @PathVariable Long initial) {
		if (functionalitiesService.verifyPermission(request, "get_Fraud_Case")) {

			try {

				return new ResponseEntity<>(fraudCaseService.getInitialFraudCase(initial), HttpStatus.OK);
			} catch (Exception ex) {
				System.out.println(ex);
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/fraudcase")
	public ResponseEntity<List<FraudCase>> getFraudCases(HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "get_Fraud_Cases")) {
			try {

				return new ResponseEntity<>(fraudCaseService.getFraudCases(), HttpStatus.OK);
			} catch (Exception ex) {
				System.out.println(ex);
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@PutMapping("/approve/fraudcase")
	public ResponseEntity<HttpStatus> approveFraudCase(@RequestBody FraudCase fraudcase, HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "approve_Fraud_Case")) {
			try {

				fraudCaseService.approveFraudCase(fraudcase);

				return new ResponseEntity<>(HttpStatus.OK);
			} catch (Exception ex) {
				System.out.println(ex);
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

	}

	@PutMapping("/cancel/fraudcase")
	public ResponseEntity<HttpStatus> cancelApprovedFraudCase(@RequestBody FraudCase fraudcase,
			HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "cancel_Approved_Fraud_Case")) {
			try {

				fraudCaseService.cancelApprovedFraudCase(fraudcase);

				return new ResponseEntity<>(HttpStatus.OK);
			} catch (Exception ex) {
				System.out.println(ex);
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

	}

	@GetMapping("/fraudcase/pending/{initial}")
	public ResponseEntity<List<FraudCase>> getPendingFraudCase(HttpServletRequest request, @PathVariable Long initial) {
		if (functionalitiesService.verifyPermission(request, "get_Pending_Fraud_Case")) {
			System.out.println("user id =" + initial);

			try {
				System.out.println(fraudCaseService.getPendingFraudCase());

				return new ResponseEntity<>(fraudCaseService.getPendingFraudCase(), HttpStatus.OK);
			} catch (Exception ex) {
				System.out.println(ex);
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/approved/fraudcase/{user_id}")
	public ResponseEntity<List<FraudCase>> getApprovedFraudCase(HttpServletRequest request,
			@PathVariable Long user_id) {
		// System.out.println(user_id);
		if (functionalitiesService.verifyPermission(request, "get_Approved_Fraud_Case")) {
			try {

				return new ResponseEntity<>(fraudCaseService.getApprovedFraudCase(user_id), HttpStatus.OK);
			} catch (Exception ex) {
				System.out.println(ex);
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@DeleteMapping("/fraudcase/{id}")
	public ResponseEntity<HttpStatus> deleteFraudCase(HttpServletRequest request, @PathVariable Long id) {
		if (functionalitiesService.verifyPermission(request, "delete_Fraud_Case")) {

			try {
				fraudCaseService.deleteFraudCase(id);

				return new ResponseEntity<>(HttpStatus.OK);
			} catch (Exception ex) {
				System.out.println(ex);
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

}
