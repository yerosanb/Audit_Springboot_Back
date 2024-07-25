package com.afr.fms.Branch_Audit.Report.Mapper;

import com.afr.fms.Admin.Entity.Region;
import com.afr.fms.Branch_Audit.Report.Model.Branch_R;
import com.afr.fms.Branch_Audit.Report.Model.DiscrepancyCategory_R;
import com.afr.fms.Branch_Audit.Report.Model.Finding_R;
import com.afr.fms.Branch_Audit.Report.Model.ReportBranch;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

@Mapper
public interface ReportMapper {
  @SelectProvider(
    type = SqlProviderAtmCard.class,
    method = "getBranchAuditReportAtmCard"
  )
  List<ReportBranch> getBranchAuditReportAtmCard(
    @Param("region_id") Long long1,
    @Param("branch_id") Long long2,
    @Param("finding_status") String string,
    @Param("rectification_date_range") String[] strings,
    @Param("date_range") String[] strings2,
    @Param("audit_finding") String string2,
    @Param("card_distributed_to_customer_min") Double double1,
    @Param("card_distributed_to_customer_max") Double double123,
    @Param("card_issued_branch_min") Double double2,
    @Param("card_issued_branch_max") Double double234,
    @Param("rectification_status") String string4,
    @Param("single_filter_info") String single_filter_info,
    @Param("user_id") Long long3,
    @Param("user_roles") String[] strings3,
    @Param("user_region_id") Long long5,
    @Param("banking") String banking,
    @Param("regional_compiler_compiled_date") String[] regional_compiler_compiled_date,
    @Param("division_compiler_compiled_date") String[] division_compiler_compiled_date,
    @Param("approved_date") String[] approved_date
  );

  @Select("select * from branch where status = 1")
  List<Branch_R> getBranches();

  @Select("select * from region where status = 1")
  List<Region> getRegions();

  @Select("  select * from finding where identifier = #{type}")
  List<Finding_R> getFindings(String type);

  @Select(
    "select * from operational_descripancy_category where audit_type = 'branch_audit'"
  )
  List<DiscrepancyCategory_R> getDiscrepancies();

  @Select("select * from suspense_account_type")
  List<DiscrepancyCategory_R> getSuspenseAccountTypeOptions();

  @Select("select * from controllable_expense_type")
  List<DiscrepancyCategory_R> getControllableExpenseTypes();

  @SelectProvider(
    type = SqlProviderAbnormalBalance.class,
    method = "getBranchAuditReportAbnormalBalance"
  )
  List<ReportBranch> getBranchAuditReportAbnormalBalance(
    @Param("region_id") Long long1,
    @Param("branch_id") Long long2,
    @Param("finding_status") String string,
    @Param("rectification_date_range") String[] strings,
    @Param("date_range") String[] strings2,
    @Param("audit_finding") String string2,
    @Param("rectification_status") String string4,
    @Param("single_filter_info") String single_filter_info,
    @Param("user_id") Long long3,
    @Param("user_roles") String[] strings3,
    @Param("user_region_id") Long long5,
    // AbnormalBalance
    @Param("account_affected") String account_affected,
    @Param("credit_amount_min") Double credit_amount_min,
    @Param("debit_amount_min") Double debit_amount_min,
    @Param("credit_amount_max") Double credit_amount_max,
    @Param("debit_amount_max") Double debit_amount_max,
    @Param("banking") String banking,
    @Param("cash_type") String cash_type,
    
    @Param("regional_compiler_compiled_date") String[] regional_compiler_compiled_date,
    @Param("division_compiler_compiled_date") String[] division_compiler_compiled_date,
    @Param("approved_date") String[] approved_date
  );

  /////////////////////

