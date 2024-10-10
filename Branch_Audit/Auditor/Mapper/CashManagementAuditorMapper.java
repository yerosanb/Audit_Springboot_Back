
package com.afr.fms.Branch_Audit.Auditor.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Entity.CashManagementBranch;

@Mapper
public interface CashManagementAuditorMapper {
	@Select("insert into branch_financial_audit(case_number, auditor_id,branch_id, finding_date, finding, audit_type, impact, recommendation, save_template,category, status, is_edited, auditor_status,drafted_date, ing) OUTPUT inserted.id values(#{case_number}, #{auditor.id}, #{auditor.branch.id}, #{finding_date}, #{finding},'cash_management_branch', #{impact}, #{recommendation}, #{save_template},#{category}, 1, 1, 0,CURRENT_TIMESTAMP, #{ing})")
	public Long createBAFinding(BranchFinancialAudit audit);

	@Select("insert into cash_management_branch(branch_audit_id, average_cash_holding, difference, branch_cash_set_limit, mid_rate_fcy, cash_type, fcy) OUTPUT inserted.id values(#{branch_audit_id}, #{average_cash_holding}, #{difference}, #{branch_cash_set_limit}, #{mid_rate_fcy}, #{cash_type}, #{fcy})")
	public Long createCashManagementBranchFinding(CashManagementBranch audit);

	@Update("update cash_management_branch set average_cash_holding=#{average_cash_holding}, difference = #{difference}, branch_cash_set_limit=#{branch_cash_set_limit}, mid_rate_fcy=#{mid_rate_fcy}, cash_type=#{cash_type}, fcy = #{fcy} where branch_audit_id=#{branch_audit_id}")
	public void updateCashManagementBranchFinding(CashManagementBranch audit);

	@Select("select * from branch_financial_audit where id in (select branch_audit_id from cash_management_branch) and auditor_id = #{auditor_id} and auditor_status = 1 and status = 1  and rectification_status !=1 Order By passed_date DESC")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "reviewer", column = "reviewer_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "cashManagementBranch", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.CashManagementAuditorMapper.getCashManagementBranch")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
			@Result(property = "bmFileUrls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Rectification.Mapper.AuditRectificationMapper.getAttachedFiles")),
			@Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")),
	})
	public List<BranchFinancialAudit> getAuditsForAuditor(Long auditor_id);

	@Select("select * from cash_management_branch where branch_audit_id = #{id}")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
	})
	public CashManagementBranch getCashManagementBranch(Long id);

	@Select("select * from branch_financial_audit where id in (select branch_audit_id from cash_management_branch) and auditor_id = #{auditor_id} and auditor_status != 1 and status = 1 Order By drafted_date DESC")
	@Results(value = {

			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),

			@Result(property = "reviewer", column = "reviewer_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "cashManagementBranch", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.CashManagementAuditorMapper.getCashManagementBranch")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
			@Result(property = "bmFileUrls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Rectification.Mapper.AuditRectificationMapper.getAttachedFiles")),
			@Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")),
	})
	public List<BranchFinancialAudit> getAuditsOnDrafting(Long auditor_id);

	@Select("select * from branch_financial_audit where id = #{id}")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "reviewer", column = "reviewer_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "incompleteAccountBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getIncompleteAccountBranch")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),

			@Result(property = "changeTrackerBranchAudits", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")),
	})
	public BranchFinancialAudit getAudit(Long id);

	@Select("SELECT TOP 1 case_number FROM branch_financial_audit ORDER BY id DESC;")
	public String getLatestCaseNumber();

	@Update("update branch_financial_audit set ing = #{ing}, finding=#{finding}, impact=#{impact}, recommendation=#{recommendation}, finding_date = #{finding_date}, is_edited = 1 where id=#{id}")
	public void updateBAFinding(BranchFinancialAudit audit);

	@Select("select * from branch_financial_audit Order By finding_date")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "reviewer", column = "reviewer_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "IS_MGTAuditee", column = "id", many = @Many(select = "com.afr.fms.Auditor.Mapper.BranchFinancialAuditMapper.getBAAuditees")),
			@Result(property = "change_tracker_BA", column = "id", many = @Many(select = "com.afr.fms.Common.Audit_Change_Tracker.BA.ChangeTrackerBAMapper.getChanges")),
	})
	public List<BranchFinancialAudit> getAuditFindings();

	@Select("insert into branch_financial_audit(case_number, auditor_id,branch_id, finding_date, finding, finding_detail, audit_type, impact, recommendation, save_template,category, status, is_edited, auditor_status,drafted_date, ing) OUTPUT inserted.id values(#{case_number}, #{auditor.id}, #{auditor.branch.id}, #{finding_date}, #{finding},#{finding_detail},'cash_management_branch', #{impact}, #{recommendation}, #{save_template},#{category}, 1, 1, 0,CURRENT_TIMESTAMP, #{ing})")
	public Long createBAFindingWithFindingDetail(BranchFinancialAudit audit);

	@Update("update branch_financial_audit set ing = #{ing}, finding=#{finding},finding_detail=#{finding_detail}, impact=#{impact}, recommendation=#{recommendation}, finding_date = #{finding_date}, is_edited = 1 where id=#{id}")
	public void updateBAFindingWithFindingDetail(BranchFinancialAudit audit);

}
