package com.afr.fms.Branch_Audit.Entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbnormalBalance {
    private Long id;
    private Long branch_audit_id;
    private String account_affected;
    private Double debit;
    private Double difference;
    private Double credit;
    private String cash_type;
    private String fcy;

}