  @SelectProvider(
    type = SqlProviderIncompleteDocument.class,
    method = "getBranchAuditReportIncompleteDocument"
  )
  List<ReportBranch> getBranchAuditReportIncompleteDocument(
    @Param("region_id") Long long1,
    @Param("branch_id") Long long2,
    @Param("finding_status") String string,
    @Param("rectification_date_range") String[] strings,
    @Param("date_range") String[] strings2,
    @Param("audit_finding") String string2,
    @Param("rectification_status") String string4,
    @Param("single_filter_info") String single_filter_info,
    @Param("user_id") Long long3,
    @Param("user_roles") String[] strings3,
    @Param("user_region_id") Long long5,
    // IncompleteDocument
    @Param("account_opened_date_range") String[] account_opened_date_range,
    @Param("account_opened_amount_max") Double account_opened_amount_max,
    @Param("account_opened_amount_min") Double account_opened_amount_min,
    @Param("account_number") String account_number,
    @Param("banking") String banking,
    @Param("cash_type") String cash_type,
    @Param("regional_compiler_compiled_date") String[] regional_compiler_compiled_date,
    @Param("division_compiler_compiled_date") String[] division_compiler_compiled_date,
    @Param("approved_date") String[] approved_date
  );

  @SelectProvider(
    type = SqlProviderControllableExpense.class,
    method = "getBranchAuditReportControllableExpense"
  )
  List<ReportBranch> getBranchAuditReportControllableExpense(
    @Param("region_id") Long long1,
    @Param("branch_id") Long long2,
    @Param("finding_status") String string,
    @Param("rectification_date_range") String[] strings,
    @Param("date_range") String[] strings2,
    @Param("audit_finding") String string2,
    @Param("rectification_status") String string4,
    @Param("single_filter_info") String single_filter_info,
    @Param("user_id") Long long3,
    @Param("user_roles") String[] strings3,
    @Param("user_region_id") Long long5,
    // ControllableExpense
    @Param("variation_min") Double variation_min,
    @Param("variation_max") Double variation_max,
    @Param("budget_per_plan_min") Double budget_per_plan_min,
    @Param("budget_per_plan_max") Double budget_per_plan_max,
    @Param("actual_balance_min") Double actual_balance_min,
    @Param("actual_balance_max") Double actual_balance_max,
    @Param("controllable_expense_type") String controllable_expense_type,
    @Param("banking") String banking,
    @Param("cash_type") String cash_type,
    @Param("regional_compiler_compiled_date") String[] regional_compiler_compiled_date,
    @Param("division_compiler_compiled_date") String[] division_compiler_compiled_date,
    @Param("approved_date") String[] approved_date
  );

  @SelectProvider(
    type = SqlProviderAssetLiability.class,
    method = "getBranchAuditReportAssetLiability"
  )
  List<ReportBranch> getBranchAuditReportAssetLiability(
    @Param("region_id") Long long1,
    @Param("branch_id") Long long2,
    @Param("finding_status") String string,
    @Param("rectification_date_range") String[] strings,
    @Param("date_range") String[] strings2,
    @Param("audit_finding") String string2,
    @Param("rectification_status") String string4,
    @Param("single_filter_info") String single_filter_info,
    @Param("user_id") Long long3,
    @Param("user_roles") String[] strings3,
    @Param("user_region_id") Long long5,
    // AssetLiability
    @Param("asset_amount_min") Double asset_amount_min,
    @Param("asset_amount_max") Double asset_amount_max,
    @Param("liability_amount_min") Double liability_amount_min,
    @Param("liability_amount_max") Double liability_amount_max,
    @Param("banking") String banking,
    @Param("cash_type") String cash_type,
    @Param("regional_compiler_compiled_date") String[] regional_compiler_compiled_date,
    @Param("division_compiler_compiled_date") String[] division_compiler_compiled_date,
    @Param("approved_date") String[] approved_date
  );

  @SelectProvider(
    type = SqlProviderDormantInactiveAccount.class,
    method = "getBranchAuditReportDormantInactiveAccount"
  )
  List<ReportBranch> getBranchAuditReportDormantInactiveAccount(
    @Param("region_id") Long long1,
    @Param("branch_id") Long long2,
    @Param("finding_status") String string,
    @Param("rectification_date_range") String[] strings,
    @Param("date_range") String[] strings2,
    @Param("audit_finding") String string2,
    @Param("rectification_status") String string4,
    @Param("single_filter_info") String single_filter_info,
    @Param("user_id") Long long3,
    @Param("user_roles") String[] strings3,
    @Param("user_region_id") Long long5,
    // DormantInactiveAccount
    @Param("account_type") String account_type,
    @Param("amount_min") Double amount_min,
    @Param("amount_max") Double amount_max,
    @Param("account_number") String account_number,
    @Param("banking") String banking,
    @Param("cash_type") String cash_type,
    @Param("regional_compiler_compiled_date") String[] regional_compiler_compiled_date,
    @Param("division_compiler_compiled_date") String[] division_compiler_compiled_date,
    @Param("approved_date") String[] approved_date
  );

