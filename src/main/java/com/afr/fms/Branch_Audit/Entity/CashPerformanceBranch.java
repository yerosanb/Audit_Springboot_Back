package com.afr.fms.Branch_Audit.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CashPerformanceBranch {
    private Long id;
    private Long branch_audit_id;
    private String accountable_staff;
    private Double amount_shortage;
    private Double amount_excess;
    private String action_taken;
    private String cash_type;
    private String fcy;

}
