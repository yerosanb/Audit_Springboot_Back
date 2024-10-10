package com.afr.fms.Common.Recommendation;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.afr.fms.Common.RecentActivity.RecentActivity;
import com.afr.fms.Common.RecentActivity.RecentActivityMapper;
import com.afr.fms.Common.Service.FunctionalitiesService;
import com.afr.fms.Payload.endpoint.Endpoint;
import com.afr.fms.Payload.response.AGPResponse;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/audit/common")
// @PreAuthorize("hasRole('ADMIN')")
public class RecommendationController {

	@Autowired
	private RecommendationService recommendationService;

	@Autowired
	private RecentActivityMapper recentActivityMapper;

	@Autowired
	private FunctionalitiesService functionalitiesService;

	RecentActivity recentActivity = new RecentActivity();

	@PostMapping(path = "/recommendation")
	public ResponseEntity<AGPResponse> createRecommendation(HttpServletRequest request,
			@RequestBody Recommendation recommendation) {
		try {
			if (recommendation.getId() != null) {

				recommendationService.updateRecommendation(recommendation);
				return AGPResponse.success("SUCCESS");
			} else {

				recommendationService.createRecommendation(recommendation);
				return AGPResponse.success("SUCCESS");
			}

		} catch (Exception ex) {
			System.out.println(ex);

			return AGPResponse.error("Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/recommendation/{user_id}")
	public ResponseEntity<List<Recommendation>> getRecommendation(@PathVariable Long user_id,
			HttpServletRequest request) {
		try {
			return new ResponseEntity<>(recommendationService.getRecommendation(user_id), HttpStatus.OK);
		} catch (Exception ex) {

			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/recommendation")
	public ResponseEntity<List<Recommendation>> getRecommendations(HttpServletRequest request) {
		try {
			return new ResponseEntity<>(recommendationService.getRecommendations(), HttpStatus.OK);
		} catch (Exception ex) {

			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/recommendations/{audit_type}")
	public ResponseEntity<List<Recommendation>> getCommonRecommendationsByAuditType(@PathVariable String audit_type,
			HttpServletRequest request) {
		try {
			return new ResponseEntity<>(recommendationService.getRecommendationsByAuditType(audit_type),
					HttpStatus.OK);
		} catch (Exception ex) {

			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<HttpStatus> deleteRecommendation(@PathVariable Long id,
			HttpServletRequest request) {
		try {
			recommendationService.deleteRecommendation(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception ex) {

			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/delete/recommendations")
	public ResponseEntity<HttpStatus> deleteRecommendations(@RequestBody List<Recommendation> recommendations,
			HttpServletRequest request) {
		// if (functionalitiesService.verifyPermission(request,
		// "delete_Common_Finding")) {
		try {
			for (Recommendation recommendation : recommendations) {
				recommendationService.deleteRecommendation(recommendation.getId());
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
