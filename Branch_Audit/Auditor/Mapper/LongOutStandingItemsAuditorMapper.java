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
import com.afr.fms.Branch_Audit.Entity.LongOutstandingItemsEntity;

@Mapper
public interface LongOutStandingItemsAuditorMapper {

	@Select("SELECT TOP 1 case_number FROM branch_financial_audit ORDER BY id DESC;")
	public String getLatestCaseNumber();

	@Select("insert into branch_financial_audit(case_number, category, auditor_id, finding_date, finding, finding_detail, audit_type, impact, recommendation, save_template, status, is_edited, auditor_status, drafted_date, branch_id, ing) OUTPUT inserted.id values(#{case_number},#{category}, #{auditor.id}, #{finding_date}, #{finding},#{finding_detail},'long_outstanding_items_branch', #{impact}, #{recommendation}, #{save_template}, 1, 1, 0, CURRENT_TIMESTAMP, #{auditor.branch.id}, #{ing})")
	public Long insertIntoBranchFinancialWithFindingDetail(BranchFinancialAudit audit);

	@Select("insert into branch_financial_audit(case_number, category, auditor_id, finding_date, finding, audit_type, impact, recommendation, save_template, status, is_edited, auditor_status, drafted_date, branch_id, ing) OUTPUT inserted.id values(#{case_number},#{category}, #{auditor.id}, #{finding_date}, #{finding},'long_outstanding_items_branch', #{impact}, #{recommendation}, #{save_template}, 1, 1, 0, CURRENT_TIMESTAMP, #{auditor.branch.id}, #{ing})")
	public Long insertIntoBranchFinancial(BranchFinancialAudit audit);

	@Insert("insert into long_outstanding_item_branch(branch_audit_id,outstanding_item,less_than_90_amount,less_than_90_number,greater_than_90_amount,greater_than_90_number,greater_than_180_amount,greater_than_180_number,greater_than_365_amount,greater_than_365_number,trial_balance,total_amount,difference,justification,cash_type,fcy, selected_item_age) values(#{branch_audit_id},#{outstanding_item},#{less_than_90_amount},#{less_than_90_number},#{greater_than_90_amount},#{greater_than_90_number},#{greater_than_180_amount},#{greater_than_180_number},#{greater_than_365_amount},#{greater_than_365_number},#{trial_balance},#{total_amount},#{difference},#{justification},#{cash_type},#{fcy}, #{selected_item_age})")
	public void saveLongOutstandingItems(LongOutstandingItemsEntity longoutstanding);

	@Update("update branch_financial_audit set ing = #{ing}, finding=#{finding}, impact=#{impact}, recommendation=#{recommendation}, finding_date = #{finding_date}, is_edited = 1 where id=#{id}")
	public void updateBranchFinancial(BranchFinancialAudit audit);

	@Update("update branch_financial_audit set ing = #{ing}, finding=#{finding}, finding_detail=#{finding_detail}, impact=#{impact}, recommendation=#{recommendation}, finding_date = #{finding_date}, is_edited = 1 where id=#{id}")
	public void updateBranchFinancialWithFindingDetail(BranchFinancialAudit audit);

	@Update("update long_outstanding_item_branch set outstanding_item=#{outstanding_item},less_than_90_amount=#{less_than_90_amount},less_than_90_number=#{less_than_90_number},greater_than_90_amount=#{greater_than_90_amount},greater_than_90_number=#{greater_than_90_number},greater_than_180_amount=#{greater_than_180_amount},greater_than_180_number=#{greater_than_180_number},greater_than_365_amount=#{greater_than_365_amount},trial_balance=#{trial_balance},total_amount=#{total_amount},difference=#{difference},justification=#{justification},cash_type=#{cash_type},fcy=#{fcy}, selected_item_age = #{selected_item_age} where branch_audit_id=#{branch_audit_id}")
	public void updateLongOutstandingItems(LongOutstandingItemsEntity longoustanding);

	@Select("select * from branch_financial_audit where auditor_id = #{id} and id in (select branch_audit_id from long_outstanding_item_branch) and auditor_status = 0 and status = 1 order by drafted_date DESC")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),

			@Result(property = "reviewer", column = "reviewer_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "long_outstanding", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.LongOutStandingItemsAuditorMapper.getLongOutstandingItemsById")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
			@Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")),
	})
	public List<BranchFinancialAudit> getPendingOutstandingItems(Long id);

	@Select("select * from long_outstanding_item_branch where branch_audit_id= #{id}")
	public LongOutstandingItemsEntity getLongOutstandingItemsById(Long id);

	@Select("select * from branch_financial_audit where id in (select branch_audit_id from long_outstanding_item_branch) and auditor_id = #{auditor_id} and auditor_status = 1  and rectification_status !=1 and status = 1 Order By passed_date DESC")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "reviewer", column = "reviewer_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "long_outstanding", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.LongOutStandingItemsAuditorMapper.getLongOutstandingItemsById")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
			@Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")),
	})
	public List<BranchFinancialAudit> getPassedLongOutstandingItems(Long auditor_id);

}
