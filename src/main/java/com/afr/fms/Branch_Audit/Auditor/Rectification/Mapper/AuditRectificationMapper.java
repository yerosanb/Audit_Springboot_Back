package com.afr.fms.Branch_Audit.Auditor.Rectification.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;

@Mapper
public interface AuditRectificationMapper {

    @Update("update branch_financial_audit set action_plan=#{action_plan}, responded_date = CURRENT_TIMESTAMP, response_added = 1 where id = #{id} ")
    public void addAuditeeResponse(BranchFinancialAudit branchFinancialAudit);

    @Update("update branch_financial_audit set rectification_status = 1, rectification_date = CURRENT_TIMESTAMP  where id = #{id} ")
    public void rectifyAuditeeResponse(BranchFinancialAudit branchFinancialAudit);

    @Update("update branch_financial_audit set rectification_status = 2 where id = #{id}  where id = #{id} ")
    public void unrectifyAuditeeResponse(BranchFinancialAudit branchFinancialAudit);

    @Select("insert into attached_file_branch_manager (branch_audit_id,file_url, uploaded_date) values (#{branch_audit_id}, #{file_url}, CURRENT_TIMESTAMP )")
    public void add_attached_files(Long branch_audit_id, String file_url);

    @Delete("delete from attached_file_branch_manager where branch_audit_id = #{branch_audit_id}")
    public void remove_attached_files(Long branch_audit_id);

    @Select("select file_url from  attached_file_branch_manager where branch_audit_id= #{branch_audit_id}")
    public List<String> getAttachedFiles(Long branch_audit_id);

    @Update("update compiled_audits set rectification_status = #{status} where audit_id = #{branch_financial_audit}")
    public void updateRectificationStatusCompiledAudits(Long branch_financial_audit, int status);

}
