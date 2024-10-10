package com.afr.fms.Branch_Audit.Auditor.Rectification.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.afr.fms.Branch_Audit.Auditor.Rectification.Mapper.AuditRectificationMapper;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;

@Service

public class AuditRectificationService {

    @Autowired
    private AuditRectificationMapper auditRectificationMapper;

    public void add_response(List<BranchFinancialAudit> branchFinancialAudit) {
        String action_plan = branchFinancialAudit.get(0).getAction_plan();
        for (BranchFinancialAudit branchFinancialAudit2 : branchFinancialAudit) {
            if (branchFinancialAudit.size() > 1) {
                branchFinancialAudit2.setAction_plan(action_plan);
                auditRectificationMapper.addAuditeeResponse(branchFinancialAudit2);
            } else {
                auditRectificationMapper.addAuditeeResponse(branchFinancialAudit2);

            }
        }
        for (BranchFinancialAudit branchFinancialAudit2 : branchFinancialAudit) {
            if (branchFinancialAudit.get(0).isFile_flag()) {
                auditRectificationMapper.remove_attached_files(branchFinancialAudit2.getId());
                for (String file_url : branchFinancialAudit.get(0).getBmFileUrls()) {
                    auditRectificationMapper.add_attached_files(branchFinancialAudit2.getId(), file_url);
                }
            }
        }

    }

    public void rectifyAuditeeResponse(BranchFinancialAudit branchFinancialAudit) {
        auditRectificationMapper.rectifyAuditeeResponse(branchFinancialAudit);
        updateRectificationStatusCompiledAudits(branchFinancialAudit, 1);
    }

    public void updateRectificationStatusCompiledAudits(BranchFinancialAudit branchFinancialAudit, int status) {
        auditRectificationMapper.updateRectificationStatusCompiledAudits(branchFinancialAudit.getId(), status);
    }

}