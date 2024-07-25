package com.afr.fms.Common.Finding;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.afr.fms.Common.Service.FunctionalitiesService;
import com.afr.fms.Payload.endpoint.Endpoint;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")

@RestController
@RequestMapping("/api/audit/common")

public class CommonFindingController {
	@Autowired
	private CommonFindingService commonFindingService;

	@Autowired
	private FunctionalitiesService functionalitiesService;

	@GetMapping("/finding/{id}")
	public ResponseEntity<List<CommonFinding>> getCommonFinding(@PathVariable Long id,
			HttpServletRequest request) {
		// if (functionalitiesService.verifyPermission(request, "get_Common_Finding")) {
		try {
			return new ResponseEntity<>(commonFindingService.getCommonFinding(id), HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// } else {
		// return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		// }
	}

	@GetMapping("/finding")
	public ResponseEntity<List<CommonFinding>> getCommonFindings(
			HttpServletRequest request) {
		// if (functionalitiesService.verifyPermission(request, "get_Common_Findings"))
		// {
		try {
			return new ResponseEntity<>(commonFindingService.getCommonFindings(), HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// } else {
		// return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		// }
	}

	@GetMapping("/findings/{audit_type}")
	public ResponseEntity<List<CommonFinding>> getFindingByAuditType(@PathVariable String audit_type,
			HttpServletRequest request) {
		// if (functionalitiesService.verifyPermission(request, "get_Common_Findings"))
		// {
		try {
			return new ResponseEntity<>(commonFindingService.getFindingsByAuditType(audit_type), HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// } else {
		// return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		// }
	}

	@PostMapping("/finding")
	public ResponseEntity<HttpStatus> addCommonFinding(@RequestBody CommonFinding commonFinding,
			HttpServletRequest request) {
		// if (functionalitiesService.verifyPermission(request, "add_Common_Finding")) {
		try {
			if (commonFinding.getId() != null) {
				commonFindingService.updateCommonFinding(commonFinding);
			} else {
				commonFindingService.createCommonFinding(commonFinding);
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

	@DeleteMapping("/finding/{id}")
	public ResponseEntity<HttpStatus> deleteCommonFinding(@PathVariable Long id,
			HttpServletRequest request) {
		// if (functionalitiesService.verifyPermission(request,
		// "delete_Common_Finding")) {
		try {
			commonFindingService.deleteCommonFinding(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// } else {
		// return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		// }
	}

	@PostMapping("/delete/findings")
	public ResponseEntity<HttpStatus> deleteFindings(@RequestBody List<CommonFinding> findings,
			HttpServletRequest request) {
		// if (functionalitiesService.verifyPermission(request,
		// "delete_Common_Finding")) {
		try {
			for (CommonFinding commonFinding : findings) {
				commonFindingService.deleteCommonFinding(commonFinding.getId());
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

}
