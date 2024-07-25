package com.afr.fms.Branch_Audit.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashManagementBranch {
    private Long id;
    private Long branch_audit_id;
    private Double average_cash_holding;
    private Double difference;
    private Double branch_cash_set_limit;
    private Double mid_rate_fcy;
    private String cash_type;
    private String fcy;
}
