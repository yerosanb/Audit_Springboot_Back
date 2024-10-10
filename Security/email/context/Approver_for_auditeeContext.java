package com.afr.fms.Security.email.context;

import org.springframework.web.util.UriComponentsBuilder;

// import com.afr.fms.Auditor.Entity.AuditISM;
// import com.afr.fms.Auditor.Entity.IS_MGT_Auditee;

public class Approver_for_auditeeContext extends AbstractEmailContext {

    // private AuditISM audit;

    // @Override
    // public <T> void init(T context) {
    //     // we can do any common configuration setup here
    //     // like setting up some base URL and context
    //     AuditISM audit = (AuditISM) context;

    //     String directorate_name = audit.getReviewer().getBranch().getName();
    //     // String auditee_email = "abebayehual@";
    //     String auditee_email = audit.getReviewer().getEmail();

    //     String director_name = audit.getReviewer().getFirst_name() + " " + audit.getReviewer().getMiddle_name();

    //     String approver_name = audit.getApprover().getFirst_name() + " " + audit.getApprover().getMiddle_name();

    //     String title = "Audit finding case number: " + audit.getCase_number() + " for " + directorate_name;

    //     String emailMessagePart1 = "This finding was reported on " + audit.getFinding_date()
    //             + ", and after careful consideration, I approved it. "
    //             + "So, please investigate and provide your response.";

    //     String emailMessagePart2 = "Sincerly, " + approver_name;

    //     put("title", title);
    //     put("Auditee_name", director_name);
    //     put("emailMessagePart1", emailMessagePart1);
    //     put("emailMessagePart2", emailMessagePart2);
    //     // put("firstName", user.getFirst_name());
    //     // put("password", user.getPassword());

    //     setTemplateLocation("ism_audit/approver_for_auditee");
    //     setSubject("Audit for Auditee Response");
    //     setFrom("AFRFMS@");
    //     setTo(auditee_email);
    // }

    // public void setISMAudit(AuditISM audit) {
    //     this.audit = audit;
    //     // put("token", this.token);
    // }

    // public void buildDisbursmentUrl(final String baseURL) {
    //     // final String url = UriComponentsBuilder.fromHttpUrl(baseURL)
    //     // .path("/manage-audit").toUriString();
    //     final String url = UriComponentsBuilder.fromHttpUrl(baseURL)
    //             .path("/").toUriString();
    //     put("redirectURL", url);
    // }

}
