package com.afr.fms.Branch_Audit.BranchManager.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Branch_Audit.BranchManager.Mapper.IncompleteAcountBranchManagerMapper;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;

@Service
public class IncompleteAccountBranchManagerService {

    @Autowired
    private IncompleteAcountBranchManagerMapper incompleteAcountBranchManagerMapper;

    public List<BranchFinancialAudit> getPendingAudits(Long branch_id) {
        return incompleteAcountBranchManagerMapper.getPendingAudits(branch_id);
    }

    public List<BranchFinancialAudit> getRectifiedAudits(Long auditee_id) {
        return incompleteAcountBranchManagerMapper.getRectifiedAudits(auditee_id);
    }

    public List<BranchFinancialAudit> getRespondedAudits(Long auditee_id) {
        return incompleteAcountBranchManagerMapper.getRespondedAudits(auditee_id);
    }

    public void branchManagerGiveResponse(BranchFinancialAudit branchFinantialAudit) {

        incompleteAcountBranchManagerMapper.branchManagerGiveResponse(branchFinantialAudit);

    }

    public void branchManagerGiveResponseSelcted(List<BranchFinancialAudit> branchFinantialAudits) {

        User auditee = branchFinantialAudits.get(0).getAuditee();
        for (BranchFinancialAudit branchFinancialAudit : branchFinantialAudits) {
            branchFinancialAudit.setAuditee(auditee);
            incompleteAcountBranchManagerMapper.branchManagerGiveResponse(branchFinancialAudit);

        }
    }
}