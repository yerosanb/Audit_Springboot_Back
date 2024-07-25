package com.afr.fms.Branch_Audit.DivisionCompiler.Mapper;

import java.util.List;
import org.apache.ibatis.annotations.*;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Entity.CompiledBranchAudit;

@Mapper
public interface DivisionCompilerMapper {

	@Select("select * from compiled_branch_audit where audit_type = #{audit_type} and review_status = 0 and  compiler_submitted = 1 Order By compiled_date DESC")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "compiler", column = "compiler_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "region", column = "region_id", one = @One(select = "com.afr.fms.Admin.Mapper.RegionMapper.getRegionById")),
	})
	public List<CompiledBranchAudit> getPendingAudits(String audit_type);

	@Select("select * from compiled_branch_audit where audit_type = #{audit_type} and division_compiler_id = #{compiler_id} and review_status = 1 and division_compiler_status != 1  Order By compiled_date DESC")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "compiler", column = "compiler_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "region", column = "region_id", one = @One(select = "com.afr.fms.Admin.Mapper.RegionMapper.getRegionById")),
	})
	public List<CompiledBranchAudit> getReviewedAudits(String audit_type, Long compiler_id);

	@Select("select * from branch_financial_audit where id = #{branchFinancialAudit_id} Order By regional_compiler_submitted_date")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
			@Result(property = "incompleteAccountBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getIncompleteAccountBranch")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
	})
	public BranchFinancialAudit getIncompleteAccountBranch(Long branchFinancialAudit_id);

	@Select("select * from branch_financial_audit where id = #{branchFinancialAudit_id} Order By regional_compiler_submitted_date")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
			@Result(property = "atmCardBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.AtmCardAuditorMapper.getAtmCardBranch")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
	})
	public BranchFinancialAudit getAtmCardBranch(Long branchFinancialAudit_id);

	@Select("select * from branch_financial_audit where id = #{branchFinancialAudit_id} Order By regional_compiler_submitted_date")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
			@Result(property = "cashcount", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.CashCountMapper.getCashCountById")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
	})
	public BranchFinancialAudit getCashCount(Long branchFinancialAudit_id);

	@Select("select * from branch_financial_audit where id = #{branchFinancialAudit_id} Order By regional_compiler_submitted_date")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
			@Result(property = "dormantInactive", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.DormantAccountMapper.getDormantInactive")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.DormantAccountMapper.getFileUrlsByAuditID")),
	})
	public BranchFinancialAudit get_dormant_inactive_account_branch(Long branchFinancialAudit_id);

	@Select("select * from branch_financial_audit where id = #{branchFinancialAudit_id} Order By regional_compiler_submitted_date")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
			@Result(property = "cashManagementBranch", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.CashManagementAuditorMapper.getCashManagementBranch")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.DormantAccountMapper.getFileUrlsByAuditID")),
	})
	public BranchFinancialAudit get_cash_management_branch(Long branchFinancialAudit_id);

	@Select("select * from branch_financial_audit where id = #{branchFinancialAudit_id} Order By regional_compiler_submitted_date")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
			@Result(property = "assetLiabilityBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.AssetLiabilityAuditorMapper.getAssetLiabilityByBranchId")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
	})

	public BranchFinancialAudit get_asset_liability_branch(Long branchFinancialAudit_id);

	@Select("select * from branch_financial_audit where id = #{branchFinancialAudit_id} Order By regional_compiler_submitted_date")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
			@Result(property = "abnormalBalanceBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.AbnormalBalanceAuditorMapper.getAbnormalBalanceByBranchId")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
	})

	public BranchFinancialAudit get_abnormal_balance_branch(Long branchFinancialAudit_id);

	@Select("select * from branch_financial_audit where id = #{branchFinancialAudit_id} Order By regional_compiler_submitted_date")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
			@Result(property = "controllableExpense", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.ControllableExpenseAuditorMapper.getControllableExpenseByBranchId")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
	})

	public BranchFinancialAudit get_controllable_expense_branch(Long branchFinancialAudit_id);

	@Select("select * from branch_financial_audit where id = #{branchFinancialAudit_id} Order By regional_compiler_submitted_date")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
			@Result(property = "suspenseAccountBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.SuspenseAccountAuditorBranchMapper.getSuspenseAccountByBranchId")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
	})

	public BranchFinancialAudit getsuspenseAccountBranch(Long branchFinancialAudit_id);

	@Select("select * from branch_financial_audit where id = #{branchFinancialAudit_id} Order By regional_compiler_submitted_date")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
			@Result(property = "negotiableInstrument", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.NegotiableInstrumentBranchMapper.getNegotiableInstrumentByBranchId")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
	})

	public BranchFinancialAudit getNegotiableInstrumentBranch(Long branchFinancialAudit_id);

	@Select("select * from branch_financial_audit where id = #{branchFinancialAudit_id} Order By regional_compiler_submitted_date")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
			@Result(property = "cashPerformanceBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.CashPerformanceAuditorMapper.getCashPerformanceBranch")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
	})
	public BranchFinancialAudit getCashPerformanceBranch(Long branchFinancialAudit_id);

	@Select("select * from branch_financial_audit where  id = #{branchFinancialAudit_id} Order By regional_compiler_submitted_date")
	@Results(value = {
					@Result(property = "id", column = "id"),
					@Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
					@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
					@Result(property = "loanAndAdvance", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.LoanAndAdvanceMapper.getLoanAndAdvanceByBFAId")),
					@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
	})
	public BranchFinancialAudit getLoanAndAdvance(Long branchFinancialAudit_id);

	@Select("select bfa.observation as finding, bfa.* from branch_financial_audit bfa where bfa.id = #{branchFinancialAudit_id} Order By bfa.regional_compiler_submitted_date ")
	@Results(value = {
					@Result(property = "id", column = "id"),
					@Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
					@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
					@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
	})
	public BranchFinancialAudit getGeneralObservation(Long branchFinancialAudit_id);

	@Select("select * from branch_financial_audit where id = #{branchFinancialAudit_id} Order By regional_compiler_submitted_date")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
			@Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
			@Result(property = "long_outstanding", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.LongOutStandingItemsAuditorMapper.getLongOutstandingItemsById")),
	})
	public BranchFinancialAudit getLongOutstandingItems(Long branchFinancialAudit_id);

	@Select("select * from branch_financial_audit where id = #{branchFinancialAudit_id} Order By regional_compiler_submitted_date")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "statusofAudit", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.StatusOfAuditMapper.getBFAByStatusofAudit")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
	})
	public BranchFinancialAudit getstatusofAudit(Long branchFinancialAudit_id);

	@Select("select * from branch_financial_audit where id = #{branchFinancialAudit_id} Order By regional_compiler_submitted_date")
	@Results(value = {
			@Result(property = "id", column = "id"),

			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "memorandumContigent", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.MemorandumAuditorMapper.getMemorandumContigent")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.MemorandumAuditorMapper.getFileUrlsByAuditID")),
			@Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
	})
	public BranchFinancialAudit getmemorandumContigent(Long branchFinancialAudit_id);

	@Select("select *  from branch_financial_audit where id = #{branchFinancialAudit_id} Order By regional_compiler_submitted_date ")
	@Results(value = {
					@Result(property = "id", column = "id"),
					@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
					@Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
					@Result(property = "suspenseAccountBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.SuspenseAccountAuditorBranchMapper.getSuspenseAccountByBranchId")),
					@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
				})

	public BranchFinancialAudit getSuspenseAccountBranch(Long branchFinancialAudit_id);


	@Select("<script>"
			+ "select bfa.* from branch_financial_audit bfa "
			+ "inner join operational_descripancy_branch nib on nib.branch_audit_id = bfa.id "
			+ "<choose>"
			+ "<when test='auditType == \"Reviewed\"'>"
			+ " left join operational_discrepancy_pooled_data odpd on odpd.branch_financial_audit_id = bfa.id and odpd.user_id = #{compiler_id}"
			+ " where bfa.rectification_status != 1 and bfa.id = #{branchFinancialAudit_id} and odpd.branch_financial_audit_id is null order by bfa.regional_compiler_submitted_date "
			+ "</when>"
			+ "<when test='auditType == \"Pending\"'>"
			+ " where bfa.rectification_status != 1 and bfa.id = #{branchFinancialAudit_id} order by bfa.regional_compiler_submitted_date "
			+ "</when>"
			+ "</choose>"
			+ "</script>")

	// @Select("select * from branch_financial_audit where id =
	// #{branchFinancialAudit_id} Order By finding_date")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
			@Result(property = "operationalDescripancyBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.OperationalDescripancyAuditorBranchMapper.getOperationalDescripancyByBranchId")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
	})
	public BranchFinancialAudit get_operational_descripancy_branch(Long branchFinancialAudit_id, Long compiler_id,
			@Param("auditType") String auditType);

	@Select("select audit_id from compiled_audits where compiled_id = #{compiled_id} and (rectification_status = 0 or rectification_status is null)")
	public List<Long> getMappedCompiledAudits(Long compiled_id);

	@Update("update compiled_branch_audit set division_compiler_id=#{compiler.id}, review_status = 1 , reviewed_date= CURRENT_TIMESTAMP, finding_status= 'Division Reviewed' where id=#{id}")
	public void reviewFinding(CompiledBranchAudit audit);

	@Update("update compiled_branch_audit set division_compiler_id=#{compiler.id}, review_status = 0, approve_status = 0 , finding_status= 'Division Unreviewed'  where id=#{id}")
	public void unreviewFinding(CompiledBranchAudit audit);

	@Update("update branch_financial_audit set division_compiler_review_status = #{status}, division_compiler_reviewed_date = CURRENT_TIMESTAMP, division_compiler_id = #{division_compiler_id} where id = #{branch_financial_audit}")
	public void updateBranchFinancialAuditAfterReviewed(Long branch_financial_audit, int status,
			Long division_compiler_id);

	@Update("update branch_financial_audit set division_compiler_review_status = #{status} where id = #{branch_financial_audit}")
	public void updateBranchFinancialAuditAfterUnreviewed(Long branch_financial_audit, int status);

	@Select("select * from branch_financial_audit where id in (select branch_audit_id from incomplete_account_branch) and review_status = 1 and regional_compiler_status != 1 and reviewer_id=#{reviewer_id} and status = 1 Order By finding_date")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "incompleteAccountBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getIncompleteAccountBranch")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
			@Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")),
	})
	public List<BranchFinancialAudit> getReviewedFindings(Long reviewer_id);

	@Select("select * from branch_financial_audit where id in (select branch_audit_id from incomplete_account_branch) and review_status = 2 and reviewer_id=#{reviewer_id} and status = 1  Order By finding_date")
	@Results(value = {
			@Result(property = "id", column = "id"),

			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "incompleteAccountBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getIncompleteAccountBranch")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
			@Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")), })
	public List<BranchFinancialAudit> getRejectedFindings(Long reviewer_id);

	@Select("select * from branch_financial_audit where id in (select branch_audit_id from incomplete_account_branch) and review_status = 1 and reviewer_id=#{reviewer_id} and status = 1 and approve_status = 1  Order By finding_date")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "IS_MGTAuditee", column = "id", many = @Many(select = "com.afr.fms.Auditor.Mapper.BranchFinancialAuditMapper.getISMAuditees")),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "incompleteAccountBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getIncompleteAccountBranch")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
			@Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")), })
	public List<BranchFinancialAudit> getReviewedFindingsStatus(Long reviewer_id);

	@Update("update branch_financial_audit set reviewer_id=#{reviewer.id}, review_status = 2, reviewer_rejected_date = CURRENT_TIMESTAMP, finding_status= 'reviewer_rejected' where id=#{id}")
	public void cancelFinding(BranchFinancialAudit audit);

}
