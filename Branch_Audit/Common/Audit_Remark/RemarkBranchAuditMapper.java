package com.afr.fms.Branch_Audit.Common.Audit_Remark;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;

@Mapper
public interface RemarkBranchAuditMapper {
        @Insert("INSERT INTO remark_branch_audit(audit_id, sender, reciever, message, remark_date,status, rejected) VALUES (#{branchFinancialAudit.id}, #{sender.id}, #{reciever.id}, #{message}, CURRENT_TIMESTAMP,0,0)")
        public void addRemark(RemarkBranchAudit remark);

        @Insert("INSERT INTO remark_branch_audit(audit_id, sender, reciever, message, remark_date,status, rejected) VALUES (#{branchFinancialAudit.id}, #{sender.id}, #{reciever.id}, #{message}, CURRENT_TIMESTAMP,0,1)")
        public void addRejectedRemark(RemarkBranchAudit remark);

        @Update("update remark_branch_audit set status=1 where audit_id=#{branchFinancialAudit.id} and reciever=#{reciever.id} and sender = #{sender.id}")
        public void seenRemark(RemarkBranchAudit remark);

        @Select("select * from branch_financial_audit where id = #{id}")
        public BranchFinancialAudit get_branch_financial_audit(Long id);

        @Update("update remark set reciever_id=#{reciever.id} where load_id=#{loan.id} and sender_id=#{sender.id}")
        public void setReciever(RemarkBranchAudit remark);

        @Select("Select * from remark_branch_audit where audit_id = #{branchFinancialAudit.id} and (sender = #{sender.id} or sender = #{reciever.id}) and (reciever = #{sender.id} or reciever = #{reciever.id}) ORDER BY remark_date")
        @Results(value = {
                        @Result(property = "branchFinancialAudit", column = "audit_id", one = @One(select = "com.afr.fms.Branch_Audit.Common.Audit_Remark.RemarkBranchAuditMapper.get_branch_financial_audit")),
                        @Result(property = "sender", column = "sender", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getUserById")),
                        @Result(property = "reciever", column = "reciever", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getUserById"))
        })
        public List<RemarkBranchAudit> getRemarks(RemarkBranchAudit remark);  

        @Select("Select * from remark_branch_audit where status=0 AND reciever=#{reciever.id} ORDER BY remark_date DESC")
        @Results(value = {
                        // @Result(property = "branchFinancialAudit", column = "audit_id", one = @One(select = "com.afr.fms.Branch_Audit.Common.Audit_Remark.RemarkBranchAuditMapper.get_branch_financial_audit")),
                        @Result(property = "sender", column = "sender", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getUserById")),
                        @Result(property = "reciever", column = "reciever", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getUserById"))
        })
        public List<RemarkBranchAudit> getAllUnseenRemarks(RemarkBranchAudit remark);

        @Select("Select * from remark_branch_audit where audit_id=#{branchFinancialAudit.id} AND (status=0 AND reciever=#{reciever.id}) ORDER BY remark_date DESC")
        @Results(value = {
                        @Result(property = "branchFinancialAudit", column = "audit_id", one = @One(select = "com.afr.fms.Branch_Audit.Common.Audit_Remark.RemarkBranchAuditMapper.get_branch_financial_audit")),
                        @Result(property = "sender", column = "sender", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getUserById")),
                        @Result(property = "reciever", column = "reciever", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getUserById"))
        })
        public List<RemarkBranchAudit> getUnseenRemarks(RemarkBranchAudit remark);

        @Select("Select * from remark where load_id=#{loan.id}  AND (status=0 AND reciever_id is NULL) ORDER BY date")
        @Results(value = {
                        @Result(property = "loan", column = "load_id", one = @One(select = "com.bfp.elfms.Maker.Mapper.LoanMapper.getLoanById")),
                        @Result(property = "sender", column = "sender_id", one = @One(select = "com.bfp.elfms.Admin.Mapper.UserMapper.getUserById")),
                        @Result(property = "reciever", column = "reciever_id", one = @One(select = "com.bfp.elfms.Admin.Mapper.UserMapper.getUserById"))
        })
        public List<RemarkBranchAudit> getUnseenRemarksUnassignedApprover(RemarkBranchAudit remark);

        @Select("Select * from remark where load_id = #{loan_id} and rejected = 1")
        public RemarkBranchAudit getRemarkByLoanId(Long loan_id);

}
