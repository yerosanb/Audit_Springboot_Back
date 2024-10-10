package com.afr.fms.Branch_Audit.Entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationalDescripancyBranch {
    private Long id;
    private Long branch_audit_id;
    private Long operational_descripancy_category_id;
    private String acount_holder_name;
    private String account_number;
    private String transaction_date;
    private Double amount;
    private  OperationalDescripancyCategory   operationalDescripancyCategory;
    private String cash_type;
    private String fcy;


}

