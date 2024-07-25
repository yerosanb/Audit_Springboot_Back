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
import com.afr.fms.Branch_Audit.Entity.CashCountEntity;

@Mapper
public interface CashCountMapper {

	@Select("select * from branch_financial_audit where auditor_id = #{id} and id in (select branch_audit_id from cash_count_branch) and auditor_status = 0 and status = 1 order by drafted_date DESC")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "reviewer", column = "reviewer_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "cashcount", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.CashCountMapper.getCashCountById")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
			@Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")),
	})
	List<BranchFinancialAudit> getCashCount(Long id);

	@Select("select * from cash_count_branch where branch_audit_id= #{id}")
	public CashCountEntity getCashCountById(Long id);

	@Select("SELECT TOP 1 case_number FROM branch_financial_audit ORDER BY id DESC;")
	public String getLatestCaseNumber();

	@Select("insert into branch_financial_audit(case_number, category, auditor_id, finding_date, finding, audit_type, impact, recommendation, save_template, status, is_edited, auditor_status, drafted_date, branch_id, banking) OUTPUT inserted.id values(#{case_number},#{category}, #{auditor.id}, #{finding_date}, #{finding},'cash_count_branch', #{impact}, #{recommendation}, #{save_template}, 1, 1, 0, CURRENT_TIMESTAMP, #{auditor.branch.id}, #{banking})")
	public Long insertIntoBranchFinancial(BranchFinancialAudit audit);

	@Insert("insert into cash_count_branch(branch_audit_id,accountable_staff,actual_cash_count,trial_balance,difference,cash_count_type, fcy) values(#{branch_audit_id},#{accountable_staff},#{actual_cash_count},#{trial_balance},#{difference},#{cash_count_type}, #{fcy})")
	public void addCashCount(CashCountEntity cashcount);

	@Select("select * from branch_financial_audit where id in (select branch_audit_id from incomplete_account_branch) and auditor_id = #{auditor_id} and auditor_status != 1 and status = 1 Order By drafted_date DESC")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),

			@Result(property = "reviewer", column = "reviewer_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "cashcount", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.CashCountMapper.getCashCountById")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
			@Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")),
	})
	public List<BranchFinancialAudit> getAuditsOnDrafting(Long auditor_id);

	@Update("update branch_financial_audit set status=0 where id=#{id}")
	public void deleteSelectedCashCount(Long id);

	@Update("update branch_financial_audit set auditor_status=1, passed_date = CURRENT_TIMESTAMP where id=#{id}")
	public void passSelectedCashCount(Long id);

	@Update("update branch_financial_audit set  banking = #{banking}, finding=#{finding}, impact=#{impact}, recommendation=#{recommendation}, finding_date = #{finding_date}, is_edited = 1 where id=#{id}")
	public void updateBranchFinancial(BranchFinancialAudit audit);

	@Update("update cash_count_branch set accountable_staff=#{accountable_staff},actual_cash_count=#{actual_cash_count},trial_balance=#{trial_balance},difference=#{difference},cash_count_type=#{cash_count_type} where branch_audit_id=#{branch_audit_id}")
	public void editCashCount(CashCountEntity cashcount);

	@Update("update branch_financial_audit set status=0 where id=#{id}")
	public void deleteCashCount(Long id);

	@Update("update branch_financial_audit set auditor_status=1, passed_date = CURRENT_TIMESTAMP where id=#{id}")
	public void passCashCount(Long id);

	@Select("select * from branch_financial_audit where id in (select branch_audit_id from cash_count_branch) and auditor_id = #{auditor_id} and auditor_status = 1  and rectification_status !=1 and status = 1 Order By passed_date DESC")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "reviewer", column = "reviewer_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "cashcount", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.CashCountMapper.getCashCountById")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
			@Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")),
			@Result(property = "bmFileUrls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Rectification.Mapper.AuditRectificationMapper.getAttachedFiles")),

	})
	public List<BranchFinancialAudit> getPassedCashCount(Long auditor_id);

	@Update("update branch_financial_audit set auditor_status = 0, review_status = 0 where id=#{id}")
	public void backSelectedCashCount(Long id);

	@Update("update cash_count_branch set accountable_staff=#{accountable_staff},actual_cash_count=#{actual_cash_count},trial_balance=#{trial_balance},difference=#{difference},cash_count_type=#{cash_count_type}, fcy = #{fcy} where branch_audit_id=#{branch_audit_id}")
	public void editPassedCashCount(CashCountEntity cashcount);

	@Update("update branch_financial_audit set auditor_status = 0, review_status = 0 where id=#{id}")
	public void backPassedCashCount(Long id);

	@Select("insert into branch_financial_audit(case_number, category, auditor_id, finding_date, finding, finding_detail,audit_type, impact, recommendation, save_template, status, is_edited, auditor_status, drafted_date, branch_id, banking) OUTPUT inserted.id values(#{case_number},#{category}, #{auditor.id}, #{finding_date}, #{finding},#{finding_detail},'cash_count_branch', #{impact}, #{recommendation}, #{save_template}, 1, 1, 0, CURRENT_TIMESTAMP, #{auditor.branch.id}, #{banking})")
	public Long insertIntoBranchFinancialWithFindingDetail(BranchFinancialAudit audit);

	@Update("update branch_financial_audit set banking = #{banking}, finding=#{finding}, finding_detail=#{finding_detail}, impact=#{impact}, recommendation=#{recommendation}, finding_date = #{finding_date}, is_edited = 1 where id=#{id}")
	public void updateBranchFinancialWithFindingDetail(BranchFinancialAudit audit);

}
