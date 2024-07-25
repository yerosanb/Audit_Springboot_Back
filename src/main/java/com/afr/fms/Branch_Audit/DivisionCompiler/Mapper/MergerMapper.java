package com.afr.fms.Branch_Audit.DivisionCompiler.Mapper;

import java.util.List;
import org.apache.ibatis.annotations.*;

import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Entity.CompiledBranchAudit;
import com.afr.fms.Branch_Audit.Entity.CompiledRegionalAudit;

@Mapper
public interface MergerMapper {

	@Select("insert into compiled_regional_audit(case_number, audit_type, finding, impact, recommendation, compiler_id, compiled_date, compiled_status) OUTPUT inserted.id values(#{case_number}, #{audit_type}, #{finding}, #{impact}, #{recommendation}, #{compiler.id}, CURRENT_TIMESTAMP, 1)")
	public Long saveCompiledFinding(CompiledRegionalAudit audit);

	@Select("insert into compiled_audits_region(compiled_id, audit_id) OUTPUT inserted.id values(#{compiled_id}, #{audit_id})")
	public Long saveIntoCompiledAudits(Long compiled_id, Long audit_id);

	@Select("delete from compiled_regional_audit where id = #{id}")
	public Long removeCompiledFinding(Long id);

	@Select("delete from compiled_audits_region where compiled_id = #{id}")
	public Long removeMappedCompiledFindings(Long id);

	@Select("SELECT CAST(CASE WHEN EXISTS (SELECT * FROM compiled_branch_audit WHERE division_compiler_status = 1 and id = #{compiled_branch_audit}) THEN 1 ELSE 0 END AS BIT)")
	public Boolean isCompiledBranchAuditCompiled(Long compiled_branch_audit);

	@Select("SELECT TOP 1 case_number FROM compiled_regional_audit ORDER BY id DESC;")
	public String getLatestCaseNumber();

	@Update("update compiled_regional_audit set division_compiler_submitted = 1, division_compiler_submitted_date = CURRENT_TIMESTAMP where id = #{id}")
	public void submitCompiledFindings(CompiledRegionalAudit audit);

	@Update("update compiled_branch_audit set division_compiler_status = #{status} where id = #{compiled_branch_audit}")
	public void updateCompiledBranchAudit(Long compiled_branch_audit, int status);

	@Update("update compiled_branch_audit set division_compiler_submitted = #{status} where id = #{compiled_branch_audit}")
	public void updateCompiledBranchAuditAfterSubmitted(Long compiled_branch_audit, int status);

	@Update("update branch_financial_audit set division_compiler_status = #{status}, division_compiler_compiled_date = CURRENT_TIMESTAMP where id = #{branch_financial_audit}")
	public void updateBranchFinancialAuditAfterCompiled(Long branch_financial_audit, int status);

	@Update("update branch_financial_audit set division_compiler_status = #{status} where id = #{branch_financial_audit}")
	public void updateBranchFinancialAuditAfterDecompiled(Long branch_financial_audit, int status);

	@Update("update branch_financial_audit set division_compiler_submitted = #{status}, division_compiler_submitted_date = CURRENT_TIMESTAMP where id = #{branch_financial_audit}")
	public void updateBranchFinancialAuditAfterSubmitted(Long branch_financial_audit, int status);

