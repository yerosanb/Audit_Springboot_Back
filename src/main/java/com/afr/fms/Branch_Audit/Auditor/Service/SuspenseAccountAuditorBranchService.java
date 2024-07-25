package com.afr.fms.Branch_Audit.Auditor.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper;
import com.afr.fms.Branch_Audit.Auditor.Mapper.SuspenseAccountAuditorBranchMapper;
import com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAudit;
import com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditService;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Entity.ControllableExpenseType;
import com.afr.fms.Branch_Audit.Entity.SuspenseAccountBranch;
import com.afr.fms.Branch_Audit.Entity.SuspenseAccountType;

@Service
public class SuspenseAccountAuditorBranchService {

    @Autowired
    private SuspenseAccountAuditorBranchMapper suspenseAccountAuditorBranchMapper;
    @Autowired
    private IncompleteAccountBranchMapper auditMapper;

    @Autowired
    private ChangeTrackerBranchAuditService changeTrackerService;

    public void createSuspenseAccountBranch(BranchFinancialAudit branchFinancialAudit) {

        branchFinancialAudit.setCase_number(generateCaseNumber(branchFinancialAudit.getCategory()));

        Long inspection_id;

        if (branchFinancialAudit.getFinding_detail() == null) {
            inspection_id = suspenseAccountAuditorBranchMapper.createBranchFinantialAudit(branchFinancialAudit);

        } else {
            inspection_id = suspenseAccountAuditorBranchMapper
                    .createBranchFinantialAuditWithFindingDetail(branchFinancialAudit);

        }

        branchFinancialAudit.getSuspenseAccountBranch().setBranch_audit_id(inspection_id);
        Long suspense_account_id = suspenseAccountAuditorBranchMapper
                .createSuspenseAccountBranch(branchFinancialAudit.getSuspenseAccountBranch());

        for (SuspenseAccountType suspenseAccountType : branchFinancialAudit.getSuspenseAccountBranch()
                .getSuspenseAccountType()) {
            suspenseAccountAuditorBranchMapper.createSuspenseAccountBranchType(suspense_account_id,
                    suspenseAccountType.getId());

        }
        if (branchFinancialAudit.getEdit_auditee()) {

            for (String file_name : branchFinancialAudit.getFile_urls()) {
                auditMapper.InsertFileUrl(file_name, inspection_id);
            }
        }
        for (ChangeTrackerBranchAudit changeTrackerBranchAudit : branchFinancialAudit
                .getChange_tracker_branch_audit()) {
            if (changeTrackerBranchAudit != null) {
                changeTrackerBranchAudit.setAudit_id(inspection_id);
                changeTrackerBranchAudit.setChanger(branchFinancialAudit.getEditor());
                changeTrackerService.insertChanges(changeTrackerBranchAudit);
            }
        }

    }

    public void updateSuspenseAccountBranch(BranchFinancialAudit branchFinancialAudit) {

        if(branchFinancialAudit.getFinding_detail() != null)
        {
            suspenseAccountAuditorBranchMapper.updateBranchFinantialAudit(branchFinancialAudit);

        }
        else{
            suspenseAccountAuditorBranchMapper.updateBranchFinantialAuditWithFindingDetail(branchFinancialAudit);
        }
        SuspenseAccountBranch suspenseAccountBranch = branchFinancialAudit.getSuspenseAccountBranch();
        suspenseAccountBranch.setBranch_audit_id(branchFinancialAudit.getId());
        suspenseAccountAuditorBranchMapper.updateSuspenseAccountBranch(suspenseAccountBranch);

        suspenseAccountAuditorBranchMapper.delete_suspense_account_branch_type(suspenseAccountBranch.getId());

        for (SuspenseAccountType suspenseAccountType : branchFinancialAudit.getSuspenseAccountBranch()
                .getSuspenseAccountType()) {
            suspenseAccountAuditorBranchMapper.createSuspenseAccountBranchType(suspenseAccountBranch.getId(),
                    suspenseAccountType.getId());
        }

        if (branchFinancialAudit.getEdit_auditee()) {
            auditMapper.removeFileUrls(branchFinancialAudit.getId());
            for (String file_name : branchFinancialAudit.getFile_urls()) {
                auditMapper.InsertFileUrl(file_name, branchFinancialAudit.getId());
            }

        }

        for (ChangeTrackerBranchAudit changeTrackerBranchAudit : branchFinancialAudit
                .getChange_tracker_branch_audit()) {
            if (changeTrackerBranchAudit != null) {
                if (changeTrackerBranchAudit != null) {
                    changeTrackerBranchAudit.setAudit_id(branchFinancialAudit.getId());
                    changeTrackerBranchAudit.setChanger(branchFinancialAudit.getEditor());
                    changeTrackerService.insertChanges(changeTrackerBranchAudit);
                }
            }
        }

    }

    public String generateCaseNumber(String category) {
        int case_number_number = 1;
        String last_case_number = suspenseAccountAuditorBranchMapper.getLatestCaseNumber();
        if (last_case_number != null) {
            case_number_number = Integer.parseInt(last_case_number.replaceAll("[^0-9]", ""));
        }
        String case_number = category + (case_number_number + 1);
        return case_number;
    }

    public List<BranchFinancialAudit> getDraftedAudits(Long auditor_id) {
        return suspenseAccountAuditorBranchMapper.getDraftedAudits(auditor_id);
    }

    public List<BranchFinancialAudit> getOnProgressAudits(Long auditor_id) {
        return suspenseAccountAuditorBranchMapper.getOnProgressAudits(auditor_id);
    }

    public List<BranchFinancialAudit> getPassedAudits(Long auditor_id) {
        return suspenseAccountAuditorBranchMapper.getPassedAudits(auditor_id);
    }

    public void passSuspenseAccountBranch(BranchFinancialAudit branchFinancialAudit) {
        suspenseAccountAuditorBranchMapper.passSuspenseAccountBranch(branchFinancialAudit.getId());

    }

    public void passMultiSuspenseAccountBranch(List<BranchFinancialAudit> branchFinancialAudits) {
        for (BranchFinancialAudit branchFinancialAudit : branchFinancialAudits) {
            suspenseAccountAuditorBranchMapper.passSuspenseAccountBranch(branchFinancialAudit.getId());
        }
    }

    public void backPassedSuspenseAccountBranch(BranchFinancialAudit branchFinancialAudit) {
        suspenseAccountAuditorBranchMapper.backPassedSuspenseAccountBranch(branchFinancialAudit.getId());
    }

    public void backMultiPassedSuspenseAccountBranch(List<BranchFinancialAudit> branchFinancialAudits) {
        for (BranchFinancialAudit branchFinancialAudit : branchFinancialAudits) {
            suspenseAccountAuditorBranchMapper.backPassedSuspenseAccountBranch(branchFinancialAudit.getId());
        }
    }

    public void deleteSuspenseAccountBranch(Long id) {

        suspenseAccountAuditorBranchMapper.deleteSuspenseAccountBranch(id);

    }

    public void deleteMultiSuspenseAccountBranch(List<BranchFinancialAudit> branchFinancialAudits) {
        for (BranchFinancialAudit branchFinancialAudit : branchFinancialAudits) {

            suspenseAccountAuditorBranchMapper.deleteSuspenseAccountBranch(branchFinancialAudit.getId());

        }

    }
}
