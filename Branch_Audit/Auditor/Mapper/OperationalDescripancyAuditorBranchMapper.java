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
import com.afr.fms.Branch_Audit.Entity.OperationalDescripancyBranch;

@Mapper
public interface OperationalDescripancyAuditorBranchMapper {

	@Select("insert into branch_financial_audit( auditor_id, branch_id, finding_date, finding, impact ,recommendation, category, case_number, save_template, status, is_edited, auditor_status , audit_type,drafted_date, ing)  OUTPUT inserted.id values(#{auditor.id}, #{auditor.branch.id}, #{finding_date}, #{finding}, #{impact}, #{recommendation}, #{category}, #{case_number}, #{save_template} , 1, 1, 0, #{audit_type},CURRENT_TIMESTAMP, #{ing})")
	public Long createBranchFinantialAudit(BranchFinancialAudit branchFinancialAudit);

	@Select("insert into branch_financial_audit( auditor_id, branch_id, finding_date, finding, impact ,recommendation, category, case_number, save_template, status, is_edited, auditor_status, finding_detail, audit_type,drafted_date, ing)  OUTPUT inserted.id values(#{auditor.id}, #{auditor.branch.id}, #{finding_date}, #{finding}, #{impact}, #{recommendation}, #{category}, #{case_number}, #{save_template} , 1, 1, 0, #{finding_detail}, #{audit_type},CURRENT_TIMESTAMP, #{ing})")
	public Long createBranchFinantialAuditWithFindingDetail(BranchFinancialAudit branchFinancialAudit);

	@Insert("insert  into operational_descripancy_branch(branch_audit_id, operational_descripancy_category_id, acount_holder_name, account_number, transaction_date, amount, cash_type , fcy) values(#{branch_audit_id}, #{operational_descripancy_category_id}, #{acount_holder_name}, #{account_number}, #{transaction_date}, #{amount}, #{cash_type}, #{fcy})")
	public void createOperationalDescripancies(OperationalDescripancyBranch operationalDescripancyBranch);

	@Update("update branch_financial_audit set ing = #{ing}, branch_id = #{branch.id} ,finding_date = #{finding_date}, finding= #{finding} ,impact= #{impact} , recommendation=#{recommendation} , category=#{category}, save_template = #{save_template} where id = #{id}")
	public void updateBranchFinantialAudit(BranchFinancialAudit branchFinancialAudit);

	@Update("update operational_descripancy_branch  set  acount_holder_name =#{acount_holder_name}, account_number=#{account_number}, transaction_date=#{transaction_date} , amount=#{amount}, cash_type = #{cash_type}, fcy = #{fcy} where branch_audit_id=#{branch_audit_id}")
	public void updateOperationalDescripancyBranch(OperationalDescripancyBranch operationalDescripancyBranch);

	@Select("select *  from branch_financial_audit where id in (select branch_audit_id from operational_descripancy_branch)  and auditor_id=#{auditor_id} and auditor_status = 0 and status = 1 and review_status != 1 and approve_status != 1 order by drafted_date DESC")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
			@Result(property = "reviewer", column = "reviewer_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "operationalDescripancyBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.OperationalDescripancyAuditorBranchMapper.getOperationalDescripancyByBranchId")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
			@Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")), })
	public List<BranchFinancialAudit> getDraftedAudits(Long auditor_id);

	@Select("select *  from branch_financial_audit where id in (select branch_audit_id from operational_descripancy_branch) and auditor_id = #{auditor_id}  and  auditor_status = 1 and status = 1 and rectification_status != 1  order by passed_date DESC")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
			@Result(property = "reviewer", column = "reviewer_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "operationalDescripancyBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.OperationalDescripancyAuditorBranchMapper.getOperationalDescripancyByBranchId")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
			@Result(property = "bmFileUrls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Rectification.Mapper.AuditRectificationMapper.getAttachedFiles")),
			@Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")), })

	public List<BranchFinancialAudit> getPassedAudits(Long auditor_id);

	@Select("select *  from branch_financial_audit where id in (select branch_audit_id from operational_descripancy_branch) and auditor_id = #{auditor_id} and category= 'BFA' and  auditor_status = 1 and status = 1 and review_status = 1  ")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "reviewer", column = "reviewer_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "operationalDescripancyBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.OperationalDescripancyAuditorBranchMapper.getOperationalDescripancyByBranchId")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
			@Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")), })

	public List<BranchFinancialAudit> getOnProgressAudits(Long auditor_id);

	@Select("select  * from  operational_descripancy_branch where branch_audit_id=#{branch_audit_id}")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "page_number", column = "page_number"),
			@Result(property = "operationalDescripancyCategory", column = "operational_descripancy_category_id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.OperationalDescripancyCategoryMapper.getOperationalDescripancyCategoryById")), })

	public OperationalDescripancyBranch getOperationalDescripancyByBranchId(Long branch_audit_id);

	@Update("update branch_financial_audit set auditor_status=1,passed_date=CURRENT_TIMESTAMP where id= #{id}")
	public void passOperationalDescripancy(Long id);

	@Update("update branch_financial_audit set auditor_status=0 where id= #{id}")
	public void backPassedOperationalDescripancy(Long id);

	@Select("SELECT TOP 1 case_number FROM branch_financial_audit ORDER BY id DESC;")
	public String getLatestCaseNumber();

	@Update("update branch_financial_audit set status=0 where id= #{id}")
	public void deleteOperationalDescripancy(Long id);

	@Update(" update  branch_financial_audit set ing = #{ing}, branch_id = #{branch.id} ,finding_date = #{finding_date}, finding= #{finding} ,finding_detail=#{finding_detail}, impact= #{impact} , recommendation=#{recommendation} , category=#{category}, save_template = #{save_template} where id = #{id}")
	public void updateBranchFinantialAuditWithFindingDetail(BranchFinancialAudit branchFinancialAudit);

}