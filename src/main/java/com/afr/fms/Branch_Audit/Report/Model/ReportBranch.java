package com.afr.fms.Branch_Audit.Report.Model;

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
public class ReportBranch {

  private Long id;
  private String case_number;
  private String region_name;
  private String branch_name;
  private String auditor_name;
  private String audit_report_date;
  private String auditee_response;
  private String audit_finding_status;
  private String rectified_on;
  private Double card_issued_branch;
  private Double card_distributed_to_customer;
  private Double return_to_card_issuance_unit;
  private Double remaining_card_at_branch;

  private String audit_finding;
  private String audit_impact;
  private String auditor_recommendation;

  private String finding_regional_reviewer;
  private String impact_regional_reviewer;
  private String recommendation_regional_reviewer;

  private String finding_division_reviewer;
  private String impact_division_reviewer;
  private String recommendation_division_reviewer;

  // AbnormalBalance
  private String account_affected;
  private Double debit_amount;
  private Double credit_amount;
  private Double difference_amount;

  // AssetLiability
  private Double asset_amount;
  private Double liability_amount;
  private Double difference;

  // ControllableExpense
  private Double variation;
  private String period;
  private Double budget_per_plan;
  private Double actual_balance;

  // DormantInactiveAccount
  private String account_number;
  private Date deactivated_date;
  private Double amount;
  private String entry_passed_by;
  private String entry_approved_by;
  private String account_type;

  // IncompleteDocument
  // private String account_type;
  private String customer_name;
  // private String account_number;
  private String account_opened_date;
  private Double account_opened_amount;
  private String opened_by;
  private String approved_by;

  // SuspenseAccount
  // private Long difference;
  private Date tracer_date;
  private Double balance_per_tracer;
  private Double balance_per_trial_balance;

  // long
  private String list_items[];
  private Double item_number;
  private Double age;

  // loan
  // private Double amount;
  // private String account_number;
  private String account_holder_name;
  private String loan_type;
  private String loan_disburse_date;
  private String borrower_name;
  private String amount_granted;
  private String interest_rate;
  private String loan_status;
  private String arrears;
  private String overdraft;
  private String due_date;
  private Double approved_amount;
  private Double overdrawn_balance;
//   private Double difference;
  private Double penality_charge_collection;

  /*
	 * + "	od., "
					+ "	od., "
					+ "	od., "
					+ "	od., "

	*/

  // operational
  private String min_amount;
  private String max_amount;
  private String transaction_date;

  // negotiable
  private String date_printed;
  private String type_of_ck;
  private String ck_range;
  private Double quantity;
  private Double unit_price;
  private Double trial_balance_amount;

  // memorandum
  private Double memorandum_amount;
  private Double contingent_amount;
  // private Double difference;

  // cashCount
  private String cash_count_type;
  private Double actual_cash_count;
  private Double trial_balance;
  private Double atm_amount_branch;
  private Double atm_amount_lobby;

  // CashManagement
  private Double average_cash_holding;
  private Double branch_cash_set_limit;
  private Double mid_rate_fcy;
  // private Double difference;
  private String cash_type;

  // CashExcessOrShortage
  private Double amount_shortage;
  private Double amount_excess;
  private String action_taken;

  // long

  private Double less_than_90_amount;
  private Double greater_than_90_amount;
  private Double greater_than_180_amount;
  private Double greater_than_365_amount;
  private Double less_than_90_number;
  private Double greater_than_90_number;
  private Double greater_than_180_number;
  private Double greater_than_365_number;

  // private Double difference;

  private String outstanding_item;
  private String justification;
  // private String trial_balance;
  // private String cash_type;
  private Double total_amount;

  private String fcy;
  private String accountable_staff;
  private String expense_type;
  private String stock_type;
  private String account_name;

  

  

  

  
  /*
	 *   less_than_90_amount?: number;
  greater_than_90_amount?: number;
  greater_than_180_amount?: number;
  greater_than_365_amount?: number;

  less_than_90_number?: number;
  greater_than_90_number?: number;
  greater_than_180_number?: number;
  greater_than_365_number?: number;


  outstanding_item?: String;
  justification?: String;
  trial_balance?: String;
  total_amount?: String;
  difference?: String;
  cash_type?: String;
  fcy?: String;
	*/

}
