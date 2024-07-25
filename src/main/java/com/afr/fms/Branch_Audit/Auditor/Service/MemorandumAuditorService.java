
package com.afr.fms.Branch_Audit.Auditor.Service;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Admin.Mapper.UserMapper;
import com.afr.fms.Branch_Audit.Auditor.Mapper.MemorandumAuditorMapper;
import com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAudit;
import com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditService;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Entity.MemorandumContigent;
import com.afr.fms.Payload.endpoint.Endpoint;
import com.afr.fms.Security.email.context.Auditor_for_ReviewerContext;
import com.afr.fms.Security.email.service.EmailService;

@Service
public class MemorandumAuditorService {
    @Autowired
    private MemorandumAuditorMapper auditMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ChangeTrackerBranchAuditService changeTrackerService;
    private String baseURL = Endpoint.URL;
    @Autowired
    private EmailService emailService;

    public List<BranchFinancialAudit> getPassedAuditsForAuditor(Long auditor_id) {
        return auditMapper.getPassedAuditsForAuditor(auditor_id);
    }

    public List<BranchFinancialAudit> getAuditsOnDrafting(Long auditor_id) {
        return auditMapper.getAuditsOnDrafting(auditor_id);
    }

    public void createISMFinding(BranchFinancialAudit audit) {
        User user = userMapper.getAuditorById(audit.getAuditor().getId());
        audit.setCategory(user.getCategory());
        audit.setBranch(user.getBranch());
        audit.setCase_number(generateCaseNumber(audit.getCategory()));
        Long audit_id;
        if (audit.getFinding_detail() == null) {
            audit_id = auditMapper.createBAFinding(audit);
        } else {
            audit_id = auditMapper.createBFAFindingWithFindingDetail(audit);

        }
        if (audit.getEdit_auditee()) {
            for (String file_name : audit.getFile_urls()) {
                auditMapper.InsertFileUrl(file_name, audit_id);
            }
        }

        MemorandumContigent memorandumContigent = audit.getMemorandumContigent();
        memorandumContigent.setBranch_audit_id(audit_id);
        auditMapper.createMemorandomContingent(memorandumContigent);

        // IS_MGT_Auditee IS_MGTAuditee = new IS_MGT_Auditee();
        // IS_MGTAuditee.setBranchFinancialAudit_id(audit_id);
        // for (Branch auditee : audit.getAuditees()) {
        // IS_MGTAuditee.setAuditee_id(auditee.getId());
        // auditMapper.createISMAuditee(IS_MGTAuditee);
        // }

        // List<User> user =
        // BranchFinancialAuditMapper.getUserByCategory(audit.getCategory());
        // for (User user2 : user) {
        // if(Long.compare(user2.getId(), audit.getAuditor().getId()) ==0 )
        // {
        // audit.setAuditor(user2);
        // }
        // for (Role role : user2.getRoles()) {
        // if (role.getCode().equalsIgnoreCase("REVIEWER")) {
        // audit.setReviewer(user2);
        // break;
        // }
        // }
        // }

        try {
            // sendEmailtoReviewer(audit);
        } catch (Exception e) {
            System.out.println(e);
        }

        for (ChangeTrackerBranchAudit changeTrackerBranchAudit : audit.getChange_tracker_branch_audit()) {
            if (changeTrackerBranchAudit != null) {
                changeTrackerBranchAudit.setAudit_id(audit_id);
                changeTrackerBranchAudit.setChanger(audit.getEditor());
                changeTrackerService.insertChanges(changeTrackerBranchAudit);
            }
        }
    }

    public void updateBAFinding(BranchFinancialAudit audit) {

        if (audit.getFinding_detail() == null) {
            auditMapper.updateBAFinding(audit);
        } else {
            auditMapper.updateBAFindingWithFindingDetail(audit);

        }

        MemorandumContigent memorandumContigent = audit.getMemorandumContigent();
        memorandumContigent.setBranch_audit_id(audit.getId());
        auditMapper.updateIAFinding(memorandumContigent);

        if (audit.getEdit_auditee()) {
            auditMapper.removeFileUrls(audit.getId());
            for (String file_name : audit.getFile_urls()) {
                auditMapper.InsertFileUrl(file_name, audit.getId());
            }

        }

        for (ChangeTrackerBranchAudit changeTrackerBranchAudit : audit.getChange_tracker_branch_audit()) {
            if (changeTrackerBranchAudit != null) {
                if (changeTrackerBranchAudit != null) {
                    changeTrackerBranchAudit.setAudit_id(audit.getId());
                    changeTrackerBranchAudit.setChanger(audit.getEditor());
                    changeTrackerService.insertChanges(changeTrackerBranchAudit);
                }
            }
        }
    }

    public String generateCaseNumber(String category) {
        int case_number_number = 1;
        String last_case_number = auditMapper.getLatestCaseNumber();
        if (last_case_number != null) {
            case_number_number = Integer.parseInt(last_case_number.replaceAll("[^0-9]", ""));
        }
        String case_number = category + (case_number_number + 1);
        return case_number;
    }

    public void sendEmailtoReviewer(BranchFinancialAudit audit) {
        Auditor_for_ReviewerContext emailContext = new Auditor_for_ReviewerContext();
        emailContext.init(audit);
        // emailContext.buildReviewRequestUrl(baseURL);
        try {
            emailService.sendMail(emailContext);
            System.out.println("Audit Review Request is sent");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

}
