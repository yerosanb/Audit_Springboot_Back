// package com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit;

// import java.util.List;

// import javax.servlet.http.HttpServletRequest;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
// import com.afr.fms.Payload.endpoint.Endpoint;

// @CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")
// @RestController
// @RequestMapping("/api/branch_audit")
// public class ChangeTrackerBranchAuditController {

// 	@Autowired
// 	ChangeTrackerBranchAuditService changeTrackerBranchAuditService;

// 	@PostMapping("/change")
// 	public ResponseEntity<HttpStatus> insertChange(@RequestBody ChangeTrackerBranchAudit changeTrackerBranchAudit,
// 			HttpServletRequest request) {
// 		try {
// 			changeTrackerBranchAuditService.insertChanges(changeTrackerBranchAudit);
// 			return new ResponseEntity<>(HttpStatus.OK);
// 		} catch (Exception ex) {
// 			System.out.println(ex);
// 			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
// 		}
// 	}

// 	@GetMapping("/change/{audit_id}")
// 	public ResponseEntity<List<ChangeTrackerBranchAudit>> getChanges(@PathVariable Long audit_id,
// 			HttpServletRequest request) {
// 		// if (functionalitiesService.verifyPermission(request, "get_edit_history")) {
// 		try {
// 			return new ResponseEntity<>(changeTrackerBranchAuditService.getChanges(audit_id), HttpStatus.OK);
// 		} catch (Exception ex) {
// 			System.out.println(ex);
// 			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
// 		}
// 		// } else {
// 		// return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
// 		// }
// 	}

// }