	@Select("select * from compiled_regional_audit where compiler_id = #{compiler_id} and division_compiler_submitted = 1 Order By compiled_date DESC")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "compiler", column = "compiler_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
	})
	public List<CompiledRegionalAudit> getSubmittedCompiledAudits(Long compiler_id);

	@Select("select * from compiled_regional_audit where compiler_id = #{compiler_id} and division_compiler_submitted != 1 Order By compiled_date DESC")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "compiler", column = "compiler_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
	})
	public List<CompiledRegionalAudit> getCompiledAudits(Long compiler_id);

	@Select("select audit_id from compiled_audits_region where compiled_id = #{compiled_id}")
	public List<Long> getMappedCompiledAudits(Long compiled_id);

	@Select("select audit_id from compiled_audits where compiled_id = #{compiled_id} and (rectification_status = 0 or rectification_status is null)")
	public List<Long> getMappedCompiledBranchAudits(Long compiled_id);

	@Select("select * from compiled_branch_audit where id = #{compiled_branch_audit_id} Order By compiled_date")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "compiler", column = "compiler_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "region", column = "region_id", one = @One(select = "com.afr.fms.Admin.Mapper.RegionMapper.getRegionById")),
	})
	public CompiledBranchAudit getCompiledBranchAuditForDivision(Long compiled_branch_audit_id);

	// @Select("select * from branch_financial_audit where id = #{branch_audit_id}
	// and rectification_status != 1 Order By finding_date")
	// @Results(value = {
	// @Result(property = "id", column = "id"),
	// @Result(property = "auditor", column = "auditor_id", one = @One(select =
	// "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
	// @Result(property = "branch", column = "branch_id", one = @One(select =
	// "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
	// @Result(property = "incompleteAccountBranch", column = "id", one =
	// @One(select =
	// "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getIncompleteAccountBranch")),
	// @Result(property = "atmCardBranch", column = "id", one = @One(select =
	// "com.afr.fms.Branch_Audit.Auditor.Mapper.AtmCardAuditorMapper.getAtmCardBranch")),
	// @Result(property = "operationalDescripancyBranch", column = "id", one =
	// @One(select =
	// "com.afr.fms.Branch_Audit.Auditor.Mapper.OperationalDescripancyAuditorBranchMapper.getOperationalDescripancyByBranchId")),
	// @Result(property = "cashPerformanceBranch", column = "id", one = @One(select
	// =
	// "com.afr.fms.Branch_Audit.Auditor.Mapper.CashPerformanceAuditorMapper.getCashPerformanceBranch")),
	// @Result(property = "controllableExpense", column = "id", one = @One(select =
	// "com.afr.fms.Branch_Audit.Auditor.Mapper.ControllableExpenseAuditorMapper.getSuspenseAccountByBranchId")),
	// @Result(property = "cashcount", column = "id", one = @One(select =
	// "com.afr.fms.Branch_Audit.Auditor.Mapper.CashCountMapper.getCashCountById")),
	// @Result(property = "file_urls", column = "id", many = @Many(select =
	// "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
	// })
	// public BranchFinancialAudit getBranchFinancialAuditForCompiler(Long
	// branch_audit_id);

	@Select("<script>"
			+ "select bfa.* from branch_financial_audit bfa "
			+ "<choose>" +
			"<when test='auditType == \"IncompleteAccount\"'>" +
			" INNER JOIN incomplete_account_branch ic ON ic.branch_audit_id = bfa.id " +
			"</when>" +
			"<when test='auditType == \"ATMCard\"'>" +
			" INNER JOIN ATM_card_branch acb ON acb.branch_audit_id = bfa.id " +
			"</when>" +
			"<when test='auditType == \"CashCount\"'>" +
			"INNER JOIN cash_count_branch ccb ON ccb.branch_audit_id = bfa.id " +
			"</when>" +
			"<when test='auditType == \"OperationalDiscrepancy\"'>" +
			"INNER JOIN operational_descripancy_branch odb ON odb.branch_audit_id = bfa.id " +
			"</when>" +
			"<when test='auditType == \"LoanAdvance\"'>" +
			"INNER JOIN loan_advance_branch lab ON lab.branch_audit_id = bfa.id " +
			"</when>" +
			"<when test='auditType == \"NegotiableInstrument\"'>" +
			"INNER JOIN negotiable_instrument_branch nib ON nib.branch_audit_id = bfa.id " +
			"INNER JOIN negotiable_stock_item nsi ON nsi.id = nib.negotiable_stock_item_id " +
			"</when>" +

			"<when test='auditType == \"AbnormalBalance\"'>" +
			"INNER JOIN abnormal_balance_branch abb ON abb.branch_audit_id = bfa.id " +
			"</when>" +

			"<when test='auditType == \"CashPerformance\"'>" +
			"INNER JOIN cash_performance_branch cpb ON cpb.branch_audit_id = bfa.id " +
			"</when>" +

			"<when test='auditType == \"CashManagement\"'>" +
			"INNER JOIN cash_management_branch cmb ON cmb.branch_audit_id = bfa.id " +
			"</when>" +

			"<when test='auditType == \"SuspenseAccount\"'>" +
			"INNER JOIN suspense_account_branch sab ON sab.branch_audit_id = bfa.id " +
			"</when>" +

			"<when test='auditType == \"ControllableExpense\"'>" +
			"INNER JOIN controllable_expense_branch ceb ON ceb.branch_audit_id = bfa.id " +
			"</when>" +

			"<when test='auditType == \"Dormant\"'>" +
			"INNER JOIN dormant_inactive_account_branch diab ON diab.branch_audit_id = bfa.id " +
			"</when>" +

			"<when test='auditType == \"Contigent\"'>" +
			"INNER JOIN memorandom_contingent_branch mcb ON mcb.branch_audit_id = bfa.id " +
			"</when>" +

			"<when test='auditType == \"LongOutstandingItems\"'>" +
			"INNER JOIN long_outstanding_item_branch loib ON loib.branch_audit_id = bfa.id " +
			"</when>" +

			"<when test='auditType == \"AssetLiability\"'>" +
			"INNER JOIN asset_liability_branch alb ON alb.branch_audit_id = bfa.id " +
			"</when>" +

			"</choose>"
			+ " where bfa.id = #{branchFinancialAudit_id} and bfa.rectification_status != 1 order by bfa.division_compiler_submitted_date DESC"
			+ "</script>")
	// @Select("select * from branch_financial_audit where id =
	// #{branchFinancialAudit_id} Order By finding_date")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
			@Result(property = "id", column = "id"),
			@Result(property = "incompleteAccountBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getIncompleteAccountBranch")),
			@Result(property = "atmCardBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.AtmCardAuditorMapper.getAtmCardBranch")),
			@Result(property = "memorandumContigent", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.MemorandumAuditorMapper.getMemorandumContigent")),
			@Result(property = "cashManagementBranch", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.CashManagementAuditorMapper.getCashManagementBranch")),
			@Result(property = "dormantInactive", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.DormantAccountMapper.getDormantInactive")),
			@Result(property = "operationalDescripancyBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.OperationalDescripancyAuditorBranchMapper.getOperationalDescripancyByBranchId")),
			@Result(property = "cashcount", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.CashCountMapper.getCashCountById")),
			@Result(property = "assetLiabilityBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.AssetLiabilityAuditorMapper.getAssetLiabilityByBranchId")),
			@Result(property = "abnormalBalanceBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.AbnormalBalanceAuditorMapper.getAbnormalBalanceByBranchId")),
			@Result(property = "negotiableInstrument", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.NegotiableInstrumentBranchMapper.getNegotiableInstrumentByBranchId")),
			@Result(property = "suspenseAccountBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.SuspenseAccountAuditorBranchMapper.getSuspenseAccountByBranchId")),
			@Result(property = "controllableExpense", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.ControllableExpenseAuditorMapper.getControllableExpenseByBranchId")),
			@Result(property = "loanAndAdvance", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.LoanAndAdvanceMapper.getLoanAndAdvanceByBFAId")),
			@Result(property = "long_outstanding", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.LongOutStandingItemsAuditorMapper.getLongOutstandingItemsById")),
			@Result(property = "cashPerformanceBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.CashPerformanceAuditorMapper.getCashPerformanceBranch")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
	})
	public BranchFinancialAudit getBranchFinancialAuditForCompiler(Long branchFinancialAudit_id,
			@Param("auditType") String auditType);

}
