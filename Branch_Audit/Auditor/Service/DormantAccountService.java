
package com.afr.fms.Branch_Audit.Auditor.Service;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Admin.Mapper.UserMapper;
import com.afr.fms.Branch_Audit.Auditor.Mapper.DormantAccountMapper;
import com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAudit;
import com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditService;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Entity.DormantInactive;
import com.afr.fms.Payload.endpoint.Endpoint;
import com.afr.fms.Security.email.context.Auditor_for_ReviewerContext;
import com.afr.fms.Security.email.service.EmailService;

@Service
public class DormantAccountService {
    @Autowired
    private DormantAccountMapper auditMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ChangeTrackerBranchAuditService changeTrackerService;
    private String baseURL = Endpoint.URL;
    @Autowired
    private EmailService emailService;

    public List<BranchFinancialAudit> getAuditsForAuditor(Long auditor_id) {
        return auditMapper.getAuditsForAuditor(auditor_id);
    }

    public List<BranchFinancialAudit> getAuditsOnDrafting(Long auditor_id) {
        return auditMapper.getAuditsOnDrafting(auditor_id);
    }

    public List<BranchFinancialAudit> getAuditsOnProgressForAuditor(Long auditor_id) {
        return auditMapper.getAuditsOnProgressForAuditor(auditor_id);
    }

    public void createISMFinding(BranchFinancialAudit audit) {
        audit.setCategory(audit.getAuditor().getCategory());
        audit.setCase_number(generateCaseNumber(audit.getCategory()));
        Long audit_id;
        if (audit.getFinding_detail() == null) {
            audit_id = auditMapper.createBAFinding(audit);
        } else {
            audit_id = auditMapper.createBranchFinantialFindingWithFindingDetail(audit);
        }

        if (audit.getEdit_auditee()) {
            for (String file_name : audit.getFile_urls()) {
                auditMapper.InsertFileUrl(file_name, audit_id);
            }
        }

        DormantInactive dormantIncative = audit.getDormantInactive();
        dormantIncative.setBranch_audit_id(audit_id);
        auditMapper.createIAFinding(dormantIncative);
        
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
        DormantInactive dormantInactive = audit.getDormantInactive();
        dormantInactive.setBranch_audit_id(audit.getId());
        auditMapper.updateIAFinding(dormantInactive);

        if (audit.getFinding_detail() == null) {
            auditMapper.updateBAFinding(audit);
        } else {
            auditMapper.updateBAFindingWithFindingDetail(audit);
        }

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

    public void deleteBAFinding(Long id) {
        auditMapper.deleteBAFinding(id);
    }

    public void deleteSelectedBAFinding(List<BranchFinancialAudit> BranchFinancialAudits) {
        for (BranchFinancialAudit BranchFinancialAudit : BranchFinancialAudits) {
            auditMapper.deleteBAFinding(BranchFinancialAudit.getId());
        }
    }

    public void passBAFinding(Long id) {
        auditMapper.passBAFinding(id);
    }

    public void passSelectedBAFinding(List<BranchFinancialAudit> BranchFinancialAudits) {
        for (BranchFinancialAudit BranchFinancialAudit : BranchFinancialAudits) {
            auditMapper.passBAFinding(BranchFinancialAudit.getId());
        }
    }

    public void backBAFinding(Long id) {
        auditMapper.backBAFinding(id);
    }

    public void backSelectedBAFinding(List<BranchFinancialAudit> BranchFinancialAudits) {
        for (BranchFinancialAudit BranchFinancialAudit : BranchFinancialAudits) {
            auditMapper.backBAFinding(BranchFinancialAudit.getId());
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

    public List<BranchFinancialAudit> getAuditFindings() {
        return auditMapper.getAuditFindings();

    }
}
