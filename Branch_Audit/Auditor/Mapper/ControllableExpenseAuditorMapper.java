
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
import com.afr.fms.Branch_Audit.Entity.ControllableExpense;
import com.afr.fms.Branch_Audit.Entity.ControllableExpenseType;

@Mapper
public interface ControllableExpenseAuditorMapper {

	@Select("insert into branch_financial_audit( auditor_id  , branch_id  ,finding_date ,finding ,impact ,recommendation ,category,case_number , save_template ,status,is_edited ,auditor_status , audit_type, drafted_date, ing)  OUTPUT inserted.id values(#{auditor.id},#{auditor.branch.id}, #{finding_date}, #{finding}, #{impact}, #{recommendation}, #{category}, #{case_number}, #{save_template} , 1, 1, 0, #{audit_type}, CURRENT_TIMESTAMP, #{ing})")
	public Long createBranchFinantialAuditwithoutFindingDetail(BranchFinancialAudit branchFinancialAudit);

	@Select("insert into branch_financial_audit( auditor_id  , branch_id  ,finding_date ,finding ,impact ,recommendation ,category,case_number , save_template ,status,is_edited ,auditor_status , finding_detail , audit_type, drafted_date, ing)  OUTPUT inserted.id values(#{auditor.id},#{auditor.branch.id}, #{finding_date}, #{finding}, #{impact}, #{recommendation}, #{category}, #{case_number}, #{save_template} , 1, 1, 0, #{finding_detail}, #{audit_type}, CURRENT_TIMESTAMP, #{ing})")
	public Long createBranchFinantialAudit(BranchFinancialAudit branchFinancialAudit);

	@Select("insert  into controllable_expense_branch(branch_audit_id, variation , period, budget_per_plan, actual_balance, cash_type, fcy) OUTPUT inserted.id values(#{branch_audit_id}, #{variation}, #{period}, #{budget_per_plan}, #{actual_balance},  #{cash_type}, #{fcy} )")
	public Long create_controllable_expense_branch(ControllableExpense controllableExpense);

	@Insert("insert  into controllable_expense_branch_mapping(controllable_branch_id, controllable_expense_type_id) values(#{controllable_branch_id}, #{controllable_expense_type_id})")
	public void create_controllable_expense_branch_type(Long controllable_branch_id, Long controllable_expense_type_id);

	@Insert("delete from controllable_expense_branch_mapping where controllable_branch_id = #{controllable_branch_id}")
	public void delete_controllable_expense_branch_type(Long controllable_branch_id);

	@Update(" update  branch_financial_audit set ing = #{ing}, finding_date = #{finding_date}, finding= #{finding} ,impact= #{impact} , recommendation=#{recommendation} , category=#{category}, save_template = #{save_template} where id = #{id}")
	public void updateBranchFinantialAudit(BranchFinancialAudit branchFinancialAudit);

	@Update(" update  branch_financial_audit set ing = #{ing}, finding_date = #{finding_date}, finding= #{finding} ,impact= #{impact} , recommendation=#{recommendation} , category=#{category}, save_template = #{save_template} , finding_detail = #{finding_detail} where id = #{id}")
	public void updateBranchFinantialAuditWithFindingDetail(BranchFinancialAudit branchFinancialAudit);

	@Update("update controllable_expense_branch set  variation =#{variation}, period=#{period}, budget_per_plan=#{budget_per_plan} , actual_balance=#{actual_balance} , cash_type = #{cash_type}, fcy = #{fcy} where id = #{id}")
	public void update_controllable_expense_branch(ControllableExpense controllableExpense);

	@Select("select *  from branch_financial_audit where id in (select branch_audit_id from controllable_expense_branch)  and auditor_id=#{auditor_id} and auditor_status = 0 and status = 1 and review_status != 1 and approve_status != 1 ORDER BY drafted_date DESC")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),

			@Result(property = "reviewer", column = "reviewer_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "controllableExpense", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.ControllableExpenseAuditorMapper.getControllableExpenseByBranchId")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
			@Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")), })

	public List<BranchFinancialAudit> getDraftedAudits(Long auditor_id);

	@Select("select *  from branch_financial_audit where id in (select branch_audit_id from controllable_expense_branch) and auditor_id = #{auditor_id} and category= 'BFA' and  auditor_status = 1 and status = 1   and rectification_status !=1  ORDER BY passed_date DESC ")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "reviewer", column = "reviewer_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "controllableExpense", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.ControllableExpenseAuditorMapper.getControllableExpenseByBranchId")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
			@Result(property = "bmFileUrls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Rectification.Mapper.AuditRectificationMapper.getAttachedFiles")),

			@Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")), })

	public List<BranchFinancialAudit> getPassedAudits(Long auditor_id);

	@Select("select *  from branch_financial_audit where id in (select branch_audit_id from controllable_expense_branch) and auditor_id = #{auditor_id} and category= 'BFA' and  auditor_status = 1 and status = 1 and review_status = 1  ")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "reviewer", column = "reviewer_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "controllableExpense", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.ControllableExpenseAuditorMapper.getControllableExpenseByBranchId")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
			@Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")), })

	public List<BranchFinancialAudit> getOnProgressAudits(Long auditor_id);

	@Select("select  * from  controllable_expense_branch where branch_audit_id=#{branch_audit_id}")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "controllableExpenseType", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.ControllableExpenseAuditorMapper.getControllableExpenseType"))
	})

	public ControllableExpense getControllableExpenseByBranchId(Long branch_audit_id);

	@Select("select  * from  controllable_expense_type where  id in(select controllable_expense_type_id from controllable_expense_branch_mapping  where controllable_branch_id=#{controllable_branch_id})")
	public List<ControllableExpenseType> getControllableExpenseType(Long controllable_branch_id);

	@Update("update branch_financial_audit set auditor_status=1 , passed_date = CURRENT_TIMESTAMP where id= #{id}")
	public void pass_controllable_expense_branch(Long id);

	@Update("update branch_financial_audit set auditor_status=0 where id= #{id}")
	public void backPassed_controllable_expense_branch(Long id);

	@Select("SELECT TOP 1 case_number FROM branch_financial_audit ORDER BY id DESC;")
	public String getLatestCaseNumber();

	@Update("update branch_financial_audit set status=0 where id= #{id}")
	public void delete_controllable_expense_branch(Long id);

}
