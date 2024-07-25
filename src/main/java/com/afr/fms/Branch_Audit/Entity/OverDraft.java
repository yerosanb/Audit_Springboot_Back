package com.afr.fms.Branch_Audit.Entity;

import com.afr.fms.Admin.Entity.Branch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OverDraft {
    private Long id;
    private Float approved_amount;
    private Float overdrawn_balance;
    private Float difference;
    private Float penalty_charge;
    private String expiry_date;
    private Branch branch;
    private String audit_type;

}
