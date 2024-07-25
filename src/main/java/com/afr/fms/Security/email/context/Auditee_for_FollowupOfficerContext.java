package com.afr.fms.Security.email.context;

import org.springframework.web.util.UriComponentsBuilder;

// import com.afr.fms.Auditor.Entity.IS_MGT_Auditee;

public class Auditee_for_FollowupOfficerContext extends AbstractEmailContext {

    // private AuditISM audit;

//     @Override
//     public <T> void init(T context) {
//         // we can do any common configuration setup here
//         // like setting up some base URL and context
//         IS_MGT_Auditee is_MGT_Auditee = (IS_MGT_Auditee) context; // we pass the customer information

//         String auditees = "";
//         // for (IS_MGT_Auditee is_MGT_Auditee : audit.getIS_MGTAuditee()) {
//         auditees += is_MGT_Auditee.getAuditee().getName();
//         // }

//         // String followupofficer_email = "abebayehual@awashbank.com";
//         String followupofficer_email =
//         is_MGT_Auditee.getAuditISM().getUsers().get(0).getEmail();
//         String followupofficer_name = is_MGT_Auditee.getAuditISM().getUsers().get(0).getFirst_name() + " "
//                 + is_MGT_Auditee.getAuditISM().getUsers().get(0).getMiddle_name();

//         String title = "Take action on finding case number: " + is_MGT_Auditee.getAuditISM().getCase_number() + " for "
//                 + auditees;

//         String emailMessagePart1 = "After careful consideration, we gave our response for finding that was reported on "
//                 + is_MGT_Auditee.getAuditISM().getFinding_date() + "."
//                 + " So, please take your action.";

//         String emailMessagePart2 = "Sincerly, " + auditees;

//         put("title", title);
//         put("followup_officer", followupofficer_name);
//         put("emailMessagePart1", emailMessagePart1);
//         put("emailMessagePart2", emailMessagePart2);

//         // put("firstName", user.getFirst_name());
//         // put("password", user.getPassword());

//         setTemplateLocation("ism_audit/auditee_for_followupofficer");
//         setSubject("Audit for Rectification");
//         setFrom("AFRFMS@awashbank.com");
//         setTo(followupofficer_email);
//     }

//     // public void setISMAudit(AuditISM audit) {
//     // this.audit = audit;
//     // // put("token", this.token);
//     // }

//     public void buildFollowupOfficerUrl(final String baseURL) {
//         // final String url = UriComponentsBuilder.fromHttpUrl(baseURL)
//         // .path("/manage-audit").toUriString();
//         final String url = UriComponentsBuilder.fromHttpUrl(baseURL)
//                 .path("/").toUriString();
//         put("redirectURL", url);
//     }

}
