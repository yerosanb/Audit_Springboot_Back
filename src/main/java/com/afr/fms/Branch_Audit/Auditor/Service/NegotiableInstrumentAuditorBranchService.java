package com.afr.fms.Branch_Audit.Auditor.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper;
import com.afr.fms.Branch_Audit.Auditor.Mapper.NegotiableInstrumentBranchMapper;
import com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAudit;
import com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditService;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Entity.NegotiableInstrumentBranch;

@Service
public class NegotiableInstrumentAuditorBranchService {

    @Autowired
    private NegotiableInstrumentBranchMapper negotiableInstrumentBranchMapper;
    @Autowired
    private IncompleteAccountBranchMapper auditMapper;

    @Autowired
    private ChangeTrackerBranchAuditService changeTrackerService;

    public void createNegotiableInstrumentBranch(BranchFinancialAudit branchFinancialAudit) {

        branchFinancialAudit.setCase_number(generateCaseNumber(branchFinancialAudit.getCategory()));

        Long inspection_id;

        if (branchFinancialAudit.getFinding_detail() == null) {
            inspection_id = negotiableInstrumentBranchMapper.createBranchFinantialAudit(branchFinancialAudit);

        } else {
            inspection_id = negotiableInstrumentBranchMapper
                    .createBranchFinantialAuditWithFindingDetail(branchFinancialAudit);

        }
        branchFinancialAudit.getNegotiableInstrument().setBranch_audit_id(inspection_id);

        negotiableInstrumentBranchMapper
                .createNegotiableInstrumentBranch(branchFinancialAudit.getNegotiableInstrument());

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

    public void updateNegotiableInstrumentBranch(BranchFinancialAudit branchFinancialAudit) {

        NegotiableInstrumentBranch negotiableInstrumentBranch = branchFinancialAudit.getNegotiableInstrument();
        negotiableInstrumentBranch.setBranch_audit_id(branchFinancialAudit.getId());

        if (branchFinancialAudit.getFinding_detail() == null) {
            negotiableInstrumentBranchMapper.updateBranchFinantialAudit(branchFinancialAudit);

        } else {
            negotiableInstrumentBranchMapper.updateBranchFinantialAuditWithFindingDetail(branchFinancialAudit);
        }
        negotiableInstrumentBranchMapper.updatenegotiableInstrumentBranch(negotiableInstrumentBranch);
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
        String last_case_number = negotiableInstrumentBranchMapper.getLatestCaseNumber();
        if (last_case_number != null) {
            case_number_number = Integer.parseInt(last_case_number.replaceAll("[^0-9]", ""));
        }
        String case_number = category + (case_number_number + 1);
        return case_number;
    }

    public List<BranchFinancialAudit> getDraftedAudits(Long auditor_id) {
        return negotiableInstrumentBranchMapper.getDraftedAudits(auditor_id);
    }

    public List<BranchFinancialAudit> getOnProgressAudits(Long auditor_id) {
        return negotiableInstrumentBranchMapper.getOnProgressAudits(auditor_id);
    }

    public List<BranchFinancialAudit> getPassedAudits(Long auditor_id) {
        return negotiableInstrumentBranchMapper.getPassedAudits(auditor_id);
    }

    public void passNegotiableInstrumentBranch(BranchFinancialAudit branchFinancialAudit) {
        negotiableInstrumentBranchMapper.passNegotiableInstrument(branchFinancialAudit.getId());

    }

    public void passMultiNegotiableInstrumentBranch(List<BranchFinancialAudit> branchFinancialAudits) {
        for (BranchFinancialAudit branchFinancialAudit : branchFinancialAudits) {
            negotiableInstrumentBranchMapper.passNegotiableInstrument(branchFinancialAudit.getId());
        }
    }

    public void backPasseNegotiableInstrumentBranch(BranchFinancialAudit branchFinancialAudit) {
        negotiableInstrumentBranchMapper.backPassedNegotiableInstrument(branchFinancialAudit.getId());
    }

    public void backMultiNegotiableInstrumentBranch(List<BranchFinancialAudit> branchFinancialAudits) {
        for (BranchFinancialAudit branchFinancialAudit : branchFinancialAudits) {
            negotiableInstrumentBranchMapper.backPassedNegotiableInstrument(branchFinancialAudit.getId());
        }
    }

    public void deleteNegotiableInstrumentBranch(Long id) {

        negotiableInstrumentBranchMapper.deleteNegotiableInstrument(id);

    }

    public void deleteMultiNegotiableInstrumentBranch(List<BranchFinancialAudit> branchFinancialAudits) {
        for (BranchFinancialAudit branchFinancialAudit : branchFinancialAudits) {

            negotiableInstrumentBranchMapper.deleteNegotiableInstrument(branchFinancialAudit.getId());

        }

    }
}
