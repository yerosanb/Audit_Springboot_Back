package com.afr.fms.Branch_Audit.Common.Notification;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Branch_Audit.Common.RectificationTracker.RectificationTracker;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Entity.CompiledBranchAudit;
import com.afr.fms.Branch_Audit.Entity.CompiledRegionalAudit;

@Mapper
public interface BranchNotificationMapper {
        @Insert("INSERT INTO rectification_tracker(audit_id, rectification_date, sender, reciever, status) VALUES (#{audit_id}, CURRENT_TIMESTAMP, #{sender}, #{reciever}, 0)")
        public void addRectificationTracker(RectificationTracker rectificationTracker);

        @Update("update rectification_tracker set status = #{status} where audit_id=#{audit_id} and reciever = #{reciever}")
        public void seenRectificationTracker(RectificationTracker rectificationTracker);

        @Update("update branch_financial_audit set seen = #{rectification_status} where id=#{id}")
        public void seenBranchFinancial(BranchFinancialAudit audit);

        @Select("<script>" +
                        "Select DATEDIFF(DAY, bfa.passed_date, GETDATE()) AS outstanding_date, bfa.audit_type, bfa.passed_date, bfa.case_number, auditor_id, branch_id "
                        +
                        " from branch_financial_audit  bfa " +
                        " <choose> " +
                        " <when test='userType == \"BranchManager\"'>" +
                        " where bfa.branch_id = #{user.branch.id} and bfa.auditor_status = 1 and bfa.rectification_status != 1 and bfa.status = 1 order by bfa.passed_date DESC "
                        +
                        "</when>" +
                        "<when test='userType == \"Reviewer\"'>" +
                        " inner join branch br on br.id = bfa.branch_id and br.region_id = #{user.region.id}" +
                        " where bfa.auditor_status = 1 and bfa.reviewer_id is NULL and bfa.rectification_status != 1 and bfa.status = 1 order by bfa.passed_date DESC  "
                        +
                        "</when>" +
                        "</choose>" +
                        "</script>")
        @Results(value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
                        @Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
        })
        public List<BranchFinancialAudit> getPendingAuditsNotification(User user,
                        @Param("userType") String userType);

        @Select(
                // " <script>" +
                        // " <choose> " +

                        " SELECT COUNT(rba.id) AS unseen_remark, bfa.id as id, bfa.audit_type as audit_type, bfa.case_number as case_number "
                        +
                        " FROM branch_financial_audit bfa " +
                        " INNER JOIN remark_branch_audit rba ON rba.audit_id = bfa.id AND rba.status = 0 AND rba.reciever = #{user.id} "
                        +
                        " GROUP BY bfa.audit_type, bfa.case_number, bfa.id " +

                        " UNION " +

                        " select COUNT(rba.id) AS unseen_remark, cba.id as id, cba.audit_type as audit_type, cba.case_number as case_number from compiled_branch_audit cba "
                        +
                        " inner join remark_branch_audit rba on rba.compiled_branch_audit_id = cba.id and rba.status = 0 and rba.reciever = #{user.id} "
                        +
                        " GROUP BY cba.audit_type, cba.case_number, cba.id " +

                        " UNION " +

                        "select COUNT(cra.id) AS unseen_remark, cra.id as id, cra.audit_type as audit_type, cra.case_number as case_number from compiled_regional_audit cra"
                        +
                        " inner join remark_branch_audit rba on rba.compiled_regional_audit_id = cra.id and rba.status = 0 and rba.reciever = #{user.id} "
                        +
                        " GROUP BY cra.audit_type, cra.case_number, cra.id " 
                        // "</choose>" +
                        // "</script>"
                        )
        @Results(value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
        })
        public List<BranchFinancialAudit> getBranchRemarkAuditsNotification(User user,
                        @Param("userType") String userType);

        @Select("select DATEDIFF(DAY, cba.compiler_submitted_date, GETDATE()) AS outstanding_date, cba.* from compiled_branch_audit cba "
                        +
                        "where cba.review_status = 0 and cba.division_compiler_id is NULL  order by cba.compiler_submitted_date ")
        @Results(value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "reviewer", column = "reviewer_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
        })
        public List<CompiledBranchAudit> getPendingAuditsNotificationCompiler(User user);

        @Select("select  DATEDIFF(DAY, cra.division_compiler_submitted_date, GETDATE()) AS outstanding_date, cra.* from compiled_regional_audit cra "
                        + " where cra.approver_id is null and cra.approve_status = 0 and cra.drafting_status = 0 ")
        @Results(value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "compiler", column = "compiler_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
        })
        public List<CompiledRegionalAudit> getPendingAuditsNotificationApprover(User user);

        @Select("Select * from rectification_tracker where audit_id=#{audit.id} and status = 0 ORDER BY rectification_date")
        @Results(value = {
                        @Result(property = "audit", column = "audit_id", one = @One(select = "com.afr.fms.Auditor.Mapper.AuditISMMapper.getAudit")),
        })
        public List<RectificationTracker> getSeenRectificationTrackers(RectificationTracker rectificationTracker);

        @Select("Select * from branch_financial_audit where id=#{audit_id}")
        @Results(value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "reviewer_id", column = "reviewer_id"),
                        @Result(property = "approver_id", column = "approver_id"),
                        @Result(property = "division_compiler_id", column = "division_compiler_id"),

        })
        public BranchFinancialAudit getBranchFinancialInfo(Long audit_id);

}
