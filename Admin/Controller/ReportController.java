package com.afr.fms.Admin.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afr.fms.Admin.Entity.AdminReport;
import com.afr.fms.Admin.Entity.UserTracker;
import com.afr.fms.Admin.Mapper.UserTrackerMapper;
import com.afr.fms.Admin.Service.RegionService;
import com.afr.fms.Admin.Service.ReportService;
import com.afr.fms.Common.Service.FunctionalitiesService;
import com.afr.fms.Payload.endpoint.Endpoint;
import com.afr.fms.Security.UserSecurity.service.RefreshTokenService;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")

@RestController
@RequestMapping("/api")
// @PreAuthorize("hasRole('ADMIN')")
public class ReportController {
    @Autowired
    private RegionService regionService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private UserTrackerMapper userTrakerMapper;

    @Autowired
    private FunctionalitiesService functionalitiesService;

    @GetMapping("/region/branch_per_region")
    public ResponseEntity<List<Object>> drawBranchPerRegionLineChart() {
        try {
            return new ResponseEntity<>(regionService.drawBranchPerRegionLineChart(), HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/user_per_region")
    public ResponseEntity<List<AdminReport>> drawBarChartUsersPerRegion() {
        try {
            return new ResponseEntity<>(reportService.drawBarChartUsersPerRegion(), HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/login_status")
    public ResponseEntity<List<UserTracker>> getOnlineOfflineUsers(HttpServletRequest request) {
        if (functionalitiesService.verifyPermission(request, "view_login_status")) {

        try {
            refreshTokenService.verifyOnlineUsers();
            List<UserTracker> userTracker = userTrakerMapper.getOnlineFailedUsers();
            return new ResponseEntity<>(userTracker, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        } else {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping("/login_status")
    public ResponseEntity<HttpStatus> updateLoginStatus(HttpServletRequest request,
            @RequestBody List<UserTracker> userTracker) {
        // if (functionalitiesService.verifyPermission(request, "view_login_status")) {

        try {
            reportService.updateLoginStatus(userTracker);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // } else {
        // return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        // }

    }
}
