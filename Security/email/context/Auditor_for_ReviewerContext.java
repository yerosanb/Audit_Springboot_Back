package com.afr.fms.Security.email.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.UriComponentsBuilder;

import com.afr.fms.Admin.Entity.Branch;
import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Admin.Mapper.UserMapper;
// import com.afr.fms.Auditor.Entity.AuditISM;
// import com.afr.fms.Auditor.Entity.IS_MGT_Auditee;

public class Auditor_for_ReviewerContext extends AbstractEmailContext {

    // private AuditISM audit;

    // @Override
    // public <T> void init(T context) {
    //     // we can do any common configuration setup here
    //     // like setting up some base URL and context
    //     AuditISM audit = (AuditISM) context; // we pass the audit information

    //     String auditees = "";
    //     for (IS_MGT_Auditee auditee : audit.getIS_MGTAuditee()) {
    //         auditees += auditee.getAuditee().getName() + ", ";
    //     }

    //     String auditor_name = audit.getAuditor().getFirst_name() + " " + audit.getAuditor().getMiddle_name();
    //     String reviewer_email = audit.getUsers().get(0).getEmail();
    //     // String reviewer_email = "abebayehual@";

    //     String reviewer_name = audit.getUsers().get(0).getFirst_name() + " " + audit.getUsers().get(0).getMiddle_name();

    //     String title = "Finding with case number: " + audit.getCase_number() + " for " + auditees;

    //     String emailMessagePart1 = "This finding was reported on " + audit.getFinding_date()
    //             + ", and after careful consideration, I sent request to review it.";

    //     String emailMessagePart2 = "Sincerly, " + auditor_name;

    //     put("title", title);
    //     put("reviewer_name", reviewer_name);
    //     put("emailMessagePart1", emailMessagePart1);
    //     put("emailMessagePart2", emailMessagePart2);
    //     // put("firstName", user.getFirst_name());
    //     // put("password", user.getPassword());

    //     setTemplateLocation("ism_audit/auditor_for_reviewer");
    //     setSubject("Audit for Reviewing");
    //     setFrom("AFRFMS@");
    //     setTo(reviewer_email);
    // }

    // public void setISMAudit(AuditISM audit) {
    //     this.audit = audit;
    //     // put("token", this.token);
    // }

    // public void buildReviewRequestUrl(final String baseURL) {
    //     // final String url = UriComponentsBuilder.fromHttpUrl(baseURL)
    //     // .path("/manage-audit").toUriString();
    //     final String url = UriComponentsBuilder.fromHttpUrl(baseURL)
    //             .path("/").toUriString();
    //     put("redirectURL", url);
    // }

}
