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
import com.afr.fms.Branch_Audit.Entity.NegotiableInstrumentBranch;

@Mapper
public interface NegotiableInstrumentBranchMapper {

	@Select("insert into branch_financial_audit( auditor_id  , branch_id  ,finding_date ,finding ,impact ,recommendation ,category,case_number , save_template ,status,is_edited ,auditor_status, audit_type, drafted_date, ing)  OUTPUT inserted.id values(#{auditor.id}, #{auditor.branch.id}, #{finding_date}, #{finding}, #{impact}, #{recommendation}, #{category}, #{case_number}, #{save_template} , 1, 1, 0, #{audit_type}, CURRENT_TIMESTAMP, #{ing})")
	public Long createBranchFinantialAudit(BranchFinancialAudit branchFinancialAudit);

	@Select("insert into branch_financial_audit( auditor_id  , branch_id  ,finding_date ,finding ,impact ,recommendation ,category,case_number , save_template ,status,is_edited ,auditor_status, finding_detail, audit_type, drafted_date, ing)  OUTPUT inserted.id values(#{auditor.id}, #{auditor.branch.id}, #{finding_date}, #{finding}, #{impact}, #{recommendation}, #{category}, #{case_number}, #{save_template} , 1, 1, 0, #{finding_detail}, #{audit_type}, CURRENT_TIMESTAMP, #{ing})")
	public Long createBranchFinantialAuditWithFindingDetail(BranchFinancialAudit branchFinancialAudit);

	@Insert("insert  into negotiable_instrument_branch(branch_audit_id, negotiable_stock_item_id, account_holder, account_number, printed_date, amount, difference, category,ck_type,ck_range,quantity,unit_price, trial_balance,action_taken, cash_type, fcy) values(#{branch_audit_id}, #{negotiable_stock_item_id}, #{account_holder}, #{account_number}, #{printed_date}, #{amount},#{difference},#{category},#{ck_type},#{ck_range},#{quantity},#{unit_price}, #{trial_balance},#{action_taken},#{cash_type},#{fcy})")
	public void createNegotiableInstrumentBranch(NegotiableInstrumentBranch negotiableInstrumentBranch);

	@Update(" update  branch_financial_audit set ing = #{ing}, finding_date = #{finding_date}, finding= #{finding} ,impact= #{impact} , recommendation=#{recommendation}, save_template = #{save_template} where id=#{id}")
	public void updateBranchFinantialAudit(BranchFinancialAudit branchFinancialAudit);

	@Update(" update  branch_financial_audit set ing = #{ing}, finding_date = #{finding_date}, finding= #{finding} ,impact= #{impact} , recommendation=#{recommendation}, save_template = #{save_template}, finding_detail = #{finding_detail} where id=#{id}")
	public void updateBranchFinantialAuditWithFindingDetail(BranchFinancialAudit branchFinancialAudit);

	@Update("update  negotiable_instrument_branch set   account_holder= #{account_holder}, account_number= #{account_number}, printed_date= #{printed_date}, difference= #{difference}, category= #{category}, ck_type= #{ck_type}, ck_range= #{ck_range},quantity= #{quantity}, unit_price= #{unit_price}, trial_balance=#{trial_balance}, action_taken=#{action_taken},  amount=#{amount}, cash_type = #{cash_type}, fcy = #{fcy} where branch_audit_id=#{branch_audit_id} ")
	public void updatenegotiableInstrumentBranch(NegotiableInstrumentBranch negotiableInstrumentBranch);

	@Select("select *  from branch_financial_audit where id in (select branch_audit_id from negotiable_instrument_branch)  and auditor_id=#{auditor_id} and auditor_status = 0 and status = 1 and review_status != 1 and approve_status != 1 ORDER BY drafted_date DESC")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),

			@Result(property = "reviewer", column = "reviewer_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "negotiableInstrument", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.NegotiableInstrumentBranchMapper.getNegotiableInstrumentByBranchId")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
			@Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")), })

	public List<BranchFinancialAudit> getDraftedAudits(Long auditor_id);

	@Select("select *  from branch_financial_audit where id in (select branch_audit_id from negotiable_instrument_branch) and auditor_id = #{auditor_id}  and  auditor_status = 1 and status = 1  and rectification_status !=1  ORDER BY passed_date DESC")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "reviewer", column = "reviewer_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "negotiableInstrument", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.NegotiableInstrumentBranchMapper.getNegotiableInstrumentByBranchId")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
			@Result(property = "bmFileUrls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Rectification.Mapper.AuditRectificationMapper.getAttachedFiles")),

			@Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")), })

	public List<BranchFinancialAudit> getPassedAudits(Long auditor_id);

	@Select("select *  from branch_financial_audit where id in (select branch_audit_id from negotiable_instrument_branch) and auditor_id = #{auditor_id} and category= 'BFA' and  auditor_status = 1 and status = 1 and review_status = 1  ")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "reviewer", column = "reviewer_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "negotiableInstrument", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.NegotiableInstrumentBranchMapper.getNegotiableInstrumentByBranchId")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
			@Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")), })

	public List<BranchFinancialAudit> getOnProgressAudits(Long auditor_id);

	@Select("select  * from  negotiable_instrument_branch where branch_audit_id=#{branch_audit_id}")
	@Result(property = "negotiableStockItem", column = "negotiable_stock_item_id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.NegotiableStockItemMapper.getNegotiableStockItemById"))

	public NegotiableInstrumentBranch getNegotiableInstrumentByBranchId(Long branch_audit_id);

	@Update("update branch_financial_audit set auditor_status=1, passed_date = CURRENT_TIMESTAMP where id= #{id}")
	public void passNegotiableInstrument(Long id);

	@Update("update branch_financial_audit set auditor_status=0 where id= #{id}")
	public void backPassedNegotiableInstrument(Long id);

	@Select("SELECT TOP 1 case_number FROM branch_financial_audit ORDER BY id DESC;")
	public String getLatestCaseNumber();

	@Update("update branch_financial_audit set status=0 where id= #{id}")
	public void deleteNegotiableInstrument(Long id);

}