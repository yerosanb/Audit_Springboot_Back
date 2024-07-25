package com.afr.fms.Common.Currency;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.afr.fms.Payload.endpoint.Endpoint;
import com.afr.fms.Common.Service.FunctionalitiesService;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")

@RestController
@RequestMapping("api/currency")
// @PreAuthorize("hasAnyRole('MAKER','APPROVER','HO')")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private FunctionalitiesService functionalitiesService;

    @GetMapping("/getAllCurrency")
    public ResponseEntity<List<Currency>> getAllCurrency(HttpServletRequest request) {
        // if (functionalitiesService.verifyPermission(request, "view_currencies")) {
            try {
                List<Currency> currency = currencyService.getAllCurrency();
                return new ResponseEntity<>(currency, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        // } else {
        //     return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        // }
    }

}
