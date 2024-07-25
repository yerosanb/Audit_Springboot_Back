package com.afr.fms.Branch_Audit.Reviewer.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Entity.CompiledBranchAudit;

@Mapper
public interface CompilerMapper {

	@Select("insert into compiled_branch_audit(case_number, audit_type, finding, impact, recommendation, region_id, compiler_id, compiled_date, compiled_status) OUTPUT inserted.id values(#{case_number}, #{audit_type}, #{finding}, #{impact}, #{recommendation}, #{region.id},#{compiler.id}, CURRENT_TIMESTAMP, 1)")
	public Long saveCompiledFinding(CompiledBranchAudit audit);

	@Select("insert into compiled_audits(compiled_id, audit_id) OUTPUT inserted.id values(#{compiled_id}, #{audit_id})")
	public Long saveIntoCompiledAudits(Long compiled_id, Long audit_id);

	@Delete("delete from compiled_branch_audit where id = #{id}")
	public Long removeCompiledFinding(Long id);

	@Delete("delete from compiled_audits where compiled_id = #{id}")
	public Long removeMappedCompiledFindings(Long id);

	@Select("SELECT TOP 1 case_number FROM compiled_branch_audit ORDER BY id DESC;")
	public String getLatestCaseNumber();

	@Update("update compiled_branch_audit set compiler_submitted = 1, compiler_submitted_date = CURRENT_TIMESTAMP where id = #{id}")
	public void submitCompiledFindings(CompiledBranchAudit audit);

	@Update("update branch_financial_audit set regional_compiler_status = #{status}, regional_compiler_compiled_date = CURRENT_TIMESTAMP where id = #{branch_financial_audit}")
	public void updateBranchFinancialAudit(Long branch_financial_audit, int status);

	@Update("update compiled_audits set rectification_status = #{status} where audit_id = #{branch_financial_audit}")
	public void updateRectificationStatusCompiledAudits(Long branch_financial_audit, int status);

	@Select("SELECT CAST(CASE WHEN EXISTS (SELECT * FROM branch_financial_audit WHERE rectification_status = 1 and id = #{branch_financial_audit}) THEN 1 ELSE 0 END AS BIT)")
	public Boolean isBranchFinancialAuditRectified(Long branch_financial_audit);

	@Select("SELECT CAST(CASE WHEN EXISTS (SELECT * FROM branch_financial_audit WHERE regional_compiler_status = 1 and id = #{branch_financial_audit}) THEN 1 ELSE 0 END AS BIT)")
	public Boolean isBranchFinancialAuditCompiled(Long branch_financial_audit);

	@Update("update branch_financial_audit set regional_compiler_submitted = #{status}, regional_compiler_submitted_date = CURRENT_TIMESTAMP where id = #{branch_financial_audit}")
	public void updateBranchFinancialAuditAfterSubmitted(Long branch_financial_audit, int status);

	@Select("select * from compiled_branch_audit where (compiler_id = #{id} or (region_id = #{region.id} and 'REGIONALD' = #{category})) and compiler_submitted = 1 Order By compiler_submitted_date DESC ")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "compiler", column = "compiler_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "region", column = "region_id", one = @One(select = "com.afr.fms.Admin.Mapper.RegionMapper.getRegionById")),
	})
	public List<CompiledBranchAudit> getSubmittedCompiledAudits(User user);

	@Select("select * from compiled_branch_audit where compiler_id = #{compiler_id} and compiler_submitted != 1 Order By compiled_date")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "compiler", column = "compiler_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "region", column = "region_id", one = @One(select = "com.afr.fms.Admin.Mapper.RegionMapper.getRegionById")),
	})
	public List<CompiledBranchAudit> getCompiledAudits(Long compiler_id);

	@Select("select audit_id from compiled_audits where compiled_id = #{compiled_id} and (rectification_status != 1 or rectification_status is null) ")
	public List<Long> getMappedCompiledAudits(Long compiled_id);

	@Select("select * from branch_financial_audit where id = #{branchFinancialAudit_id} Order By finding_date")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
			@Result(property = "incompleteAccountBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getIncompleteAccountBranch")),
			@Result(property = "atmCardBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.AtmCardAuditorMapper.getAtmCardBranch")),
			@Result(property = "memorandumContigent", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.MemorandumAuditorMapper.getMemorandumContigent")),
			@Result(property = "cashManagementBranch", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.CashManagementAuditorMapper.getCashManagementBranch")),
			@Result(property = "cashPerformanceBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.CashPerformanceAuditorMapper.getCashPerformanceBranch")),
			@Result(property = "dormantInactive", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.DormantAccountMapper.getDormantInactive")),
			@Result(property = "cashcount", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.CashCountMapper.getCashCountById")),
			@Result(property = "assetLiabilityBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.AssetLiabilityAuditorMapper.getAssetLiabilityByBranchId")),
			@Result(property = "abnormalBalanceBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.AbnormalBalanceAuditorMapper.getAbnormalBalanceByBranchId")),
			@Result(property = "negotiableInstrument", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.NegotiableInstrumentBranchMapper.getNegotiableInstrumentByBranchId")),
			@Result(property = "operationalDescripancyBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.OperationalDescripancyAuditorBranchMapper.getOperationalDescripancyByBranchId")),
			@Result(property = "long_outstanding", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.LongOutStandingItemsAuditorMapper.getLongOutstandingItemsById")),
			@Result(property = "loanAndAdvance", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.LoanAndAdvanceMapper.getLoanAndAdvanceByBFAId")),
			@Result(property = "statusofAudit", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.StatusOfAuditMapper.getBFAByStatusofAudit")),
			@Result(property = "controllableExpense", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.ControllableExpenseAuditorMapper.getControllableExpenseByBranchId")),
			@Result(property = "suspenseAccountBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.SuspenseAccountAuditorBranchMapper.getSuspenseAccountByBranchId")),
			@Result(property = "cashcount", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.CashCountMapper.getCashCountById")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
	})
	public BranchFinancialAudit getBranchFinancialAuditForCompiler(Long branchFinancialAudit_id);

}
