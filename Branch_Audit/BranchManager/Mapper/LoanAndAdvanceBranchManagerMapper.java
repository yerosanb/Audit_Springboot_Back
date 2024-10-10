package com.afr.fms.Branch_Audit.BranchManager.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;

@Mapper
public interface LoanAndAdvanceBranchManagerMapper {

	@Select("select *  from branch_financial_audit where id in (select branch_audit_id from loan_advance_branch )  and branch_id=#{branch_id} and auditor_status = 1 and status = 1  and rectification_status !=1 and response_added !=1   order by passed_date DESC ")
	@Results(value = {
		@Result(property = "id", column = "id"),
		@Result(property = "penality_charge_collection", column = "penalty_charge"),

		@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
		@Result(property = "reviewer", column = "reviewer_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
		@Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
		@Result(property = "loanAndAdvance", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.LoanAndAdvanceMapper.getLoanAndAdvanceByBFAId")),
		@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.LoanAndAdvanceMapper.getFileUrlsByAuditID")),
		@Result(property = "bmFileUrls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Rectification.Mapper.AuditRectificationMapper.getAttachedFiles")),

		@Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")),})


	List<BranchFinancialAudit> getPendingAudits(Long branch_id);

	@Select("select *  from branch_financial_audit where id in (select branch_audit_id from loan_advance_branch )  and auditee_id=#{auditee_id} and auditor_status = 1 and status = 1 and rectification_status = 1 and response_added !=1 and and rectification_status !=1 ")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "penality_charge_collection", column = "penalty_charge"),

			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "reviewer", column = "reviewer_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "branch_manager", column = "branch_manager_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "loanAndAdvance", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.LoanAndAdvanceMapper.getLoanAndAdvanceByBFAId")),
			@Result(property = "bmFileUrls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.BranchManagerResponse.Mapper.BranchManagrResponseMapper.getAttachedFiles")),

			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
			@Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")), })

	List<BranchFinancialAudit> getRectifiedAudits(Long auditee_id);

	@Select("select *  from branch_financial_audit where id in (select branch_audit_id from loan_advance_branch )  and auditee_id=#{auditee_id} and auditor_status = 1 and status = 1 and rectification_status =1 and response_added =1")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "penality_charge_collection", column = "penalty_charge"),

			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "reviewer", column = "reviewer_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "branch_manager", column = "branch_manager_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "loanAndAdvance", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.LoanAndAdvanceMapper.getLoanAndAdvanceByBFAId")),
			@Result(property = "bmFileUrls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.BranchManagerResponse.Mapper.BranchManagrResponseMapper.getAttachedFiles")),

			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
			@Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")), })

	List<BranchFinancialAudit> getRespondedAudits(Long auditee_id);

	@Update("update branch_financial_audit set  responded_date = CURRENT_TIMESTAMP, auditee_id=#{auditee.id}, response_added=1 where id = #{id} ")
	public void branchManagerGiveResponse(BranchFinancialAudit branchFinancialAudit);

}
