package com.afr.fms.Branch_Audit.Auditor.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Admin.Mapper.UserMapper;
import com.afr.fms.Branch_Audit.Auditor.Mapper.LoanAndAdvanceMapper;
import com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAudit;
import com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditService;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Entity.LoanAndAdvance;
import com.afr.fms.Branch_Audit.Entity.OverDraft;

@Service
public class LoanAndAdvanceService {

    @Autowired
    private LoanAndAdvanceMapper loanMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ChangeTrackerBranchAuditService changeTrackerService;

    public List<BranchFinancialAudit> getOnProgressAudits(Long auditor_id) {
        return loanMapper.getOnProgressAudits(auditor_id);
    }

    public void backBAFinding(Long id) {
        loanMapper.backBAFinding(id);
    }

    public void backSelectedBAFinding(List<BranchFinancialAudit> BranchFinancialAudits) {
        for (BranchFinancialAudit BranchFinancialAudit : BranchFinancialAudits) {
            loanMapper.backBAFinding(BranchFinancialAudit.getId());
        }
    }

    public List<BranchFinancialAudit> getAuditsForAuditor(Long auditor_id) {
        return loanMapper.getAuditsForAuditor(auditor_id);
    }

    public void createLoanAndAdvance(BranchFinancialAudit audit) {
        User user = userMapper.getAuditorById(audit.getAuditor().getId());
        audit.setCategory(user.getCategory());
        audit.setCase_number(generateCaseNumber(audit.getCategory()));
        Long audit_id;

        if (audit.getFinding_detail() == null) {
            audit_id = loanMapper.createBAFinding(audit);

        } else {
            audit_id = loanMapper.createBranchFinantialWithFindingDetailAFinding(audit);

        }
        
        OverDraft overDraft = audit.getLoanAndAdvance().getOverDraft();
        overDraft.setBranch(user.getBranch());
        Long over_draft = loanMapper.createOverDraft(overDraft);

        LoanAndAdvance loanAdvance = audit.getLoanAndAdvance();
        loanAdvance.setBranch_audit_id(audit_id);
        loanAdvance.setOver_draft_id(over_draft);
        loanAdvance.setBranch(user.getBranch());
        loanMapper.createLoanAndAdvance(loanAdvance);
        if (audit.getEdit_auditee()) {
            for (String file_name : audit.getFile_urls()) {
                loanMapper.InsertFileUrl(file_name, audit_id);
            }
        }


        for (ChangeTrackerBranchAudit changeTrackerBranchAudit : audit.getChange_tracker_branch_audit()) {
            if (changeTrackerBranchAudit != null) {
                changeTrackerBranchAudit.setAudit_id(audit_id);
                changeTrackerBranchAudit.setChanger(audit.getEditor());
                changeTrackerService.insertChanges(changeTrackerBranchAudit);
            }
        }

    }

    public void updateLoanAndAdvance(BranchFinancialAudit audit) {

        LoanAndAdvance loanAndAdvance = audit.getLoanAndAdvance();


        if (audit.getFinding_detail() == null) {
           loanMapper.updateBFA(audit);

        } else {
         loanMapper.updateBFAWithFindingDetail(audit);

        }

        loanAndAdvance.setBranch_audit_id(audit.getId());

        loanMapper.updateLoanAndAdvance(loanAndAdvance);
        OverDraft ovd = new OverDraft();
        ovd =audit .getLoanAndAdvance().getOverDraft();
        ovd.setId(audit.getLoanAndAdvance().getOver_draft_id());
        loanMapper.updateOverDraft(ovd);


    }

    public String generateCaseNumber(String category) {
        int case_number_number = 1;
        String last_case_number = loanMapper.getLatestCaseNumber();
        if (last_case_number != null) {
            case_number_number = Integer.parseInt(last_case_number.replaceAll("[^0-9]", ""));
        }
        String case_number = category + (case_number_number + 1);
        return case_number;
    }

    public List<BranchFinancialAudit> getLoanAndAdvanceOnDrafting(Long auditor_id) {

        return loanMapper.getLoanAndAdvanceOnDrafting(auditor_id);

        // List<BranchFinancialAudit> audits = new ArrayList<BranchFinancialAudit>();

        // for (BranchFinancialAudit audit :
        // loanMapper.getLoanAndAdvanceOnDrafting(auditor_id)) {
        // if (audit.getLoanAndAdvance().getOverDraft() != null) {
        // audits.add(audit);
        // }
        // }
        // return audits;
    }

    public void deleteBFA(Long id) {
        loanMapper.deleteBFA(id);
    }

    public void deleteSelectedBFA(List<BranchFinancialAudit> BranchFinancialAudits) {
        for (BranchFinancialAudit BranchFinancialAudit : BranchFinancialAudits) {
            loanMapper.deleteBFA(BranchFinancialAudit.getId());
        }
    }

    public void passBFA(Long id) {
        loanMapper.passBFA(id);
    }

    public void passSelectedBFA(List<BranchFinancialAudit> BranchFinancialAudits) {
        for (BranchFinancialAudit BranchFinancialAudit : BranchFinancialAudits) {
            loanMapper.passBFA(BranchFinancialAudit.getId());
        }
    }

}
