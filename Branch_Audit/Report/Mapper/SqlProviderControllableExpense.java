package com.afr.fms.Branch_Audit.Report.Mapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class SqlProviderControllableExpense {

	public String getBranchAuditReportControllableExpense(@Param("region_id") Long region_id,
			@Param("branch_id") Long branch_id, @Param("finding_status") String finding_status,
			@Param("rectification_date_range") String[] rectification_date_range,
			@Param("date_range") String[] date_range, @Param("audit_finding") String audit_finding,

			@Param("variation_min") Double variation_min, @Param("variation_max") Double variation_max,
			@Param("budget_per_plan_min") Double budget_per_plan_min,
			@Param("budget_per_plan_max") Double budget_per_plan_max,
			@Param("actual_balance_min") Double actual_balance_min, @Param("actual_balance_max") Double actual_balance_max,
			@Param("controllable_expense_type") String controllable_expense_type,

			@Param("rectification_status") String rectification_status,
			@Param("single_filter_info") String single_filter_info, @Param("user_id") Long user_id,
			@Param("user_roles") String[] user_roles, @Param("user_region_id") Long user_region_id,
    		@Param("ing") String ing,
			@Param("cash_type") String cash_type,
		    @Param("regional_compiler_compiled_date") String[] regional_compiler_compiled_date,
		    @Param("division_compiler_compiled_date") String[] division_compiler_compiled_date,
		    @Param("approved_date") String[] approved_date) {

		if (!single_filter_info.equalsIgnoreCase("general"))
			if (!regional_compiler_compiled_date[0].equalsIgnoreCase("none42e") ||
				!division_compiler_compiled_date[0].equalsIgnoreCase("none42e")|| 
				!approved_date[0].equalsIgnoreCase("none42e")|| 
				controllable_expense_type != null || ing != null || cash_type != null)
				single_filter_info = "general";

		System.out.println("================================================: ");
		System.out.println("region_id: " + region_id);
		System.out.println("branch_id: " + branch_id);
		System.out.println("finding_status: " + finding_status);
		System.out.println("rectification_date_range: " + Arrays.toString(rectification_date_range));
		System.out.println("date_range: " + Arrays.toString(date_range));
		System.out.println("audit_finding: " + audit_finding);
		System.out.println("variation_min: " + variation_min);
		System.out.println("variation_max: " + variation_max);
		System.out.println("budget_per_plan_min: " + budget_per_plan_min);
		System.out.println("budget_per_plan_max: " + budget_per_plan_max);
		System.out.println("actual_balance_min: " + actual_balance_min);
		System.out.println("actual_balance_max: " + actual_balance_max);
		System.out.println("controllable_expense_type: " + controllable_expense_type);
		System.out.println("rectification_status: " + rectification_status);
		System.out.println("single_filter_info: " + single_filter_info);
		System.out.println("user_id: " + user_id);
		System.out.println("user_roles: " + Arrays.toString(user_roles));
		System.out.println("user_region_id: " + user_region_id);
		System.out.println("================================================: ");

		SQL sql = new SQL();
		if (single_filter_info.equalsIgnoreCase("branch")) {
			sql.SELECT("	bfa.id, " + "	bfa.case_number, " + "	b.name as branch_name, "
					+ "	dbo._StripHTML( CASE "
					+ "    WHEN r.name IN ('ROLE_APPROVER_BFA',  'ROLE_COMPILER_BFA') THEN COALESCE(cra.finding, cba.finding, bfa.finding) "
					+ "    WHEN r.name IN ('ROLE_REVIEWER_BFA', 'ROLE_REGIONALD_BFA') THEN COALESCE(cba.finding, bfa.finding) "
					+ "    WHEN r.name IN ('ROLE_AUDITOR_BFA', 'ROLE_BRANCHM_BFA') THEN bfa.finding " + "    ELSE NULL "
					+ "  END) AS audit_finding, "

					+ "	ceb.variation as variation, " + "	ceb.period as period, "
					+ "	ceb.budget_per_plan as budget_per_plan, " + "	ceb.actual_balance as actual_balance, "
					+ "	ceb.cash_type as cash_type, "
					+ "	ceb.fcy as fcy, "
					+ "	cet.name as expense_type, "

					+ "	  dbo._StripHTML( CASE "
					+ "    WHEN r.name IN ('ROLE_APPROVER_BFA',  'ROLE_COMPILER_BFA') THEN COALESCE(cra.impact, cba.impact, bfa.impact) "
					+ "    WHEN r.name IN ('ROLE_REVIEWER_BFA', 'ROLE_REGIONALD_BFA') THEN COALESCE(cba.impact, bfa.impact) "
					+ "    WHEN r.name IN ('ROLE_AUDITOR_BFA', 'ROLE_BRANCHM_BFA') THEN bfa.impact " + "    ELSE NULL "
					+ "  END) AS audit_impact, " + "	dbo._StripHTML( CASE "
					+ "    WHEN r.name IN ('ROLE_APPROVER_BFA',  'ROLE_COMPILER_BFA') THEN COALESCE(cra.recommendation, cba.recommendation, bfa.recommendation) "
					+ "    WHEN r.name IN ('ROLE_REVIEWER_BFA', 'ROLE_REGIONALD_BFA') THEN COALESCE(cba.recommendation, bfa.recommendation) "
					+ "    WHEN r.name IN ('ROLE_AUDITOR_BFA', 'ROLE_BRANCHM_BFA') THEN bfa.recommendation " + "    ELSE NULL "
					+ "  END) AS auditor_recommendation, " + "	dbo._StripHTML(bfa.action_plan) as auditee_response, "
					+ "	bfa.finding_status as audit_finding_status, "
					+ "	cast(bfa.rectification_date as date) as rectified_on").FROM("branch_financial_audit AS bfa")
					.LEFT_OUTER_JOIN("compiled_audits AS ca ON bfa.id = ca.audit_id")
					.LEFT_OUTER_JOIN("compiled_branch_audit AS cba ON bfa.id = ca.audit_id AND ca.compiled_id = cba.id")
					.LEFT_OUTER_JOIN("compiled_audits_region AS car ON car.audit_id = cba.id")
					.LEFT_OUTER_JOIN("compiled_regional_audit AS cra ON cra.id = car.compiled_id")
					.JOIN("controllable_expense_branch AS ceb ON bfa.id = ceb.branch_audit_id")
					.JOIN("controllable_expense_branch_mapping as cebm on ceb.id = cebm.controllable_branch_id")
					.JOIN("controllable_expense_type as cet on cebm.controllable_expense_type_id = cet.id")
					.JOIN("user_role AS ur ON ur.user_id = #{user_id}").JOIN("role AS r ON ur.role_id = r.id")
					.JOIN("branch as b on bfa.branch_id = b.id and bfa.id = ceb.branch_audit_id").WHERE("1 = 1");
			if (branch_id != null)
				sql.AND().WHERE("b.id = #{branch_id}");
		} else if (single_filter_info.equalsIgnoreCase("region")) {
			sql.SELECT("	bfa.id, " + "	re.name as region_name, " + "	dbo._StripHTML( CASE "
					+ "    WHEN r.name IN ('ROLE_APPROVER_BFA',  'ROLE_COMPILER_BFA') THEN COALESCE(cra.finding, cba.finding, bfa.finding) "
					+ "    WHEN r.name IN ('ROLE_REVIEWER_BFA', 'ROLE_REGIONALD_BFA') THEN COALESCE(cba.finding, bfa.finding) "
					+ "    WHEN r.name IN ('ROLE_AUDITOR_BFA', 'ROLE_BRANCHM_BFA') THEN bfa.finding " + "    ELSE NULL "
					+ "  END) AS audit_finding, "

					+ "	ceb.variation as variation, " + "	ceb.period as period, "
					+ "	ceb.budget_per_plan as budget_per_plan, " + "	ceb.actual_balance as actual_balance, "
					+ "	ceb.cash_type as cash_type, "
					+ "	ceb.fcy as fcy, "
					+ "	cet.name as expense_type, "

					+ "	dbo._StripHTML( CASE "
					+ "    WHEN r.name IN ('ROLE_APPROVER_BFA',  'ROLE_COMPILER_BFA') THEN COALESCE(cra.impact, cba.impact, bfa.impact) "
					+ "    WHEN r.name IN ('ROLE_REVIEWER_BFA', 'ROLE_REGIONALD_BFA') THEN COALESCE(cba.impact, bfa.impact) "
					+ "    WHEN r.name IN ('ROLE_AUDITOR_BFA', 'ROLE_BRANCHM_BFA') THEN bfa.impact " + "    ELSE NULL "
					+ "  END) AS audit_impact, " + "	dbo._StripHTML( CASE "
					+ "    WHEN r.name IN ('ROLE_APPROVER_BFA',  'ROLE_COMPILER_BFA') THEN COALESCE(cra.recommendation, cba.recommendation, bfa.recommendation) "
					+ "    WHEN r.name IN ('ROLE_REVIEWER_BFA', 'ROLE_REGIONALD_BFA') THEN COALESCE(cba.recommendation, bfa.recommendation) "
					+ "    WHEN r.name IN ('ROLE_AUDITOR_BFA', 'ROLE_BRANCHM_BFA') THEN bfa.recommendation " + "    ELSE NULL "
					+ "  END) AS auditor_recommendation, " + "	dbo._StripHTML(bfa.action_plan) as auditee_response, "
					+ "	bfa.finding_status as audit_finding_status, "
					+ "	cast(bfa.rectification_date as date) as rectified_on").FROM("branch_financial_audit AS bfa")
					.LEFT_OUTER_JOIN("compiled_audits AS ca ON bfa.id = ca.audit_id")
					.LEFT_OUTER_JOIN("compiled_branch_audit AS cba ON bfa.id = ca.audit_id AND ca.compiled_id = cba.id")
					.LEFT_OUTER_JOIN("compiled_audits_region AS car ON car.audit_id = cba.id")
					.LEFT_OUTER_JOIN("compiled_regional_audit AS cra ON cra.id = car.compiled_id")
					.JOIN("controllable_expense_branch AS ceb ON bfa.id = ceb.branch_audit_id")
					.JOIN("controllable_expense_branch_mapping as cebm on ceb.id = cebm.controllable_branch_id")
					.JOIN("controllable_expense_type as cet on cebm.controllable_expense_type_id = cet.id")
					.JOIN("user_role AS ur ON ur.user_id = #{user_id}").JOIN("role AS r ON ur.role_id = r.id")
					.JOIN("branch as b on bfa.branch_id = b.id")
					.JOIN("region as re on bfa.branch_id = b.id and b.region_id = re.id").WHERE("1 = 1");
			if (region_id != null)
				sql.AND().WHERE("b.region_id = #{region_id}");
		} else if (single_filter_info.equalsIgnoreCase("finding_status")) {
			sql.SELECT("	bfa.id, " + " " + "	b.name as branch_name, " + "	dbo._StripHTML( CASE "
					+ "    WHEN r.name IN ('ROLE_APPROVER_BFA',  'ROLE_COMPILER_BFA') THEN COALESCE(cra.finding, cba.finding, bfa.finding) "
					+ "    WHEN r.name IN ('ROLE_REVIEWER_BFA', 'ROLE_REGIONALD_BFA') THEN COALESCE(cba.finding, bfa.finding) "
					+ "    WHEN r.name IN ('ROLE_AUDITOR_BFA', 'ROLE_BRANCHM_BFA') THEN bfa.finding " + "    ELSE NULL "
					+ "  END) AS audit_finding, "

					+ "	ceb.variation as variation, " + "	ceb.period as period, "
					+ "	ceb.budget_per_plan as budget_per_plan, " + "	ceb.actual_balance as actual_balance, "
					+ "	ceb.cash_type as cash_type, "
					+ "	ceb.fcy as fcy, "
					+ "	cet.name as expense_type, "

					+ "	dbo._StripHTML( CASE "
					+ "    WHEN r.name IN ('ROLE_APPROVER_BFA',  'ROLE_COMPILER_BFA') THEN COALESCE(cra.impact, cba.impact, bfa.impact) "
					+ "    WHEN r.name IN ('ROLE_REVIEWER_BFA', 'ROLE_REGIONALD_BFA') THEN COALESCE(cba.impact, bfa.impact) "
					+ "    WHEN r.name IN ('ROLE_AUDITOR_BFA', 'ROLE_BRANCHM_BFA') THEN bfa.impact " + "    ELSE NULL "
					+ "  END) AS audit_impact, " + "	dbo._StripHTML( CASE "
					+ "    WHEN r.name IN ('ROLE_APPROVER_BFA',  'ROLE_COMPILER_BFA') THEN COALESCE(cra.recommendation, cba.recommendation, bfa.recommendation) "
					+ "    WHEN r.name IN ('ROLE_REVIEWER_BFA', 'ROLE_REGIONALD_BFA') THEN COALESCE(cba.recommendation, bfa.recommendation) "
					+ "    WHEN r.name IN ('ROLE_AUDITOR_BFA', 'ROLE_BRANCHM_BFA') THEN bfa.recommendation " + "    ELSE NULL "
					+ "  END) AS auditor_recommendation, " + "	dbo._StripHTML(bfa.action_plan) as auditee_response, "
					+ "	bfa.finding_status as audit_finding_status, "
					+ "	cast(bfa.rectification_date as date) as rectified_on").FROM("branch_financial_audit AS bfa")
					.LEFT_OUTER_JOIN("compiled_audits AS ca ON bfa.id = ca.audit_id")
					.LEFT_OUTER_JOIN("compiled_branch_audit AS cba ON bfa.id = ca.audit_id AND ca.compiled_id = cba.id")
					.LEFT_OUTER_JOIN("compiled_audits_region AS car ON car.audit_id = cba.id")
					.LEFT_OUTER_JOIN("compiled_regional_audit AS cra ON cra.id = car.compiled_id")
					.JOIN("controllable_expense_branch AS ceb ON bfa.id = ceb.branch_audit_id")
					.JOIN("controllable_expense_branch_mapping as cebm on ceb.id = cebm.controllable_branch_id")
					.JOIN("controllable_expense_type as cet on cebm.controllable_expense_type_id = cet.id")
					.JOIN("user_role AS ur ON ur.user_id = #{user_id}").JOIN("role AS r ON ur.role_id = r.id")
					.JOIN("branch as b on bfa.branch_id = b.id and bfa.id = ceb.branch_audit_id").WHERE("1 = 1");
			if (finding_status != null)
				sql.AND().WHERE("bfa.finding_status = #{finding_status}");
		} else if (single_filter_info.equalsIgnoreCase("rectification_date_range")) {
			sql.SELECT("	bfa.id, " + " " + "	b.name as branch_name, " + "	dbo._StripHTML( CASE "
					+ "    WHEN r.name IN ('ROLE_APPROVER_BFA',  'ROLE_COMPILER_BFA') THEN COALESCE(cra.finding, cba.finding, bfa.finding) "
					+ "    WHEN r.name IN ('ROLE_REVIEWER_BFA', 'ROLE_REGIONALD_BFA') THEN COALESCE(cba.finding, bfa.finding) "
					+ "    WHEN r.name IN ('ROLE_AUDITOR_BFA', 'ROLE_BRANCHM_BFA') THEN bfa.finding " + "    ELSE NULL "
					+ "  END) AS audit_finding, "

					+ "	ceb.variation as variation, " + "	ceb.period as period, "
					+ "	ceb.budget_per_plan as budget_per_plan, " + "	ceb.actual_balance as actual_balance, "
					+ "	ceb.cash_type as cash_type, "
					+ "	ceb.fcy as fcy, "
					+ "	cet.name as expense_type, "

					+ "	dbo._StripHTML( CASE "
					+ "    WHEN r.name IN ('ROLE_APPROVER_BFA',  'ROLE_COMPILER_BFA') THEN COALESCE(cra.impact, cba.impact, bfa.impact) "
					+ "    WHEN r.name IN ('ROLE_REVIEWER_BFA', 'ROLE_REGIONALD_BFA') THEN COALESCE(cba.impact, bfa.impact) "
					+ "    WHEN r.name IN ('ROLE_AUDITOR_BFA', 'ROLE_BRANCHM_BFA') THEN bfa.impact " + "    ELSE NULL "
					+ "  END) AS audit_impact, " + "	dbo._StripHTML( CASE "
					+ "    WHEN r.name IN ('ROLE_APPROVER_BFA',  'ROLE_COMPILER_BFA') THEN COALESCE(cra.recommendation, cba.recommendation, bfa.recommendation) "
					+ "    WHEN r.name IN ('ROLE_REVIEWER_BFA', 'ROLE_REGIONALD_BFA') THEN COALESCE(cba.recommendation, bfa.recommendation) "
					+ "    WHEN r.name IN ('ROLE_AUDITOR_BFA', 'ROLE_BRANCHM_BFA') THEN bfa.recommendation " + "    ELSE NULL "
					+ "  END) AS auditor_recommendation, " + "	dbo._StripHTML(bfa.action_plan) as auditee_response, "
					+ "	bfa.finding_status as audit_finding_status, "
					+ "	cast(bfa.rectification_date as date) as rectified_on").FROM("branch_financial_audit AS bfa")
					.LEFT_OUTER_JOIN("compiled_audits AS ca ON bfa.id = ca.audit_id")
					.LEFT_OUTER_JOIN("compiled_branch_audit AS cba ON bfa.id = ca.audit_id AND ca.compiled_id = cba.id")
					.LEFT_OUTER_JOIN("compiled_audits_region AS car ON car.audit_id = cba.id")
					.LEFT_OUTER_JOIN("compiled_regional_audit AS cra ON cra.id = car.compiled_id")
					.JOIN("controllable_expense_branch AS ceb ON bfa.id = ceb.branch_audit_id")
					.JOIN("controllable_expense_branch_mapping as cebm on ceb.id = cebm.controllable_branch_id")
					.JOIN("controllable_expense_type as cet on cebm.controllable_expense_type_id = cet.id")
					.JOIN("user_role AS ur ON ur.user_id = #{user_id}").JOIN("role AS r ON ur.role_id = r.id")
					.JOIN("branch as b on bfa.branch_id = b.id and bfa.id = ceb.branch_audit_id").WHERE("1 = 1");
			if (!rectification_date_range[0].equalsIgnoreCase("none42e")
					&& !rectification_date_range[1].equalsIgnoreCase("none42e")) {
				sql.AND()
						.WHERE("cast(bfa.rectification_date as date) >= '"
								+ LocalDateTime.ofInstant(Instant.parse(rectification_date_range[0]), ZoneId.of("UTC"))
										.plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
								+ "'");
				sql.AND()
						.WHERE("cast(bfa.rectification_date as date) <= '"
								+ LocalDateTime.ofInstant(Instant.parse(rectification_date_range[1]), ZoneId.of("UTC"))
										.plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
								+ "'");
			} else if (!rectification_date_range[0].equalsIgnoreCase("none42e")
					&& rectification_date_range[1].equalsIgnoreCase("none42e"))
				sql.AND()
						.WHERE("cast(bfa.rectification_date as date) = '"
								+ LocalDateTime.ofInstant(Instant.parse(rectification_date_range[0]), ZoneId.of("UTC"))
										.plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
								+ "'");
		} else if (single_filter_info.equalsIgnoreCase("audit_finding")) {
			sql.SELECT("bfa.id, " + "	b.name as branch_name, " + "	dbo._StripHTML( CASE "
					+ "    WHEN r.name IN ('ROLE_APPROVER_BFA',  'ROLE_COMPILER_BFA') THEN COALESCE(cra.finding, cba.finding, bfa.finding) "
					+ "    WHEN r.name IN ('ROLE_REVIEWER_BFA', 'ROLE_REGIONALD_BFA') THEN COALESCE(cba.finding, bfa.finding) "
					+ "    WHEN r.name IN ('ROLE_AUDITOR_BFA', 'ROLE_BRANCHM_BFA') THEN bfa.finding " + "    ELSE NULL "
					+ "  END) AS audit_finding, "

					+ "	ceb.variation as variation, " + "	ceb.period as period, "
					+ "	ceb.budget_per_plan as budget_per_plan, " + "	ceb.actual_balance as actual_balance, "
					+ "	ceb.cash_type as cash_type, "
					+ "	ceb.fcy as fcy, "
					+ "	cet.name as expense_type, "

					+ "	dbo._StripHTML( CASE "
					+ "    WHEN r.name IN ('ROLE_APPROVER_BFA',  'ROLE_COMPILER_BFA') THEN COALESCE(cra.impact, cba.impact, bfa.impact) "
					+ "    WHEN r.name IN ('ROLE_REVIEWER_BFA', 'ROLE_REGIONALD_BFA') THEN COALESCE(cba.impact, bfa.impact) "
					+ "    WHEN r.name IN ('ROLE_AUDITOR_BFA', 'ROLE_BRANCHM_BFA') THEN bfa.impact " + "    ELSE NULL "
					+ "  END) AS audit_impact, " + "	dbo._StripHTML( CASE "
					+ "    WHEN r.name IN ('ROLE_APPROVER_BFA',  'ROLE_COMPILER_BFA') THEN COALESCE(cra.recommendation, cba.recommendation, bfa.recommendation) "
					+ "    WHEN r.name IN ('ROLE_REVIEWER_BFA', 'ROLE_REGIONALD_BFA') THEN COALESCE(cba.recommendation, bfa.recommendation) "
					+ "    WHEN r.name IN ('ROLE_AUDITOR_BFA', 'ROLE_BRANCHM_BFA') THEN bfa.recommendation " + "    ELSE NULL "
					+ "  END) AS auditor_recommendation, " + "	dbo._StripHTML(bfa.action_plan) as auditee_response, "
					+ "	bfa.finding_status as audit_finding_status, "
					+ "	cast(bfa.rectification_date as date) as rectified_on").FROM("branch_financial_audit AS bfa")
					.LEFT_OUTER_JOIN("compiled_audits AS ca ON bfa.id = ca.audit_id")
					.LEFT_OUTER_JOIN("compiled_branch_audit AS cba ON bfa.id = ca.audit_id AND ca.compiled_id = cba.id")
					.LEFT_OUTER_JOIN("compiled_audits_region AS car ON car.audit_id = cba.id")
					.LEFT_OUTER_JOIN("compiled_regional_audit AS cra ON cra.id = car.compiled_id")
					.JOIN("controllable_expense_branch AS ceb ON bfa.id = ceb.branch_audit_id")
					.JOIN("controllable_expense_branch_mapping as cebm on ceb.id = cebm.controllable_branch_id")
					.JOIN("controllable_expense_type as cet on cebm.controllable_expense_type_id = cet.id")
					.JOIN("user_role AS ur ON ur.user_id = #{user_id}").JOIN("role AS r ON ur.role_id = r.id")
					.JOIN("branch as b on bfa.branch_id = b.id and bfa.id = ceb.branch_audit_id").WHERE("1 = 1");
			if (audit_finding != null)
				sql.AND().WHERE("dbo._StripHTML(bfa.finding) LIKE '%' + #{audit_finding} + '%'");
		} else if (single_filter_info.equalsIgnoreCase("date_range")) {
			sql.SELECT("bfa.id, " + "	cast(bfa.finding_date as date) as audit_report_date, "
					+ "	b.name as branch_name, " + "	dbo._StripHTML( CASE "
					+ "    WHEN r.name IN ('ROLE_APPROVER_BFA',  'ROLE_COMPILER_BFA') THEN COALESCE(cra.finding, cba.finding, bfa.finding) "
					+ "    WHEN r.name IN ('ROLE_REVIEWER_BFA', 'ROLE_REGIONALD_BFA') THEN COALESCE(cba.finding, bfa.finding) "
					+ "    WHEN r.name IN ('ROLE_AUDITOR_BFA', 'ROLE_BRANCHM_BFA') THEN bfa.finding " + "    ELSE NULL "
					+ "  END) AS audit_finding, "

					+ "	ceb.variation as variation, " + "	ceb.period as period, "
					+ "	ceb.budget_per_plan as budget_per_plan, " + "	ceb.actual_balance as actual_balance, "
					+ "	ceb.cash_type as cash_type, "
					+ "	ceb.fcy as fcy, "
					+ "	cet.name as expense_type, "

					+ "	dbo._StripHTML( CASE "
					+ "    WHEN r.name IN ('ROLE_APPROVER_BFA',  'ROLE_COMPILER_BFA') THEN COALESCE(cra.impact, cba.impact, bfa.impact) "
					+ "    WHEN r.name IN ('ROLE_REVIEWER_BFA', 'ROLE_REGIONALD_BFA') THEN COALESCE(cba.impact, bfa.impact) "
					+ "    WHEN r.name IN ('ROLE_AUDITOR_BFA', 'ROLE_BRANCHM_BFA') THEN bfa.impact " + "    ELSE NULL "
					+ "  END) AS audit_impact, " + "	dbo._StripHTML( CASE "
					+ "    WHEN r.name IN ('ROLE_APPROVER_BFA',  'ROLE_COMPILER_BFA') THEN COALESCE(cra.recommendation, cba.recommendation, bfa.recommendation) "
					+ "    WHEN r.name IN ('ROLE_REVIEWER_BFA', 'ROLE_REGIONALD_BFA') THEN COALESCE(cba.recommendation, bfa.recommendation) "
					+ "    WHEN r.name IN ('ROLE_AUDITOR_BFA', 'ROLE_BRANCHM_BFA') THEN bfa.recommendation " + "    ELSE NULL "
					+ "  END) AS auditor_recommendation, " + "	dbo._StripHTML(bfa.action_plan) as auditee_response, "
					+ "	bfa.finding_status as audit_finding_status, "
					+ "	cast(bfa.rectification_date as date) as rectified_on").FROM("branch_financial_audit AS bfa")
					.LEFT_OUTER_JOIN("compiled_audits AS ca ON bfa.id = ca.audit_id")
					.LEFT_OUTER_JOIN("compiled_branch_audit AS cba ON bfa.id = ca.audit_id AND ca.compiled_id = cba.id")
					.LEFT_OUTER_JOIN("compiled_audits_region AS car ON car.audit_id = cba.id")
					.LEFT_OUTER_JOIN("compiled_regional_audit AS cra ON cra.id = car.compiled_id")
					.JOIN("controllable_expense_branch AS ceb ON bfa.id = ceb.branch_audit_id")
					.JOIN("controllable_expense_branch_mapping as cebm on ceb.id = cebm.controllable_branch_id")
					.JOIN("controllable_expense_type as cet on cebm.controllable_expense_type_id = cet.id")
					.JOIN("user_role AS ur ON ur.user_id = #{user_id}").JOIN("role AS r ON ur.role_id = r.id")
					.JOIN("branch as b on bfa.branch_id = b.id and bfa.id = ceb.branch_audit_id").WHERE("1 = 1");
			if (!date_range[0].equalsIgnoreCase("none42e") && !date_range[1].equalsIgnoreCase("none42e")) {
				sql.AND()
						.WHERE("cast(bfa.finding_date as date) >= '"
								+ LocalDateTime.ofInstant(Instant.parse(date_range[0]), ZoneId.of("UTC")).plusDays(1)
										.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
								+ "'");
				sql.AND()
						.WHERE("cast(bfa.finding_date as date) <= '"
								+ LocalDateTime.ofInstant(Instant.parse(date_range[1]), ZoneId.of("UTC")).plusDays(1)
										.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
								+ "'");
			} else if (!date_range[0].equalsIgnoreCase("none42e") && date_range[1].equalsIgnoreCase("none42e"))
				sql.AND()
						.WHERE("cast(bfa.finding_date as date) = '"
								+ LocalDateTime.ofInstant(Instant.parse(date_range[0]), ZoneId.of("UTC")).plusDays(1)
										.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
								+ "'");
		} else if (single_filter_info.equalsIgnoreCase("amount")) {
			sql.SELECT("	bfa.id, " + "	re.name as region_name, " + "	dbo._StripHTML( CASE "
					+ "    WHEN r.name IN ('ROLE_APPROVER_BFA',  'ROLE_COMPILER_BFA') THEN COALESCE(cra.finding, cba.finding, bfa.finding) "
					+ "    WHEN r.name IN ('ROLE_REVIEWER_BFA', 'ROLE_REGIONALD_BFA') THEN COALESCE(cba.finding, bfa.finding) "
					+ "    WHEN r.name IN ('ROLE_AUDITOR_BFA', 'ROLE_BRANCHM_BFA') THEN bfa.finding " + "    ELSE NULL "
					+ "  END) AS audit_finding, "

					+ "	ceb.variation as variation, " + "	ceb.period as period, "
					+ "	ceb.budget_per_plan as budget_per_plan, " + "	ceb.actual_balance as actual_balance, "
					+ "	ceb.cash_type as cash_type, "
					+ "	ceb.fcy as fcy, "
					+ "	cet.name as expense_type, "

					+ "	dbo._StripHTML( CASE "
					+ "    WHEN r.name IN ('ROLE_APPROVER_BFA',  'ROLE_COMPILER_BFA') THEN COALESCE(cra.impact, cba.impact, bfa.impact) "
					+ "    WHEN r.name IN ('ROLE_REVIEWER_BFA', 'ROLE_REGIONALD_BFA') THEN COALESCE(cba.impact, bfa.impact) "
					+ "    WHEN r.name IN ('ROLE_AUDITOR_BFA', 'ROLE_BRANCHM_BFA') THEN bfa.impact " + "    ELSE NULL "
					+ "  END) AS audit_impact, " + "	dbo._StripHTML( CASE "
					+ "    WHEN r.name IN ('ROLE_APPROVER_BFA',  'ROLE_COMPILER_BFA') THEN COALESCE(cra.recommendation, cba.recommendation, bfa.recommendation) "
					+ "    WHEN r.name IN ('ROLE_REVIEWER_BFA', 'ROLE_REGIONALD_BFA') THEN COALESCE(cba.recommendation, bfa.recommendation) "
					+ "    WHEN r.name IN ('ROLE_AUDITOR_BFA', 'ROLE_BRANCHM_BFA') THEN bfa.recommendation " + "    ELSE NULL "
					+ "  END) AS auditor_recommendation, " + "	dbo._StripHTML(bfa.action_plan) as auditee_response, "
					+ "	bfa.finding_status as audit_finding_status, "
					+ "	cast(bfa.rectification_date as date) as rectified_on").FROM("branch_financial_audit AS bfa")
					.LEFT_OUTER_JOIN("compiled_audits AS ca ON bfa.id = ca.audit_id")
					.LEFT_OUTER_JOIN("compiled_branch_audit AS cba ON bfa.id = ca.audit_id AND ca.compiled_id = cba.id")
					.LEFT_OUTER_JOIN("compiled_audits_region AS car ON car.audit_id = cba.id")
					.LEFT_OUTER_JOIN("compiled_regional_audit AS cra ON cra.id = car.compiled_id")

					.JOIN("controllable_expense_branch AS ceb ON bfa.id = ceb.branch_audit_id")
					.JOIN("controllable_expense_branch_mapping as cebm on ceb.id = cebm.controllable_branch_id")
					.JOIN("controllable_expense_type as cet on cebm.controllable_expense_type_id = cet.id")
					.JOIN("user_role AS ur ON ur.user_id = #{user_id}").JOIN("role AS r ON ur.role_id = r.id")
					// .JOIN("operational_descripancy_category AS odc ON
					// acb.operational_descripancy_category_id = odc.id")

					.JOIN("branch as b on bfa.branch_id = b.id")
					.JOIN("region as re on bfa.branch_id = b.id and b.region_id = re.id").WHERE("1 = 1");
//			if (region_id != null)
//				sql.AND().WHERE("b.region_id = #{region_id}");

			if (variation_min != null)
				sql.AND().WHERE("ceb.variation >= #{variation_min} ");
			if (variation_max != null)
				sql.AND().WHERE("ceb.variation <= #{variation_max} ");
			if (budget_per_plan_min != null)
				sql.AND().WHERE("ceb.budget_per_plan >= #{budget_per_plan_min} ");
			if (budget_per_plan_max != null)
				sql.AND().WHERE("ceb.budget_per_plan <= #{budget_per_plan_max} ");
			if (actual_balance_min != null)
				sql.AND().WHERE("ceb.actual_balance >= #{actual_balance_min} ");
			if (actual_balance_max != null)
				sql.AND().WHERE("ceb.actual_balance <= #{actual_balance_max} ");

		} else {
//			sql.SELECT("bfa.id, " + "	b.name as branch_name, " + "	dbo._StripHTML( CASE "
//					+ "    WHEN r.name IN ('ROLE_APPROVER_BFA',  'ROLE_COMPILER_BFA') THEN COALESCE(cra.finding, cba.finding, bfa.finding) "
//					+ "    WHEN r.name IN ('ROLE_REVIEWER_BFA', 'ROLE_REGIONALD_BFA') THEN COALESCE(cba.finding, bfa.finding) "
//					+ "    WHEN r.name IN ('ROLE_AUDITOR_BFA', 'ROLE_BRANCHM_BFA') THEN bfa.finding " + "    ELSE NULL "
//					+ "  END) AS audit_finding, "
//
//					+ "	ceb.variation as variation, " + "	ceb.period as period, "
//					+ "	ceb.budget_per_plan as budget_per_plan, " + "	ceb.actual_balance as actual_balance, "
//
//					+ "	dbo._StripHTML( CASE "
//					+ "    WHEN r.name IN ('ROLE_APPROVER_BFA',  'ROLE_COMPILER_BFA') THEN COALESCE(cra.impact, cba.impact, bfa.impact) "
//					+ "    WHEN r.name IN ('ROLE_REVIEWER_BFA', 'ROLE_REGIONALD_BFA') THEN COALESCE(cba.impact, bfa.impact) "
//					+ "    WHEN r.name IN ('ROLE_AUDITOR_BFA', 'ROLE_BRANCHM_BFA') THEN bfa.impact " + "    ELSE NULL "
//					+ "  END) AS audit_impact, " + "	dbo._StripHTML( CASE "
//					+ "    WHEN r.name IN ('ROLE_APPROVER_BFA',  'ROLE_COMPILER_BFA') THEN COALESCE(cra.recommendation, cba.recommendation, bfa.recommendation) "
//					+ "    WHEN r.name IN ('ROLE_REVIEWER_BFA', 'ROLE_REGIONALD_BFA') THEN COALESCE(cba.recommendation, bfa.recommendation) "
//					+ "    WHEN r.name IN ('ROLE_AUDITOR_BFA', 'ROLE_BRANCHM_BFA') THEN bfa.recommendation " + "    ELSE NULL "
//					+ "  END) AS auditor_recommendation, " + "	dbo._StripHTML(bfa.action_plan) as auditee_response, "
//					+ "	bfa.case_number, " + "	bfa.finding_status as audit_finding_status, "
//					+ "	cast (bfa.rectification_date as date) as rectified_on, "
//					+ "	cast (bfa.finding_date as date) as audit_report_date").FROM("branch_financial_audit AS bfa")
//					.LEFT_OUTER_JOIN("compiled_audits AS ca ON bfa.id = ca.audit_id")
//					.LEFT_OUTER_JOIN("compiled_branch_audit AS cba ON bfa.id = ca.audit_id AND ca.compiled_id = cba.id")
//					.LEFT_OUTER_JOIN("compiled_audits_region AS car ON car.audit_id = cba.id")
//					.LEFT_OUTER_JOIN("compiled_regional_audit AS cra ON cra.id = car.compiled_id")
//					.JOIN("controllable_expense_branch AS ceb ON bfa.id = ceb.branch_audit_id")
//					.JOIN("user_role AS ur ON ur.user_id = #{user_id}").JOIN("role AS r ON ur.role_id = r.id")
//					.JOIN("branch as b on bfa.branch_id = b.id and bfa.id = ceb.branch_audit_id").WHERE("1 = 1");

			sql.SELECT("bfa.id, " + "	b.name as branch_name, " + "	dbo._StripHTML( CASE "
					+ "    WHEN r.name IN ('ROLE_APPROVER_BFA',  'ROLE_COMPILER_BFA') THEN COALESCE(cra.finding, cba.finding, bfa.finding) "
					+ "    WHEN r.name IN ('ROLE_REVIEWER_BFA', 'ROLE_REGIONALD_BFA') THEN COALESCE(cba.finding, bfa.finding) "
					+ "    WHEN r.name IN ('ROLE_AUDITOR_BFA', 'ROLE_BRANCHM_BFA') THEN bfa.finding " + "    ELSE NULL "
					+ "  END) AS audit_finding, "

					+ "	ceb.variation as variation, " + "	ceb.period as period, "
					+ "	ceb.budget_per_plan as budget_per_plan, " + "	ceb.actual_balance as actual_balance, "
					+ "	ceb.cash_type as cash_type, "
					+ "	ceb.fcy as fcy, "
					+ "	cet.name as expense_type, "

					+ "	dbo._StripHTML( CASE "
					+ "    WHEN r.name IN ('ROLE_APPROVER_BFA',  'ROLE_COMPILER_BFA') THEN COALESCE(cra.impact, cba.impact, bfa.impact) "
					+ "    WHEN r.name IN ('ROLE_REVIEWER_BFA', 'ROLE_REGIONALD_BFA') THEN COALESCE(cba.impact, bfa.impact) "
					+ "    WHEN r.name IN ('ROLE_AUDITOR_BFA', 'ROLE_BRANCHM_BFA') THEN bfa.impact " + "    ELSE NULL "
					+ "  END) AS audit_impact, " + "	dbo._StripHTML( CASE "
					+ "    WHEN r.name IN ('ROLE_APPROVER_BFA',  'ROLE_COMPILER_BFA') THEN COALESCE(cra.recommendation, cba.recommendation, bfa.recommendation) "
					+ "    WHEN r.name IN ('ROLE_REVIEWER_BFA', 'ROLE_REGIONALD_BFA') THEN COALESCE(cba.recommendation, bfa.recommendation) "
					+ "    WHEN r.name IN ('ROLE_AUDITOR_BFA', 'ROLE_BRANCHM_BFA') THEN bfa.recommendation " + "    ELSE NULL "
					+ "  END) AS auditor_recommendation, " + "	dbo._StripHTML(bfa.action_plan) as auditee_response, "
					+ "	bfa.case_number, " + "	bfa.finding_status as audit_finding_status, "
					+ "	cast (bfa.rectification_date as date) as rectified_on, "
					+ "	cast (bfa.finding_date as date) as audit_report_date").FROM("branch_financial_audit AS bfa")
					.LEFT_OUTER_JOIN("compiled_audits AS ca ON bfa.id = ca.audit_id")
					.LEFT_OUTER_JOIN("compiled_branch_audit AS cba ON bfa.id = ca.audit_id AND ca.compiled_id = cba.id")
					.LEFT_OUTER_JOIN("compiled_audits_region AS car ON car.audit_id = cba.id")
					.LEFT_OUTER_JOIN("compiled_regional_audit AS cra ON cra.id = car.compiled_id")
					.JOIN("controllable_expense_branch AS ceb ON bfa.id = ceb.branch_audit_id")
					.JOIN("user_role AS ur ON ur.user_id = #{user_id}").JOIN("role AS r ON ur.role_id = r.id")
					.JOIN("branch as b on bfa.branch_id = b.id and bfa.id = ceb.branch_audit_id")
					.JOIN("controllable_expense_branch_mapping as cebm on ceb.id = cebm.controllable_branch_id")
					.JOIN("controllable_expense_type as cet on cebm.controllable_expense_type_id = cet.id")
					.WHERE("1 = 1");

			if (cash_type != null)
				sql.AND().WHERE("ceb.cash_type = #{cash_type}");

			if (region_id != null)
				sql.AND().WHERE("b.region_id = #{region_id}");
			if (branch_id != null)
				sql.AND().WHERE("b.id = #{branch_id}");
			if (finding_status != null)
				sql.AND().WHERE("bfa.finding_status = #{finding_status}");
			if (ing != null)
				sql.AND().WHERE("bfa.ing = #{ing}");
			if (!rectification_date_range[0].equalsIgnoreCase("none42e")
					&& !rectification_date_range[1].equalsIgnoreCase("none42e")) {
				sql.AND()
						.WHERE("cast(bfa.rectification_date as date) >= '"
								+ LocalDateTime.ofInstant(Instant.parse(rectification_date_range[0]), ZoneId.of("UTC"))
										.plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
								+ "'");
				sql.AND()
						.WHERE("cast(bfa.rectification_date as date) <= '"
								+ LocalDateTime.ofInstant(Instant.parse(rectification_date_range[1]), ZoneId.of("UTC"))
										.plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
								+ "'");
			} else if (!rectification_date_range[0].equalsIgnoreCase("none42e")
					&& rectification_date_range[1].equalsIgnoreCase("none42e"))
				sql.AND()
						.WHERE("cast(bfa.rectification_date as date) = '"
								+ LocalDateTime.ofInstant(Instant.parse(rectification_date_range[0]), ZoneId.of("UTC"))
										.plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
								+ "'");
        //======================================================================================
			
			if (!regional_compiler_compiled_date[0].equalsIgnoreCase("none42e")
					&& !regional_compiler_compiled_date[1].equalsIgnoreCase("none42e")) {
				sql.AND()
						.WHERE("cast(bfa.regional_compiler_compiled_date as date) >= '"
								+ LocalDateTime.ofInstant(Instant.parse(regional_compiler_compiled_date[0]), ZoneId.of("UTC"))
										.plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
								+ "'");
				sql.AND()
						.WHERE("cast(bfa.regional_compiler_compiled_date as date) <= '"
								+ LocalDateTime.ofInstant(Instant.parse(regional_compiler_compiled_date[1]), ZoneId.of("UTC"))
										.plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
								+ "'");
			} else if (!regional_compiler_compiled_date[0].equalsIgnoreCase("none42e")
					&& regional_compiler_compiled_date[1].equalsIgnoreCase("none42e"))
				sql.AND()
						.WHERE("cast(bfa.regional_compiler_compiled_date as date) = '"
								+ LocalDateTime.ofInstant(Instant.parse(regional_compiler_compiled_date[0]), ZoneId.of("UTC"))
										.plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
								+ "'");
			//====================
			if (!division_compiler_compiled_date[0].equalsIgnoreCase("none42e")
					&& !division_compiler_compiled_date[1].equalsIgnoreCase("none42e")) {
				sql.AND()
						.WHERE("cast(bfa.division_compiler_compiled_date as date) >= '"
								+ LocalDateTime.ofInstant(Instant.parse(division_compiler_compiled_date[0]), ZoneId.of("UTC"))
										.plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
								+ "'");
				sql.AND()
						.WHERE("cast(bfa.division_compiler_compiled_date as date) <= '"
								+ LocalDateTime.ofInstant(Instant.parse(division_compiler_compiled_date[1]), ZoneId.of("UTC"))
										.plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
								+ "'");
			} else if (!division_compiler_compiled_date[0].equalsIgnoreCase("none42e")
					&& division_compiler_compiled_date[1].equalsIgnoreCase("none42e"))
				sql.AND()
						.WHERE("cast(bfa.division_compiler_compiled_date as date) = '"
								+ LocalDateTime.ofInstant(Instant.parse(division_compiler_compiled_date[0]), ZoneId.of("UTC"))
										.plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
								+ "'");
			//==================
			
			if (!approved_date[0].equalsIgnoreCase("none42e")
					&& !approved_date[1].equalsIgnoreCase("none42e")) {
				sql.AND()
						.WHERE("cast(bfa.approved_date as date) >= '"
								+ LocalDateTime.ofInstant(Instant.parse(approved_date[0]), ZoneId.of("UTC"))
										.plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
								+ "'");
				sql.AND()
						.WHERE("cast(bfa.approved_date as date) <= '"
								+ LocalDateTime.ofInstant(Instant.parse(approved_date[1]), ZoneId.of("UTC"))
										.plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
								+ "'");
			} else if (!approved_date[0].equalsIgnoreCase("none42e")
					&& approved_date[1].equalsIgnoreCase("none42e"))
				sql.AND()
						.WHERE("cast(bfa.approved_date as date) = '"
								+ LocalDateTime.ofInstant(Instant.parse(approved_date[0]), ZoneId.of("UTC"))
										.plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
								+ "'");
			
			
			//========================================================================================
			
			if (audit_finding != null)
				sql.AND().WHERE("dbo._StripHTML(bfa.finding) LIKE '%' + #{audit_finding} + '%'");
			if (!date_range[0].equalsIgnoreCase("none42e") && !date_range[1].equalsIgnoreCase("none42e")) {
				sql.AND()
						.WHERE("cast(bfa.finding_date as date) >= '"
								+ LocalDateTime.ofInstant(Instant.parse(date_range[0]), ZoneId.of("UTC")).plusDays(1)
										.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
								+ "'");
				sql.AND()
						.WHERE("cast(bfa.finding_date as date) <= '"
								+ LocalDateTime.ofInstant(Instant.parse(date_range[1]), ZoneId.of("UTC")).plusDays(1)
										.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
								+ "'");
			} else if (!date_range[0].equalsIgnoreCase("none42e") && date_range[1].equalsIgnoreCase("none42e"))
				sql.AND()
						.WHERE("cast(bfa.finding_date as date) = '"
								+ LocalDateTime.ofInstant(Instant.parse(date_range[0]), ZoneId.of("UTC")).plusDays(1)
										.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
								+ "'");
			if (rectification_status != null)
				sql.AND().WHERE(
						"bfa.rectification_status = '" + (rectification_status.equalsIgnoreCase("unrectified") ? 0 : 1) + "'");

			if (variation_min != null)
				sql.AND().WHERE("ceb.variation >= #{variation_min} ");
			if (variation_max != null)
				sql.AND().WHERE("ceb.variation <= #{variation_max} ");
			if (budget_per_plan_min != null)
				sql.AND().WHERE("ceb.budget_per_plan >= #{budget_per_plan_min} ");
			if (budget_per_plan_max != null)
				sql.AND().WHERE("ceb.budget_per_plan <= #{budget_per_plan_max} ");
			if (actual_balance_min != null)
				sql.AND().WHERE("ceb.actual_balance >= #{actual_balance_min} ");
			if (actual_balance_max != null)
				sql.AND().WHERE("ceb.actual_balance <= #{actual_balance_max} ");

			if (controllable_expense_type != null && controllable_expense_type.trim() != "")
				sql.AND().WHERE("cet.name = #{controllable_expense_type}");
		}
		if (Arrays.asList(user_roles).contains("ROLE_AUDITOR_BFA"))
			sql.AND().WHERE("bfa.auditor_id = #{user_id}");

		if (Arrays.asList(user_roles).contains("ROLE_BRANCHM_BFA")) {
			sql.AND().WHERE("bfa.auditor_status = 1");
			sql.AND().WHERE("bfa.branch_id = (select top 1 branch_id from [user] where id =  #{user_id})");
		}
		if (Arrays.asList(user_roles).contains("ROLE_REGIONALD_BFA"))
			sql.AND().WHERE("bfa.regional_compiler_submitted = 1");

		if (Arrays.asList(user_roles).contains("ROLE_REVIEWER_BFA")) {
			sql.AND().WHERE(
					"((bfa.review_status = 0 and b.region_id = #{user_region_id}) or (bfa.review_status = 1 and bfa.reviewer_id = #{user_id}))");
		}

		if (Arrays.asList(user_roles).contains("ROLE_REVIEWER_BFA")
				|| Arrays.asList(user_roles).contains("ROLE_REGIONALD_BFA")) {
			sql.AND().WHERE("b.region_id = #{user_region_id}");
			sql.AND().WHERE("bfa.auditor_status = 1");
		}
		if (Arrays.asList(user_roles).contains("ROLE_COMPILER_BFA")){
			sql.AND().WHERE("bfa.regional_compiler_submitted = 1");
			sql.AND().WHERE(
					"(bfa.division_compiler_review_status = 0 or (bfa.division_compiler_review_status = 1 and bfa.division_compiler_id = #{user_id}))");
		}
		if (Arrays.asList(user_roles).contains("ROLE_APPROVER_BFA"))
			sql.AND().WHERE("bfa.division_compiler_submitted = 1");

		sql.AND().WHERE("bfa.status = 1");

		System.out.println("total sql: " + sql.toString());
		return sql.toString();

	}
	/*
	 * 'ROLE_AUDITOR_BFA', 'ROLE_APPROVER_BFA', 'ROLE_REVIEWER_BFA', 'ROLE_COMPILER_BFA',
	 * 'ROLE_BRANCHM_BFA', 'ROLE_REGIONALD_BFA',
	 */
}