  @SelectProvider(
    type = SqlProviderSuspenseAccount.class,
    method = "getBranchAuditReportSuspenseAccount"
  )
  List<ReportBranch> getBranchAuditReportSuspenseAccount(
    @Param("region_id") Long long1,
    @Param("branch_id") Long long2,
    @Param("finding_status") String string,
    @Param("rectification_date_range") String[] strings,
    @Param("date_range") String[] strings2,
    @Param("audit_finding") String string2,
    @Param("rectification_status") String string4,
    @Param("single_filter_info") String single_filter_info,
    @Param("user_id") Long long3,
    @Param("user_roles") String[] strings3,
    @Param("user_region_id") Long long5,
    // SuspenseAccount
    @Param("tracer_date_range") String[] tracer_date_range,
    @Param("balance_per_tracer_min") Double balance_per_tracer_min,
    @Param("balance_per_tracer_max") Double balance_per_tracer_max,
    @Param(
      "balance_per_trial_balance_min"
    ) Double balance_per_trial_balance_min,
    @Param(
      "balance_per_trial_balance_max"
    ) Double balance_per_trial_balance_max,
    @Param("suspense_account_type") String suspense_account_type,
    @Param("banking") String banking,
    @Param("cash_type") String cash_type,
    @Param("regional_compiler_compiled_date") String[] regional_compiler_compiled_date,
    @Param("division_compiler_compiled_date") String[] division_compiler_compiled_date,
    @Param("approved_date") String[] approved_date
  );

  @SelectProvider(
    type = OperationReportSqlProvider.class,
    method = "getBranchOperationAuditReport"
  )
  List<ReportBranch> getBranchOperationAuditReport(
    @Param("region_id") Long long1,
    @Param("branch_id") Long long2,
    @Param("finding_status") String string,
    @Param("rectification_date_range") String[] strings,
    @Param("date_range") String[] strings2,
    @Param("audit_finding") String string2,
    @Param("category_of_discrepancy") String string3,
    @Param("min_amount") Double min_amount,
    @Param("max_amount") Double max_amount,
    @Param("account_number") String account_number,
    @Param("rectification_status") String string4,
    @Param("single_filter_info") String single_filter_info,
    @Param("user_id") Long long3,
    @Param("user_roles") String[] strings3,
    @Param("user_region_id") Long long5,
    @Param("banking") String banking,
    @Param("cash_type") String cash_type,
    @Param("regional_compiler_compiled_date") String[] regional_compiler_compiled_date,
    @Param("division_compiler_compiled_date") String[] division_compiler_compiled_date,
    @Param("approved_date") String[] approved_date
  );

  @SelectProvider(
    type = ObservationReportSqlProvider2.class,
    method = "getBranchObservationAuditReport"
  )
  List<ReportBranch> getBranchObservationAuditReport(
    @Param("region_id") Long long1,
    @Param("branch_id") Long long2,
    @Param("finding_status") String string,
    @Param("rectification_date_range") String[] strings,
    @Param("date_range") String[] strings2,
    @Param("audit_finding") String string2,
    @Param("rectification_status") String string4,
    @Param("single_filter_info") String single_filter_info,
    @Param("user_id") Long long3,
    @Param("user_roles") String[] strings3,
    @Param("user_region_id") Long long5,
    @Param("banking") String banking,
    @Param("regional_compiler_compiled_date") String[] regional_compiler_compiled_date,
    @Param("division_compiler_compiled_date") String[] division_compiler_compiled_date,
    @Param("approved_date") String[] approved_date
  );

