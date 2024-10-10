
package com.afr.fms.Branch_Audit.Auditor.Service;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Admin.Mapper.UserMapper;
import com.afr.fms.Branch_Audit.Auditor.Mapper.CashManagementAuditorMapper;
import com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper;
import com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAudit;
import com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditService;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Entity.CashManagementBranch;
import com.afr.fms.Payload.endpoint.Endpoint;
import com.afr.fms.Security.email.context.Auditor_for_ReviewerContext;
import com.afr.fms.Security.email.service.EmailService;

@Service
public class CashManagementAuditorService {
    @Autowired
    private CashManagementAuditorMapper auditMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IncompleteAccountBranchMapper auditMapper2;

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

    public void createISMFinding(BranchFinancialAudit audit) {
        User user = userMapper.getAuditorById(audit.getAuditor().getId());
        audit.setCategory(user.getCategory());
        audit.setCase_number(generateCaseNumber(audit.getCategory()));
        audit.setBranch(user.getBranch());
        Long audit_id;

        if (audit.getFinding_detail() != null) {
            audit_id = auditMapper.createBAFindingWithFindingDetail(audit);
        } else {
            audit_id = auditMapper.createBAFinding(audit);
        }

        if (audit.getEdit_auditee()) {
            for (String file_name : audit.getFile_urls()) {
                auditMapper2.InsertFileUrl(file_name, audit_id);
            }
        }

        CashManagementBranch cashManagementBranch = audit.getCashManagementBranch();
        cashManagementBranch.setBranch_audit_id(audit_id);
        auditMapper.createCashManagementBranchFinding(cashManagementBranch);

        for (ChangeTrackerBranchAudit changeTrackerBranchAudit : audit.getChange_tracker_branch_audit()) {
            if (changeTrackerBranchAudit != null) {
                changeTrackerBranchAudit.setAudit_id(audit_id);
                changeTrackerBranchAudit.setChanger(audit.getEditor());
                changeTrackerService.insertChanges(changeTrackerBranchAudit);
            }
        }
        try {
            // sendEmailtoReviewer(audit);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void updateBAFinding(BranchFinancialAudit audit) {
        CashManagementBranch cashManagementBranch = audit.getCashManagementBranch();
        cashManagementBranch.setBranch_audit_id(audit.getId());
        auditMapper.updateCashManagementBranchFinding(cashManagementBranch); 


        if (audit.getFinding_detail() != null) {
           auditMapper.updateBAFindingWithFindingDetail(audit);
        } else {
            auditMapper.updateBAFinding(audit);
        }

        if (audit.getEdit_auditee()) {
            auditMapper2.removeFileUrls(audit.getId());
            for (String file_name : audit.getFile_urls()) {
                auditMapper2.InsertFileUrl(file_name, audit.getId());
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
