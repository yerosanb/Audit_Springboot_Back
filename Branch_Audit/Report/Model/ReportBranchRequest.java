package com.afr.fms.Branch_Audit.Report.Model;

import com.afr.fms.Admin.Entity.Branch;
import com.afr.fms.Admin.Entity.Region;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReportBranchRequest {

  private Long region;
  private Long branch;
  private String finding_status;
  private String[] rectification_date_range;
  private String[] date_range;
  private String audit_finding;
  private String category_of_discrepancy;
  private Double card_distributed_to_customer_min;
  private Double card_distributed_to_customer_max;
  private Double card_issued_branch_min;
  private Double card_issued_branch_max;
  private String rectification_status;
  private String single_filter_info;

  private Long user_id;
  private String[] user_roles;
  private Long user_region_id;

  // AbnormalBalance
  private String account_affected;
  private Double debit_amount_min;
  private Double debit_amount_max;
  private Double credit_amount_min;
  private Double credit_amount_max;

  // SuspenseAccount
  private String[] tracer_date_range;
  private Double balance_per_tracer_min;
  private Double balance_per_tracer_max;
  private Double balance_per_trial_balance_min;
  private Double balance_per_trial_balance_max;
  private String suspense_account_type;

  // DormantInactiveAccount
  private String account_type;
  private Double amount_min;
  private Double amount_max;
  private String account_number;

  // AssetLiability
  private Double asset_amount_min;
  private Double asset_amount_max;
  private Double liability_amount_min;
  private Double liability_amount_max;

  // ControllableExpense
  private Double variation_min;
  private Double variation_max;
  private Double budget_per_plan_min;
  private Double budget_per_plan_max;
  private Double actual_balance_min;
  private Double actual_balance_max;
  private String controllable_expense_type;

  // IncompleteDocument
  private String[] account_opened_date_range;
  private Double account_opened_amount_max;
  private Double account_opened_amount_min;
  // private String account_number;

  private Double amount;

  // loan
  private String loan_type;
  // private String loan_disburse_date;
  private String[] loan_disburse_date;
  // private String cash_type;

  private String borrower_name;
  private Double granted_amount_min;
  private Double granted_amount_max;

  // CashCount
  private String cash_count_type;
  private Double actual_cash_count_min;
  private Double actual_cash_count_max;
  private Double trial_balance_min;
  private Double trial_balance_max;

  // Memorandum
  private Double difference;
  private Double min_amount;
  private Double max_amount;
  private Double contingent_amount_min;
  private Double contingent_amount_max;
  private Double memorandum_amount_min;
  private Double memorandum_amount_max;

  // CashManagement
  private Double mid_rate_fcy_min;
  private Double mid_rate_fcy_max;
  private Double average_cash_holding_min;
  private Double average_cash_holding_max;
  private String cash_type;

  // CashExcessOrShortage
  private Double amount_shortage_min;
  private Double amount_shortage_max;
  private Double amount_excess_min;
  private Double amount_excess_max;

  //new added operation
  private String transaction_date;
  private Double item_number; //for longoutstanding
  private Double total_amount_min;
  private Double total_amount_max;

  private Double less_than_90_amount_min;
  private Double less_than_90_amount_max;

  private Double greater_than_90_amount_min;
  private Double greater_than_90_amount_max;

  private Double greater_than_180_amount_min;
  private Double greater_than_180_amount_max;

  private Double greater_than_365_amount_min;
  private Double greater_than_365_amount_max;

  private String outstanding_item;
  private String[] selected_item_age;

  //ALL
  private String ing;

  //NEGOTIABLE
  private String stock_item_type;
  
  //the compilation date
  private String[] regional_compiler_compiled_date;
  private String[] division_compiler_compiled_date;
  private String[] approved_date;

}
