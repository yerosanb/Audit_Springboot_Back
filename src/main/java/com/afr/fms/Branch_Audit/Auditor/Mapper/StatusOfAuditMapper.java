package com.afr.fms.Branch_Audit.Auditor.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Entity.StatusOfAudit;

@Mapper
public interface StatusOfAuditMapper {

	@Select("insert into branch_financial_audit(case_number, category, branch_id, auditor_id, finding_date, save_template, status, is_edited, auditor_status, drafted_date, audit_type, banking) OUTPUT inserted.id values(#{case_number},#{category}, #{auditor.branch.id}, #{auditor.id}, #{finding_date}, #{save_template}, 1, 1, 0, CURRENT_TIMESTAMP, 'Audit Status', #{banking})")
	public Long insertIntoBranchFinancial(BranchFinancialAudit audit);

	@Insert("insert into audit_status_branch(branch_audit_id,number_of_back_log_day,justification) values(#{branch_audit_id},#{number_of_back_log_day},#{justification})")
	public void saveStatusOfAudit(StatusOfAudit audit);

	@Update("update audit_status_branch set number_of_back_log_day=#{number_of_back_log_day}, justification = #{justification} where branch_audit_id=#{branch_audit_id}")
	public void updateStatusOfAudit(StatusOfAudit statusOfAudit);
	
	@Update("update branch_financial_audit set banking = #{banking}, finding_date=#{finding_date} where id=#{id}")
	public void updateBFAudit(BranchFinancialAudit branchFinancialAudit);

	@Select("SELECT TOP 1 case_number FROM branch_financial_audit ORDER BY id DESC;")
	public String getLatestCaseNumber();

	@Select("select * from branch_financial_audit where auditor_id = #{auditor_id} and id in (select branch_audit_id from audit_status_branch) and auditor_status = 0 and status = 1 order by drafted_date DESC")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),

			@Result(property = "statusofAudit", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.StatusOfAuditMapper.getBFAByStatusofAudit")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
	})
	List<BranchFinancialAudit> getStatusOfAudit(Long auditor_id);

	@Select("select * from audit_status_branch where branch_audit_id= #{id}")
	public StatusOfAudit getBFAByStatusofAudit(Long id);

	@Update("update branch_financial_audit set status=0 where id=#{id}")
	public void deleteStatusOfAudit(Long id);

	@Update("update branch_financial_audit set auditor_status=1, passed_date = CURRENT_TIMESTAMP where id=#{id}")
	public void passStatusOfAudit(Long id);

	@Update("update branch_financial_audit set auditor_status = 0 where id=#{id}")
	public void backStatusOfAudit(Long id);

	@Select("select * from branch_financial_audit where auditor_id = #{auditor_id} and id in (select branch_audit_id from audit_status_branch) and auditor_status = 1 and status = 1 order by passed_date DESC")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "statusofAudit", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.StatusOfAuditMapper.getBFAByStatusofAudit")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
			@Result(property = "bmFileUrls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Rectification.Mapper.AuditRectificationMapper.getAttachedFiles")),
			@Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")),
	})
	public List<BranchFinancialAudit> getPassedStatusOfAudit(Long auditor_id);

}
