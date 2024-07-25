package com.afr.fms.Branch_Audit.Common.RectificationTracker;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;

@Mapper
public interface RectificationTrackerMapper {
        @Insert("INSERT INTO rectification_tracker(audit_id, rectification_date, sender, reciever, status) VALUES (#{audit_id}, CURRENT_TIMESTAMP, #{sender}, #{reciever}, 0)")
        public void addRectificationTracker(RectificationTracker rectificationTracker);

        @Update("update rectification_tracker set status = #{status} where audit_id=#{audit_id} and reciever = #{reciever}")
        public void seenRectificationTracker(RectificationTracker rectificationTracker);

        @Update("update branch_financial_audit set seen = #{rectification_status} where id=#{id}")
        public void seenBranchFinancial(BranchFinancialAudit audit);

        @Select("<script>" +
                        "Select bfa.*, b.name as branch_name , " +
                        " dbo._StripHTML(  " +
                        " CASE  " +
                        " WHEN r.name IN ('ROLE_APPROVER_BFA',  'ROLE_COMPILER_BFA') THEN COALESCE(cra.finding, cba.finding, bfa.finding) "
                        +
                        " WHEN r.name IN ('ROLE_REVIEWER_BFA', 'ROLE_REGIONALD_BFA') THEN COALESCE(cba.finding, bfa.finding) " +
                        " WHEN r.name IN ('ROLE_AUDITOR_BFA', 'ROLE_BRANCHM_BFA') THEN bfa.finding " +
                        " ELSE NULL  " +
                        " END) AS audit_finding, " +
                        " dbo._StripHTML(  " +
                        " CASE  " +
                        " WHEN r.name IN ('ROLE_APPROVER_BFA',  'ROLE_COMPILER_BFA') THEN COALESCE(cra.impact, cba.impact, bfa.impact) "
                        +
                        " WHEN r.name IN ('ROLE_REVIEWER_BFA', 'ROLE_REGIONALD_BFA') THEN COALESCE(cba.impact, bfa.impact) " +
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

                        " " +
                        " CASE  " +
                        " WHEN r.name IN ('ROLE_APPROVER_BFA',  'ROLE_COMPILER_BFA') THEN COALESCE(cra.case_number, cba.case_number, bfa.case_number) "
                        +
                        " WHEN r.name IN ('ROLE_REVIEWER_BFA', 'ROLE_REGIONALD_BFA') THEN COALESCE(cba.case_number, bfa.case_number) "
                        +
                        " WHEN r.name IN ('ROLE_AUDITOR_BFA', 'ROLE_BRANCHM_BFA') THEN bfa.case_number " +
                        " ELSE NULL  " +
                        " END AS audit_case_number " +
                        " " +

                        "from branch_financial_audit  bfa " +
                        "inner join rectification_tracker rt on rt.audit_id = bfa.id and rt.status = 0 and rt.reciever = #{branchFinancialAudit.reciever.id} "
                        +
                        "left join compiled_audits ca ON bfa.id = ca.audit_id " +
                        "left join compiled_branch_audit cba ON bfa.id = ca.audit_id AND ca.compiled_id = cba.id " +
                        "left join compiled_audits_region car ON car.audit_id = cba.id " +
                        "left join compiled_regional_audit cra ON cra.id = car.compiled_id " +
                        "<choose>" +
                        "<when test='auditType == \"IncompleteAccount\"'>" +
                        "INNER JOIN incomplete_account_branch ic ON ic.branch_audit_id = bfa.id " +
                        "</when>" +
                        "<when test='auditType == \"ATMCard\"'>" +
                        "INNER JOIN ATM_card_branch acb ON acb.branch_audit_id = bfa.id " +
                        "</when>" +
                        "<when test='auditType == \"CashCount\"'>" +
                        "INNER JOIN cash_count_branch ccb ON ccb.branch_audit_id = bfa.id " +
                        "</when>" +
                        "<when test='auditType == \"OperationalDescripancy\"'>" +
                        "INNER JOIN operational_descripancy_branch odb ON odb.branch_audit_id = bfa.id " +
                        "</when>" +
                        "<when test='auditType == \"LoanAdvance\"'>" +
                        "INNER JOIN loan_advance_branch lab ON lab.branch_audit_id = bfa.id " +
                        "</when>" +
                        "<when test='auditType == \"NegotiableInstrument\"'>" +
                        "INNER JOIN negotiable_instrument_branch nib ON nib.branch_audit_id = bfa.id " +
                        "INNER JOIN negotiable_stock_item nsi ON nsi.id = nib.negotiable_stock_item_id " +
                        "</when>" +

                        "<when test='auditType == \"AbnormalBalance\"'>" +
			"INNER JOIN abnormal_balance_branch abb ON abb.branch_audit_id = bfa.id " +
			"</when>" +

			"<when test='auditType == \"CashPerformance\"'>" +
			"INNER JOIN cash_performance_branch cpb ON cpb.branch_audit_id = bfa.id " +
			"</when>" +

			"<when test='auditType == \"CashManagement\"'>" +
			"INNER JOIN cash_management_branch cmb ON cmb.branch_audit_id = bfa.id " +
			"</when>" +

			"<when test='auditType == \"SuspenseAccount\"'>" +
			"INNER JOIN suspense_account_branch sab ON sab.branch_audit_id = bfa.id " +
			"</when>" +

			"<when test='auditType == \"ControllableExpense\"'>" +
			"INNER JOIN controllable_expense_branch ceb ON ceb.branch_audit_id = bfa.id " +
			"</when>" +

			"<when test='auditType == \"Dormant\"'>" +
			"INNER JOIN dormant_inactive_account_branch diab ON diab.branch_audit_id = bfa.id " +
			"</when>" +

			"<when test='auditType == \"Contigent\"'>" +
			"INNER JOIN memorandom_contingent_branch mcb ON mcb.branch_audit_id = bfa.id " +
			"</when>" +

                        "<when test='auditType == \"GeneralObservation\"'>" +
			"INNER JOIN branch_financial_audit  bfa2 ON bfa2.id = bfa.id  and bfa2.observation is NOT NULL" +
			"</when>" +

			"<when test='auditType == \"LongOutstandingItems\"'>" +
			"INNER JOIN long_outstanding_item_branch loib ON loib.branch_audit_id = bfa.id " +
			"</when>" +

			"<when test='auditType == \"AssetLiability\"'>" +
			"INNER JOIN asset_liability_branch alb ON alb.branch_audit_id = bfa.id " +
			"</when>" +   
                        
                        "<when test='auditType == \"StatusOfAudit\"'>" +
			"INNER JOIN audit_status_branch asb ON asb.branch_audit_id = bfa.id " +
			"</when>" + 
                        
                        "</choose>" +
                        "inner join user_role ur ON ur.user_id = rt.reciever " +
                        "inner join role r ON ur.role_id = r.id " +
                        "inner join branch as b on bfa.branch_id = b.id " +
                        "where bfa.status = 1 and bfa.rectification_status = 1 ORDER BY bfa.rectification_date DESC"
                        +
                        "</script>")
        @Results(value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "incompleteAccountBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper.getIncompleteAccountBranch")),
			@Result(property = "atmCardBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.AtmCardAuditorMapper.getAtmCardBranch")),
			@Result(property = "memorandumContigent", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.MemorandumAuditorMapper.getMemorandumContigent")),
			@Result(property = "cashManagementBranch", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.CashManagementAuditorMapper.getCashManagementBranch")),
			@Result(property = "dormantInactive", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.DormantAccountMapper.getDormantInactive")),
			@Result(property = "operationalDescripancyBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.OperationalDescripancyAuditorBranchMapper.getOperationalDescripancyByBranchId")),
			@Result(property = "cashcount", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.CashCountMapper.getCashCountById")),
			@Result(property = "assetLiabilityBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.AssetLiabilityAuditorMapper.getAssetLiabilityByBranchId")),
			@Result(property = "abnormalBalanceBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.AbnormalBalanceAuditorMapper.getAbnormalBalanceByBranchId")),
                        @Result(property = "statusofAudit", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.StatusOfAuditMapper.getBFAByStatusofAudit")),
			@Result(property = "negotiableInstrument", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.NegotiableInstrumentBranchMapper.getNegotiableInstrumentByBranchId")),
			@Result(property = "suspenseAccountBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.SuspenseAccountAuditorBranchMapper.getSuspenseAccountByBranchId")),
			@Result(property = "controllableExpense", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.ControllableExpenseAuditorMapper.getControllableExpenseByBranchId")),
			@Result(property = "loanAndAdvance", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.LoanAndAdvanceMapper.getLoanAndAdvanceByBFAId")),
			@Result(property = "long_outstanding", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.LongOutStandingItemsAuditorMapper.getLongOutstandingItemsById")),
			@Result(property = "cashPerformanceBranch", column = "id", one = @One(select = "com.afr.fms.Branch_Audit.Auditor.Mapper.CashPerformanceAuditorMapper.getCashPerformanceBranch")),
                        @Result(property = "bmFileUrls", column = "id", many = @Many(select = "com.afr.fms.Branch_Audit.Auditor.Rectification.Mapper.AuditRectificationMapper.getAttachedFiles")),

        })
        public List<BranchFinancialAudit> getUnseenRectificationTrackers(BranchFinancialAudit branchFinancialAudit,
                        @Param("auditType") String auditType);

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
