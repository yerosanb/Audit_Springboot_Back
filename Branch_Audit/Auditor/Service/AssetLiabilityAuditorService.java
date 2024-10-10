package com.afr.fms.Branch_Audit.Auditor.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Admin.Mapper.UserMapper;
import com.afr.fms.Branch_Audit.Auditor.Mapper.AssetLiabilityAuditorMapper;
import com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper;
import com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAudit;
import com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditService;
import com.afr.fms.Branch_Audit.Entity.AssetLiablity;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;

@Service
public class AssetLiabilityAuditorService {
    @Autowired
    private AssetLiabilityAuditorMapper assetLiabilityAuditorMapper;

    @Autowired
    private IncompleteAccountBranchMapper auditMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ChangeTrackerBranchAuditService changeTrackerService;

    public void createAssetLiability(BranchFinancialAudit branchFinancialAudit) {

        branchFinancialAudit.setCase_number(generateCaseNumber(branchFinancialAudit.getCategory()));
        Long inspection_id;

        if (branchFinancialAudit.getFinding_detail() == null) {
            inspection_id = assetLiabilityAuditorMapper
                    .createBranchFinantialAuditwithoutFindingDetail(branchFinancialAudit);
        } else {
            inspection_id = assetLiabilityAuditorMapper.createBranchFinantialAudit(branchFinancialAudit);
        }
        branchFinancialAudit.getAssetLiabilityBranch().setBranch_audit_id(inspection_id);

        assetLiabilityAuditorMapper.createAssetLiability(branchFinancialAudit.getAssetLiabilityBranch());
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

    public void updateAssetLiability(BranchFinancialAudit branchFinancialAudit) {

        if (branchFinancialAudit.getFinding_detail() != null) {
            assetLiabilityAuditorMapper.updateBranchFinantialAuditWithFindingDetail(branchFinancialAudit);

        } else {
            assetLiabilityAuditorMapper.updateBranchFinantialAudit(branchFinancialAudit);
        }

        AssetLiablity assetLiablity = branchFinancialAudit.getAssetLiabilityBranch();
        assetLiablity.setBranch_audit_id(branchFinancialAudit.getId());
        assetLiabilityAuditorMapper.updateAssetLiability(assetLiablity);

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
        String last_case_number = assetLiabilityAuditorMapper.getLatestCaseNumber();
        if (last_case_number != null) {
            case_number_number = Integer.parseInt(last_case_number.replaceAll("[^0-9]", ""));
        }
        String case_number = category + (case_number_number + 1);
        return case_number;
    }

    public List<BranchFinancialAudit> getDraftedAudits(Long auditor_id) {
        return assetLiabilityAuditorMapper.getDraftedAudits(auditor_id);
    }

    public List<BranchFinancialAudit> getOnProgressAudits(Long auditor_id) {
        return assetLiabilityAuditorMapper.getOnProgressAudits(auditor_id);
    }

    public List<BranchFinancialAudit> getPassedAudits(Long auditor_id) {
        return assetLiabilityAuditorMapper.getPassedAudits(auditor_id);
    }

    public void passAssetLiability(BranchFinancialAudit branchFinancialAudit) {
        assetLiabilityAuditorMapper.passAssetLiability(branchFinancialAudit.getId());

    }

    public void passMultiAssetLiability(List<BranchFinancialAudit> branchFinancialAudits) {
        for (BranchFinancialAudit branchFinancialAudit : branchFinancialAudits) {
            assetLiabilityAuditorMapper.passAssetLiability(branchFinancialAudit.getId());
        }
    }

    public void backPassedAssetLiability(Long id) {
        assetLiabilityAuditorMapper.backPassedAssetLiability(id);
    }

    public void backMultiPassedAssetLiability(List<BranchFinancialAudit> branchFinancialAudits) {
        for (BranchFinancialAudit branchFinancialAudit : branchFinancialAudits) {
            assetLiabilityAuditorMapper.backPassedAssetLiability(branchFinancialAudit.getId());
        }
    }

    public void deleteAssetLiability(Long id) {

        assetLiabilityAuditorMapper.deleteAssetLiability(id);

    }

    public void deleteMultiAssetLiability(List<BranchFinancialAudit> branchFinancialAudits) {
        for (BranchFinancialAudit branchFinancialAudit : branchFinancialAudits) {

            assetLiabilityAuditorMapper.deleteAssetLiability(branchFinancialAudit.getId());

        }

    }

}
