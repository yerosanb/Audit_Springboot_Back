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
import com.afr.fms.Branch_Audit.Entity.SuspenseAccountBranch;
import com.afr.fms.Branch_Audit.Entity.SuspenseAccountType;

@Mapper
public interface SuspenseAccountAuditorBranchMapper {

	@Select("insert into branch_financial_audit( auditor_id  , branch_id  ,finding_date ,finding ,impact ,recommendation ,category,case_number , save_template ,status,is_edited ,auditor_status, audit_type, drafted_date, banking)  OUTPUT inserted.id values(#{auditor.id}, #{auditor.branch.id}, #{finding_date}, #{finding}, #{impact}, #{recommendation}, #{category}, #{case_number}, #{save_template} , 1, 1, 0, #{audit_type}, CURRENT_TIMESTAMP, #{banking})")
	public Long createBranchFinantialAudit(BranchFinancialAudit branchFinancialAudit);

	@Select("insert into branch_financial_audit( auditor_id  , branch_id  ,finding_date ,finding ,impact ,recommendation ,category,case_number , save_template ,status,is_edited ,auditor_status, finding_detail, audit_type, drafted_date, banking)  OUTPUT inserted.id values(#{auditor.id}, #{auditor.branch.id}, #{finding_date}, #{finding}, #{impact}, #{recommendation}, #{category}, #{case_number}, #{save_template} , 1, 1, 0, #{finding_detail}, #{audit_type}, CURRENT_TIMESTAMP, #{banking})")
	public Long createBranchFinantialAuditWithFindingDetail(BranchFinancialAudit branchFinancialAudit);

	@Select("insert  into suspense_account_branch(branch_audit_id, difference , tracer_date, balance_per_tracer, balance_per_trial_balance, cash_type , fcy ) OUTPUT inserted.id values(#{branch_audit_id}, #{difference}, #{tracer_date}, #{balance_per_tracer}, #{balance_per_trial_balance}, #{cash_type} , #{fcy})")
	public Long createSuspenseAccountBranch(SuspenseAccountBranch suspenseAccountBranch);

	@Insert("insert  into suspense_account_branch_type(suspense_account_id, suspense_account_type_id) values(#{suspense_account_id}, #{suspense_account_type_id})")

	public void createSuspenseAccountBranchType(Long suspense_account_id, Long suspense_account_type_id);

	@Insert("delete from suspense_account_branch_type where suspense_account_id = #{suspense_account_id}")

	public void delete_suspense_account_branch_type(Long suspense_account_id);

	@Update(" update branch_financial_audit set banking = #{banking}, finding_date = #{finding_date}, finding= #{finding} ,impact= #{impact} , recommendation=#{recommendation} , category=#{category}, save_template = #{save_template} where id = #{id}")
	public void updateBranchFinantialAudit(BranchFinancialAudit branchFinancialAudit);

	@Update(" update  branch_financial_audit  set banking = #{banking},  finding_date = #{finding_date}, finding= #{finding} ,impact= #{impact} , recommendation=#{recommendation} , category=#{category}, save_template = #{save_template}, finding_detail = #{finding_detail} where id = #{id}")
	public void updateBranchFinantialAuditWithFindingDetail(BranchFinancialAudit branchFinancialAudit);

	@Update("update suspense_account_branch  set  difference =#{difference}, tracer_date=#{tracer_date}, balance_per_tracer=#{balance_per_tracer} , balance_per_trial_balance=#{balance_per_trial_balance} , cash_type = #{cash_type}, fcy = #{fcy} where branch_audit_id = #{branch_audit_id}")
	public void updateSuspenseAccountBranch(SuspenseAccountBranch suspenseAccountBranch);

	@Select("select *  from branch_financial_audit where id in (select branch_audit_id from suspense_account_branch)  and auditor_id=#{auditor_id} and auditor_status = 0 and status = 1 and review_status != 1 and approve_status != 1 ORDER BY drafted_date DESC")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),

			@Result(property = "reviewer", column = "reviewer_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "suspenseAccountBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.SuspenseAccountAuditorBranchMapper.getSuspenseAccountByBranchId")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
			@Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")), })

	public List<BranchFinancialAudit> getDraftedAudits(Long auditor_id);

	@Select("select *  from branch_financial_audit where id in (select branch_audit_id from suspense_account_branch) and auditor_id = #{auditor_id} and category= 'BFA' and  auditor_status = 1 and status = 1 and rectification_status !=1  ORDER BY passed_date DESC ")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "reviewer", column = "reviewer_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "suspenseAccountBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.SuspenseAccountAuditorBranchMapper.getSuspenseAccountByBranchId")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
			@Result(property = "bmFileUrls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Rectification.Mapper.AuditRectificationMapper.getAttachedFiles")),
			@Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")), })

	public List<BranchFinancialAudit> getPassedAudits(Long auditor_id);

	@Select("select *  from branch_financial_audit where id in (select branch_audit_id from suspense_account_branch) and auditor_id = #{auditor_id} and category= 'BFA' and  auditor_status = 1 and status = 1 and review_status = 1  ")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "reviewer", column = "reviewer_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "suspenseAccountBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.SuspenseAccountAuditorBranchMapper.getSuspenseAccountByBranchId")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
			@Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")), })

	public List<BranchFinancialAudit> getOnProgressAudits(Long auditor_id);

	@Select("select  * from  suspense_account_branch where branch_audit_id=#{branch_audit_id}")

	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "suspenseAccountType", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.SuspenseAccountAuditorBranchMapper.getSuspenseAccountTypeBySuspenseAccountId"))
	})

	public SuspenseAccountBranch getSuspenseAccountByBranchId(Long branch_audit_id);

	@Select("select  * from  suspense_account_type where  id in(select suspense_account_type_id from suspense_account_branch_type  where suspense_account_id=#{suspense_account_id})")

	public List<SuspenseAccountType> getSuspenseAccountTypeBySuspenseAccountId(Long suspense_account_id);

	@Update("update branch_financial_audit set auditor_status=1, passed_date = CURRENT_TIMESTAMP where id= #{id}")
	public void passSuspenseAccountBranch(Long id);

	@Update("update branch_financial_audit set auditor_status=0 where id= #{id}")
	public void backPassedSuspenseAccountBranch(Long id);

	@Select("SELECT TOP 1 case_number FROM branch_financial_audit ORDER BY id DESC;")
	public String getLatestCaseNumber();

	@Update("update branch_financial_audit set status=0 where id= #{id}")
	public void deleteSuspenseAccountBranch(Long id);

}