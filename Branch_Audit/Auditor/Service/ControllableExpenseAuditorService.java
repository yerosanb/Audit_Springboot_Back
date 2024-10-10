
package com.afr.fms.Branch_Audit.Auditor.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Branch_Audit.Auditor.Mapper.ControllableExpenseAuditorMapper;
import com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper;
import com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAudit;
import com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditService;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Entity.ControllableExpense;
import com.afr.fms.Branch_Audit.Entity.ControllableExpenseType;

@Service
public class ControllableExpenseAuditorService {

    @Autowired
    private ControllableExpenseAuditorMapper controllableExpenseMapper;
    @Autowired
    private IncompleteAccountBranchMapper auditMapper;

    @Autowired
    private ChangeTrackerBranchAuditService changeTrackerService;

    public void createControllableExpenseBranch(BranchFinancialAudit branchFinancialAudit) {

        branchFinancialAudit.setCase_number(generateCaseNumber(branchFinancialAudit.getCategory()));

        Long branch_id;

        if (branchFinancialAudit.getFinding_detail() == null) {

            branch_id = controllableExpenseMapper
                    .createBranchFinantialAuditwithoutFindingDetail(branchFinancialAudit);

        } else {
            branch_id = controllableExpenseMapper
                    .createBranchFinantialAudit(branchFinancialAudit);

        }
        branchFinancialAudit.getControllableExpense().setBranch_audit_id(branch_id);
        Long expense_id = controllableExpenseMapper
                .create_controllable_expense_branch(branchFinancialAudit.getControllableExpense());

        for (ControllableExpenseType controllableExpenseType : branchFinancialAudit.getControllableExpense()
                .getControllableExpenseType()) {
            controllableExpenseMapper.create_controllable_expense_branch_type(expense_id,
                    controllableExpenseType.getId());
        }

        if (branchFinancialAudit.getEdit_auditee()) {

            for (String file_name : branchFinancialAudit.getFile_urls()) {
                auditMapper.InsertFileUrl(file_name, branch_id);
            }
        }
        for (ChangeTrackerBranchAudit changeTrackerBranchAudit : branchFinancialAudit
                .getChange_tracker_branch_audit()) {
            if (changeTrackerBranchAudit != null) {
                changeTrackerBranchAudit.setAudit_id(branch_id);
                changeTrackerBranchAudit.setChanger(branchFinancialAudit.getEditor());
                changeTrackerService.insertChanges(changeTrackerBranchAudit);
            }
        }

    }

    public void updateControllableExpenseBranch(BranchFinancialAudit branchFinancialAudit) {

        if (branchFinancialAudit.getFinding_detail() == null) {
            controllableExpenseMapper.updateBranchFinantialAudit(branchFinancialAudit);
        } else {
            controllableExpenseMapper.updateBranchFinantialAuditWithFindingDetail(branchFinancialAudit);
        }

        ControllableExpense controllableExpense = branchFinancialAudit.getControllableExpense();
        controllableExpense.setBranch_audit_id(branchFinancialAudit.getId());
        controllableExpenseMapper.update_controllable_expense_branch(controllableExpense);
        controllableExpenseMapper.delete_controllable_expense_branch_type(controllableExpense.getId());

        for (ControllableExpenseType controllableExpenseType : branchFinancialAudit.getControllableExpense()
                .getControllableExpenseType()) {
            controllableExpenseMapper.create_controllable_expense_branch_type(controllableExpense.getId(),
                    controllableExpenseType.getId());
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
        String last_case_number = controllableExpenseMapper.getLatestCaseNumber();
        if (last_case_number != null) {
            case_number_number = Integer.parseInt(last_case_number.replaceAll("[^0-9]", ""));
        }
        String case_number = category + (case_number_number + 1);
        return case_number;
    }

    public List<BranchFinancialAudit> getDraftedAudits(Long auditor_id) {
        return controllableExpenseMapper.getDraftedAudits(auditor_id);
    }

    public List<BranchFinancialAudit> getOnProgressAudits(Long auditor_id) {
        return controllableExpenseMapper.getOnProgressAudits(auditor_id);
    }

    public List<BranchFinancialAudit> getPassedAudits(Long auditor_id) {
        return controllableExpenseMapper.getPassedAudits(auditor_id);
    }

    public void passControllableExpenseBranch(BranchFinancialAudit branchFinancialAudit) {
        controllableExpenseMapper.pass_controllable_expense_branch(branchFinancialAudit.getId());

    }

    public void passMultiControllableExpenseBranch(List<BranchFinancialAudit> branchFinancialAudits) {
        for (BranchFinancialAudit branchFinancialAudit : branchFinancialAudits) {
            controllableExpenseMapper.pass_controllable_expense_branch(branchFinancialAudit.getId());
        }
    }

    public void backPassedControllableExpenseBranch(BranchFinancialAudit branchFinancialAudit) {
        controllableExpenseMapper.backPassed_controllable_expense_branch(branchFinancialAudit.getId());
    }

    public void backMultiPassedControllableExpenseBranch(List<BranchFinancialAudit> branchFinancialAudits) {
        for (BranchFinancialAudit branchFinancialAudit : branchFinancialAudits) {
            controllableExpenseMapper.backPassed_controllable_expense_branch(branchFinancialAudit.getId());
        }
    }

    public void deleteControllableExpenseBranch(Long id) {

        controllableExpenseMapper.delete_controllable_expense_branch(id);

    }

    public void deleteMultiControllableExpenseBranch(List<BranchFinancialAudit> branchFinancialAudits) {
        for (BranchFinancialAudit branchFinancialAudit : branchFinancialAudits) {

            controllableExpenseMapper.delete_controllable_expense_branch(branchFinancialAudit.getId());

        }

    }
}
