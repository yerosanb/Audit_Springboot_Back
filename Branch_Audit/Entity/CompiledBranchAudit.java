package com.afr.fms.Branch_Audit.Entity;

import java.util.List;

import com.afr.fms.Admin.Entity.Region;
import com.afr.fms.Admin.Entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompiledBranchAudit {
    private Long id;
    private User compiler;
    private Region region;
    private String case_number;
    private String audit_type;
    private String finding;
    private String impact;
    private String recommendation;
    private boolean review_status;
    private int approve_status;
    private String compiled_date;
    private String approved_date;
    private String reviewed_date;
    private boolean compiled_status;
    private boolean compiler_submitted;
    private boolean division_compiler_submitted;
    private String compiler_submitted_date;
    private boolean division_compiler_status;
    private List<Long> compiled_audits;
    private List<BranchFinancialAudit> branchFinancialAudits;
    private int outstanding_date;

}
