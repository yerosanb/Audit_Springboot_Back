package com.afr.fms.Branch_Audit.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class MemorandumContigent {
    private Long id;
    private Long branch_audit_id;
    private Double memorandom_amount;
    private Double difference;
    private Double contingent_amount;
    private String cash_type;
    private String fcy;

}
