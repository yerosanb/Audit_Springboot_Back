package com.afr.fms.Branch_Audit.BranchManager.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Branch_Audit.BranchManager.Mapper.StatusOfAuditBranchManagerMapper;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;

@Service
public class StatusOfAuditBranchManagerService {
    @Autowired StatusOfAuditBranchManagerMapper mapper;

     public List<BranchFinancialAudit> getPendingAudits(Long branch_id) {
        try {
            return mapper.getPendingAudits(branch_id);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        
    }
    
    
}
