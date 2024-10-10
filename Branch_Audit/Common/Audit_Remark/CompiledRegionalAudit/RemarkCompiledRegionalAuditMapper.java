package com.afr.fms.Branch_Audit.Common.Audit_Remark.CompiledRegionalAudit;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.afr.fms.Branch_Audit.Common.Audit_Remark.RemarkBranchAudit;
import com.afr.fms.Branch_Audit.Entity.CompiledRegionalAudit;

@Mapper
public interface RemarkCompiledRegionalAuditMapper {
        @Insert("INSERT INTO remark_branch_audit(compiled_regional_audit_id, sender, reciever, message, remark_date,status, rejected) VALUES (#{compiledRegionalAudit.id}, #{sender.id}, #{reciever.id}, #{message}, CURRENT_TIMESTAMP,0,0)")
        public void addRemark(RemarkBranchAudit remark);

        @Insert("INSERT INTO remark_branch_audit(compiled_regional_audit_id, sender, reciever, message, remark_date,status, rejected) VALUES (#{compiledRegionalAudit.id}, #{sender.id}, #{reciever.id}, #{message}, CURRENT_TIMESTAMP,0,1)")
        public void addRejectedRemark(RemarkBranchAudit remark);

        @Update("update remark_branch_audit set status=1 where compiled_regional_audit_id=#{compiledRegionalAudit.id} and reciever=#{reciever.id} and sender = #{sender.id}")
        public void seenRemark(RemarkBranchAudit remark);

        @Select("select * from compiled_regional_audit where id = #{id}")
        public CompiledRegionalAudit get_compiled_regional_audit(Long id);

        @Update("update remark set reciever_id=#{reciever.id} where load_id=#{loan.id} and sender_id=#{sender.id}")
        public void setReciever(RemarkBranchAudit remark);

        @Select("Select * from remark_branch_audit where compiled_regional_audit_id = #{compiledRegionalAudit.id} and (sender = #{sender.id} or sender = #{reciever.id}) and (reciever = #{sender.id} or reciever = #{reciever.id}) ORDER BY remark_date")
        @Results(value = {
                        @Result(property = "compiledRegionalAudit", column = "compiled_regional_audit_id", one = @One(select = "com.afr.fms.Branch_Audit.Common.Audit_Remark.CompiledRegionalAudit.RemarkCompiledRegionalAuditMapper.get_compiled_regional_audit")),
                        @Result(property = "sender", column = "sender", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getUserById")),
                        @Result(property = "reciever", column = "reciever", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getUserById"))
        })
        public List<RemarkBranchAudit> getRemarks(RemarkBranchAudit remark);

        @Select("Select * from remark_branch_audit where compiled_regional_audit_id=#{compiledRegionalAudit.id} AND (status=0 AND reciever=#{reciever.id}) ORDER BY remark_date DESC")
        @Results(value = {
                        @Result(property = "compiledRegionalAudit", column = "compiled_regional_audit_id", one = @One(select = "com.afr.fms.Branch_Audit.Common.Audit_Remark.CompiledRegionalAudit.RemarkCompiledRegionalAuditMapper.get_compiled_regional_audit")),
                        @Result(property = "sender", column = "sender", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getUserById")),
                        @Result(property = "reciever", column = "reciever", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getUserById"))
        })
        public List<RemarkBranchAudit> getUnseenRemarks(RemarkBranchAudit remark);

     

}
