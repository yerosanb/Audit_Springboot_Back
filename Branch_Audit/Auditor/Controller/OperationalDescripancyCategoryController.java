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

import com.afr.fms.Branch_Audit.Auditor.Service.OperationalDescripancyCategoryService;
import com.afr.fms.Branch_Audit.Entity.OperationalDescripancyCategory;
import com.afr.fms.Payload.endpoint.Endpoint;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/branch/auditor/operational_descripancy_category")
// @PreAuthorize("hasRole('COMPILER_BFA')")
public class OperationalDescripancyCategoryController {

	@Autowired

	private OperationalDescripancyCategoryService operationalDescripancyCategoryService;

	@PostMapping("/create")
	public ResponseEntity<HttpStatus> createOperationaDescripancyCategory(
			@RequestBody OperationalDescripancyCategory operationalDescripancyCategory,
			HttpServletRequest request) {
		// if (functionalitiesService.verifyPermission(request,
		// "create_or_update_ISM_finding")) {
		try {
			if (operationalDescripancyCategory.getId() != null) {
				operationalDescripancyCategoryService
						.updateOperationalDescripancyCategory(operationalDescripancyCategory);
			} else {
				operationalDescripancyCategoryService.createDescripancyCategory(operationalDescripancyCategory);
			}
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// } else {
		// return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		// }

	}

	@GetMapping("/descripancy_category")
	public ResponseEntity<List<OperationalDescripancyCategory>> getOperationalDescripancyCategory(
			HttpServletRequest request) {
		// if (functionalitiesService.verifyPermission(request,
		// "get_ISM_findings_auditor")) {
		try {
			return new ResponseEntity<>(operationalDescripancyCategoryService.getOperationalDescripancyCategory(),
					HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// } else {
		// return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		// }
	}

	@GetMapping("/descripancy_category/{category}")

	public ResponseEntity<List<OperationalDescripancyCategory>> getOperationalDescripancyCategoryByCategory(
			@PathVariable String category,
			HttpServletRequest request) {
		// if (functionalitiesService.verifyPermission(request,
		// "get_ISM_findings_auditor")) {
		try {
			return new ResponseEntity<>(
					operationalDescripancyCategoryService.getOperationalDescripancyCategoryByCategory(category),
					HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// } else {
		// return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		// }
	}

	@PostMapping("/delete")
	public ResponseEntity<HttpStatus> deleteSingle(
			@RequestBody OperationalDescripancyCategory operationalDescripancyCategory,
			HttpServletRequest request) {
		try {
			operationalDescripancyCategoryService.deleteOperationalDescripancyCategory(operationalDescripancyCategory);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

}
