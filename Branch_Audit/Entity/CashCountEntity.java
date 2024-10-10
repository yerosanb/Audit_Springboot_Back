package com.afr.fms.Branch_Audit.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashCountEntity  {
    private Long id;
    private Long branch_audit_id;
    private String accountable_staff;
    private Double actual_cash_count;
    private Double trial_balance;
    private Double difference;
    private String cash_count_type; 
    private String fcy;
}