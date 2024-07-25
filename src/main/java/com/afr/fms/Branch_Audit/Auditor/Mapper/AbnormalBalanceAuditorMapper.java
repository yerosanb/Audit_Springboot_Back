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

import com.afr.fms.Branch_Audit.Entity.AbnormalBalance;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;

@Mapper
public interface AbnormalBalanceAuditorMapper {

	@Select("insert into branch_financial_audit( auditor_id  , branch_id  ,finding_date ,finding ,impact ,recommendation ,category,case_number ,save_template ,status,is_edited ,auditor_status, finding_detail, audit_type, drafted_date, banking)  OUTPUT inserted.id values(#{auditor.id}, #{auditor.branch.id}, #{finding_date}, #{finding}, #{impact}, #{recommendation}, #{category}, #{case_number}, #{save_template} , 1, 1, 0, #{finding_detail},#{audit_type}, CURRENT_TIMESTAMP, #{banking})")
	public Long createBranchFinantialAudit(BranchFinancialAudit abnormalBalance);

	@Select("insert into branch_financial_audit( auditor_id  , branch_id  ,finding_date ,finding ,impact ,recommendation ,category,case_number ,save_template ,status,is_edited ,auditor_status, audit_type, drafted_date, banking)  OUTPUT inserted.id values(#{auditor.id}, #{auditor.branch.id}, #{finding_date}, #{finding}, #{impact}, #{recommendation}, #{category}, #{case_number}, #{save_template} , 1, 1, 0, #{audit_type}, CURRENT_TIMESTAMP, #{banking})")
	public Long createBranchFinantialAuditWithOutFindingDetail(BranchFinancialAudit abnormalBalance);

	@Insert("insert into abnormal_balance_branch (branch_audit_id , account_affected , debit, credit , difference,  cash_type, fcy) values(#{branch_audit_id}, #{account_affected},  #{debit}, #{credit}, #{difference}, #{cash_type}, #{fcy})")
	public void createAbnormalBalance(AbnormalBalance abnormalBalance);

	@Update(" update  branch_financial_audit set banking = #{banking}, finding_date = #{finding_date}, finding= #{finding} ,impact= #{impact} , recommendation=#{recommendation} , category=#{category}, save_template = #{save_template} where id =#{id}")
	public void updateBranchFinantialAudit(BranchFinancialAudit branchFinancialAudit);

	@Update(" update  branch_financial_audit set banking = #{banking}, finding_date = #{finding_date}, finding= #{finding} ,impact= #{impact} , recommendation=#{recommendation} , category=#{category}, save_template = #{save_template}, finding_detail= #{finding_detail} where id= #{id} ")
	public void updateBranchFinantialAuditWithFindingDetail(BranchFinancialAudit branchFinancialAudit);

	@Update("update abnormal_balance_branch  set  account_affected =#{account_affected}, debit=#{debit}, credit=#{credit} , difference=#{difference} , cash_type = #{cash_type}, fcy = #{fcy} where branch_audit_id=#{branch_audit_id}")
	public void updateAbnormalBalance(AbnormalBalance abnormalBalance);

	@Select("select *  from branch_financial_audit where id in (select branch_audit_id from abnormal_balance_branch)  and auditor_id=#{auditor_id} and auditor_status = 0 and status = 1 and review_status != 1 and approve_status != 1 ORDER BY drafted_date DESC ")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),

			@Result(property = "reviewer", column = "reviewer_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "abnormalBalanceBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.AbnormalBalanceAuditorMapper.getAbnormalBalanceByBranchId")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),

			@Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")), })

	public List<BranchFinancialAudit> getDraftedAudits(Long auditor_id);

	@Select("select *  from branch_financial_audit where id in (select branch_audit_id from abnormal_balance_branch) and auditor_id = #{auditor_id} and category= 'BFA' and  auditor_status = 1 and status = 1 and rectification_status !=1  ORDER BY passed_date DESC ")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "reviewer", column = "reviewer_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "abnormalBalanceBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.AbnormalBalanceAuditorMapper.getAbnormalBalanceByBranchId")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
			@Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")),
			@Result(property = "bmFileUrls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Rectification.Mapper.AuditRectificationMapper.getAttachedFiles")),
	})

	public List<BranchFinancialAudit> getPassedAudits(Long auditor_id);

	@Select("select *  from branch_financial_audit where id in (select branch_audit_id from abnormal_balance_branch) and auditor_id = #{auditor_id} and category= 'BFA' and  auditor_status = 1 and status = 1 and review_status = 1  ")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "reviewer", column = "reviewer_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "abnormalBalanceBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.AbnormalBalanceAuditorMapper.getAbnormalBalanceByBranchId")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
			@Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")), })

	public List<BranchFinancialAudit> getOnProgressAudits(Long auditor_id);

	@Select("select  * from  abnormal_balance_branch where branch_audit_id=#{branch_audit_id}")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "page_number", column = "page_number"),
	})

	public AbnormalBalance getAbnormalBalanceByBranchId(Long branch_audit_id);

	@Update("update branch_financial_audit set auditor_status=1, passed_date = CURRENT_TIMESTAMP where id= #{id}")
	public void passAbnormalBalance(Long id);

	@Update("update branch_financial_audit set auditor_status=0 where id= #{id}")
	public void backPassedAbnormalBalance(Long id);

	@Select("SELECT TOP 1 case_number FROM branch_financial_audit ORDER BY id DESC;")
	public String getLatestCaseNumber();

	@Update("update branch_financial_audit set status=0 where id= #{id}")
	public void deleteAbnormalBalance(Long id);

}