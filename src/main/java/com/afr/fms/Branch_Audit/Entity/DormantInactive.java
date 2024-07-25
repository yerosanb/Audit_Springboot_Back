package com.afr.fms.Branch_Audit.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DormantInactive {
    private Long id;
    private Long branch_audit_id;
    private String acount_name;
    private String account_number;
    private String deactivated_date;
    private String entry_passed_by;
    private String entry_approved_by;
    private Double amount;
    private String account_type;
    private String cash_type;
    private String fcy;

}
