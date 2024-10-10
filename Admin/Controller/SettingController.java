package com.afr.fms.Admin.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afr.fms.Admin.Entity.Setting;
import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Admin.Mapper.UserMapper;
import com.afr.fms.Admin.Service.SettingService;
import com.afr.fms.Common.Service.FunctionalitiesService;
import com.afr.fms.Payload.endpoint.Endpoint;
import com.afr.fms.Security.jwt.JwtUtils;
import com.nimbusds.oauth2.sdk.ParseException;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('ADMIN')")
public class SettingController {

    @Autowired
    private SettingService settingService;

    @Autowired
    private FunctionalitiesService functionalitiesService;

    @Autowired
    JwtUtils jwtUtils;

    private User user;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/setting/account")
    public ResponseEntity<?> manage_account_setting(HttpServletRequest request, @RequestBody Setting setting)
            throws ParseException {
        if (functionalitiesService.verifyPermission(request, "manage_account_setting")) {
            try {

                String jwt = jwtUtils.getJwtFromCookies(request);
                String username = jwtUtils.getUserNameFromJwtToken(jwt);
                user = userMapper.findByEmail(username);
                settingService.manage_account_setting(setting, user);
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            } catch (Exception ex) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        }
    }

    @PostMapping("/setting/jwt")
    public ResponseEntity<?> manage_JWT_setting(HttpServletRequest request, @RequestBody Setting setting)
            throws ParseException {
        if (functionalitiesService.verifyPermission(request, "manage_JWT_setting")) {
            try {
                String jwt = jwtUtils.getJwtFromCookies(request);
                String username = jwtUtils.getUserNameFromJwtToken(jwt);
                user = userMapper.findByEmail(username);
                settingService.manage_JWT_setting(setting, user);
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            } catch (Exception ex) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } 
        else
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/setting")
    public ResponseEntity<Setting> getSetting(HttpServletRequest request) {
        if (functionalitiesService.verifyPermission(request, "get_setting")) {
            try {
                return new ResponseEntity<>(settingService.getSetting(), HttpStatus.OK);
            } catch (Exception ex) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }

}
