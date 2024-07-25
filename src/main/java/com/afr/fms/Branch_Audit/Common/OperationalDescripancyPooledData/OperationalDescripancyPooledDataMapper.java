package com.afr.fms.Branch_Audit.Common.OperationalDescripancyPooledData;

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
public interface OperationalDescripancyPooledDataMapper {
        @Insert("INSERT INTO operational_discrepancy_pooled_data(branch_financial_audit_id, pooled_date, user_id, pool_amount, cash_type, fcy, status) VALUES (#{branch_financial_audit_id}, CURRENT_TIMESTAMP, #{pooler}, #{pool_amount}, #{cash_type}, #{fcy}, 1)")
        public void addToPool(OperationalDescripancyPooledData operationalDescripancyPooledData);

        @Update("update operational_discrepancy_pooled_data set status = #{status} where branch_financial_audit_id=#{branch_financial_audit_id} and user_id = #{pooler}")
        public void deletePooledData(OperationalDescripancyPooledData operationalDescripancyPooledData);

        @Select("Select bfa.*, b.name as branch_name , " +
                        " dbo._StripHTML(  " +
                        " CASE  " +
                        " WHEN r.name IN ('ROLE_APPROVER_BFA',  'ROLE_COMPILER_BFA') THEN COALESCE(cra.finding, cba.finding, bfa.finding) "
                        +
                        " WHEN r.name IN ('ROLE_REVIEWER_BFA', 'ROLE_REGIONALD_BFA') THEN COALESCE(cba.finding, bfa.finding) "
                        +
                        " WHEN r.name IN ('ROLE_AUDITOR_BFA', 'ROLE_BRANCHM_BFA') THEN bfa.finding " +
                        " ELSE NULL  " +
                        " END) AS audit_finding, " +
                        " dbo._StripHTML(  " +
                        " CASE  " +
                        " WHEN r.name IN ('ROLE_APPROVER_BFA',  'ROLE_COMPILER_BFA') THEN COALESCE(cra.impact, cba.impact, bfa.impact) "
                        +
                        " WHEN r.name IN ('ROLE_REVIEWER_BFA', 'ROLE_REGIONALD_BFA') THEN COALESCE(cba.impact, bfa.impact) "
                        +
                        " WHEN r.name IN ('ROLE_AUDITOR_BFA', 'ROLE_BRANCHM_BFA') THEN bfa.impact " +
                        " ELSE NULL  " +
                        " END) AS audit_impact, " +
                        "  dbo._StripHTML(  " +
                        " CASE  " +
                        " WHEN r.name IN ('ROLE_APPROVER_BFA',  'ROLE_COMPILER_BFA') THEN COALESCE(cra.recommendation, cba.recommendation, bfa.recommendation) "
                        +
                        " WHEN r.name IN ('ROLE_REVIEWER_BFA', 'ROLE_REGIONALD_BFA') THEN COALESCE(cba.recommendation, bfa.recommendation) "
                        +
                        " WHEN r.name IN ('ROLE_AUDITOR_BFA', 'ROLE_BRANCHM_BFA') THEN bfa.recommendation " +
                        " ELSE NULL  " +
                        " END) AS audit_recommendation, " +

                        " CASE  " +
                        " WHEN r.name IN ('ROLE_APPROVER_BFA',  'ROLE_COMPILER_BFA') THEN COALESCE(cra.case_number, cba.case_number, bfa.case_number) "
                        +
                        " WHEN r.name IN ('ROLE_REVIEWER_BFA', 'ROLE_REGIONALD_BFA') THEN COALESCE(cba.case_number, bfa.case_number) "
                        +
                        " WHEN r.name IN ('ROLE_AUDITOR_BFA', 'ROLE_BRANCHM_BFA') THEN bfa.case_number " +
                        " ELSE bfa.case_number  " +
                        " END AS audit_case_number " +

                        "from branch_financial_audit  bfa " +
                        "inner join operational_discrepancy_pooled_data odpd on odpd.branch_financial_audit_id = bfa.id and odpd.status = 1 and odpd.user_id = #{pooler} "
                        +
                        "left join compiled_audits ca ON bfa.id = ca.audit_id " +
                        "left join compiled_branch_audit cba ON bfa.id = ca.audit_id AND ca.compiled_id = cba.id " +
                        "left join compiled_audits_region car ON car.audit_id = cba.id " +
                        "left join compiled_regional_audit cra ON cra.id = car.compiled_id " +
                        "INNER JOIN operational_descripancy_branch odb ON odb.branch_audit_id = bfa.id " +
                        "inner join user_role ur ON ur.user_id = odpd.user_id " +
                        "inner join role r ON ur.role_id = r.id " +
                        "inner join branch as b on bfa.branch_id = b.id " +
                        "where bfa.status = 1 ORDER BY odpd.pooled_date")
        @Results(value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "operationalDescripancyBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.OperationalDescripancyAuditorBranchMapper.getOperationalDescripancyByBranchId")),
        })
        public List<BranchFinancialAudit> getPooledData(
                        OperationalDescripancyPooledData operationalDescripancyPooledData);

}
