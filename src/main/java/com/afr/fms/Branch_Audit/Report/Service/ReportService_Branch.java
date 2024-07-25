package com.afr.fms.Branch_Audit.Report.Service;

import com.afr.fms.Admin.Entity.Region;
import com.afr.fms.Branch_Audit.Report.Mapper.ReportMapper;
import com.afr.fms.Branch_Audit.Report.Model.Branch_R;
import com.afr.fms.Branch_Audit.Report.Model.DiscrepancyCategory_R;
import com.afr.fms.Branch_Audit.Report.Model.Finding_R;
import com.afr.fms.Branch_Audit.Report.Model.ReportBranch;
import com.afr.fms.Branch_Audit.Report.Model.ReportBranchRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService_Branch {

	@Autowired
	ReportMapper reportMapper;

	public List<ReportBranch> fetchReportAtmCard(ReportBranchRequest report_request) {
		if (report_request.getDate_range()[0] == null || report_request.getDate_range()[0].trim().equalsIgnoreCase(""))
			report_request.getDate_range()[0] = "none42e";
		if (report_request.getDate_range()[1] == null || report_request.getDate_range()[1].trim().equalsIgnoreCase(""))
			report_request.getDate_range()[1] = "none42e";
		if (report_request.getRectification_date_range()[0] == null
				|| report_request.getRectification_date_range()[0].trim().equalsIgnoreCase(""))
			report_request.getRectification_date_range()[0] = "none42e";
		if (report_request.getRectification_date_range()[1] == null
				|| report_request.getRectification_date_range()[0].trim().equalsIgnoreCase(""))
			report_request.getRectification_date_range()[1] = "none42e";
		
		if (report_request.getRegional_compiler_compiled_date()[0] == null
				|| report_request.getRegional_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getRegional_compiler_compiled_date()[0] = "none42e";
		if (report_request.getRegional_compiler_compiled_date()[1] == null
				|| report_request.getRegional_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getRegional_compiler_compiled_date()[1] = "none42e";
		
		if (report_request.getDivision_compiler_compiled_date()[0] == null
				|| report_request.getDivision_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getDivision_compiler_compiled_date()[0] = "none42e";
		if (report_request.getDivision_compiler_compiled_date()[1] == null
				|| report_request.getDivision_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getDivision_compiler_compiled_date()[1] = "none42e";
		
		if (report_request.getApproved_date()[0] == null
				|| report_request.getApproved_date()[0].trim().equalsIgnoreCase(""))
			report_request.getApproved_date()[0] = "none42e";
		if (report_request.getApproved_date()[1] == null
				|| report_request.getApproved_date()[0].trim().equalsIgnoreCase(""))
			report_request.getApproved_date()[1] = "none42e";
		
		for (int i = 0; i < report_request.getRegional_compiler_compiled_date().length; i++) {
 			System.out.println("regional compiler date" + report_request.getRegional_compiler_compiled_date()[i]);
		}
		for (int i = 0; i < report_request.getDivision_compiler_compiled_date().length; i++) {
 			System.out.println("division compiler date" + report_request.getDivision_compiler_compiled_date()[i]);
		}
		for (int i = 0; i < report_request.getApproved_date().length; i++) {
 			System.out.println("approve date" + report_request.getApproved_date()[i]);
		}

		List<ReportBranch> aa = reportMapper.getBranchAuditReportAtmCard(report_request.getRegion(),
				report_request.getBranch(), report_request.getFinding_status(),
				report_request.getRectification_date_range(), report_request.getDate_range(),
				report_request.getAudit_finding(), report_request.getCard_distributed_to_customer_min(),
				report_request.getCard_distributed_to_customer_max(), report_request.getCard_issued_branch_min(),
				report_request.getCard_issued_branch_max(), report_request.getRectification_status(),
				report_request.getSingle_filter_info(), report_request.getUser_id(), report_request.getUser_roles(),
				report_request.getUser_region_id(), report_request.getBanking(), 
				report_request.getRegional_compiler_compiled_date(),
				report_request.getDivision_compiler_compiled_date(), report_request.getApproved_date());
		return aa;
	}

	public List<ReportBranch> fetchReportAbnormalBalance(ReportBranchRequest report_request) {
		if (report_request.getDate_range()[0] == null || report_request.getDate_range()[0].trim().equalsIgnoreCase(""))
			report_request.getDate_range()[0] = "none42e";
		if (report_request.getDate_range()[1] == null || report_request.getDate_range()[1].trim().equalsIgnoreCase(""))
			report_request.getDate_range()[1] = "none42e";
		if (report_request.getRectification_date_range()[0] == null
				|| report_request.getRectification_date_range()[0].trim().equalsIgnoreCase(""))
			report_request.getRectification_date_range()[0] = "none42e";
		if (report_request.getRectification_date_range()[1] == null
				|| report_request.getRectification_date_range()[0].trim().equalsIgnoreCase(""))
			report_request.getRectification_date_range()[1] = "none42e";
		
		if (report_request.getRegional_compiler_compiled_date()[0] == null
				|| report_request.getRegional_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getRegional_compiler_compiled_date()[0] = "none42e";
		if (report_request.getRegional_compiler_compiled_date()[1] == null
				|| report_request.getRegional_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getRegional_compiler_compiled_date()[1] = "none42e";
		
		if (report_request.getDivision_compiler_compiled_date()[0] == null
				|| report_request.getDivision_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getDivision_compiler_compiled_date()[0] = "none42e";
		if (report_request.getDivision_compiler_compiled_date()[1] == null
				|| report_request.getDivision_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getDivision_compiler_compiled_date()[1] = "none42e";
		
		if (report_request.getApproved_date()[0] == null
				|| report_request.getApproved_date()[0].trim().equalsIgnoreCase(""))
			report_request.getApproved_date()[0] = "none42e";
		if (report_request.getApproved_date()[1] == null
				|| report_request.getApproved_date()[0].trim().equalsIgnoreCase(""))
			report_request.getApproved_date()[1] = "none42e";
		
		for (int i = 0; i < report_request.getRegional_compiler_compiled_date().length; i++) {
 			System.out.println("regional compiler date" + report_request.getRegional_compiler_compiled_date()[i]);
		}
		for (int i = 0; i < report_request.getDivision_compiler_compiled_date().length; i++) {
 			System.out.println("division compiler date" + report_request.getDivision_compiler_compiled_date()[i]);
		}
		for (int i = 0; i < report_request.getApproved_date().length; i++) {
 			System.out.println("approve date" + report_request.getApproved_date()[i]);
		}

		List<ReportBranch> aa = reportMapper.getBranchAuditReportAbnormalBalance(report_request.getRegion(),
				report_request.getBranch(), report_request.getFinding_status(),
				report_request.getRectification_date_range(), report_request.getDate_range(),
				report_request.getAudit_finding(), report_request.getRectification_status(),
				report_request.getSingle_filter_info(), report_request.getUser_id(), report_request.getUser_roles(),
				report_request.getUser_region_id(),
				// AbnormalBalance
				report_request.getAccount_affected(), report_request.getCredit_amount_min(),
				report_request.getDebit_amount_min(), report_request.getCredit_amount_max(),
				report_request.getDebit_amount_max(), report_request.getBanking(), report_request.getCash_type(), 
				report_request.getRegional_compiler_compiled_date(),
				report_request.getDivision_compiler_compiled_date(), report_request.getApproved_date());
		return aa;
	}

	/////////////////////////

	public List<ReportBranch> fetchReportIncompleteDocument(ReportBranchRequest report_request) {
		if (report_request.getDate_range()[0] == null || report_request.getDate_range()[0].trim().equalsIgnoreCase(""))
			report_request.getDate_range()[0] = "none42e";
		if (report_request.getDate_range()[1] == null || report_request.getDate_range()[1].trim().equalsIgnoreCase(""))
			report_request.getDate_range()[1] = "none42e";
		if (report_request.getRectification_date_range()[0] == null
				|| report_request.getRectification_date_range()[0].trim().equalsIgnoreCase(""))
			report_request.getRectification_date_range()[0] = "none42e";
		if (report_request.getRectification_date_range()[1] == null
				|| report_request.getRectification_date_range()[0].trim().equalsIgnoreCase(""))
			report_request.getRectification_date_range()[1] = "none42e";
		// IncompleteDocument
		if (report_request.getAccount_opened_date_range()[0] == null
				|| report_request.getAccount_opened_date_range()[0].trim().equalsIgnoreCase(""))
			report_request.getAccount_opened_date_range()[0] = "none42e";
		if (report_request.getAccount_opened_date_range()[1] == null
				|| report_request.getAccount_opened_date_range()[1].trim().equalsIgnoreCase(""))
			report_request.getAccount_opened_date_range()[1] = "none42e";
		
		if (report_request.getRegional_compiler_compiled_date()[0] == null
				|| report_request.getRegional_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getRegional_compiler_compiled_date()[0] = "none42e";
		if (report_request.getRegional_compiler_compiled_date()[1] == null
				|| report_request.getRegional_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getRegional_compiler_compiled_date()[1] = "none42e";
		
		if (report_request.getDivision_compiler_compiled_date()[0] == null
				|| report_request.getDivision_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getDivision_compiler_compiled_date()[0] = "none42e";
		if (report_request.getDivision_compiler_compiled_date()[1] == null
				|| report_request.getDivision_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getDivision_compiler_compiled_date()[1] = "none42e";
		
		if (report_request.getApproved_date()[0] == null
				|| report_request.getApproved_date()[0].trim().equalsIgnoreCase(""))
			report_request.getApproved_date()[0] = "none42e";
		if (report_request.getApproved_date()[1] == null
				|| report_request.getApproved_date()[0].trim().equalsIgnoreCase(""))
			report_request.getApproved_date()[1] = "none42e";
		
		for (int i = 0; i < report_request.getRegional_compiler_compiled_date().length; i++) {
 			System.out.println("regional compiler date" + report_request.getRegional_compiler_compiled_date()[i]);
		}
		for (int i = 0; i < report_request.getDivision_compiler_compiled_date().length; i++) {
 			System.out.println("division compiler date" + report_request.getDivision_compiler_compiled_date()[i]);
		}
		for (int i = 0; i < report_request.getApproved_date().length; i++) {
 			System.out.println("approve date" + report_request.getApproved_date()[i]);
		}
		

		List<ReportBranch> aa = reportMapper.getBranchAuditReportIncompleteDocument(report_request.getRegion(),
				report_request.getBranch(), report_request.getFinding_status(),
				report_request.getRectification_date_range(), report_request.getDate_range(),
				report_request.getAudit_finding(), report_request.getRectification_status(),
				report_request.getSingle_filter_info(), report_request.getUser_id(), report_request.getUser_roles(),
				report_request.getUser_region_id(),
				// IncompleteDocument
				report_request.getAccount_opened_date_range(), report_request.getAccount_opened_amount_max(),
				report_request.getAccount_opened_amount_min(), report_request.getAccount_number(),
				report_request.getBanking(), report_request.getCash_type(), 
				report_request.getRegional_compiler_compiled_date(),
				report_request.getDivision_compiler_compiled_date(), report_request.getApproved_date());
		return aa;
	}

	public List<ReportBranch> fetchReportControllableExpense(ReportBranchRequest report_request) {
		if (report_request.getDate_range()[0] == null || report_request.getDate_range()[0].trim().equalsIgnoreCase(""))
			report_request.getDate_range()[0] = "none42e";
		if (report_request.getDate_range()[1] == null || report_request.getDate_range()[1].trim().equalsIgnoreCase(""))
			report_request.getDate_range()[1] = "none42e";
		if (report_request.getRectification_date_range()[0] == null
				|| report_request.getRectification_date_range()[0].trim().equalsIgnoreCase(""))
			report_request.getRectification_date_range()[0] = "none42e";
		if (report_request.getRectification_date_range()[1] == null
				|| report_request.getRectification_date_range()[0].trim().equalsIgnoreCase(""))
			report_request.getRectification_date_range()[1] = "none42e";
		
		if (report_request.getRegional_compiler_compiled_date()[0] == null
				|| report_request.getRegional_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getRegional_compiler_compiled_date()[0] = "none42e";
		if (report_request.getRegional_compiler_compiled_date()[1] == null
				|| report_request.getRegional_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getRegional_compiler_compiled_date()[1] = "none42e";
		
		if (report_request.getDivision_compiler_compiled_date()[0] == null
				|| report_request.getDivision_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getDivision_compiler_compiled_date()[0] = "none42e";
		if (report_request.getDivision_compiler_compiled_date()[1] == null
				|| report_request.getDivision_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getDivision_compiler_compiled_date()[1] = "none42e";
		
		if (report_request.getApproved_date()[0] == null
				|| report_request.getApproved_date()[0].trim().equalsIgnoreCase(""))
			report_request.getApproved_date()[0] = "none42e";
		if (report_request.getApproved_date()[1] == null
				|| report_request.getApproved_date()[0].trim().equalsIgnoreCase(""))
			report_request.getApproved_date()[1] = "none42e";
		
		for (int i = 0; i < report_request.getRegional_compiler_compiled_date().length; i++) {
 			System.out.println("regional compiler date" + report_request.getRegional_compiler_compiled_date()[i]);
		}
		for (int i = 0; i < report_request.getDivision_compiler_compiled_date().length; i++) {
 			System.out.println("division compiler date" + report_request.getDivision_compiler_compiled_date()[i]);
		}
		for (int i = 0; i < report_request.getApproved_date().length; i++) {
 			System.out.println("approve date" + report_request.getApproved_date()[i]);
		}
		

		List<ReportBranch> aa = reportMapper.getBranchAuditReportControllableExpense(report_request.getRegion(),
				report_request.getBranch(), report_request.getFinding_status(),
				report_request.getRectification_date_range(), report_request.getDate_range(),
				report_request.getAudit_finding(), report_request.getRectification_status(),
				report_request.getSingle_filter_info(), report_request.getUser_id(), report_request.getUser_roles(),
				report_request.getUser_region_id(),
				// ControllableExpense
				report_request.getVariation_min(), report_request.getVariation_max(),
				report_request.getBudget_per_plan_min(), report_request.getBudget_per_plan_max(),
				report_request.getActual_balance_min(), report_request.getActual_balance_max(),
				report_request.getControllable_expense_type(), report_request.getBanking(),
				report_request.getCash_type(), 
				report_request.getRegional_compiler_compiled_date(),
				report_request.getDivision_compiler_compiled_date(), report_request.getApproved_date());
		return aa;
	}

	public List<ReportBranch> fetchReportAssetLiability(ReportBranchRequest report_request) {
		if (report_request.getDate_range()[0] == null || report_request.getDate_range()[0].trim().equalsIgnoreCase(""))
			report_request.getDate_range()[0] = "none42e";
		if (report_request.getDate_range()[1] == null || report_request.getDate_range()[1].trim().equalsIgnoreCase(""))
			report_request.getDate_range()[1] = "none42e";
		if (report_request.getRectification_date_range()[0] == null
				|| report_request.getRectification_date_range()[0].trim().equalsIgnoreCase(""))
			report_request.getRectification_date_range()[0] = "none42e";
		if (report_request.getRectification_date_range()[1] == null
				|| report_request.getRectification_date_range()[0].trim().equalsIgnoreCase(""))
			report_request.getRectification_date_range()[1] = "none42e";
		if (report_request.getRegional_compiler_compiled_date()[0] == null
				|| report_request.getRegional_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getRegional_compiler_compiled_date()[0] = "none42e";
		if (report_request.getRegional_compiler_compiled_date()[1] == null
				|| report_request.getRegional_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getRegional_compiler_compiled_date()[1] = "none42e";
		
		if (report_request.getDivision_compiler_compiled_date()[0] == null
				|| report_request.getDivision_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getDivision_compiler_compiled_date()[0] = "none42e";
		if (report_request.getDivision_compiler_compiled_date()[1] == null
				|| report_request.getDivision_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getDivision_compiler_compiled_date()[1] = "none42e";
		
		if (report_request.getApproved_date()[0] == null
				|| report_request.getApproved_date()[0].trim().equalsIgnoreCase(""))
			report_request.getApproved_date()[0] = "none42e";
		if (report_request.getApproved_date()[1] == null
				|| report_request.getApproved_date()[0].trim().equalsIgnoreCase(""))
			report_request.getApproved_date()[1] = "none42e";
		
		for (int i = 0; i < report_request.getRegional_compiler_compiled_date().length; i++) {
 			System.out.println("regional compiler date" + report_request.getRegional_compiler_compiled_date()[i]);
		}
		for (int i = 0; i < report_request.getDivision_compiler_compiled_date().length; i++) {
 			System.out.println("division compiler date" + report_request.getDivision_compiler_compiled_date()[i]);
		}
		for (int i = 0; i < report_request.getApproved_date().length; i++) {
 			System.out.println("approve date" + report_request.getApproved_date()[i]);
		}
		

		List<ReportBranch> aa = reportMapper.getBranchAuditReportAssetLiability(report_request.getRegion(),
				report_request.getBranch(), report_request.getFinding_status(),
				report_request.getRectification_date_range(), report_request.getDate_range(),
				report_request.getAudit_finding(), report_request.getRectification_status(),
				report_request.getSingle_filter_info(), report_request.getUser_id(), report_request.getUser_roles(),
				report_request.getUser_region_id(),
				// AssetLiability
				report_request.getAsset_amount_min(), report_request.getAsset_amount_max(),
				report_request.getLiability_amount_min(), report_request.getLiability_amount_max(),
				report_request.getBanking(), report_request.getCash_type(), 
				report_request.getRegional_compiler_compiled_date(),
				report_request.getDivision_compiler_compiled_date(), report_request.getApproved_date());
		return aa;
	}

	public List<ReportBranch> fetchReportDormantInactiveAccount(ReportBranchRequest report_request) {
		if (report_request.getDate_range()[0] == null || report_request.getDate_range()[0].trim().equalsIgnoreCase(""))
			report_request.getDate_range()[0] = "none42e";
		if (report_request.getDate_range()[1] == null || report_request.getDate_range()[1].trim().equalsIgnoreCase(""))
			report_request.getDate_range()[1] = "none42e";
		if (report_request.getRectification_date_range()[0] == null
				|| report_request.getRectification_date_range()[0].trim().equalsIgnoreCase(""))
			report_request.getRectification_date_range()[0] = "none42e";
		if (report_request.getRectification_date_range()[1] == null
				|| report_request.getRectification_date_range()[0].trim().equalsIgnoreCase(""))
			report_request.getRectification_date_range()[1] = "none42e";
		
		if (report_request.getRegional_compiler_compiled_date()[0] == null
				|| report_request.getRegional_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getRegional_compiler_compiled_date()[0] = "none42e";
		if (report_request.getRegional_compiler_compiled_date()[1] == null
				|| report_request.getRegional_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getRegional_compiler_compiled_date()[1] = "none42e";
		
		if (report_request.getDivision_compiler_compiled_date()[0] == null
				|| report_request.getDivision_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getDivision_compiler_compiled_date()[0] = "none42e";
		if (report_request.getDivision_compiler_compiled_date()[1] == null
				|| report_request.getDivision_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getDivision_compiler_compiled_date()[1] = "none42e";
		
		if (report_request.getApproved_date()[0] == null
				|| report_request.getApproved_date()[0].trim().equalsIgnoreCase(""))
			report_request.getApproved_date()[0] = "none42e";
		if (report_request.getApproved_date()[1] == null
				|| report_request.getApproved_date()[0].trim().equalsIgnoreCase(""))
			report_request.getApproved_date()[1] = "none42e";
		
		for (int i = 0; i < report_request.getRegional_compiler_compiled_date().length; i++) {
 			System.out.println("regional compiler date" + report_request.getRegional_compiler_compiled_date()[i]);
		}
		for (int i = 0; i < report_request.getDivision_compiler_compiled_date().length; i++) {
 			System.out.println("division compiler date" + report_request.getDivision_compiler_compiled_date()[i]);
		}
		for (int i = 0; i < report_request.getApproved_date().length; i++) {
 			System.out.println("approve date" + report_request.getApproved_date()[i]);
		}
		

		List<ReportBranch> aa = reportMapper.getBranchAuditReportDormantInactiveAccount(report_request.getRegion(),
				report_request.getBranch(), report_request.getFinding_status(),
				report_request.getRectification_date_range(), report_request.getDate_range(),
				report_request.getAudit_finding(), report_request.getRectification_status(),
				report_request.getSingle_filter_info(), report_request.getUser_id(), report_request.getUser_roles(),
				report_request.getUser_region_id(),
				// DormantInactiveAccount
				report_request.getAccount_type(), report_request.getAmount_min(), report_request.getAmount_max(),
				report_request.getAccount_number(), report_request.getBanking(), report_request.getCash_type(), 
				report_request.getRegional_compiler_compiled_date(),
				report_request.getDivision_compiler_compiled_date(), report_request.getApproved_date());
		return aa;
	}

	public List<ReportBranch> fetchReportSuspenseAccount(ReportBranchRequest report_request) {
		if (report_request.getDate_range()[0] == null || report_request.getDate_range()[0].trim().equalsIgnoreCase(""))
			report_request.getDate_range()[0] = "none42e";
		if (report_request.getDate_range()[1] == null || report_request.getDate_range()[1].trim().equalsIgnoreCase(""))
			report_request.getDate_range()[1] = "none42e";
		if (report_request.getRectification_date_range()[0] == null
				|| report_request.getRectification_date_range()[0].trim().equalsIgnoreCase(""))
			report_request.getRectification_date_range()[0] = "none42e";
		if (report_request.getRectification_date_range()[1] == null
				|| report_request.getRectification_date_range()[0].trim().equalsIgnoreCase(""))
			report_request.getRectification_date_range()[1] = "none42e";
		// SuspenseAccount
		if (report_request.getTracer_date_range()[0] == null
				|| report_request.getTracer_date_range()[0].trim().equalsIgnoreCase(""))
			report_request.getTracer_date_range()[0] = "none42e";
		if (report_request.getTracer_date_range()[1] == null
				|| report_request.getTracer_date_range()[1].trim().equalsIgnoreCase(""))
			report_request.getTracer_date_range()[1] = "none42e";
		
		if (report_request.getRegional_compiler_compiled_date()[0] == null
				|| report_request.getRegional_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getRegional_compiler_compiled_date()[0] = "none42e";
		if (report_request.getRegional_compiler_compiled_date()[1] == null
				|| report_request.getRegional_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getRegional_compiler_compiled_date()[1] = "none42e";
		
		if (report_request.getDivision_compiler_compiled_date()[0] == null
				|| report_request.getDivision_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getDivision_compiler_compiled_date()[0] = "none42e";
		if (report_request.getDivision_compiler_compiled_date()[1] == null
				|| report_request.getDivision_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getDivision_compiler_compiled_date()[1] = "none42e";
		
		if (report_request.getApproved_date()[0] == null
				|| report_request.getApproved_date()[0].trim().equalsIgnoreCase(""))
			report_request.getApproved_date()[0] = "none42e";
		if (report_request.getApproved_date()[1] == null
				|| report_request.getApproved_date()[0].trim().equalsIgnoreCase(""))
			report_request.getApproved_date()[1] = "none42e";
		
		for (int i = 0; i < report_request.getRegional_compiler_compiled_date().length; i++) {
 			System.out.println("regional compiler date" + report_request.getRegional_compiler_compiled_date()[i]);
		}
		for (int i = 0; i < report_request.getDivision_compiler_compiled_date().length; i++) {
 			System.out.println("division compiler date" + report_request.getDivision_compiler_compiled_date()[i]);
		}
		for (int i = 0; i < report_request.getApproved_date().length; i++) {
 			System.out.println("approve date" + report_request.getApproved_date()[i]);
		}
		
		List<ReportBranch> aa = reportMapper.getBranchAuditReportSuspenseAccount(report_request.getRegion(),
				report_request.getBranch(), report_request.getFinding_status(),
				report_request.getRectification_date_range(), report_request.getDate_range(),
				report_request.getAudit_finding(), report_request.getRectification_status(),
				report_request.getSingle_filter_info(), report_request.getUser_id(), report_request.getUser_roles(),
				report_request.getUser_region_id(),
				// SuspenseAccount
				report_request.getTracer_date_range(), report_request.getBalance_per_tracer_min(),
				report_request.getBalance_per_tracer_max(), report_request.getBalance_per_trial_balance_min(),
				report_request.getBalance_per_trial_balance_max(), report_request.getSuspense_account_type(),
				report_request.getBanking(), report_request.getCash_type(), 
				report_request.getRegional_compiler_compiled_date(),
				report_request.getDivision_compiler_compiled_date(), report_request.getApproved_date());
		return aa;
	}

	public List<ReportBranch> fetchReportOperation(ReportBranchRequest report_request) {
		if (report_request.getDate_range()[0] == null || report_request.getDate_range()[0].trim().equalsIgnoreCase(""))
			report_request.getDate_range()[0] = "none42e";
		if (report_request.getDate_range()[1] == null || report_request.getDate_range()[1].trim().equalsIgnoreCase(""))
			report_request.getDate_range()[1] = "none42e";
		if (report_request.getRectification_date_range()[0] == null
				|| report_request.getRectification_date_range()[0].trim().equalsIgnoreCase(""))
			report_request.getRectification_date_range()[0] = "none42e";
		if (report_request.getRectification_date_range()[1] == null
				|| report_request.getRectification_date_range()[0].trim().equalsIgnoreCase(""))
			report_request.getRectification_date_range()[1] = "none42e";
		
		if (report_request.getRegional_compiler_compiled_date()[0] == null
				|| report_request.getRegional_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getRegional_compiler_compiled_date()[0] = "none42e";
		if (report_request.getRegional_compiler_compiled_date()[1] == null
				|| report_request.getRegional_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getRegional_compiler_compiled_date()[1] = "none42e";
		
		if (report_request.getDivision_compiler_compiled_date()[0] == null
				|| report_request.getDivision_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getDivision_compiler_compiled_date()[0] = "none42e";
		if (report_request.getDivision_compiler_compiled_date()[1] == null
				|| report_request.getDivision_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getDivision_compiler_compiled_date()[1] = "none42e";
		
		if (report_request.getApproved_date()[0] == null
				|| report_request.getApproved_date()[0].trim().equalsIgnoreCase(""))
			report_request.getApproved_date()[0] = "none42e";
		if (report_request.getApproved_date()[1] == null
				|| report_request.getApproved_date()[0].trim().equalsIgnoreCase(""))
			report_request.getApproved_date()[1] = "none42e";
		
		for (int i = 0; i < report_request.getRegional_compiler_compiled_date().length; i++) {
 			System.out.println("regional compiler date" + report_request.getRegional_compiler_compiled_date()[i]);
		}
		for (int i = 0; i < report_request.getDivision_compiler_compiled_date().length; i++) {
 			System.out.println("division compiler date" + report_request.getDivision_compiler_compiled_date()[i]);
		}
		for (int i = 0; i < report_request.getApproved_date().length; i++) {
 			System.out.println("approve date" + report_request.getApproved_date()[i]);
		}
		

		List<ReportBranch> aa = reportMapper.getBranchOperationAuditReport(report_request.getRegion(),
				report_request.getBranch(), report_request.getFinding_status(),
				report_request.getRectification_date_range(), report_request.getDate_range(),
				report_request.getAudit_finding(), report_request.getCategory_of_discrepancy(),
				report_request.getMax_amount(), report_request.getMin_amount(), report_request.getAccount_number(),
				report_request.getRectification_status(), report_request.getSingle_filter_info(),
				report_request.getUser_id(), report_request.getUser_roles(), report_request.getUser_region_id(),
				report_request.getBanking(), report_request.getCash_type(), 
				report_request.getRegional_compiler_compiled_date(),
				report_request.getDivision_compiler_compiled_date(), report_request.getApproved_date());
		return aa;
	}

	public List<ReportBranch> fetchReportMemorandum(ReportBranchRequest report_request) {
		System.out.println("the single filter infoooooooo" + report_request.getSingle_filter_info());
		if (report_request.getDate_range()[0] == null || report_request.getDate_range()[0].trim().equalsIgnoreCase(""))
			report_request.getDate_range()[0] = "none42e";
		if (report_request.getDate_range()[1] == null || report_request.getDate_range()[1].trim().equalsIgnoreCase(""))
			report_request.getDate_range()[1] = "none42e";
		if (report_request.getRectification_date_range()[0] == null
				|| report_request.getRectification_date_range()[0].trim().equalsIgnoreCase(""))
			report_request.getRectification_date_range()[0] = "none42e";
		if (report_request.getRectification_date_range()[1] == null
				|| report_request.getRectification_date_range()[0].trim().equalsIgnoreCase(""))
			report_request.getRectification_date_range()[1] = "none42e";
		
		if (report_request.getRegional_compiler_compiled_date()[0] == null
				|| report_request.getRegional_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getRegional_compiler_compiled_date()[0] = "none42e";
		if (report_request.getRegional_compiler_compiled_date()[1] == null
				|| report_request.getRegional_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getRegional_compiler_compiled_date()[1] = "none42e";
		
		if (report_request.getDivision_compiler_compiled_date()[0] == null
				|| report_request.getDivision_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getDivision_compiler_compiled_date()[0] = "none42e";
		if (report_request.getDivision_compiler_compiled_date()[1] == null
				|| report_request.getDivision_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getDivision_compiler_compiled_date()[1] = "none42e";
		
		if (report_request.getApproved_date()[0] == null
				|| report_request.getApproved_date()[0].trim().equalsIgnoreCase(""))
			report_request.getApproved_date()[0] = "none42e";
		if (report_request.getApproved_date()[1] == null
				|| report_request.getApproved_date()[0].trim().equalsIgnoreCase(""))
			report_request.getApproved_date()[1] = "none42e";
		
		for (int i = 0; i < report_request.getRegional_compiler_compiled_date().length; i++) {
 			System.out.println("regional compiler date" + report_request.getRegional_compiler_compiled_date()[i]);
		}
		for (int i = 0; i < report_request.getDivision_compiler_compiled_date().length; i++) {
 			System.out.println("division compiler date" + report_request.getDivision_compiler_compiled_date()[i]);
		}
		for (int i = 0; i < report_request.getApproved_date().length; i++) {
 			System.out.println("approve date" + report_request.getApproved_date()[i]);
		}
		
		List<ReportBranch> aa = reportMapper.getBranchMemorandumAuditReport(report_request.getRegion(),
				report_request.getBranch(), report_request.getFinding_status(),
				report_request.getRectification_date_range(), report_request.getDate_range(),
				report_request.getAudit_finding(), report_request.getMemorandum_amount_min(),
				report_request.getContingent_amount_min(), report_request.getMemorandum_amount_max(),
				report_request.getContingent_amount_max(), report_request.getRectification_status(),
				report_request.getSingle_filter_info(), report_request.getUser_id(), report_request.getUser_roles(),
				report_request.getUser_region_id(), report_request.getBanking(), report_request.getCash_type(), 
				report_request.getRegional_compiler_compiled_date(),
				report_request.getDivision_compiler_compiled_date(), report_request.getApproved_date());
		return aa;
	}

	public List<ReportBranch> fetchReportObservation(ReportBranchRequest report_request) {
		if (report_request.getDate_range()[0] == null || report_request.getDate_range()[0].trim().equalsIgnoreCase(""))
			report_request.getDate_range()[0] = "none42e";
		if (report_request.getDate_range()[1] == null || report_request.getDate_range()[1].trim().equalsIgnoreCase(""))
			report_request.getDate_range()[1] = "none42e";
		if (report_request.getRectification_date_range()[0] == null
				|| report_request.getRectification_date_range()[0].trim().equalsIgnoreCase(""))
			report_request.getRectification_date_range()[0] = "none42e";
		if (report_request.getRectification_date_range()[1] == null
				|| report_request.getRectification_date_range()[0].trim().equalsIgnoreCase(""))
			report_request.getRectification_date_range()[1] = "none42e";
		
		if (report_request.getRegional_compiler_compiled_date()[0] == null
				|| report_request.getRegional_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getRegional_compiler_compiled_date()[0] = "none42e";
		if (report_request.getRegional_compiler_compiled_date()[1] == null
				|| report_request.getRegional_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getRegional_compiler_compiled_date()[1] = "none42e";
		
		if (report_request.getDivision_compiler_compiled_date()[0] == null
				|| report_request.getDivision_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getDivision_compiler_compiled_date()[0] = "none42e";
		if (report_request.getDivision_compiler_compiled_date()[1] == null
				|| report_request.getDivision_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getDivision_compiler_compiled_date()[1] = "none42e";
		
		if (report_request.getApproved_date()[0] == null
				|| report_request.getApproved_date()[0].trim().equalsIgnoreCase(""))
			report_request.getApproved_date()[0] = "none42e";
		if (report_request.getApproved_date()[1] == null
				|| report_request.getApproved_date()[0].trim().equalsIgnoreCase(""))
			report_request.getApproved_date()[1] = "none42e";
		
		for (int i = 0; i < report_request.getRegional_compiler_compiled_date().length; i++) {
 			System.out.println("regional compiler date" + report_request.getRegional_compiler_compiled_date()[i]);
		}
		for (int i = 0; i < report_request.getDivision_compiler_compiled_date().length; i++) {
 			System.out.println("division compiler date" + report_request.getDivision_compiler_compiled_date()[i]);
		}
		for (int i = 0; i < report_request.getApproved_date().length; i++) {
 			System.out.println("approve date" + report_request.getApproved_date()[i]);
		}

		List<ReportBranch> aa = reportMapper.getBranchObservationAuditReport(report_request.getRegion(),
				report_request.getBranch(), report_request.getFinding_status(),
				report_request.getRectification_date_range(), report_request.getDate_range(),
				report_request.getAudit_finding(), report_request.getRectification_status(),
				report_request.getSingle_filter_info(), report_request.getUser_id(), report_request.getUser_roles(),
				report_request.getUser_region_id(), report_request.getBanking(), 
				report_request.getRegional_compiler_compiled_date(),
				report_request.getDivision_compiler_compiled_date(), report_request.getApproved_date());
		return aa;
	}

	public List<ReportBranch> fetchReportNegotiable(ReportBranchRequest report_request) {
		// System.out.println("the amount and account number"+report_request.gte)
		if (report_request.getDate_range()[0] == null || report_request.getDate_range()[0].trim().equalsIgnoreCase(""))
			report_request.getDate_range()[0] = "none42e";
		if (report_request.getDate_range()[1] == null || report_request.getDate_range()[1].trim().equalsIgnoreCase(""))
			report_request.getDate_range()[1] = "none42e";
		if (report_request.getRectification_date_range()[0] == null
				|| report_request.getRectification_date_range()[0].trim().equalsIgnoreCase(""))
			report_request.getRectification_date_range()[0] = "none42e";
		if (report_request.getRectification_date_range()[1] == null
				|| report_request.getRectification_date_range()[0].trim().equalsIgnoreCase(""))
			report_request.getRectification_date_range()[1] = "none42e";
		
		if (report_request.getRegional_compiler_compiled_date()[0] == null
				|| report_request.getRegional_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getRegional_compiler_compiled_date()[0] = "none42e";
		if (report_request.getRegional_compiler_compiled_date()[1] == null
				|| report_request.getRegional_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getRegional_compiler_compiled_date()[1] = "none42e";
		
		if (report_request.getDivision_compiler_compiled_date()[0] == null
				|| report_request.getDivision_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getDivision_compiler_compiled_date()[0] = "none42e";
		if (report_request.getDivision_compiler_compiled_date()[1] == null
				|| report_request.getDivision_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getDivision_compiler_compiled_date()[1] = "none42e";
		
		if (report_request.getApproved_date()[0] == null
				|| report_request.getApproved_date()[0].trim().equalsIgnoreCase(""))
			report_request.getApproved_date()[0] = "none42e";
		if (report_request.getApproved_date()[1] == null
				|| report_request.getApproved_date()[0].trim().equalsIgnoreCase(""))
			report_request.getApproved_date()[1] = "none42e";
		
		for (int i = 0; i < report_request.getRegional_compiler_compiled_date().length; i++) {
 			System.out.println("regional compiler date" + report_request.getRegional_compiler_compiled_date()[i]);
		}
		for (int i = 0; i < report_request.getDivision_compiler_compiled_date().length; i++) {
 			System.out.println("division compiler date" + report_request.getDivision_compiler_compiled_date()[i]);
		}
		for (int i = 0; i < report_request.getApproved_date().length; i++) {
 			System.out.println("approve date" + report_request.getApproved_date()[i]);
		}

		List<ReportBranch> aa = reportMapper.getBranchNegotiableAuditReport(report_request.getRegion(),
				report_request.getBranch(), report_request.getFinding_status(),
				report_request.getRectification_date_range(), report_request.getDate_range(),
				report_request.getAudit_finding(), report_request.getAccount_number(), report_request.getMin_amount(),
				report_request.getMax_amount(), report_request.getRectification_status(),
				report_request.getSingle_filter_info(), report_request.getUser_id(), report_request.getUser_roles(),
				report_request.getUser_region_id(), report_request.getBanking(), report_request.getStock_item_type(),
				report_request.getCash_type(), 
				report_request.getRegional_compiler_compiled_date(),
				report_request.getDivision_compiler_compiled_date(), report_request.getApproved_date());
		return aa;
	}

	public List<ReportBranch> fetchReportLong(ReportBranchRequest report_request) {
		// Long Outstanding Item

		if (report_request.getDate_range()[0] == null || report_request.getDate_range()[0].trim().equalsIgnoreCase(""))
			report_request.getDate_range()[0] = "none42e";
		if (report_request.getDate_range()[1] == null || report_request.getDate_range()[1].trim().equalsIgnoreCase(""))
			report_request.getDate_range()[1] = "none42e";
		if (report_request.getRectification_date_range()[0] == null
				|| report_request.getRectification_date_range()[0].trim().equalsIgnoreCase(""))
			report_request.getRectification_date_range()[0] = "none42e";
		if (report_request.getRectification_date_range()[1] == null
				|| report_request.getRectification_date_range()[0].trim().equalsIgnoreCase(""))
			report_request.getRectification_date_range()[1] = "none42e";

		
		if (report_request.getRegional_compiler_compiled_date()[0] == null
				|| report_request.getRegional_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getRegional_compiler_compiled_date()[0] = "none42e";
		if (report_request.getRegional_compiler_compiled_date()[1] == null
				|| report_request.getRegional_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getRegional_compiler_compiled_date()[1] = "none42e";
		
		if (report_request.getDivision_compiler_compiled_date()[0] == null
				|| report_request.getDivision_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getDivision_compiler_compiled_date()[0] = "none42e";
		if (report_request.getDivision_compiler_compiled_date()[1] == null
				|| report_request.getDivision_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getDivision_compiler_compiled_date()[1] = "none42e";
		
		if (report_request.getApproved_date()[0] == null
				|| report_request.getApproved_date()[0].trim().equalsIgnoreCase(""))
			report_request.getApproved_date()[0] = "none42e";
		if (report_request.getApproved_date()[1] == null
				|| report_request.getApproved_date()[0].trim().equalsIgnoreCase(""))
			report_request.getApproved_date()[1] = "none42e";
		
		for (int i = 0; i < report_request.getRegional_compiler_compiled_date().length; i++) {
 			System.out.println("regional compiler date" + report_request.getRegional_compiler_compiled_date()[i]);
		}
		for (int i = 0; i < report_request.getDivision_compiler_compiled_date().length; i++) {
 			System.out.println("division compiler date" + report_request.getDivision_compiler_compiled_date()[i]);
		}
		for (int i = 0; i < report_request.getApproved_date().length; i++) {
 			System.out.println("approve date" + report_request.getApproved_date()[i]);
		}
		
		System.out.println("Region: " + report_request.getRegion());
		System.out.println("Branch: " + report_request.getBranch());
		System.out.println("Finding Status: " + report_request.getFinding_status());
		System.out.println("Rectification Date Range: " + report_request.getRectification_date_range());
		System.out.println("Date Range: " + report_request.getDate_range());
		System.out.println("Audit Finding: " + report_request.getAudit_finding());
		System.out.println("Rectification Status: " + report_request.getRectification_status());
		System.out.println("Less than 90 Amount Min: " + report_request.getLess_than_90_amount_min());
		System.out.println("Less than 90 Amount Max: " + report_request.getLess_than_90_amount_max());
		System.out.println("Greater than 90 Amount Min: " + report_request.getGreater_than_90_amount_min());
		System.out.println("Greater than 90 Amount Max: " + report_request.getGreater_than_90_amount_max());
		System.out.println("Greater than 180 Amount Min: " + report_request.getGreater_than_180_amount_min());
		System.out.println("Greater than 180 Amount Max: " + report_request.getGreater_than_180_amount_max());
		System.out.println("Greater than 365 Amount Min: " + report_request.getGreater_than_365_amount_min());
		System.out.println("Greater than 365 Amount Max: " + report_request.getGreater_than_365_amount_max());
		System.out.println("Cash Type: " + report_request.getCash_type());
		System.out.println("Single Filter Info: " + report_request.getSingle_filter_info());
		System.out.println("User ID: " + report_request.getUser_id());
		System.out.println("User Roles: " + report_request.getUser_roles());
		System.out.println("User Region ID: " + report_request.getUser_region_id());
		System.out.println("Outstanding Item: " + report_request.getOutstanding_item());
		System.out.println("Selected Item Age: " + report_request.getSelected_item_age());
		System.out.println("Banking: " + report_request.getBanking());

		List<ReportBranch> aa = reportMapper.getBranchLongAuditReport(report_request.getRegion(),
				report_request.getBranch(), report_request.getFinding_status(),
				report_request.getRectification_date_range(), report_request.getDate_range(),
				report_request.getAudit_finding(), report_request.getRectification_status(),
				report_request.getLess_than_90_amount_min(), report_request.getLess_than_90_amount_max(),
				report_request.getGreater_than_90_amount_min(), report_request.getGreater_than_90_amount_max(),
				report_request.getGreater_than_180_amount_min(), report_request.getGreater_than_180_amount_max(),
				report_request.getGreater_than_365_amount_min(), report_request.getGreater_than_365_amount_max(),
				report_request.getCash_type(), report_request.getSingle_filter_info(), report_request.getUser_id(),
				report_request.getUser_roles(), report_request.getUser_region_id(),
				report_request.getOutstanding_item(), report_request.getSelected_item_age(),
				report_request.getBanking(),  report_request.getRegional_compiler_compiled_date(),
				report_request.getDivision_compiler_compiled_date(), report_request.getApproved_date());

		return aa;
	}

	public List<ReportBranch> fetchReportLoanAdvance(ReportBranchRequest report_request) {
 
		if (report_request.getDate_range()[0] == null || report_request.getDate_range()[0].trim().equalsIgnoreCase(""))
			report_request.getDate_range()[0] = "none42e";
		if (report_request.getDate_range()[1] == null || report_request.getDate_range()[1].trim().equalsIgnoreCase(""))
			report_request.getDate_range()[1] = "none42e";

		if (report_request.getRectification_date_range()[0] == null
				|| report_request.getRectification_date_range()[0].trim().equalsIgnoreCase(""))
			report_request.getRectification_date_range()[0] = "none42e";
		if (report_request.getRectification_date_range()[1] == null
				|| report_request.getRectification_date_range()[0].trim().equalsIgnoreCase(""))
			report_request.getRectification_date_range()[1] = "none42e";
		
		if (report_request.getRegional_compiler_compiled_date()[0] == null
				|| report_request.getRegional_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getRegional_compiler_compiled_date()[0] = "none42e";
		if (report_request.getRegional_compiler_compiled_date()[1] == null
				|| report_request.getRegional_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getRegional_compiler_compiled_date()[1] = "none42e";
		
		if (report_request.getDivision_compiler_compiled_date()[0] == null
				|| report_request.getDivision_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getDivision_compiler_compiled_date()[0] = "none42e";
		if (report_request.getDivision_compiler_compiled_date()[1] == null
				|| report_request.getDivision_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getDivision_compiler_compiled_date()[1] = "none42e";
		
		if (report_request.getApproved_date()[0] == null
				|| report_request.getApproved_date()[0].trim().equalsIgnoreCase(""))
			report_request.getApproved_date()[0] = "none42e";
		if (report_request.getApproved_date()[1] == null
				|| report_request.getApproved_date()[0].trim().equalsIgnoreCase(""))
			report_request.getApproved_date()[1] = "none42e";
	 
	 
		
		for (int i = 0; i < report_request.getRegional_compiler_compiled_date().length; i++) {
 			System.out.println("regional compiler date" + report_request.getRegional_compiler_compiled_date()[i]);
		}
		for (int i = 0; i < report_request.getDivision_compiler_compiled_date().length; i++) {
 			System.out.println("division compiler date" + report_request.getDivision_compiler_compiled_date()[i]);
		}
		for (int i = 0; i < report_request.getApproved_date().length; i++) {
 			System.out.println("approve date" + report_request.getApproved_date()[i]);
		}
		
		
		List<ReportBranch> aa = reportMapper.getBranchLoandAdvanceAuditReport(report_request.getRegion(),
				report_request.getBranch(), report_request.getFinding_status(),
				report_request.getRectification_date_range(), report_request.getDate_range(),
				report_request.getAudit_finding(), report_request.getRectification_status(),
				report_request.getSingle_filter_info(), report_request.getBorrower_name(),
				// report_request.getLoan_disburse_date(),
				report_request.getLoan_type(), report_request.getAccount_number(),
				report_request.getGranted_amount_min(), report_request.getGranted_amount_max(),
				report_request.getUser_id(), report_request.getUser_roles(), report_request.getUser_region_id(),
				report_request.getBanking(), report_request.getCash_type(),  report_request.getRegional_compiler_compiled_date(),
				report_request.getDivision_compiler_compiled_date(), report_request.getApproved_date());
		return aa;
	}

	public List<ReportBranch> fetchReportCashCount(ReportBranchRequest report_request) {
		System.out.println("service");
		// System.out.println("the amount and account number"+report_request.gte)
		if (report_request.getDate_range()[0] == null || report_request.getDate_range()[0].trim().equalsIgnoreCase(""))
			report_request.getDate_range()[0] = "none42e";
		if (report_request.getDate_range()[1] == null || report_request.getDate_range()[1].trim().equalsIgnoreCase(""))
			report_request.getDate_range()[1] = "none42e";
		if (report_request.getRectification_date_range()[0] == null
				|| report_request.getRectification_date_range()[0].trim().equalsIgnoreCase(""))
			report_request.getRectification_date_range()[0] = "none42e";
		if (report_request.getRectification_date_range()[1] == null
				|| report_request.getRectification_date_range()[0].trim().equalsIgnoreCase(""))
			report_request.getRectification_date_range()[1] = "none42e";
		
		if (report_request.getRegional_compiler_compiled_date()[0] == null
				|| report_request.getRegional_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getRegional_compiler_compiled_date()[0] = "none42e";
		if (report_request.getRegional_compiler_compiled_date()[1] == null
				|| report_request.getRegional_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getRegional_compiler_compiled_date()[1] = "none42e";
		
		if (report_request.getDivision_compiler_compiled_date()[0] == null
				|| report_request.getDivision_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getDivision_compiler_compiled_date()[0] = "none42e";
		if (report_request.getDivision_compiler_compiled_date()[1] == null
				|| report_request.getDivision_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getDivision_compiler_compiled_date()[1] = "none42e";
		
		if (report_request.getApproved_date()[0] == null
				|| report_request.getApproved_date()[0].trim().equalsIgnoreCase(""))
			report_request.getApproved_date()[0] = "none42e";
		if (report_request.getApproved_date()[1] == null
				|| report_request.getApproved_date()[0].trim().equalsIgnoreCase(""))
			report_request.getApproved_date()[1] = "none42e";
	 
	 
		
		for (int i = 0; i < report_request.getRegional_compiler_compiled_date().length; i++) {
 			System.out.println("regional compiler date" + report_request.getRegional_compiler_compiled_date()[i]);
		}
		for (int i = 0; i < report_request.getDivision_compiler_compiled_date().length; i++) {
 			System.out.println("division compiler date" + report_request.getDivision_compiler_compiled_date()[i]);
		}
		for (int i = 0; i < report_request.getApproved_date().length; i++) {
 			System.out.println("approve date" + report_request.getApproved_date()[i]);
		}
		
		List<ReportBranch> aa = reportMapper.getBranchCashCountAuditReport(report_request.getRegion(),
				report_request.getBranch(), report_request.getFinding_status(),
				report_request.getRectification_date_range(), report_request.getDate_range(),
				report_request.getAudit_finding(), report_request.getRectification_status(),
				report_request.getActual_cash_count_min(), report_request.getActual_cash_count_max(),
				report_request.getTrial_balance_min(), report_request.getTrial_balance_max(),
				report_request.getCash_count_type(), report_request.getSingle_filter_info(),
				report_request.getUser_id(), report_request.getUser_roles(), report_request.getUser_region_id(),
				report_request.getBanking(),  report_request.getRegional_compiler_compiled_date(),
				report_request.getDivision_compiler_compiled_date(), report_request.getApproved_date());
		return aa;
	}

	public List<ReportBranch> fetchReportCashManagement(ReportBranchRequest report_request) {
		if (report_request.getDate_range()[0] == null || report_request.getDate_range()[0].trim().equalsIgnoreCase(""))
			report_request.getDate_range()[0] = "none42e";
		if (report_request.getDate_range()[1] == null || report_request.getDate_range()[1].trim().equalsIgnoreCase(""))
			report_request.getDate_range()[1] = "none42e";
		if (report_request.getRectification_date_range()[0] == null
				|| report_request.getRectification_date_range()[0].trim().equalsIgnoreCase(""))
			report_request.getRectification_date_range()[0] = "none42e";
		if (report_request.getRectification_date_range()[1] == null
				|| report_request.getRectification_date_range()[0].trim().equalsIgnoreCase(""))
			report_request.getRectification_date_range()[1] = "none42e";
		
		if (report_request.getRegional_compiler_compiled_date()[0] == null
				|| report_request.getRegional_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getRegional_compiler_compiled_date()[0] = "none42e";
		if (report_request.getRegional_compiler_compiled_date()[1] == null
				|| report_request.getRegional_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getRegional_compiler_compiled_date()[1] = "none42e";
		
		if (report_request.getDivision_compiler_compiled_date()[0] == null
				|| report_request.getDivision_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getDivision_compiler_compiled_date()[0] = "none42e";
		if (report_request.getDivision_compiler_compiled_date()[1] == null
				|| report_request.getDivision_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getDivision_compiler_compiled_date()[1] = "none42e";
		
		if (report_request.getApproved_date()[0] == null
				|| report_request.getApproved_date()[0].trim().equalsIgnoreCase(""))
			report_request.getApproved_date()[0] = "none42e";
		if (report_request.getApproved_date()[1] == null
				|| report_request.getApproved_date()[0].trim().equalsIgnoreCase(""))
			report_request.getApproved_date()[1] = "none42e";
		
		for (int i = 0; i < report_request.getRegional_compiler_compiled_date().length; i++) {
 			System.out.println("regional compiler date" + report_request.getRegional_compiler_compiled_date()[i]);
		}
		for (int i = 0; i < report_request.getDivision_compiler_compiled_date().length; i++) {
 			System.out.println("division compiler date" + report_request.getDivision_compiler_compiled_date()[i]);
		}
		for (int i = 0; i < report_request.getApproved_date().length; i++) {
 			System.out.println("approve date" + report_request.getApproved_date()[i]);
		}

		List<ReportBranch> aa = reportMapper.getBranchAuditReportCashManagement(report_request.getRegion(),
				report_request.getBranch(), report_request.getFinding_status(),
				report_request.getRectification_date_range(), report_request.getDate_range(),
				report_request.getAudit_finding(), report_request.getRectification_status(),
				report_request.getSingle_filter_info(), report_request.getUser_id(), report_request.getUser_roles(),
				report_request.getUser_region_id(),
				// CashManagement
				report_request.getMid_rate_fcy_min(), report_request.getMid_rate_fcy_max(),
				report_request.getAverage_cash_holding_min(), report_request.getAverage_cash_holding_max(),
				report_request.getCash_type(), report_request.getBanking(), 
				report_request.getRegional_compiler_compiled_date(),
				report_request.getDivision_compiler_compiled_date(), report_request.getApproved_date());
		return aa;
	}

	public List<ReportBranch> fetchReportCashExcessOrShortage(ReportBranchRequest report_request) {
		if (report_request.getDate_range()[0] == null || report_request.getDate_range()[0].trim().equalsIgnoreCase(""))
			report_request.getDate_range()[0] = "none42e";
		if (report_request.getDate_range()[1] == null || report_request.getDate_range()[1].trim().equalsIgnoreCase(""))
			report_request.getDate_range()[1] = "none42e";
		if (report_request.getRectification_date_range()[0] == null
				|| report_request.getRectification_date_range()[0].trim().equalsIgnoreCase(""))
			report_request.getRectification_date_range()[0] = "none42e";
		if (report_request.getRectification_date_range()[1] == null
				|| report_request.getRectification_date_range()[0].trim().equalsIgnoreCase(""))
			report_request.getRectification_date_range()[1] = "none42e";
		if (report_request.getRegional_compiler_compiled_date()[0] == null
				|| report_request.getRegional_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getRegional_compiler_compiled_date()[0] = "none42e";
		if (report_request.getRegional_compiler_compiled_date()[1] == null
				|| report_request.getRegional_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getRegional_compiler_compiled_date()[1] = "none42e";
		
		if (report_request.getDivision_compiler_compiled_date()[0] == null
				|| report_request.getDivision_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getDivision_compiler_compiled_date()[0] = "none42e";
		if (report_request.getDivision_compiler_compiled_date()[1] == null
				|| report_request.getDivision_compiler_compiled_date()[0].trim().equalsIgnoreCase(""))
			report_request.getDivision_compiler_compiled_date()[1] = "none42e";
		
		if (report_request.getApproved_date()[0] == null
				|| report_request.getApproved_date()[0].trim().equalsIgnoreCase(""))
			report_request.getApproved_date()[0] = "none42e";
		if (report_request.getApproved_date()[1] == null
				|| report_request.getApproved_date()[0].trim().equalsIgnoreCase(""))
			report_request.getApproved_date()[1] = "none42e";
		
		for (int i = 0; i < report_request.getRegional_compiler_compiled_date().length; i++) {
 			System.out.println("regional compiler date" + report_request.getRegional_compiler_compiled_date()[i]);
		}
		for (int i = 0; i < report_request.getDivision_compiler_compiled_date().length; i++) {
 			System.out.println("division compiler date" + report_request.getDivision_compiler_compiled_date()[i]);
		}
		for (int i = 0; i < report_request.getApproved_date().length; i++) {
 			System.out.println("approve date" + report_request.getApproved_date()[i]);
		}

		List<ReportBranch> aa = reportMapper.fetchReportCashExcessOrShortage(report_request.getRegion(),
				report_request.getBranch(), report_request.getFinding_status(),
				report_request.getRectification_date_range(), report_request.getDate_range(),
				report_request.getAudit_finding(), report_request.getRectification_status(),
				report_request.getSingle_filter_info(), report_request.getUser_id(), report_request.getUser_roles(),
				report_request.getUser_region_id(),
				// CashExcessOrShortage
				report_request.getAmount_shortage_min(), report_request.getAmount_shortage_max(),
				report_request.getAmount_excess_min(), report_request.getAmount_excess_max(),
				report_request.getBanking(), report_request.getCash_type(), 
				report_request.getRegional_compiler_compiled_date(),
				report_request.getDivision_compiler_compiled_date(), report_request.getApproved_date());
		return aa;
	}

	/////////////////////////

	public List<Branch_R> getBranches() {
		return reportMapper.getBranches();
	}

	public List<Region> getRegions() {
		return reportMapper.getRegions();
	}

	public List<Finding_R> getFindings(String type) {
		return reportMapper.getFindings(type);
	}

	public List<DiscrepancyCategory_R> getDiscrepancies() {
		List<DiscrepancyCategory_R> aa = reportMapper.getDiscrepancies();
		System.out.println("dd: " + aa.get(0).getDescription());
		return reportMapper.getDiscrepancies();
	}

	public List<DiscrepancyCategory_R> getControllableExpenseTypes() {
		// List<DiscrepancyCategory_R> aa = reportMapper.getControllableExpenseTypes();
		// System.out.println("dd: " + aa.get(0).getDescription());
		return reportMapper.getControllableExpenseTypes();
	}

	public List<DiscrepancyCategory_R> getSuspenseAccountTypeOptions() {
		// List<DiscrepancyCategory_R> aa =
		// reportMapper.getSuspenseAccountTypeOptions();
		// System.out.println("dd: " + aa.get(0).getDescription());
		return reportMapper.getSuspenseAccountTypeOptions();
	}
}
