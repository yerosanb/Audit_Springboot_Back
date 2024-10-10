package com.afr.fms.Branch_Audit.Common.OperationalDescripancyPooledData;

import java.util.List;

import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OperationalDescripancyPooledData {
    private Long id;
    private List<BranchFinancialAudit> branchFinancialAudits;
    private BranchFinancialAudit audit;
    private Long branch_financial_audit_id;
    private String pooled_date;
    private String cash_type;
    private String fcy;
    private boolean status;
    private Double pool_amount;
    private Long pooler;
}
