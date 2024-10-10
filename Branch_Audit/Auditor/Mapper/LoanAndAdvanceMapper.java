package com.afr.fms.Branch_Audit.Auditor.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Entity.LoanAndAdvance;
import com.afr.fms.Branch_Audit.Entity.OverDraft;

@Mapper
public interface LoanAndAdvanceMapper {

        @Update("update branch_financial_audit set auditor_status=1, passed_date = CURRENT_TIMESTAMP where id=#{id}")
        public void passBFA(Long id);

        @Update("update branch_financial_audit set status=0 where id=#{id}")
        public void deleteBFA(Long id);

        @Select("insert into branch_financial_audit(case_number,category, auditor_id, finding, impact, recommendation, status, is_edited, auditor_status, drafted_date, branch_id, audit_type, ing) OUTPUT inserted.id values(#{case_number},#{auditor.category}, #{auditor.id}, #{finding}, #{impact}, #{recommendation}, 1, 1, 0, CURRENT_TIMESTAMP, #{auditor.branch.id}, #{audit_type}, #{ing})")
        public Long createBAFinding(BranchFinancialAudit audit);

        @Select("insert into branch_financial_audit(case_number,category, auditor_id, finding, impact, recommendation, status, is_edited, auditor_status, drafted_date, branch_id, finding_detail, audit_type, ing) OUTPUT inserted.id values(#{case_number},#{auditor.category}, #{auditor.id}, #{finding}, #{impact}, #{recommendation}, 1, 1, 0, CURRENT_TIMESTAMP, #{auditor.branch.id}, #{finding_detail}, #{audit_type}, #{ing})")
        public Long createBranchFinantialWithFindingDetailAFinding(BranchFinancialAudit audit);
      
        @Select("insert into over_draft(approved_amount, overdrawn_balance, difference, penality_charge_collection, expiry_date) OUTPUT inserted.id values(#{approved_amount}, #{overdrawn_balance}, #{difference}, #{penalty_charge}, #{expiry_date})")
        public Long createOverDraft(OverDraft audit);

        @Select("insert into loan_advance_branch(branch_audit_id,disbursed_date ,account_number, borrower_name, loan_type, amount_granted, interest_rate, due_date, arrears, loan_status, over_draft_id, cash_type, fcy, category) OUTPUT inserted.id values(#{branch_audit_id}, #{loan_disbursed_date}, #{account_number}, #{borrower_name}, #{loan_type}, #{amount_granted}, #{interest_rate}, #{due_date}, #{arrears},#{loan_status},#{over_draft_id}, #{cash_type} , #{fcy}, #{category})")
        public Long createLoanAndAdvance(LoanAndAdvance audit);

        @Select("insert into uploaded_file_branch(branch_audit_id, file_url, uploaded_date) OUTPUT inserted.id values(#{branch_audit_id}, #{file_url}, CURRENT_TIMESTAMP)")
        public Long InsertFileUrl(String file_url, Long branch_audit_id);

        @Select("select * from over_draft where id = #{id}")

        @Results(value = {
                        @Result(property = "penalty_charge", column = "penality_charge_collection"),
        })
        public OverDraft getOverDraftById(Long id);

        @Update("update branch_financial_audit set auditor_status = 0, review_status = 0 where id=#{id}")
        public void backBAFinding(Long id);

        @Select("select file_url from uploaded_file_branch where branch_audit_id = #{branch_audit_id}")
        public List<String> getFileUrlsByAuditID(Long branch_audit_id);