  @SelectProvider(
    type = ReportSqlMemorandum.class,
    method = "getBranchMemorandumAuditReport"
  )
  List<ReportBranch> getBranchMemorandumAuditReport(
    @Param("region_id") Long long1,
    @Param("branch_id") Long long2,
    @Param("finding_status") String string,
    @Param("rectification_date_range") String[] strings,
    @Param("date_range") String[] strings2,
    @Param("audit_finding") String string2,
    @Param("memorandum_amount_min") Double double1,
    @Param("contingent_amount_min") Double double2,
    @Param("memorandum_amount_max") Double double3,
    @Param("contingent_amount_max") Double double4,
    @Param("rectification_status") String string4,
    @Param("single_filter_info") String single_filter_info,
    @Param("user_id") Long long3,
    @Param("user_roles") String[] strings3,
    @Param("user_region_id") Long long5,
    @Param("banking") String banking,
    @Param("cash_type") String cash_type,
    @Param("regional_compiler_compiled_date") String[] regional_compiler_compiled_date,
    @Param("division_compiler_compiled_date") String[] division_compiler_compiled_date,
    @Param("approved_date") String[] approved_date
  );

  @SelectProvider(
    type = NegotiableReportSqlProvider2.class,
    method = "getBranchNegotiableAuditReport"
  )
  List<ReportBranch> getBranchNegotiableAuditReport(
    @Param("region_id") Long long1,
    @Param("branch_id") Long long2,
    @Param("finding_status") String string,
    @Param("rectification_date_range") String[] strings,
    @Param("date_range") String[] strings2,
    @Param("audit_finding") String string2,
    @Param("account_number") String account_number,
    @Param("min_amount") Double min_amount,
    @Param("max_amount") Double max_amount,
    @Param("rectification_status") String string4,
    @Param("single_filter_info") String single_filter_info,
    @Param("user_id") Long long3,
    @Param("user_roles") String[] strings3,
    @Param("user_region_id") Long long5,
    @Param("banking") String banking,
    @Param("stock_item_type") String stock_item_type,
    @Param("cash_type") String cash_type,
    @Param("regional_compiler_compiled_date") String[] regional_compiler_compiled_date,
    @Param("division_compiler_compiled_date") String[] division_compiler_compiled_date,
    @Param("approved_date") String[] approved_date
  );

  @SelectProvider(
    type = LongReportSqlProvider.class,
    method = "getBranchLongAuditReport"
  )
  List<ReportBranch> getBranchLongAuditReport(
    @Param("region_id") Long long1,
    @Param("branch_id") Long long2,
    @Param("finding_status") String string,
    @Param("rectification_date_range") String[] strings,
    @Param("date_range") String[] strings2,
    @Param("audit_finding") String string2,
    @Param("rectification_status") String string3,
    @Param("less_than_90_amount_min") Double less_than_90_amount_min,
    @Param("less_than_90_amount_max") Double less_than_90_amount_max,
    @Param("greater_than_90_amount_min") Double greater_than_90_amount_min,
    @Param("greater_than_90_amount_max") Double greater_than_90_amount_max,
    @Param("greater_than_180_amount_min") Double greater_than_180_amount_min,
    @Param("greater_than_180_amount_max") Double greater_than_180_amount_max,
    @Param("greater_than_365_amount_min") Double greater_than_365_amount_min,
    @Param("greater_than_365_amount_max") Double greater_than_365_amount_max,
    @Param("cash_type") String cash_type,
    @Param("single_filter_info") String single_filter_info,
    @Param("user_id") Long long3,
    @Param("user_roles") String[] strings4,
    @Param("user_region_id") Long long5,
    @Param("outstanding_item") String outstanding_item,
    @Param("selected_item_age") String[] selected_item_age,
    @Param("banking") String banking,
    
    @Param("regional_compiler_compiled_date") String[] regional_compiler_compiled_date,
    @Param("division_compiler_compiled_date") String[] division_compiler_compiled_date,
    @Param("approved_date") String[] approved_date
    
  );


  @SelectProvider(
    type = LoanAdvanceReportSqlProvider.class,
    method = "getBranchLoandAdvanceAuditReport"
  )
  List<ReportBranch> getBranchLoandAdvanceAuditReport(
    @Param("region_id") Long long1,
    @Param("branch_id") Long long2,
    @Param("finding_status") String string,
    @Param("rectification_date_range") String[] strings,
    @Param("date_range") String[] strings2,
    @Param("audit_finding") String string2,
    @Param("rectification_status") String string3,
    @Param("single_filter_info") String single_filter_info,
    @Param("borrower_name") String string4,
    // @Param("loan_disburse_date") String[] string5,
    @Param("loan_type") String loan_type,
    @Param("account_number") String string6,
    @Param("granted_amount_min") Double granted_amount_min,
    @Param("granted_amount_max") Double granted_amount_max,
    @Param("user_id") Long long3,
    @Param("user_roles") String[] strings3,
    @Param("user_region_id") Long long5,
    @Param("banking") String banking,
    @Param("cash_type") String cash_type,
    
    @Param("regional_compiler_compiled_date") String[] regional_compiler_compiled_date,
    @Param("division_compiler_compiled_date") String[] division_compiler_compiled_date,
    @Param("approved_date") String[] approved_date
    
  );

