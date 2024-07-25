package com.afr.fms.Branch_Audit.Entity;

import java.util.List;

import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAudit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompiledRegionalAudit {
    private Long id;
    private User compiler;
    private User approver;
    private String case_number;
    private String audit_type;
    private String finding;
    private String impact;
    private String recommendation;
    private boolean review_status;
    private boolean approve_status;
    private String compiled_date;
    private String approved_date;
    private String reviewed_date;
    private boolean compiled_status;
    private List<Long> compiled_audits;
    private List<ChangeTrackerBranchAudit>change_tracker_branch_audit;
    private List<CompiledBranchAudit> compiledBranchAudits;
}