        @Select("select * from loan_advance_branch where branch_audit_id = #{id}")
        @Results(value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
        })
        public LoanAndAdvance getLoanAndAdvanceBranch(Long id);

        @Select("select * from branch_financial_audit where id in (select branch_audit_id from loan_advance_branch) and auditor_id = #{auditor_id} and auditor_status = 1 and rectification_status !=1  and status = 1 Order By passed_date DESC")
        @Results(value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "penality_charge_collection", column = "penalty_charge"),
                        @Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
                        @Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),

                        @Result(property = "reviewer", column = "reviewer_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
                        @Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
                        @Result(property = "loanAndAdvance", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.LoanAndAdvanceMapper.getLoanAndAdvanceByBFAId")),
                        @Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.LoanAndAdvanceMapper.getFileUrlsByAuditID")),
                        @Result(property = "bmFileUrls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Rectification.Mapper.AuditRectificationMapper.getAttachedFiles")),

                        @Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")),
        })
        public List<BranchFinancialAudit> getAuditsForAuditor(Long auditor_id);

        @Select("SELECT * from loan_advance_branch where branch_audit_id =  #{branch_audit_id};")
        @Results(value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "loan_disbursed_date", column = "disbursed_date"),
                        @Result(property = "overDraft", column = "over_draft_id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.LoanAndAdvanceMapper.getOverDraftById")),
        })
        public LoanAndAdvance getLoanAndAdvanceByBFAId(Long branch_audit_id);

        @Select("SELECT TOP 1 case_number FROM branch_financial_audit ORDER BY id DESC;")
        public String getLatestCaseNumber();

        @Select("select * from branch_financial_audit where id in (select branch_audit_id from loan_advance_branch) and auditor_id = #{auditor_id} and auditor_status != 1 and status = 1 Order By drafted_date DESC")
        @Results(value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "penality_charge_collection", column = "penalty_charge"),
                        @Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),


                        @Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
                        @Result(property = "reviewer", column = "reviewer_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
                        @Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
                        @Result(property = "loanAndAdvance", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.LoanAndAdvanceMapper.getLoanAndAdvanceByBFAId")),
                        @Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
                        @Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")),
        })
        public List<BranchFinancialAudit> getLoanAndAdvanceOnDrafting(Long auditor_id);

        @Select("select *  from branch_financial_audit where id in (branch_audit_id from loan_advance_branch) and auditor_id=#{auditor_id} and auditor_status = 1 and status = 1 and review_status = 1  ")

        @Results(value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "penality_charge_collection", column = "penalty_charge"),

                        @Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
                        @Result(property = "reviewer", column = "reviewer_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
                        @Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
                        @Result(property = "loanAndAdvance", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.LoanAndAdvanceMapper.getLoanAndAdvanceByBFAId")),
                        @Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.LoanAndAdvanceMapper.getFileUrlsByAuditID")),
                        @Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")),
        })

        public List<BranchFinancialAudit> getOnProgressAudits(Long auditor_id);


        @Update("update loan_advance_branch set  disbursed_date = #{loan_disbursed_date}, account_number = #{account_number}, borrower_name=#{borrower_name}, loan_type=#{loan_type}, amount_granted = #{amount_granted}, interest_rate = #{interest_rate}, due_date = #{due_date},arrears =#{arrears}, loan_status=#{loan_status} , cash_type = #{cash_type}, fcy = #{fcy} , category = #{category} where branch_audit_id=#{branch_audit_id}")
        public void updateLoanAndAdvance(LoanAndAdvance audit);
        @Update("update branch_financial_audit set ing = #{ing}, finding=#{finding}, impact=#{impact}, recommendation=#{recommendation}, is_edited = 1 where id=#{id}")
        public void updateBFA(BranchFinancialAudit audit);
        
        @Update("update branch_financial_audit set ing = #{ing}, finding=#{finding}, impact=#{impact}, recommendation=#{recommendation}, is_edited = 1, finding_detail = #{finding_detail} where id=#{id}")
        public void updateBFAWithFindingDetail(BranchFinancialAudit audit);


        @Update ("update  over_draft set approved_amount = #{approved_amount} , overdrawn_balance = #{overdrawn_balance}, difference = #{difference} , penality_charge_collection = #{penalty_charge}, expiry_date = #{expiry_date} where id = #{id}")
        public void updateOverDraft(OverDraft audit);



}
