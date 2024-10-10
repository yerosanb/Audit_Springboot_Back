package com.afr.fms.Branch_Audit.Core.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Entity.CompiledBranchAudit;
import com.afr.fms.Branch_Audit.Entity.CompiledRegionalAudit;

@Mapper
public interface TrialBalanceMapper {

	@Update("update compiled_regional_audit set finding = #{finding}, impact = #{impact}, recommendation = #{recommendation}, drafted_date= CURRENT_TIMESTAMP where id=#{id}")
	public void updateCompiledFinding(CompiledRegionalAudit audit);

	@Update("update compiled_regional_audit set approver_id=#{approver.id}, drafting_status = 0, approve_status = 1 , approved_date= CURRENT_TIMESTAMP, finding_status= 'approved' where id=#{id}")
	public void approveFinding(CompiledRegionalAudit audit);

	@Update("update compiled_regional_audit set approver_id=#{approver.id}, approve_status = 0, finding_status= 'Unapproved'  where id=#{id}")
	public void unapproveFinding(CompiledRegionalAudit audit);

	@Update("update compiled_regional_audit set approve_status = 3, finding_status= 'ApprovedandClosed'  where id=#{id}")
	public void closeFinding(CompiledRegionalAudit audit);

	@Update("update compiled_regional_audit set approver_id=#{approver.id}, drafting_status = 1, finding_status= 'Drafting'  where id=#{id}")
	public void addToDrafting(CompiledRegionalAudit audit);

	@Select("select * from compiled_regional_audit where approver_id = #{approver_id} and approve_status = 1 Order By approved_date DESC")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "compiler", column = "compiler_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
	})
	public List<CompiledRegionalAudit> getApprovedCompiledAudits(Long approver_id);

	@Select("select * from compiled_regional_audit where approver_id = #{approver_id} and drafting_status = 1 and approve_status = 0 Order By drafted_date DESC")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "compiler", column = "compiler_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
	})
	public List<CompiledRegionalAudit> getDraftedCompiledAudits(Long approver_id);

	@Select("select * from compiled_regional_audit where approve_status = 0 and drafting_status != 1 and division_compiler_submitted = 1 Order By compiled_date DESC")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "compiler", column = "compiler_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
	})
	public List<CompiledRegionalAudit> getPendingCompiledAudits();

	@Select("select * from branch_financial_audit where id = #{branch_audit_id} Order By finding_date")
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
			@Result(property = "controllableExpense", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.ControllableExpenseAuditorMapper.getControllableExpenseByBranchId")),
			@Result(property = "suspenseAccountBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.SuspenseAccountAuditorBranchMapper.getSuspenseAccountByBranchId")),
			@Result(property = "cashcount", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.CashCountMapper.getCashCountById")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
	})
	public BranchFinancialAudit getBranchFinancialAuditForCompiler(Long branch_audit_id);

	@Select("select audit_id from compiled_audits_region where compiled_id = #{compiled_id} ")
	public List<Long> getMappedCompiledAudits(Long compiled_id);

	@Select("select audit_id from compiled_audits where compiled_id = #{compiled_id} and (rectification_status != 1 or rectification_status is null)")
	public List<Long> getMappedCompiledBranchAudits(Long compiled_id);

	@Select("select * from compiled_branch_audit where id = #{compiled_branch_audit_id} Order By compiled_date DESC")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "compiler", column = "compiler_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "region", column = "region_id", one = @One(select = "com.afr.fms.Admin.Mapper.RegionMapper.getRegionById")),
	})
	public CompiledBranchAudit getCompiledBranchAuditForDivision(Long compiled_branch_audit_id);

	@Update("update branch_financial_audit set approve_status = #{status}, approved_date = CURRENT_TIMESTAMP, approver_id = #{approver_id} where id = #{branch_financial_audit}")
	public void updateBranchFinancialAuditAfterApproved(Long branch_financial_audit, int status, Long approver_id);

	@Update("update branch_financial_audit set approve_status = #{status} where id = #{branch_financial_audit}")
	public void updateBranchFinancialAuditAfterUnapproved(Long branch_financial_audit, int status);

	@Update("update branch_financial_audit set approve_status = #{status}, approver_id = #{approver_id} where id = #{branch_financial_audit}")
	public void updateBranchFinancialAuditAfterDrafted(Long branch_financial_audit, int status, Long approver_id);

	@Update("update branch_financial_audit set approve_status = #{status}, approver_edited_date = CURRENT_TIMESTAMP, approver_id = #{approver_id} where id = #{branch_financial_audit}")
	public void updateBranchFinancialAuditAfterEdited(Long branch_financial_audit, int status, Long approver_id);

	@Update("update compiled_branch_audit set approve_status = #{status}, approver_id = #{approver_id}, approved_date = CURRENT_TIMESTAMP where id = #{compiled_branch_audit}")
	public void updateCompiledBranchAuditAfterApproved(Long compiled_branch_audit, Long approver_id, int status);

}
