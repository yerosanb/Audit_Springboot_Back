package com.afr.fms.Branch_Audit.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ControllableExpense {

    private Long id;
    private Long branch_audit_id;
    private Double variation;
    private Double budget_per_plan;
    private Double actual_balance;
    private String period;
    private List<ControllableExpenseType> controllableExpenseType;
    private String cash_type;
    private String fcy;

}
