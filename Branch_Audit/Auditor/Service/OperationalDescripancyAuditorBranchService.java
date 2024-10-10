package com.afr.fms.Branch_Audit.Auditor.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper;
import com.afr.fms.Branch_Audit.Auditor.Mapper.OperationalDescripancyAuditorBranchMapper;
import com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAudit;
import com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditService;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Entity.OperationalDescripancyBranch;

@Service
public class OperationalDescripancyAuditorBranchService {

    @Autowired
    private OperationalDescripancyAuditorBranchMapper operationalDescripancyAuditorMapper;
    @Autowired
    private IncompleteAccountBranchMapper auditMapper;

    @Autowired
    private ChangeTrackerBranchAuditService changeTrackerService;

    public void createoperationalDescripancy(BranchFinancialAudit branchFinancialAudit) {
        branchFinancialAudit.setCase_number(generateCaseNumber(branchFinancialAudit.getCategory()));
        Long inspection_id;
        if (branchFinancialAudit.getFinding_detail() == null) {
            inspection_id = operationalDescripancyAuditorMapper.createBranchFinantialAudit(branchFinancialAudit);
        } else {
            inspection_id = operationalDescripancyAuditorMapper
                    .createBranchFinantialAuditWithFindingDetail(branchFinancialAudit);

        }

        branchFinancialAudit.getOperationalDescripancyBranch().setBranch_audit_id(inspection_id);
        operationalDescripancyAuditorMapper
                .createOperationalDescripancies(branchFinancialAudit.getOperationalDescripancyBranch());

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

    public void updateoperationalDescripancy(BranchFinancialAudit branchFinancialAudit) {
        OperationalDescripancyBranch operationalDescripancy = branchFinancialAudit.getOperationalDescripancyBranch();
        operationalDescripancy.setBranch_audit_id(branchFinancialAudit.getId());
        operationalDescripancyAuditorMapper.updateOperationalDescripancyBranch(operationalDescripancy);

        if (branchFinancialAudit.getFinding_detail() == null) {
            operationalDescripancyAuditorMapper.updateBranchFinantialAudit(branchFinancialAudit);
        } else {
            operationalDescripancyAuditorMapper.updateBranchFinantialAuditWithFindingDetail(branchFinancialAudit);
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
        String last_case_number = operationalDescripancyAuditorMapper.getLatestCaseNumber();
        if (last_case_number != null) {
            case_number_number = Integer.parseInt(last_case_number.replaceAll("[^0-9]", ""));
        }
        String case_number = category + (case_number_number + 1);
        return case_number;
    }

    public List<BranchFinancialAudit> getDraftedAudits(Long auditor_id) {
        return operationalDescripancyAuditorMapper.getDraftedAudits(auditor_id);
    }

    public List<BranchFinancialAudit> getOnProgressAudits(Long auditor_id) {
        return operationalDescripancyAuditorMapper.getOnProgressAudits(auditor_id);
    }

    public List<BranchFinancialAudit> getPassedAudits(Long auditor_id) {
        return operationalDescripancyAuditorMapper.getPassedAudits(auditor_id);
    }

    public void passoperationalDescripancy(BranchFinancialAudit branchFinancialAudit) {
        operationalDescripancyAuditorMapper.passOperationalDescripancy(branchFinancialAudit.getId());

    }

    public void passMultioperationalDescripancy(List<BranchFinancialAudit> branchFinancialAudits) {
        for (BranchFinancialAudit branchFinancialAudit : branchFinancialAudits) {
            operationalDescripancyAuditorMapper.passOperationalDescripancy(branchFinancialAudit.getId());
        }
    }

    public void backPassedoperationalDescripancy(BranchFinancialAudit branchFinancialAudit) {
        operationalDescripancyAuditorMapper.backPassedOperationalDescripancy(branchFinancialAudit.getId());
    }

    public void backMultiPassedoperationalDescripancy(List<BranchFinancialAudit> branchFinancialAudits) {
        for (BranchFinancialAudit branchFinancialAudit : branchFinancialAudits) {
            operationalDescripancyAuditorMapper.backPassedOperationalDescripancy(branchFinancialAudit.getId());
        }
    }

    public void deleteoperationalDescripancy(Long id) {

        operationalDescripancyAuditorMapper.deleteOperationalDescripancy(id);

    }

    public void deleteMultioperationalDescripancy(List<BranchFinancialAudit> branchFinancialAudits) {
        for (BranchFinancialAudit branchFinancialAudit : branchFinancialAudits) {

            operationalDescripancyAuditorMapper.deleteOperationalDescripancy(branchFinancialAudit.getId());

        }

    }
}
