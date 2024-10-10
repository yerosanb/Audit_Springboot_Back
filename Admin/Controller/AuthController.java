package com.afr.fms.Admin.Controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.afr.fms.Admin.Entity.Role;
import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Admin.Mapper.JobPositionMapper;
import com.afr.fms.Admin.Mapper.UserMapper;
import com.afr.fms.Admin.Mapper.UserTrackerMapper;
import com.afr.fms.Admin.Service.RoleService;
import com.afr.fms.Admin.Service.UserService;
import com.afr.fms.Admin.utilis.HttpUtils;
import com.afr.fms.Common.Service.FunctionalitiesService;
import com.afr.fms.Payload.endpoint.Endpoint;
import com.afr.fms.Payload.request.LoginRequest;
import com.afr.fms.Payload.response.MessageResponse;
import com.afr.fms.Payload.response.UserInfoResponse;
import com.afr.fms.Security.UserDetailsImpl;
import com.afr.fms.Security.UserSecurity.entity.RefreshToken;
import com.afr.fms.Security.UserSecurity.entity.UserSecurity;
import com.afr.fms.Security.UserSecurity.exception.TokenRefreshException;
import com.afr.fms.Security.UserSecurity.service.RefreshTokenService;
import com.afr.fms.Security.UserSecurity.service.UserSecurityService;
import com.afr.fms.Security.jwt.JwtUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

        private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

        @Autowired
        AuthenticationManager authenticationManager;

        @Autowired
        PasswordEncoder encoder;

        @Autowired
        UserService userService;

        @Autowired
        RoleService roleService;

        @Autowired
        UserSecurityService userSecurityService;

        @Autowired
        JwtUtils jwtUtils;

        @Autowired
        private FunctionalitiesService functionalitiesService;

        @Autowired
        RefreshTokenService refreshTokenService;

        @Autowired
        private UserTrackerMapper userTrakerMapper;

        @Autowired
        private JobPositionMapper jobPositionMapper;

        @Autowired
        private UserMapper userMapper;

        @PostMapping("/signin")
        public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest,
                        HttpServletRequest request) {

                if (functionalitiesService.processVerfyingPermission(loginRequest.getUsername(), "authenticate_user")) {
                        // checking whether the user credentials are expired or not.
                        try {
                                User user = userService.findUserByEmail(loginRequest.getUsername());
                                if (user != null) {
                                        UserSecurity us = user.getUser_security();
                                        userSecurityService.checkCredentialTimeExpired(us);
                                }
                        } catch (Exception e) {

                                logger.info("Checking the user credential expiration is failed !");
                        }

                        System.out.println(encoder.encode("admin"));
                        Authentication authentication = authenticationManager
                                        .authenticate(new UsernamePasswordAuthenticationToken(
                                                        loginRequest.getUsername(),
                                                        loginRequest.getPassword()));

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

                        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails, request);

                        List<String> roles = userDetails.getAuthorities().stream()
                                        .map(item -> item.getAuthority())
                                        .collect(Collectors.toList());

                        String ip_address = HttpUtils.clientIp(request);

                        Long id_login_tracker = userTrakerMapper.registerOnlineUser(loginRequest.getUsername(),
                                        loginRequest.getUserAgent(),
                                        ip_address);
                        String title = userMapper.findByEmail(loginRequest.getUsername()).getJobPosition().getTitle();
                        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId(),
                                        id_login_tracker);

                        ResponseCookie jwtRefreshCookie = jwtUtils.generateRefreshJwtCookie(refreshToken.getToken(), request);
                        return ResponseEntity.ok()
                                        .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                                        .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                                        .body(new UserInfoResponse(userDetails.getId(),
                                                        id_login_tracker,
                                                        userDetails.getUsername(),
                                                        userDetails.getEmail(),
                                                        userDetails.getPhotoUrl(),
                                                        title,
                                                        userDetails.getCategory(),
                                                        userDetails.getBranch(),
                                                        userDetails.getRegion(),
                                                        userDetails.geting(),
                                                        roles));
                } else {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                        .body(new MessageResponse("You have no permission!"));
                }
        }

        @GetMapping("/signout/{id_login_tracker}")
        public ResponseEntity<?> logoutUser(@PathVariable("id_login_tracker") Long id_login_tracker,
                        HttpServletRequest request) {
                Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if (principle.toString() != "anonymousUser") {
                        Long userId = ((UserDetailsImpl) principle).getId();

                        // delete the current user refresh token ...
                        refreshTokenService.deleteByUserId(userId);
                }

                ResponseCookie jwtCookie = jwtUtils.getCleanJwtCookie(request);
                ResponseCookie jwtRefreshCookie = jwtUtils.getCleanJwtRefreshCookie(request);
                userTrakerMapper.registerOfflineUser(id_login_tracker);

                return ResponseEntity.ok()
                                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                                .body(new MessageResponse("You've been signed out!"));

        }

        @PostMapping("/refreshtoken")
        public ResponseEntity<?> refreshtoken(HttpServletRequest request) {
                String refreshToken = jwtUtils.getJwtRefreshFromCookies(request);
                if ((refreshToken != null) && (refreshToken.length() > 0)) {
                        return refreshTokenService.findByToken(refreshToken)
                                        .map(refreshTokenService::verifyExpiration)
                                        .map(RefreshToken::getUser)
                                        .map(user -> {
                                                user.setUsername(user.getEmail());
                                                ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(user, request);

                                                return ResponseEntity.ok()
                                                                .header(HttpHeaders.SET_COOKIE,
                                                                                jwtCookie.toString())

                                                                .body(new MessageResponse(
                                                                                "JWT is refreshed successfully!"));
                                        })
                                        .orElseThrow(() -> new TokenRefreshException(refreshToken,
                                                        "Refresh token is not in database!"));
                }

                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                .body(new MessageResponse("Refresh Token is empty!"));

        }

        @PostMapping("/signup")
        public ResponseEntity<?> signup(@RequestBody User user) {
                try {
                        List<Role> roles = jobPositionMapper.getRoleByJobPositionId(user.getJobPosition().getId(),
                                        user.getCategory());
                        user.setRoles(roles);
                        Exception e = userService.saveUser(user);
                        if (e == null) {
                                return new ResponseEntity<>(HttpStatus.ACCEPTED);
                        } else {
                                System.out.println(e);
                                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                        }
                } catch (Exception e) {
                        System.out.println(e);
                        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
        }

}
