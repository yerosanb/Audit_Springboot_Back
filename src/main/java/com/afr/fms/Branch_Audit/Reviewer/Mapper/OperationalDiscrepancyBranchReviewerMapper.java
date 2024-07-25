package com.afr.fms.Branch_Audit.Reviewer.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;

@Mapper
public interface OperationalDiscrepancyBranchReviewerMapper {

	// @Select("select bfa.* from branch_financial_audit bfa "
	// + " inner join branch br on bfa.branch_id = br.id and br.region_id =
	// #{region.id} "
	// + " inner join operational_descripancy_branch nib on nib.branch_audit_id =
	// bfa.id"
	// + " where bfa.auditor_status = 1 and bfa.status = 1 and
	// bfa.rectification_status != 1 and bfa.review_status != 1 and
	// bfa.regional_compiler_status != 1 order by passed_date DESC")

	@Select("<script>"
			+ "select bfa.* from branch_financial_audit bfa "
			+ "<choose>"
			+ "<when test='auditType == \"conventional\"'>"
			+ " inner join branch br on bfa.branch_id = br.id and br.region_id = #{user.region.id} "
			+ "</when>"
			+ "</choose>"
			+ " inner join operational_descripancy_branch nib on nib.branch_audit_id = bfa.id"
			+ " where  bfa.auditor_status = 1 and bfa.status = 1 and bfa.banking = #{auditType} and bfa.rectification_status != 1 and bfa.review_status != 1 and bfa.regional_compiler_status != 1 ORDER BY bfa.passed_date DESC"
			+ "</script>")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "operationalDescripancyBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.OperationalDescripancyAuditorBranchMapper.getOperationalDescripancyByBranchId")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
			@Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")),
	})
	public List<BranchFinancialAudit> getPendingAudits(@Param("auditType") String auditType, User user);

	@Select("select bfa.* from branch_financial_audit bfa "
			+ " inner join operational_descripancy_branch nib on nib.branch_audit_id = bfa.id "
			+ " left join operational_discrepancy_pooled_data odpd on odpd.branch_financial_audit_id = bfa.id and odpd.user_id = #{reviewer_id}"
			+ " where  bfa.review_status = 1 and bfa.reviewer_id = #{reviewer_id} and bfa.status = 1 and bfa.rectification_status != 1 and bfa.regional_compiler_status != 1 and odpd.branch_financial_audit_id is null order by bfa.reviewed_date DESC")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "operationalDescripancyBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.OperationalDescripancyAuditorBranchMapper.getOperationalDescripancyByBranchId")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
			@Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")),
	})
	public List<BranchFinancialAudit> getReviewedFindings(Long reviewer_id);

	@Select("select * from branch_financial_audit where id in (select branch_audit_id from incomplete_account_branch) and review_status = 2 and reviewer_id=#{reviewer_id} and status = 1  Order By finding_date")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "operationalDescripancyBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.OperationalDescripancyAuditorBranchMapper.getOperationalDescripancyByBranchId")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
			@Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")), })
	public List<BranchFinancialAudit> getRejectedFindings(Long reviewer_id);

	@Select("select * from branch_financial_audit where id in (select branch_audit_id from incomplete_account_branch) and review_status = 1 and reviewer_id=#{reviewer_id} and status = 1 and approve_status = 1  Order By finding_date")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "operationalDescripancyBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.OperationalDescripancyAuditorBranchMapper.getOperationalDescripancyByBranchId")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
			@Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")), })
	public List<BranchFinancialAudit> getReviewedFindingsStatus(Long reviewer_id);

}
