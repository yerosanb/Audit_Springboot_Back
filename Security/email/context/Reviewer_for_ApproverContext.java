package com.afr.fms.Security.email.context;

import org.springframework.web.util.UriComponentsBuilder;
// import com.afr.fms.Auditor.Entity.AuditISM;
// import com.afr.fms.Auditor.Entity.IS_MGT_Auditee;

public class Reviewer_for_ApproverContext extends AbstractEmailContext {

    // private AuditISM audit;

    // @Override
    // public <T> void init(T context) {
    //     // we can do any common configuration setup here
    //     // like setting up some base URL and context
    //     AuditISM audit = (AuditISM) context; // we pass the customer information

    //     String auditees = "";

    //     for (IS_MGT_Auditee is_MGT_Auditee : audit.getIS_MGTAuditee()) {
    //         auditees += is_MGT_Auditee.getAuditee().getName() + ", ";
    //     }

    //     String approver_email = audit.getUsers().get(0).getEmail();
    //     // String approver_email = "abebayehual@";

    //     String approver_name = audit.getUsers().get(0).getFirst_name() + " " + audit.getUsers().get(0).getMiddle_name();

    //     String reviewer_name = audit.getReviewer().getFirst_name() + " " + audit.getReviewer().getMiddle_name();

    //     String title = "Finding with case number: " + audit.getCase_number() + " for " + auditees;

    //     String emailMessagePart1 = "This finding was reported on " + audit.getFinding_date()
    //             + ", and after careful consideration , I sent request to approve it.";

    //     String emailMessagePart2 = "Sincerly, " + reviewer_name;

    //     put("title", title);
    //     put("approver_name", approver_name);
    //     put("emailMessagePart1", emailMessagePart1);
    //     put("emailMessagePart2", emailMessagePart2);
    //     // put("firstName", user.getFirst_name());
    //     // put("password", user.getPassword());

    //     setTemplateLocation("ism_audit/reviewer_for_approver");
    //     setSubject("Audit for Approvement");
    //     setFrom("AFRFMS@");
    //     setTo(approver_email);
    // }

    // public void setISMAudit(AuditISM audit) {
    //     this.audit = audit;
    //     // put("token", this.token);
    // }

    // public void buildApproveRequestUrl(final String baseURL) {
    //     // final String url = UriComponentsBuilder.fromHttpUrl(baseURL)
    //     // .path("/manage-audit").toUriString();
    //     final String url = UriComponentsBuilder.fromHttpUrl(baseURL)
    //             .path("/").toUriString();
    //     put("redirectURL", url);
    // }

}
