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
public interface StatusOfAuditReviewerMapper {


	@Select("<script>"
			+ "select bfa.* from branch_financial_audit bfa "
			+ "<choose>"
			+ "<when test='auditType == \"conventional\"'>"
			+ " inner join branch br on bfa.branch_id = br.id and br.region_id = #{user.region.id} "
			+ "</when>"
			+ "</choose>"
			+ " inner join audit_status_branch nib on nib.branch_audit_id = bfa.id"
			+ " where  bfa.auditor_status = 1 and bfa.status = 1 and bfa.banking = #{auditType} and bfa.rectification_status != 1 and bfa.review_status != 1 and bfa.regional_compiler_status != 1 ORDER BY bfa.passed_date DESC"
			+ "</script>")
	// @Select("select bfa.* from branch_financial_audit bfa "
	// + " inner join branch br on bfa.branch_id = br.id and br.region_id =
	// #{region_id} "
	// + " inner join audit_status_branch nib on nib.branch_audit_id = bfa.id"
	// + " where bfa.auditor_status = 1 and bfa.status = 1 and
	// bfa.rectification_status != 1 and bfa.review_status != 1 and
	// bfa.regional_compiler_status != 1 order by passed_date DESC")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),

			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "statusofAudit", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.StatusOfAuditMapper.getBFAByStatusofAudit")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
	})
	public List<BranchFinancialAudit> getPendingStatusOfAudit(@Param("auditType") String auditType, User user);

	@Select("select * from branch_financial_audit where id in (select branch_audit_id from audit_status_branch) and review_status = 1 and reviewer_id=#{reviewer_id} and status = 1 Order By reviewed_date DESC")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),

			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "statusofAudit", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.StatusOfAuditMapper.getBFAByStatusofAudit")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
	})
	public List<BranchFinancialAudit> getReviewedStatusOfAudit(Long reviewer_d);

}
