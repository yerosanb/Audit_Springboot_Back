package com.afr.fms.Branch_Audit.Entity;

import com.afr.fms.Admin.Entity.Branch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanAndAdvance {

    private Long id;
    private Long branch_audit_id;
    private Long over_draft_id;
    private String loan_disbursed_date;
    private String borrower_name;
    private String account_number;
    private String loan_type;
    private Float amount_granted;
    private Float interest_rate;
    private String due_date;
    private Float arrears;
    private String loan_status;
    private Branch branch;
    private OverDraft overDraft;
    private String cash_type;
    private String fcy;
    private String category;

}
