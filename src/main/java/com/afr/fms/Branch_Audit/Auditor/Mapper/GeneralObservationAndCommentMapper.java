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

@Mapper
public interface GeneralObservationAndCommentMapper {

        @Select("insert into branch_financial_audit(auditor_id, branch_id, category,observation,recommendation,impact,finding_date, case_number, drafted_date, audit_type, banking) OUTPUT inserted.id values(#{auditor.id}, #{auditor.branch.id}, #{auditor.category},#{finding},#{recommendation}, #{impact},#{finding_date}, #{case_number}, CURRENT_TIMESTAMP, 'GeneralObservation', #{banking})")
        Long createGeneralObservationAndComment(BranchFinancialAudit abnormalBalance);

        @Select("insert into branch_financial_audit(auditor_id, branch_id, category,observation,recommendation,impact,finding_date, case_number, drafted_date, finding_detail, audit_type, banking) OUTPUT inserted.id values(#{auditor.id}, #{auditor.branch.id}, #{auditor.category},#{finding},#{recommendation}, #{impact},#{finding_date}, #{case_number}, CURRENT_TIMESTAMP, #{finding_detail}, 'GeneralObservation', #{banking})")
        Long createGeneralObservationAndCommentWithFindingDetail(BranchFinancialAudit abnormalBalance);

        @Select("SELECT TOP 1 case_number FROM branch_financial_audit ORDER BY id DESC;")
        public String getLatestCaseNumber();

        @Select("select bfa.observation as finding, bfa.* from branch_financial_audit bfa where bfa.auditor_id = #{auditor_id} and bfa.category= 'BFA' and bfa.auditor_status = 0 and bfa.status = 1 and bfa.observation is not null order by drafted_date DESC")
        @Results(value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
                        @Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),

                        @Result(property = "reviewer", column = "reviewer_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
                        @Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.DormantAccountMapper.getFileUrlsByAuditID")),
                        @Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")),
        })
        public List<BranchFinancialAudit> getDraftedGeneralObservationAndComment(Long auditor_id);

        @Update("update branch_financial_audit set status=0 where id= #{id}")
        public void deleteSingleGeneralObservationAndComment(Long id);

        @Update("update branch_financial_audit set auditor_status=1,passed_date=CURRENT_TIMESTAMP where id= #{id}")
        public void passSingleGeneralObservation(Long id);

        @Update("update branch_financial_audit set banking = #{banking},  observation = #{finding}, recommendation = #{recommendation}, impact=#{impact}, finding_date = #{finding_date}, is_edited = 1 where id=#{id}")
        public void updateGeneralObservationAndComment(BranchFinancialAudit audit);

        @Select("select *  from branch_financial_audit where auditor_id = #{auditor_id} and category= 'BFA' and  auditor_status = 1 and status = 1 and rectification_status !=1 and observation is NOT NULL order by passed_date DESC ")
        @Results(value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "auditor", column = "auditor_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
                        @Result(property = "reviewer", column = "reviewer_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
                        @Result(property = "approver", column = "approver_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
                        @Result(property = "file_urls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.DormantAccountMapper.getFileUrlsByAuditID")),
                        @Result(property = "bmFileUrls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Rectification.Mapper.AuditRectificationMapper.getAttachedFiles")),
                        @Result(property = "change_tracker_branch_audit", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditMapper.getChanges")),
        })
        public List<BranchFinancialAudit> getPassedGeneralObservation(Long auditor_id);

        @Update("update branch_financial_audit set auditor_status=0 where id= #{id}")
        public void backPassedGeneralObservation(Long id);

        @Update("update branch_financial_audit set banking = #{banking}, observation = #{finding}, finding_detail=#{finding_detail}, recommendation = #{recommendation}, impact=#{impact}, finding_date = #{finding_date}, is_edited = 1 where id=#{id}")
        public void updateGeneralObservationAndCommentWithFindingDetail(BranchFinancialAudit audit);

}
