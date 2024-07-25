package com.afr.fms.Admin.Controller;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.afr.fms.Admin.Entity.Branch;
import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Admin.Service.BranchService;
import com.afr.fms.Common.RecentActivity.RecentActivity;
import com.afr.fms.Common.RecentActivity.RecentActivityMapper;
import com.afr.fms.Common.Service.FunctionalitiesService;
import com.afr.fms.Payload.endpoint.Endpoint;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")

@RestController
@RequestMapping("/api")
// @PreAuthorize("hasRole('ADMIN')")
public class BranchController {

	@Autowired
	private BranchService branchService;
	@Autowired
	private RecentActivityMapper recentActivityMapper;

	@Autowired
	private FunctionalitiesService functionalitiesService;

	RecentActivity recentActivity = new RecentActivity();

	@GetMapping("/branch")
	public ResponseEntity<List<Branch>> getBranches(HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "fetch_all_branches")) {
			try {
				List<Branch> branches = branchService.getBranches();
				return new ResponseEntity<>(branches, HttpStatus.OK);
			} catch (Exception ex) {
				System.out.println(ex);
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/branch/active")
	public ResponseEntity<List<Branch>> getActiveBranches() {

		try {
			List<Branch> branches = branchService.getActiveBranches();
			return new ResponseEntity<>(branches, HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println(ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/branch/{id}")
	public ResponseEntity<Branch> getBranchById(@PathVariable("id") Long id, HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "get_branch_by_id")) {
			Branch branches = branchService.getBranchById(id);
			return new ResponseEntity<>(branches, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("/branch")
	public ResponseEntity<HttpStatus> saveBranch(@RequestBody Branch branch, HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "manage_branch")) {
			try {
				User user = new User();
				if (branch.getId() == null) {
					System.out.println("here");
					branchService.createBranch(branch);
					recentActivity.setMessage(branch.getName() + " branch is created ");
					user.setId(branch.getUser_id());
					recentActivity.setUser(user);
					recentActivityMapper.addRecentActivity(recentActivity);
				} else {
					branchService.updateBranch(branch);
					recentActivity.setMessage(branch.getName() + " branch info is updated ");
					user.setId(branch.getUser_id());
					recentActivity.setUser(user);
					recentActivityMapper.addRecentActivity(recentActivity);
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

	// @PutMapping("/branch")
	// public ResponseEntity<HttpStatus> updateBranch(@RequestBody Branch branch,
	// HttpServletRequest request) {
	// if (functionalitiesService.verifyPermission(request, "update_branch")) {
	// try {
	// User user = new User();
	// branchService.updateBranch(branch);
	// recentActivity.setMessage(branch.getName() + " branch info is updated ");
	// user.setId(branch.getUser_id());
	// recentActivity.setUser(user);
	// recentActivityMapper.addRecentActivity(recentActivity);
	// return new ResponseEntity<>(HttpStatus.OK);
	// } catch (Exception ex) {
	// System.out.println(ex);
	// return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	// }
	// } else {
	// return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	// }
	// }

	@GetMapping("/branch/search")
	public ResponseEntity<Branch> searchBranches(@RequestParam String searchKey) {
		branchService.searchBranch(searchKey);
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@PostMapping("/branch/delete")
	public ResponseEntity<Branch> deleteBranch(@RequestBody List<Branch> branches, HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "delete_branch")) {
			try {

				for (Branch branch2 : branches) {
					branchService.deleteBranch(branch2);
				}
				return new ResponseEntity<>(null, HttpStatus.OK);
			} catch (Exception ex) {
				System.out.println(ex);
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("/branch/activate")
	public ResponseEntity<Branch> activateBranch(@RequestBody List<Branch> branches, HttpServletRequest request) {
		if (functionalitiesService.verifyPermission(request, "activate_branch")) {
			try {
				for (Branch branch2 : branches) {
					branchService.activateBranch(branch2);
				}
				return new ResponseEntity<>(null, HttpStatus.OK);
			} catch (Exception ex) {
				System.out.println(ex);
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

}
