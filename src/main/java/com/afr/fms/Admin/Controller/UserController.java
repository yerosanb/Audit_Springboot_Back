package com.afr.fms.Admin.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.afr.fms.Admin.Entity.Role;
import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Admin.Mapper.JobPositionMapper;
import com.afr.fms.Admin.Mapper.UserMapper;
import com.afr.fms.Admin.Service.UserService;
import com.afr.fms.Admin.utilis.ImageHandlerUtil;
import com.afr.fms.Admin.utilis.ImageService;
import com.afr.fms.Common.Service.FunctionalitiesService;
import com.afr.fms.Payload.endpoint.Endpoint;
import com.afr.fms.Payload.response.AGPResponse;
import com.afr.fms.Security.exception.NoUsersAvailableException;
import com.afr.fms.Security.exception.UserNotFoundException;
import com.afr.fms.Security.jwt.JwtUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")

@RestController
@RequestMapping("api")
// @PreAuthorize("hasAnyRole('MAKER','APPROVER','HO', 'ADMIN')")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private JobPositionMapper jobPositionMapper;

    @Autowired
    private FunctionalitiesService functionalitiesService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping("/user")
    public ResponseEntity<List<User>> get_users(HttpServletRequest request) throws NoUsersAvailableException {
        if (functionalitiesService.verifyPermission(request, "get_users")) {
            try {
                return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
            } catch (Exception ex) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    @GetMapping("/user_status")
    public ResponseEntity<List<User>> getUsersStatus(HttpServletRequest request) throws NoUsersAvailableException {
        // if (functionalitiesService.verifyPermission(request, "get_users_status")) {
            try {
                return new ResponseEntity<>(userService.getUsersStatus(), HttpStatus.OK);
            } catch (Exception ex) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        // } else {
        //     return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        // }
    }

    @PostMapping(path = "/user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AGPResponse> create_user(HttpServletRequest request, @RequestBody User user) {
        // if (functionalitiesService.verifyPermission(request, "create_user")) {

            try {
                List<Role> roles = new ArrayList<>();
                if (user.getId() == null) {
                    roles = jobPositionMapper.getRoleByJobPositionId(user.getJobPosition().getId(), user.getCategory());
                    user.setRoles(roles);
                }
                
                Exception e = userService.saveUser(user);

                if (e == null) {
                    return AGPResponse.success("SUCCESS");
                } else {
                    return AGPResponse.error("Error", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } catch (Exception ex) {
                System.out.println(ex);
                return AGPResponse.error("Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        // } else {
        //     return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        // }
    }

    @PutMapping(path = "/user", consumes = { "application/json", "multipart/form-data" })
    public ResponseEntity<AGPResponse> updateUser(HttpServletRequest request,
            @RequestPart(name = "user_data") String user_data,
            @RequestParam("image") MultipartFile multipartFile)
            throws ParseException, JsonProcessingException, IOException {
        // if (functionalitiesService.verifyPermission(request, "update_user")) {
            try {
                User user = new ObjectMapper().readValue(user_data, User.class);
                if (!multipartFile.isEmpty()) {
                    ImageHandlerUtil imageHandlerUtil = this.imageService.uploadImage(multipartFile, "user");
                    user.setPhotoUrl(imageHandlerUtil.getName());
                } else {

                    if (user.getPhotoUrl() == null) {
                        user.setPhotoUrl(null);
                    }
                }
                userService.saveUser(user);
                return AGPResponse.success("SUCCESS");
            } catch (DuplicateKeyException ex) {
                return AGPResponse.error("Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        // } else {
        //     return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        // }
    }

    @GetMapping("/searchUser")
    public ResponseEntity<List<User>> searchUser(HttpServletRequest request, @RequestParam String key) {
        try {
            return new ResponseEntity<>(userService.searchUser(key), HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> get_user_by_Id(HttpServletRequest request, @PathVariable("id") long id)
            throws UserNotFoundException {
        if (functionalitiesService.verifyPermission(request, "get_user_by_Id")) {
        User userData = userService.getUserById(id);
        if (userData != null) {
            return new ResponseEntity<>(userData, HttpStatus.OK);
        } else {
            throw UserNotFoundException.forUser(Long.toString(id));
        }
        } else {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping(value = "user/image/{photoUrl}", produces = MediaType.IMAGE_PNG_VALUE)
    private ResponseEntity<Object> getImage(HttpServletRequest request, @PathVariable("photoUrl") String photoUrl)
            throws IOException {
        // if (functionalitiesService.verifyPermission(request, "fetch_user_image")) {
        ImageHandlerUtil imageHandlerUtil = this.imageService.getImage(photoUrl);
        imageHandlerUtil.setUserType("user");
        File file = new File(imageHandlerUtil.getFilePath());
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        try {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(file.length())
                    .contentType(
                            MediaType.IMAGE_PNG)
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.badRequest()
                    .body("Couldn't find " + file.getName() +
                            " => " + e.getMessage());
        }
        // } else {
        // return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        // }
    }

    @GetMapping("/user/verify")
    public ResponseEntity<String> verifyUserAccount(HttpServletRequest request,
            @RequestParam(required = false) String token) {
        if (StringUtils.isEmpty(token)) {

            // return ResponseEntity.status(HttpStatus.FOUND)
            // .location(URI.create("https://lms.awashbank.com/bigfcy/invalid-token")).build();
            //
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create("https://afrfms.awashbank.com/afrfms/invalid-token")).build();

        }
        try {
            userService.verifyUser(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create("https://afrfms.awashbank.com/afrfms/invalid-token")).build();
        }
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("https://afrfms.awashbank.com/afrfms")).build();
    }

    @PostMapping("/user/account")
    public ResponseEntity<HttpStatus> unlockUser(HttpServletRequest request, @RequestBody User user) {
        // if (functionalitiesService.verifyPermission(request, "unlock_user")) {
            try {
                String jwt = jwtUtils.getJwtFromCookies(request);
                String username = jwtUtils.getUserNameFromJwtToken(jwt);
                User user1 = userMapper.findByEmail(username);
                // userService.unlockUserAccount(user, user1);
                userService.updateUserSecurity(user, user1);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception ex) {
                System.out.println(ex);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        // } 
        // else {
        //     return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        // }
    }

}
