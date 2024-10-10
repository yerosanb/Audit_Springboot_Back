package com.afr.fms.Branch_Audit.Reviewer.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;

@Mapper
public interface ATMCardReviewerMapper {

        @Select("<script>"
                        + "select bfa.* from branch_financial_audit bfa "
                        + "<choose>"
                        + "<when test='auditType == \"conventional\"'>"
                        + " inner join branch br on bfa.branch_id = br.id and br.region_id = #{user.region.id} "
                        + "</when>"
                        + "</choose>"
                        + " inner join ATM_card_branch nib on nib.branch_audit_id = bfa.id"
                        + " where  bfa.auditor_status = 1 and bfa.status = 1 and bfa.ing = #{auditType} and bfa.rectification_status != 1 and bfa.review_status != 1 and bfa.regional_compiler_status != 1 ORDER BY bfa.passed_date DESC"
                        + "</script>")

        // @Select("select bfa.* from branch_financial_audit bfa "
        // + " inner join branch br on bfa.branch_id = br.id and br.region_id =
        // #{region_id} "
        // + " inner join ATM_card_branch nib on nib.branch_audit_id = bfa.id"
        // + " where bfa.auditor_status = 1 and bfa.status = 1 and
        // bfa.rectification_status != 1 and bfa.review_status != 1 and
        // bfa.regional_compiler_status != 1 Order By passed_date DESC")
        @Results(value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),

                        @Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
                        @Result(property = "atmCardBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.AtmCardAuditorMapper.getAtmCardBranch")),
                        @Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
                        @Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")),
        })
        public List<BranchFinancialAudit> getPendingAuditsForReviewer(@Param("auditType") String auditType, User user);

        @Select("select * from branch_financial_audit where review_status = 1 and reviewer_id=#{reviewer_id} and id in (select branch_audit_id from ATM_card_branch) and status = 1 and regional_compiler_status != 1 and rectification_status != 1  Order By reviewed_date DESC")
        @Results(value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),

                        @Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
                        @Result(property = "atmCardBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.AtmCardAuditorMapper.getAtmCardBranch")),
                        @Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getFileUrlsByAuditID")),
                        @Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")),
        })
        public List<BranchFinancialAudit> getReviewedBranchFindings(Long reviewer_id);
}
