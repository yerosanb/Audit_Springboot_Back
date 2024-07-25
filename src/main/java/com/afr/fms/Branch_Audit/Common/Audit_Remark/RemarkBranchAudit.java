package com.afr.fms.Branch_Audit.Common.Audit_Remark;

import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Entity.CompiledBranchAudit;
import com.afr.fms.Branch_Audit.Entity.CompiledRegionalAudit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RemarkBranchAudit {
    private Long id;
    private BranchFinancialAudit branchFinancialAudit;
    private CompiledBranchAudit compiledBranchAudit;
    private CompiledRegionalAudit compiledRegionalAudit;
    private User sender;
    private User reciever;
    private String message;
    private String remark_date;
    private boolean status;
    private boolean rejected;
}
