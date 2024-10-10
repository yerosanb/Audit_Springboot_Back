package com.afr.fms.Common.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.afr.fms.Admin.Entity.NotifyAdmin;
import com.afr.fms.Common.Service.FunctionalitiesService;
import com.afr.fms.Common.Service.NotificationService;
import com.afr.fms.Payload.endpoint.Endpoint;
import com.afr.fms.Payload.realtime.RealTime;
import com.afr.fms.Security.UserSecurity.service.UserSecurityService;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")

@RestController
@RequestMapping("/api")
@PreAuthorize("hasAnyRole('MAKER','APPROVER','ADMIN')")
public class NotificationController {

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private UserSecurityService userSecurityService;

	
	@Autowired
	private FunctionalitiesService functionalitiesService;

	// @PostMapping("/notify")
	// public ResponseEntity<List<Loan>> notify(@RequestBody RealTime realTime, HttpServletRequest request) {
	// 	if (functionalitiesService.verifyPermission(request, "notify")) {

	// 	try {
	// 		return new ResponseEntity<>(notificationService.notify(realTime), HttpStatus.OK);

	// 	} catch (Exception ex) {
	// 		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	// 	}
	// }else{
	// 	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	// }
	// }

	// @PostMapping("/notifyApprover")
	// public ResponseEntity<List<Loan>> notifyApprover(@RequestBody RealTime realTime, HttpServletRequest request) {
	// 	if (functionalitiesService.verifyPermission(request, "notify_approver")) {

	// 	try {
	// 		return new ResponseEntity<>(notificationService.notifyApprover(realTime), HttpStatus.OK);
	// 	} catch (Exception ex) {
	// 		System.out.println(ex);
	// 		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	// 	}
	// }else{
	// 	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	// }
	// }

	// @PostMapping("/viewedLoans")
	// public ResponseEntity<HttpStatus> notifyApprover(@RequestBody List<Loan> loans, HttpServletRequest request) {
	// 	// if (functionalitiesService.verifyPermission(request, "update_loan_purpose")) {

	// 	try {
	// 		notificationService.viewedLoans(loans);
	// 		return new ResponseEntity<>(HttpStatus.OK);
	// 	} catch (Exception ex) {
	// 		System.out.println(ex);
	// 		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	// 	// }
	// // }else{
	// 	// return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	// }
	// }

	@PostMapping("/notifyAdmin")
	public ResponseEntity<List<NotifyAdmin>> notifyAdmin(@RequestBody RealTime realtime, HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "notify_admin")) {

		try {
			return new ResponseEntity<>(userSecurityService.notifyAdmin(realtime), HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}else{
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	}

	@PostMapping("/viewedNotificationsByAdmin")
	public ResponseEntity<HttpStatus> viewedNotificationsByAdmin(@RequestBody List<NotifyAdmin> adminNotification, HttpServletRequest request) {
		// if (functionalitiesService.verifyPermission(request, "update_loan_purpose")) {

		try {
			userSecurityService.viewedNotificationsByAdmin(adminNotification);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	// }else{
		// return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	// }
	}
}
