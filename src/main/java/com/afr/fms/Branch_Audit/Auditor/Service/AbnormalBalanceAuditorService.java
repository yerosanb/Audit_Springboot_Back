package com.afr.fms.Branch_Audit.Auditor.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Admin.Mapper.UserMapper;
import com.afr.fms.Branch_Audit.Auditor.Mapper.AbnormalBalanceAuditorMapper;
import com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper;
import com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAudit;
import com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditService;
import com.afr.fms.Branch_Audit.Entity.AbnormalBalance;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;

@Service
public class AbnormalBalanceAuditorService {
    @Autowired
    private AbnormalBalanceAuditorMapper abnormalBalanceAuditorMapper;
    @Autowired
    private IncompleteAccountBranchMapper auditMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ChangeTrackerBranchAuditService changeTrackerService;

    public void createAbnormalBalance(BranchFinancialAudit branchFinancialAudit) {

        branchFinancialAudit.setCase_number(generateCaseNumber(branchFinancialAudit.getCategory()));

        Long inspection_id;

        if (branchFinancialAudit.getFinding_detail() == null) {
            inspection_id = abnormalBalanceAuditorMapper
                    .createBranchFinantialAuditWithOutFindingDetail(branchFinancialAudit);

        } else {
            inspection_id = abnormalBalanceAuditorMapper
                    .createBranchFinantialAudit(branchFinancialAudit);

        }
        branchFinancialAudit.getAbnormalBalanceBranch().setBranch_audit_id(inspection_id);

        abnormalBalanceAuditorMapper.createAbnormalBalance(branchFinancialAudit.getAbnormalBalanceBranch());
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

    public void updateAbnormalBalance(BranchFinancialAudit branchFinancialAudit) {

        if (branchFinancialAudit.getFinding_detail() == null) {
            abnormalBalanceAuditorMapper.updateBranchFinantialAudit(branchFinancialAudit);

        } else {
            abnormalBalanceAuditorMapper.updateBranchFinantialAuditWithFindingDetail(branchFinancialAudit);

        }

        AbnormalBalance abnormalBalance = branchFinancialAudit.getAbnormalBalanceBranch();
        abnormalBalance.setBranch_audit_id(branchFinancialAudit.getId());

        abnormalBalanceAuditorMapper.updateAbnormalBalance(abnormalBalance);

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
        String last_case_number = abnormalBalanceAuditorMapper.getLatestCaseNumber();
        if (last_case_number != null) {
            case_number_number = Integer.parseInt(last_case_number.replaceAll("[^0-9]", ""));
        }
        String case_number = category + (case_number_number + 1);
        return case_number;
    }

    public List<BranchFinancialAudit> getDraftedAudits(Long auditor_id) {
        return abnormalBalanceAuditorMapper.getDraftedAudits(auditor_id);
    }

    public List<BranchFinancialAudit> getOnProgressAudits(Long auditor_id) {
        return abnormalBalanceAuditorMapper.getOnProgressAudits(auditor_id);
    }

    public List<BranchFinancialAudit> getPassedAudits(Long auditor_id) {
        return abnormalBalanceAuditorMapper.getPassedAudits(auditor_id);
    }

    public void passAbnormalBalance(BranchFinancialAudit branchFinancialAudit) {
        abnormalBalanceAuditorMapper.passAbnormalBalance(branchFinancialAudit.getId());

    }

    public void passMultiAbnormalBalance(List<BranchFinancialAudit> branchFinancialAudits) {
        for (BranchFinancialAudit branchFinancialAudit : branchFinancialAudits) {
            abnormalBalanceAuditorMapper.passAbnormalBalance(branchFinancialAudit.getId());
        }
    }

    public void backPassedAbnormalBalance(BranchFinancialAudit branchFinancialAudit) {
        abnormalBalanceAuditorMapper.backPassedAbnormalBalance(branchFinancialAudit.getId());
    }

    public void backMultiPassedAbnormalBalance(List<BranchFinancialAudit> branchFinancialAudits) {
        for (BranchFinancialAudit branchFinancialAudit : branchFinancialAudits) {
            abnormalBalanceAuditorMapper.backPassedAbnormalBalance(branchFinancialAudit.getId());
        }
    }

    public void deleteAbnormalBalance(Long id) {

        abnormalBalanceAuditorMapper.deleteAbnormalBalance(id);

    }

    public void deleteMultiAbnormalBalance(List<BranchFinancialAudit> branchFinancialAudits) {
        for (BranchFinancialAudit branchFinancialAudit : branchFinancialAudits) {

            abnormalBalanceAuditorMapper.deleteAbnormalBalance(branchFinancialAudit.getId());

        }

    }
}
