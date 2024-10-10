package com.afr.fms.Branch_Audit.Auditor.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Entity.IncompleteAccountBranch;

@Mapper
public interface IncompleteAccountBranchMapper {
	@Select("insert into branch_financial_audit(case_number, category, auditor_id, finding_date, finding, impact, recommendation, save_template, status, is_edited, auditor_status, drafted_date, branch_id, audit_type, ing) OUTPUT inserted.id values(#{case_number}, #{category}, #{auditor.id}, #{finding_date}, #{finding}, #{impact}, #{recommendation}, #{save_template}, 1, 1, 0, CURRENT_TIMESTAMP,#{auditor.branch.id}, #{audit_type}, #{ing})")
	public Long createBAFinding(BranchFinancialAudit audit);

	@Select("insert into branch_financial_audit(case_number, category, auditor_id, finding_date, finding, finding_detail, impact, recommendation, save_template, status, is_edited, auditor_status, drafted_date, branch_id, audit_type, ing) OUTPUT inserted.id values(#{case_number}, #{category}, #{auditor.id}, #{finding_date}, #{finding}, #{finding_detail}, #{impact}, #{recommendation}, #{save_template}, 1, 1, 0, CURRENT_TIMESTAMP,#{auditor.branch.id}, #{audit_type}, #{ing})")
	public Long createBAFindingWithFindingDetail(BranchFinancialAudit audit);

	@Select("insert into incomplete_account_branch(branch_audit_id, account_number, account_type, account_opened_amount, customer_name, account_opened_date, opened_by, approved_by, branch_id, fcy) OUTPUT inserted.id values(#{branch_audit_id}, #{account_number}, #{account_type}, #{account_opened_amount}, #{customer_name}, #{account_opened_date}, #{opened_by}, #{approved_by}, #{branch.id}, #{fcy})")
	public Long createIAFinding(IncompleteAccountBranch audit);

	@Select("insert into uploaded_file_branch(branch_audit_id, file_url, uploaded_date) OUTPUT inserted.id values(#{branch_audit_id}, #{file_url}, CURRENT_TIMESTAMP)")
	public Long InsertFileUrl(String file_url, Long branch_audit_id);

	@Delete("delete from uploaded_file_branch where branch_audit_id = #{branch_audit_id}")
	public void removeFileUrls(Long branch_audit_id);

	@Select("select file_url from uploaded_file_branch where branch_audit_id = #{branch_audit_id}")
	public List<String> getFileUrlsByAuditID(Long branch_audit_id);

	@Update("update incomplete_account_branch set  account_number=#{account_number}, account_type = #{account_type}, account_opened_amount=#{account_opened_amount}, customer_name=#{customer_name}, account_opened_date = #{account_opened_date}, opened_by = #{opened_by}, approved_by = #{approved_by}, fcy = #{fcy} where branch_audit_id=#{branch_audit_id}")
	public void updateIAFinding(IncompleteAccountBranch audit);

	@Select("select * from branch_financial_audit where id in (select branch_audit_id from incomplete_account_branch) and auditor_id = #{auditor_id} and auditor_status = 1  and rectification_status !=1 and status = 1 Order By passed_date DESC")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),

			@Result(property = "reviewer", column = "reviewer_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "incompleteAccountBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getIncompleteAccountBranch")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
			@Result(property = "bmFileUrls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Rectification.Mapper.AuditRectificationMapper.getAttachedFiles")),
			@Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")),
	})

	public List<BranchFinancialAudit> getAuditsForAuditor(Long auditor_id);

	@Select("select * from incomplete_account_branch where branch_audit_id = #{id}")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
	})
	public IncompleteAccountBranch getIncompleteAccountBranch(Long id);

	@Select("select * from branch_financial_audit where id in (select branch_audit_id from incomplete_account_branch) and auditor_id = #{auditor_id} and auditor_status != 1 and status = 1 Order By drafted_date DESC")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "reviewer", column = "reviewer_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "incompleteAccountBranch", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getIncompleteAccountBranch")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
			@Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")),
	})
	public List<BranchFinancialAudit> getAuditsOnDrafting(Long auditor_id);

	@Select("select CAST(finding_date AS date) AS audit_date, * from branch_financial_audit where  auditor_id = #{auditor_id} and rectification_status !=1 and auditor_status = 1 and status = 1 Order By drafted_date DESC")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "reviewer", column = "reviewer_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "incompleteAccountBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getIncompleteAccountBranch")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
			@Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")),
	})
	public List<BranchFinancialAudit> getAuditsOnProgressForAuditor(Long auditor_id);

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

	@Select("SELECT TOP 1 file_url FROM uploaded_file_branch ORDER BY id DESC;")
	public String getLatestFileUrl();

	@Update("update branch_financial_audit set ing = #{ing}, finding=#{finding}, impact=#{impact}, recommendation=#{recommendation}, finding_date = #{finding_date}, is_edited = 1 where id=#{id}")
	public void updateBAFinding(BranchFinancialAudit audit);

	@Update("update branch_financial_audit set ing = #{ing}, finding=#{finding}, finding_detail=#{finding_detail}, impact=#{impact}, recommendation=#{recommendation}, finding_date = #{finding_date}, is_edited = 1 where id=#{id}")
	public void updateBAFindingWithFindingDetail(BranchFinancialAudit audit);

	@Update("update branch_financial_audit set status=0 where id=#{id}")
	public void deleteBAFinding(Long id);

	@Update("update branch_financial_audit set auditor_status=1, passed_date = CURRENT_TIMESTAMP where id=#{id}")
	public void passBAFinding(Long id);

	@Update("update branch_financial_audit set auditor_status = 0, review_status = 0 where id=#{id}")
	public void backBAFinding(Long id);

	@Delete("delete from IS_MGT_Auditee where IS_MGT_id = #{IS_MGT_id}")
	public void deleteBAAuditee(Long IS_MGT_id);

	@Update("update branch_financial_audit set rectification_status = #{rectification_status} where id = #{id}")
	public void changeRecitificationStatus(BranchFinancialAudit audit);

	@Update("update branch_financial_audit set reviewer_id=#{reviewer.id}, review_status = 1 where id=#{id}")
	public void reviewBAFinding(BranchFinancialAudit audit);

	@Update("update branch_financial_audit set reviewer_id=#{reviewer.id}, review_status = 0 where id=#{id}")
	public void unreviewBAFnding(BranchFinancialAudit audit);

	@Update("update branch_financial_audit set rectification_status = 1 , followup_id= #{followup_officer.id}  where id = #{id}")
	public void rectifyBAFinding(BranchFinancialAudit BranchFinancialAudit);

	@Update("update branch_financial_audit set rectification_status = 0 , followup_id= #{followup_officer.id}  where id = #{id}")
	public void UnrectifyBAFinding(BranchFinancialAudit audit);

	@Update("update branch_financial_audit set approver_id = #{approver.id}, approve_status=1 where id = #{id}")
	public void approveBAAudit(BranchFinancialAudit audit);

	@Select("select * from branch_financial_audit where approver_id=#{approver_id}  and approve_status=1 and status = 1")
	public List<BranchFinancialAudit> getApprovedFndings(Long approver_id);

	@Update("update branch_financial_audit set approver_id = #{approver.id}, approve_status=0 where id = #{id}")
	public void cancelApprovedBAAudit(BranchFinancialAudit audit);

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
	
}
