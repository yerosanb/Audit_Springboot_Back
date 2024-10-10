package com.afr.fms.Branch_Audit.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NegotiableInstrumentBranch {
    private Long id;
    private Long branch_audit_id;
    private String account_holder;
    private String account_number;
    private String printed_date;
    private Double amount;
    private Double difference;
    private String category;
    private String ck_type;
    private String ck_range;
    private Long quantity;
    private Double unit_price;
    private Double trial_balance;
    private String action_taken;
    private Long negotiable_stock_item_id;
    private NegotiableStockItem negotiableStockItem;
    private String cash_type;
    private String fcy;

}
