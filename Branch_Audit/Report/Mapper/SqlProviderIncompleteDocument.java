package com.afr.fms.Branch_Audit.Report.Mapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class SqlProviderIncompleteDocument {

	public String getBranchAuditReportIncompleteDocument(@Param("region_id") Long region_id,
			@Param("branch_id") Long branch_id, @Param("finding_status") String finding_status,
			@Param("rectification_date_range") String[] rectification_date_range,
			@Param("date_range") String[] date_range, @Param("audit_finding") String audit_finding,

			@Param("account_opened_date_range") String[] account_opened_date_range,
			@Param("account_opened_amount_max") Double account_opened_amount_max,
			@Param("account_opened_amount_min") Double account_opened_amount_min,
			@Param("account_number") String account_number,

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
					!account_opened_date_range[0].equalsIgnoreCase("none42e")
					|| account_number != null || ing != null || cash_type != null)
				single_filter_info = "general";

		SQL sql = new SQL();
		if (single_filter_info.equalsIgnoreCase("branch")) {
			sql.SELECT("	bfa.id, " + "	bfa.case_number, " + "	b.name as branch_name, "
					+ "	dbo._StripHTML( CASE "
					+ "    WHEN r.name IN ('ROLE_APPROVER_BFA',  'ROLE_COMPILER_BFA') THEN COALESCE(cra.finding, cba.finding, bfa.finding) "
					+ "    WHEN r.name IN ('ROLE_REVIEWER_BFA', 'ROLE_REGIONALD_BFA') THEN COALESCE(cba.finding, bfa.finding) "
					+ "    WHEN r.name IN ('ROLE_AUDITOR_BFA', 'ROLE_BRANCHM_BFA') THEN bfa.finding " + "    ELSE NULL "
					+ "  END) AS audit_finding, "

					+ "	aib.account_type as account_type, " + "	aib.customer_name as customer_name, "
					+ "	aib.account_number as account_number, " + "	aib.account_opened_date as account_opened_date, "
					+ "	aib.account_opened_amount as account_opened_amount, " + "	aib.opened_by as opened_by, "
					+ "	aib.approved_by as approved_by, "
					+ "	aib.account_type as cash_type, "
					+ "	aib.fcy as fcy, "

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
					.JOIN("incomplete_account_branch AS aib ON bfa.id = aib.branch_audit_id")
					.JOIN("user_role AS ur ON ur.user_id = #{user_id}").JOIN("role AS r ON ur.role_id = r.id")
					.JOIN("branch as b on bfa.branch_id = b.id and bfa.id = aib.branch_audit_id").WHERE("1 = 1");
			if (branch_id != null)
				sql.AND().WHERE("b.id = #{branch_id}");
		} else if (single_filter_info.equalsIgnoreCase("region")) {
			sql.SELECT("	bfa.id, " + "	re.name as region_name, " + "	dbo._StripHTML( CASE "
					+ "    WHEN r.name IN ('ROLE_APPROVER_BFA',  'ROLE_COMPILER_BFA') THEN COALESCE(cra.finding, cba.finding, bfa.finding) "
					+ "    WHEN r.name IN ('ROLE_REVIEWER_BFA', 'ROLE_REGIONALD_BFA') THEN COALESCE(cba.finding, bfa.finding) "
					+ "    WHEN r.name IN ('ROLE_AUDITOR_BFA', 'ROLE_BRANCHM_BFA') THEN bfa.finding " + "    ELSE NULL "
					+ "  END) AS audit_finding, "

					+ "	aib.account_type as account_type, " + "	aib.customer_name as customer_name, "
					+ "	aib.account_number as account_number, " + "	aib.account_opened_date as account_opened_date, "
					+ "	aib.account_opened_amount as account_opened_amount, " + "	aib.opened_by as opened_by, "
					+ "	aib.approved_by as approved_by, "
					+ "	aib.account_type as cash_type, "
					+ "	aib.fcy as fcy, "

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
					.JOIN("incomplete_account_branch AS aib ON bfa.id = aib.branch_audit_id")
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

					+ "	aib.account_type as account_type, " + "	aib.customer_name as customer_name, "
					+ "	aib.account_number as account_number, " + "	aib.account_opened_date as account_opened_date, "
					+ "	aib.account_opened_amount as account_opened_amount, " + "	aib.opened_by as opened_by, "
					+ "	aib.approved_by as approved_by, "
					+ "	aib.account_type as cash_type, "
					+ "	aib.fcy as fcy, "

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
					.JOIN("incomplete_account_branch AS aib ON bfa.id = aib.branch_audit_id")
					.JOIN("user_role AS ur ON ur.user_id = #{user_id}").JOIN("role AS r ON ur.role_id = r.id")
					.JOIN("branch as b on bfa.branch_id = b.id and bfa.id = aib.branch_audit_id").WHERE("1 = 1");
			if (finding_status != null)
				sql.AND().WHERE("bfa.finding_status = #{finding_status}");
		} else if (single_filter_info.equalsIgnoreCase("rectification_date_range")) {
			sql.SELECT("	bfa.id, " + " " + "	b.name as branch_name, " + "	dbo._StripHTML( CASE "
					+ "    WHEN r.name IN ('ROLE_APPROVER_BFA',  'ROLE_COMPILER_BFA') THEN COALESCE(cra.finding, cba.finding, bfa.finding) "
					+ "    WHEN r.name IN ('ROLE_REVIEWER_BFA', 'ROLE_REGIONALD_BFA') THEN COALESCE(cba.finding, bfa.finding) "
					+ "    WHEN r.name IN ('ROLE_AUDITOR_BFA', 'ROLE_BRANCHM_BFA') THEN bfa.finding " + "    ELSE NULL "
					+ "  END) AS audit_finding, "

					+ "	aib.account_type as account_type, " + "	aib.customer_name as customer_name, "
					+ "	aib.account_number as account_number, " + "	aib.account_opened_date as account_opened_date, "
					+ "	aib.account_opened_amount as account_opened_amount, " + "	aib.opened_by as opened_by, "
					+ "	aib.approved_by as approved_by, "
					+ "	aib.account_type as cash_type, "
					+ "	aib.fcy as fcy, "

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
					.JOIN("incomplete_account_branch AS aib ON bfa.id = aib.branch_audit_id")
					.JOIN("user_role AS ur ON ur.user_id = #{user_id}").JOIN("role AS r ON ur.role_id = r.id")
					.JOIN("branch as b on bfa.branch_id = b.id and bfa.id = aib.branch_audit_id").WHERE("1 = 1");
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

					+ "	aib.account_type as account_type, " + "	aib.customer_name as customer_name, "
					+ "	aib.account_number as account_number, " + "	aib.account_opened_date as account_opened_date, "
					+ "	aib.account_opened_amount as account_opened_amount, " + "	aib.opened_by as opened_by, "
					+ "	aib.approved_by as approved_by, "
					+ "	aib.account_type as cash_type, "
					+ "	aib.fcy as fcy, "

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
					.JOIN("incomplete_account_branch AS aib ON bfa.id = aib.branch_audit_id")
					.JOIN("user_role AS ur ON ur.user_id = #{user_id}").JOIN("role AS r ON ur.role_id = r.id")
					.JOIN("branch as b on bfa.branch_id = b.id and bfa.id = aib.branch_audit_id").WHERE("1 = 1");
			if (audit_finding != null)
				sql.AND().WHERE("dbo._StripHTML(bfa.finding) LIKE '%' + #{audit_finding} + '%'");
		} else if (single_filter_info.equalsIgnoreCase("date_range")) {
			sql.SELECT("bfa.id, " + "	cast(bfa.finding_date as date) as audit_report_date, "
					+ "	b.name as branch_name, " + "	dbo._StripHTML( CASE "
					+ "    WHEN r.name IN ('ROLE_APPROVER_BFA',  'ROLE_COMPILER_BFA') THEN COALESCE(cra.finding, cba.finding, bfa.finding) "
					+ "    WHEN r.name IN ('ROLE_REVIEWER_BFA', 'ROLE_REGIONALD_BFA') THEN COALESCE(cba.finding, bfa.finding) "
					+ "    WHEN r.name IN ('ROLE_AUDITOR_BFA', 'ROLE_BRANCHM_BFA') THEN bfa.finding " + "    ELSE NULL "
					+ "  END) AS audit_finding, "

					+ "	aib.account_type as account_type, " + "	aib.customer_name as customer_name, "
					+ "	aib.account_number as account_number, " + "	aib.account_opened_date as account_opened_date, "
					+ "	aib.account_opened_amount as account_opened_amount, " + "	aib.opened_by as opened_by, "
					+ "	aib.approved_by as approved_by, "
					+ "	aib.account_type as cash_type, "
					+ "	aib.fcy as fcy, "

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
					.JOIN("incomplete_account_branch AS aib ON bfa.id = aib.branch_audit_id")
					.JOIN("user_role AS ur ON ur.user_id = #{user_id}").JOIN("role AS r ON ur.role_id = r.id")
					.JOIN("branch as b on bfa.branch_id = b.id and bfa.id = aib.branch_audit_id").WHERE("1 = 1");
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
			sql.SELECT("	bfa.id, " + "	CAST(bfa.finding_date as date) as audit_report_date, b.name as branch_name, " + "	dbo._StripHTML( CASE "
					+ "    WHEN r.name IN ('ROLE_APPROVER_BFA',  'ROLE_COMPILER_BFA') THEN COALESCE(cra.finding, cba.finding, bfa.finding) "
					+ "    WHEN r.name IN ('ROLE_REVIEWER_BFA', 'ROLE_REGIONALD_BFA') THEN COALESCE(cba.finding, bfa.finding) "
					+ "    WHEN r.name IN ('ROLE_AUDITOR_BFA', 'ROLE_BRANCHM_BFA') THEN bfa.finding " + "    ELSE NULL "
					+ "  END) AS audit_finding, "

					+ "	aib.account_type as account_type, " + "	aib.customer_name as customer_name, "
					+ "	aib.account_number as account_number, " + "	aib.account_opened_date as account_opened_date, "
					+ "	aib.account_opened_amount as account_opened_amount, " + "	aib.opened_by as opened_by, "
					+ "	aib.approved_by as approved_by, "
					+ "	aib.account_type as cash_type, "
					+ "	aib.fcy as fcy, "

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

					.JOIN("incomplete_account_branch AS aib ON bfa.id = aib.branch_audit_id")
					.JOIN("user_role AS ur ON ur.user_id = #{user_id}").JOIN("role AS r ON ur.role_id = r.id")
					// .JOIN("operational_descripancy_category AS odc ON
					// acb.operational_descripancy_category_id = odc.id")

					.JOIN("branch as b on bfa.branch_id = b.id")
					.JOIN("region as re on bfa.branch_id = b.id and b.region_id = re.id").WHERE("1 = 1");
//			if (region_id != null)
//				sql.AND().WHERE("b.region_id = #{region_id}");

			if (account_opened_amount_min != null)
				sql.AND().WHERE("aib.account_opened_amount >= #{account_opened_amount_min} ");
			if (account_opened_amount_max != null)
				sql.AND().WHERE("aib.account_opened_amount <= #{account_opened_amount_max} ");

		} else {
			sql.SELECT("bfa.id, " + "	b.name as branch_name, " + "	dbo._StripHTML( CASE "
					+ "    WHEN r.name IN ('ROLE_APPROVER_BFA',  'ROLE_COMPILER_BFA') THEN COALESCE(cra.finding, cba.finding, bfa.finding) "
					+ "    WHEN r.name IN ('ROLE_REVIEWER_BFA', 'ROLE_REGIONALD_BFA') THEN COALESCE(cba.finding, bfa.finding) "
					+ "    WHEN r.name IN ('ROLE_AUDITOR_BFA', 'ROLE_BRANCHM_BFA') THEN bfa.finding " + "    ELSE NULL "
					+ "  END) AS audit_finding, "

					+ "	aib.account_type as account_type, " + "	aib.customer_name as customer_name, "
					+ "	aib.account_number as account_number, " + "	aib.account_opened_date as account_opened_date, "
					+ "	aib.account_opened_amount as account_opened_amount, " + "	aib.opened_by as opened_by, "
					+ "	aib.approved_by as approved_by, "
					+ "	aib.account_type as cash_type, "
					+ "	aib.fcy as fcy, "

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
					.JOIN("incomplete_account_branch AS aib ON bfa.id = aib.branch_audit_id")
					.JOIN("user_role AS ur ON ur.user_id = #{user_id}").JOIN("role AS r ON ur.role_id = r.id")
					.JOIN("branch as b on bfa.branch_id = b.id and bfa.id = aib.branch_audit_id").WHERE("1 = 1");

			if (cash_type != null)
				sql.AND().WHERE("aib.account_type = #{cash_type}");
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
 			if (account_opened_amount_min != null)
				sql.AND().WHERE("aib.account_opened_amount >= #{account_opened_amount_min} ");
			if (account_opened_amount_max != null)
				sql.AND().WHERE("aib.account_opened_amount <= #{account_opened_amount_max} ");
			if (account_number != null)
				sql.AND().WHERE("aib.account_number LIKE '%' + #{account_number} + '%'");

			if (!account_opened_date_range[0].equalsIgnoreCase("none42e")
					&& !account_opened_date_range[1].equalsIgnoreCase("none42e")) {
				sql.AND()
						.WHERE("cast(aib.account_opened_date as date) >= '"
								+ LocalDateTime.ofInstant(Instant.parse(account_opened_date_range[0]), ZoneId.of("UTC"))
										.plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
								+ "'");
				sql.AND()
						.WHERE("cast(aib.account_opened_date as date) <= '"
								+ LocalDateTime.ofInstant(Instant.parse(account_opened_date_range[1]), ZoneId.of("UTC"))
										.plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
								+ "'");
			} else if (!account_opened_date_range[0].equalsIgnoreCase("none42e")
					&& account_opened_date_range[1].equalsIgnoreCase("none42e"))
				sql.AND()
						.WHERE("cast(aib.account_opened_date as date) = '"
								+ LocalDateTime.ofInstant(Instant.parse(account_opened_date_range[0]), ZoneId.of("UTC"))
										.plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
								+ "'");
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
