package com.afr.fms.Branch_Audit.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LongOutstandingItemsEntity  {
    private Long id;
    private Long branch_audit_id;
    private String outstanding_item;
    private Double less_than_90_amount;
    private int less_than_90_number;
    private Double greater_than_90_amount;
    private int greater_than_90_number;
    private Double greater_than_180_amount;
    private int greater_than_180_number;
    private Double greater_than_365_amount;
    private int greater_than_365_number;
    private Double trial_balance;
    private Double total_amount;
    private Double difference;
    private String justification;
    private String cash_type;
    private String fcy;
    private String selected_item_age;

}