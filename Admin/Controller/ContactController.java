package com.afr.fms.Admin.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afr.fms.Admin.Entity.Contact;
import com.afr.fms.Admin.Entity.Feedback;
import com.afr.fms.Admin.Service.ContactService;
import com.afr.fms.Common.Service.FunctionalitiesService;
import com.afr.fms.Payload.endpoint.Endpoint;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")

@RestController
@RequestMapping("/api")
// @PreAuthorize("hasRole('ROLE_')")
public class ContactController {
    @Autowired
    private ContactService contactService;
    @Autowired
    private FunctionalitiesService functionalitiesService;

    @PostMapping("/contact")
    public ResponseEntity<?> createContact(@RequestBody Contact contact, HttpServletRequest request) {
        if (functionalitiesService.verifyPermission(request, "save_supporter_contact")) {
            try {
                contactService.createContact(contact);

                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            } catch (Exception e) {
                System.out.println(e);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/contact/delete")
    public ResponseEntity<?> deleteContact(@RequestBody List<Contact> contacts, HttpServletRequest request) {
        // if (functionalitiesService.verifyPermission(request,
        // "save_supporter_contact")) {
        try {
            for (Contact contact : contacts) {
                contactService.deleteContact(contact);
            }
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // } else {
        // return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        // }
    }

    @GetMapping("/contacts")
    public ResponseEntity<List<Contact>> getContacts(HttpServletRequest request) {
        if (functionalitiesService.verifyPermission(request, "view_supporters_contact")) {
            try {
                return new ResponseEntity<>(contactService.getContacts(), HttpStatus.OK);
            } catch (Exception ex) {
                System.out.println(ex);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/contact/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable("id") long id, HttpServletRequest request) {
        if (functionalitiesService.verifyPermission(request, "get_supporter_contact_by_id")) {
            Contact contact = contactService.getContactById(id);
            if (contact != null) {
                return new ResponseEntity<>(contact, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/feedback/{id}")
    public ResponseEntity<List<Feedback>> get(@PathVariable("id") long id, HttpServletRequest request) {
        // if (functionalitiesService.verifyPermission(request,
        // "get_supporter_contact_by_id")) {
        try {
            return new ResponseEntity<>(contactService.getFeedbacksByUserID(id), HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // } else {
        // return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        // }
    }

    @PostMapping("/feedback")
    public ResponseEntity<?> createFeedback(@RequestBody Feedback feedback, HttpServletRequest request) {
        if (functionalitiesService.verifyPermission(request, "save_feedback")) {
            try {
                System.out.println("Here");
                contactService.createFeedback(feedback);
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            } catch (Exception e) {
                System.out.println(e);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/feedback")
    public ResponseEntity<List<Feedback>> getFeedbacks(HttpServletRequest request) {
        if (functionalitiesService.verifyPermission(request, "view_users_feedback")) {
            try {
                return new ResponseEntity<>(contactService.getFeedbacks(), HttpStatus.OK);
            } catch (Exception ex) {
                System.out.println(ex);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/feedback/close")
    public ResponseEntity<?> closeFeedback(@RequestBody List<Feedback> feedbacks, HttpServletRequest request) {
        // if (functionalitiesService.verifyPermission(request,
        // "save_supporter_contact")) {
        try {
            for (Feedback feedback : feedbacks) {
                contactService.closeFeedback(feedback);
            }
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // } else {
        // return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        // }
    }

    @PostMapping("/feedback/delete")
    public ResponseEntity<?> deleteFeedback(@RequestBody List<Feedback> feedbacks, HttpServletRequest request) {
        // if (functionalitiesService.verifyPermission(request,
        // "save_supporter_contact")) {
        try {
            for (Feedback feedback : feedbacks) {
                contactService.deleteFeedback(feedback);
            }
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // } else {
        // return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        // }
    }

    @PostMapping("/feedback/respond")
    public ResponseEntity<?> respondFeedback(@RequestBody List<Feedback> feedbacks, HttpServletRequest request) {
        // if (functionalitiesService.verifyPermission(request,
        // "save_supporter_contact")) {
        try {
            String response = feedbacks.get(0).getResponse();
            for (Feedback feedback : feedbacks) {
                feedback.setResponse(response);
                contactService.respondFeedback(feedback);
            }
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // } else {
        // return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        // }
    }

}
