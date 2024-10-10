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

import com.afr.fms.Branch_Audit.Entity.AtmCardBranch;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;

@Mapper

public interface AtmCardAuditorMapper {

        @Select("insert into branch_financial_audit(case_number, finding, audit_type, impact, recommendation,finding_date,auditor_id,category, status, drafted_date, is_edited, branch_id, ing) OUTPUT inserted.id values(#{case_number},#{finding},'ATMCard', #{impact}, #{recommendation}, #{finding_date}, #{auditor.id}, #{category}, 1, CURRENT_TIMESTAMP, 1, #{auditor.branch.id}, #{ing})")
        public Long createBranchFinancialAudit(BranchFinancialAudit audit);

        @Insert("insert into ATM_card_branch (branch_audit_id,issued_card, distributed_card, returned_card,remaining_card) values(#{branch_audit_id}, #{issued_card},#{distributed_card},#{returned_card},#{remaining_card})")
        public void createATMFinding(AtmCardBranch financialBranchEntity);

        @Select("Select * from ATM_card_branch where branch_audit_id = #{branch_audit_id}")
        @Results(value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
        })
        public AtmCardBranch getAtmCardBranch(Long branch_audit_id);

        @Update("update ATM_card_branch set issued_card=#{atmCardBranch.issued_card}, distributed_card=#{atmCardBranch.distributed_card}, returned_card=#{atmCardBranch.returned_card},remaining_card=#{atmCardBranch.remaining_card} where branch_audit_id= #{id}")
        public void updateATMFinding(AtmCardBranch atmCardBranch, long id);

        @Update("update branch_financial_audit set ing=#{ing},  finding=#{finding}, impact=#{impact}, recommendation=#{recommendation}, finding_date = #{finding_date}, is_edited = 1 where id=#{id}")
        public void updateBranchFinantialAudit(BranchFinancialAudit audit);

        @Select("select * from branch_financial_audit where id in (select branch_audit_id from ATM_card_branch) and auditor_id = #{auditor_id} and auditor_status = 0 and status = 1 Order By drafted_date DESC")
        @Results(value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),

                        @Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
                        @Result(property = "atmCardBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.AtmCardAuditorMapper.getAtmCardBranch")),
                        @Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
                        @Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")),

        })
        public List<BranchFinancialAudit> getAuditsOnDraftingBranch(Long auditor_id);

        @Select("SELECT TOP 1 case_number FROM branch_financial_audit ORDER BY id DESC;")
        public String getLatestCaseNumber();

        @Update("update branch_financial_audit set status=0 where id=#{id}")
        public void deleteAtmFinding(Long id);

        @Update("update branch_financial_audit set auditor_status=1, finding_status = 'PassedtoRegionalSupervisor', passed_date=CURRENT_TIMESTAMP where id=#{id}")
        public void passBranchFinding(Long id);

        @Update("update branch_financial_audit set auditor_status=0 where id=#{id}")
        public void backBranchFinding(Long id);

        @Update("update branch_financial_audit set auditor_status=0 where id=#{id}")
        public void BackCashBranchFinding(Long id);

        @Select("select * from branch_financial_audit where id in (select branch_audit_id from ATM_card_branch) and auditor_id = #{auditor_id} and auditor_status = 1   and rectification_status !=1 and status = 1 Order By passed_date DESC")
        @Results(value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
                        @Result(property = "atmCardBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.AtmCardAuditorMapper.getAtmCardBranch")),
                        @Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
                        @Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")),
                        @Result(property = "bmFileUrls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Rectification.Mapper.AuditRectificationMapper.getAttachedFiles")),

        })
        public List<BranchFinancialAudit> getPassedAuditsForAuditor(Long auditor_id);

        @Select("insert into branch_financial_audit(case_number, finding, finding_detail, audit_type, impact, recommendation,finding_date,auditor_id,category, status, is_edited, branch_id, ing) OUTPUT inserted.id values(#{case_number},#{finding},#{finding_detail},'ATMCard', #{impact}, #{recommendation}, #{finding_date}, #{auditor.id}, #{category}, 1, 1, #{auditor.branch.id}, #{ing})")
        public Long createBranchFinancialAuditWithFindingDetail(BranchFinancialAudit audit);

        @Update("update branch_financial_audit set ing=#{ing}, finding=#{finding}, finding_detail=#{finding_detail}, impact=#{impact}, recommendation=#{recommendation}, finding_date = #{finding_date}, is_edited = 1 where id=#{id}")
        public void updateBranchFinantialAuditwithfindingDetail(BranchFinancialAudit audit);

}
