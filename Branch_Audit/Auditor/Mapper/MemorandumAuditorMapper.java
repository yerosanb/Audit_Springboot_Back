
package com.afr.fms.Branch_Audit.Auditor.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Entity.MemorandumContigent;

@Mapper
public interface MemorandumAuditorMapper {
	@Select("insert into branch_financial_audit(case_number, auditor_id, branch_id, finding_date, finding, impact, recommendation, save_template,category, status, is_edited, auditor_status, audit_type, drafted_date, ing) OUTPUT inserted.id values(#{case_number}, #{auditor.id}, #{auditor.branch.id}, #{finding_date}, #{finding}, #{impact}, #{recommendation}, #{save_template},#{category}, 1, 1, 0, #{audit_type}, CURRENT_TIMESTAMP, #{ing} )")
	public Long createBAFinding(BranchFinancialAudit audit);

	@Select("insert into branch_financial_audit(case_number, auditor_id, branch_id, finding_date, finding, impact, recommendation, save_template,category, status, is_edited, auditor_status , finding_detail , audit_type, drafted_date, ing) OUTPUT inserted.id values(#{case_number}, #{auditor.id}, #{auditor.branch.id}, #{finding_date}, #{finding}, #{impact}, #{recommendation}, #{save_template},#{category}, 1, 1, 0 , #{finding_detail}, #{audit_type}, CURRENT_TIMESTAMP, #{ing} )")
	public Long createBFAFindingWithFindingDetail(BranchFinancialAudit audit);

	@Select("insert into memorandom_contingent_branch(branch_audit_id, memorandom_amount, difference, contingent_amount,cash_type, fcy) OUTPUT inserted.id values(#{branch_audit_id}, #{memorandom_amount}, #{difference}, #{contingent_amount},#{cash_type}, #{fcy})")
	public Long createMemorandomContingent(MemorandumContigent audit);

	@Select("insert into  uploaded_file_branch(branch_audit_id, file_url, uploaded_date) OUTPUT inserted.id values(#{branch_audit_id}, #{file_url}, CURRENT_TIMESTAMP )")
	public Long InsertFileUrl(String file_url, Long branch_audit_id);

	@Delete("delete from uploaded_file_branch where branch_audit_id = #{branch_audit_id}")
	public void removeFileUrls(Long branch_audit_id);

	@Select("select file_url from uploaded_file_branch where branch_audit_id = #{branch_audit_id}")
	public List<String> getFileUrlsByAuditID(Long branch_audit_id);


	@Select("select * from branch_financial_audit where id in (select branch_audit_id from memorandom_contingent_branch) and auditor_id = #{auditor_id} and auditor_status = 1 and status = 1 and review_status !=1 Order By passed_date DESC")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "reviewer", column = "reviewer_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "memorandumContigent", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.MemorandumAuditorMapper.getMemorandumContigent")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.MemorandumAuditorMapper.getFileUrlsByAuditID")),
			@Result(property = "bmFileUrls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Rectification.Mapper.AuditRectificationMapper.getAttachedFiles")),

			@Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")),
	})
	public List<BranchFinancialAudit> getPassedAuditsForAuditor(Long auditor_id);

	@Select("select * from memorandom_contingent_branch where branch_audit_id = #{id}")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
	})
	public MemorandumContigent getMemorandumContigent(Long id);

	@Select("select * from branch_financial_audit where id in (select branch_audit_id from memorandom_contingent_branch) and auditor_id = #{auditor_id} and auditor_status != 1 and status = 1 Order By drafted_date DESC")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),

			@Result(property = "reviewer", column = "reviewer_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
			@Result(property = "memorandumContigent", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.MemorandumAuditorMapper.getMemorandumContigent")),
			@Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.MemorandumAuditorMapper.getFileUrlsByAuditID")),
			@Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")),
	})
	public List<BranchFinancialAudit> getAuditsOnDrafting(Long auditor_id);

	@Select("SELECT TOP 1 case_number FROM branch_financial_audit ORDER BY id DESC;")
	public String getLatestCaseNumber();

	@Update("update branch_financial_audit set ing = #{ing}, finding=#{finding}, impact=#{impact}, recommendation=#{recommendation}, finding_date = #{finding_date}, is_edited = 1 where id=#{id}")
	public void updateBAFinding(BranchFinancialAudit audit);
	
	@Update("update branch_financial_audit set fing = #{ing}, inding=#{finding}, impact=#{impact}, recommendation=#{recommendation}, finding_date = #{finding_date}, is_edited = 1 , finding_detail = #{finding_detail} where id=#{id}")
	public void updateBAFindingWithFindingDetail(BranchFinancialAudit audit);
	
	@Update("update memorandom_contingent_branch set memorandom_amount=#{memorandom_amount}, difference = #{difference}, contingent_amount=#{contingent_amount} , cash_type = #{cash_type}, fcy = #{fcy} where branch_audit_id=#{branch_audit_id}")
	public void updateIAFinding(MemorandumContigent audit);

}
