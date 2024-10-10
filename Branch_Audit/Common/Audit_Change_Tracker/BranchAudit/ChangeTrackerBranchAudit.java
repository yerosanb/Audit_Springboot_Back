package com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit;

import com.afr.fms.Admin.Entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeTrackerBranchAudit {
    private Long id;
    private User changer;
    private String change;
    private Long audit_id;
    private Long compiled_audit_id;
    private String change_date;
    private String content_type;
}
