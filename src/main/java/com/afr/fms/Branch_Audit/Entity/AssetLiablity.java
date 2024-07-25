package com.afr.fms.Branch_Audit.Entity;

import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class AssetLiablity {

    private Long id;
    private Long branch_audit_id;
    private Double asset_amount;
    private Double liability_amount;
    private Double difference;
  
    private String cash_type;
    private String fcy;
}