  @SelectProvider(
    type = CashCountReportSqlProvider.class,
    method = "getBranchCashCountAuditReport"
  )
  List<ReportBranch> getBranchCashCountAuditReport(
    @Param("region_id") Long long1,
    @Param("branch_id") Long long2,
    @Param("finding_status") String string,
    @Param("rectification_date_range") String[] strings,
    @Param("date_range") String[] strings2,
    @Param("audit_finding") String string3,
    @Param("rectification_status") String string4,
    @Param("cash_count_min") Double cash_count_min,
    @Param("cash_count_max") Double cash_count_max,
    @Param("trial_balance_min") Double trial_balance_min,
    @Param("trial_balance_max") Double trial_balance_max,
    @Param("cash_count_type") String cash_count_type,
    @Param("single_filter_info") String single_filter_info,
    @Param("user_id") Long long3,
    @Param("user_roles") String[] strings5,
    @Param("user_region_id") Long long5,
    @Param("banking") String banking,
    @Param("regional_compiler_compiled_date") String[] regional_compiler_compiled_date,
    @Param("division_compiler_compiled_date") String[] division_compiler_compiled_date,
    @Param("approved_date") String[] approved_date
  );

  @SelectProvider(
    type = SqlProviderCashManagement.class,
    method = "getBranchAuditReportCashManagement"
  )
  List<ReportBranch> getBranchAuditReportCashManagement(
    @Param("region_id") Long long1,
    @Param("branch_id") Long long2,
    @Param("finding_status") String string,
    @Param("rectification_date_range") String[] strings,
    @Param("date_range") String[] strings2,
    @Param("audit_finding") String string2,
    @Param("rectification_status") String string4,
    @Param("single_filter_info") String single_filter_info,
    @Param("user_id") Long long3,
    @Param("user_roles") String[] strings3,
    @Param("user_region_id") Long long5,
    // CashManagement
    @Param("mid_rate_fcy_min") Double mid_rate_fcy_min,
    @Param("mid_rate_fcy_max") Double mid_rate_fcy_max,
    @Param("average_cash_holding_min") Double average_cash_holding_min,
    @Param("average_cash_holding_max") Double average_cash_holding_max,
    @Param("cash_type") String cash_type,
    @Param("banking") String banking,
    @Param("regional_compiler_compiled_date") String[] regional_compiler_compiled_date,
    @Param("division_compiler_compiled_date") String[] division_compiler_compiled_date,
    @Param("approved_date") String[] approved_date
  );

  @SelectProvider(
    type = SqlProviderCashExcessOrShortage.class,
    method = "fetchReportCashExcessOrShortage"
  )
  List<ReportBranch> fetchReportCashExcessOrShortage(
    @Param("region_id") Long long1,
    @Param("branch_id") Long long2,
    @Param("finding_status") String string,
    @Param("rectification_date_range") String[] strings,
    @Param("date_range") String[] strings2,
    @Param("audit_finding") String string2,
    @Param("rectification_status") String string4,
    @Param("single_filter_info") String single_filter_info,
    @Param("user_id") Long long3,
    @Param("user_roles") String[] strings3,
    @Param("user_region_id") Long long5,
    // CashExcessOrShortage
    @Param("amount_shortage_min") Double amount_shortage_min,
    @Param("amount_shortage_max") Double amount_shortage_max,
    @Param("amount_excess_min") Double amount_excess_min,
    @Param("amount_excess_max") Double amount_excess_max,
    @Param("banking") String banking,
    @Param("cash_type") String cash_type,
    @Param("regional_compiler_compiled_date") String[] regional_compiler_compiled_date,
    @Param("division_compiler_compiled_date") String[] division_compiler_compiled_date,
    @Param("approved_date") String[] approved_date
  );
}
