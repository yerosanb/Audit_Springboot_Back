package com.afr.fms.Branch_Audit.Entity;
import com.afr.fms.Admin.Entity.Branch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncompleteAccountBranch {
    private Long id;
    private Long branch_audit_id;
    private String account_type;
    private String customer_name;
    private String account_number;
    private String account_opened_date;
    private double account_opened_amount;
    private String opened_by;
    private String approved_by;
    private Branch branch;
    private String fcy;
}
