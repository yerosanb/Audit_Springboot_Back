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
import com.afr.fms.Branch_Audit.Entity.CashPerformanceBranch;

@Mapper
public interface CashPerformanceAuditorMapper {

        @Select("insert into branch_financial_audit(case_number,finding, impact, recommendation,finding_date ,auditor_id, branch_id, category,audit_type,save_template, status, is_edited, auditor_status,drafted_date, ing) OUTPUT inserted.id values(#{case_number},#{finding}, #{impact}, #{recommendation}, #{finding_date}, #{auditor.id}, #{auditor.branch.id}, #{category}, 'cash_performance_branch', #{save_template}, 1, 1, 0,CURRENT_TIMESTAMP, #{ing})")
        public Long createCashFinding(BranchFinancialAudit audit);

        @Insert("insert into cash_performance_branch (branch_audit_id,accountable_staff,amount_shortage, amount_excess,action_taken, cash_type, fcy) values(#{branch_audit_id}, #{accountable_staff}, #{amount_shortage}, #{amount_excess}, #{action_taken}, #{cash_type}, #{fcy})")
        public void createBranchCashAudit(CashPerformanceBranch cashPerformanceBranch);

        @Update("update branch_financial_audit set ing = #{ing}, finding=#{finding}, impact=#{impact}, recommendation=#{recommendation}, finding_date = #{finding_date}, is_edited = 1 where id=#{id}")
        public void updateBranchFinantialAudit(BranchFinancialAudit audit);

        @Update("update cash_performance_branch set accountable_staff=#{cashPerformanceBranch.accountable_staff}, amount_shortage=#{cashPerformanceBranch.amount_shortage}, amount_excess=#{cashPerformanceBranch.amount_excess},action_taken=#{cashPerformanceBranch.action_taken}, cash_type= #{cashPerformanceBranch.cash_type}, fcy = #{cashPerformanceBranch.fcy} where branch_audit_id= #{id}")
        public void updateCashFinding(CashPerformanceBranch cashPerformanceBranch, long id);

        @Select("select * from branch_financial_audit where id in (select branch_audit_id from cash_performance_branch) and auditor_id = #{auditor_id} and auditor_status = 0 and status = 1 Order By drafted_date DESC")
        @Results(value = {
                @Result(property = "id", column = "id"),
                @Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
                @Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),

                @Result(property = "reviewer", column = "reviewer_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
                @Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
                @Result(property = "cashPerformanceBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.CashPerformanceAuditorMapper.getCashPerformanceBranch")),
                @Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
                @Result(property = "bmFileUrls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Rectification.Mapper.AuditRectificationMapper.getAttachedFiles")),
                @Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")),

        })
        public List<BranchFinancialAudit> getDraftingCashPerformanceFindings(Long auditor_id);

        @Select("SELECT TOP 1 case_number FROM branch_financial_audit ORDER BY id DESC;")
        public String getLatestCaseNumber();

        @Select("Select * from cash_performance_branch where branch_audit_id = #{branch_audit_id}")

        public CashPerformanceBranch getCashPerformanceBranch(Long branch_audit_id);

        @Select("select * from branch_financial_audit where id in (select branch_audit_id from cash_performance_branch) and auditor_id = #{auditor_id} and auditor_status = 1 and status = 1 and rectification_status !=1 Order By passed_date DESC")
        @Results(value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "id", column = "id"),
                        @Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
                        @Result(property = "reviewer", column = "reviewer_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
                        @Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
                        @Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
                        @Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")),
                        @Result(property = "cashPerformanceBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.CashPerformanceAuditorMapper.getCashPerformanceBranch")),
                        @Result(property = "bmFileUrls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Rectification.Mapper.AuditRectificationMapper.getAttachedFiles")),

        })
        public List<BranchFinancialAudit> getPassedCashPerfomanceFindings(Long auditor_id);

        @Select("insert into branch_financial_audit(case_number, category, auditor_id, finding_date, finding, finding_detail, audit_type, impact, recommendation, save_template, status, is_edited, auditor_status, drafted_date, branch_id, ing) OUTPUT inserted.id values(#{case_number},#{category}, #{auditor.id}, #{finding_date}, #{finding},#{finding_detail},'cash_performance_branch', #{impact}, #{recommendation}, #{save_template}, 1, 1, 0, CURRENT_TIMESTAMP, #{auditor.branch.id}, #{ing})")
        public Long createCashWithFindingDetail(BranchFinancialAudit audit);

        @Update("update branch_financial_audit set ing = #{ing},  finding=#{finding},finding_detail=#{finding_detail}, impact=#{impact}, recommendation=#{recommendation}, finding_date = #{finding_date}, is_edited = 1 where id=#{id}")
        public void updateBranchFinantialAuditWithfindingDetail(BranchFinancialAudit audit);

}
