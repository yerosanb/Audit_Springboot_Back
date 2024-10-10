
package com.afr.fms.Branch_Audit.Entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuspenseAccountBranch {
    private Long id;
    private Long branch_audit_id;
    private String tracer_date;
    private Double difference;
    private Double balance_per_tracer;
    private Double balance_per_trial_balance;
    private List<SuspenseAccountType> suspenseAccountType;
    private String cash_type;
    private String fcy;

}
