package com.afr.fms.Common.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.afr.fms.Common.Entity.Functionalities;
import com.afr.fms.Common.Service.FunctionalitiesService;
import com.afr.fms.Payload.endpoint.Endpoint;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('ADMIN')")
public class FunctionalityController {
    @Autowired
    private FunctionalitiesService functionalitiesService;

    @GetMapping("/getRoleFunctionalitiesById/{id}")
    public ResponseEntity<List<Functionalities>> getRoleFunctionalitiesById(@PathVariable Long id,
            HttpServletRequest request) {

        if (functionalitiesService.verifyPermission(request, "view_functionalities_for_given_role")) {
            try {

                return new ResponseEntity<>(functionalitiesService.getAllFunctionalitiesByRole(id), HttpStatus.OK);
            } catch (Exception ex) {
            
                                                System.out.println("ex"+ex);

                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/getAllRoleFunctionalities")
    public ResponseEntity<List<Functionalities>> getAllRoleFunctionalities(HttpServletRequest request) {
        if (functionalitiesService.verifyPermission(request, "view_all_functionalities")) {
            try {
                return new ResponseEntity<>(functionalitiesService.getAllRoleFunctionalities(), HttpStatus.OK);
            } catch (Exception ex) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }

    @RequestMapping(value = "/updateRoleFunctionalitiesById/{role_id}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Boolean> updateRoleFunctionalitiesById(
            @PathVariable("role_id") String role_id, @RequestBody String functionality_status_string,
            HttpServletRequest request) {
        if (functionalitiesService.verifyPermission(request, "update_functionalities_for_the_given_role")) {
            try {

                return new ResponseEntity<Boolean>(
                        functionalitiesService.updateRoleFunctionalityStatus(role_id, functionality_status_string),
                        HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/updateFunctionalitiesById", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Boolean> updateFunctionalitiesById(@RequestBody String functionality_status_string,
            HttpServletRequest request) {
        if (functionalitiesService.verifyPermission(request, "update_functionalities")) {
            try {
                return new ResponseEntity<Boolean>(
                        functionalitiesService.updateFunctionalityStatus(functionality_status_string), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}
