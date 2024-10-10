package com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChangeTrackerBranchAuditService {
    
    @Autowired
    private ChangeTrackerBranchAuditMapper changeTrackerBranchAuditMapper;

    public void insertChanges(ChangeTrackerBranchAudit changeTrackerBranchAudit) {
        changeTrackerBranchAuditMapper.insertChange(changeTrackerBranchAudit);
    } 
    
    public void insertCompiledAuditChanges(ChangeTrackerBranchAudit changeTrackerBranchAudit) {
        changeTrackerBranchAuditMapper.insertCompiledAuditChange(changeTrackerBranchAudit);
    }

    // public void insertChangeINS(ChangeTrackerBranchAudit changeTrackerBranchAudit) {
    //     changeTrackerBranchAuditMapper.insertChangeINS(changeTrackerBranchAudit);
    // }
   
    public List<ChangeTrackerBranchAudit> getChanges(Long audit_id) {
        return changeTrackerBranchAuditMapper.getChanges(audit_id);
    }
}
