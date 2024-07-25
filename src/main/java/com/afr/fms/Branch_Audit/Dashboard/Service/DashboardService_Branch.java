package com.afr.fms.Branch_Audit.Dashboard.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Branch_Audit.Dashboard.Mapper.DashboardMapper;
import com.afr.fms.Branch_Audit.Dashboard.Model.DashboardBranchRequest;
import com.afr.fms.Branch_Audit.Dashboard.Model.DashboardRecentActivities;
import com.afr.fms.Branch_Audit.Dashboard.Model.DashboardResponse;
import com.afr.fms.Branch_Audit.Dashboard.Model.DashboardResponseFive;
import com.afr.fms.Branch_Audit.Dashboard.Model.DashboardResponseFour;
import com.afr.fms.Branch_Audit.Dashboard.Model.DashboardResponseOne;
import com.afr.fms.Branch_Audit.Dashboard.Model.DashboardResponseThree;
import com.afr.fms.Branch_Audit.Dashboard.Model.DashboardResponseTwo;

@Service
public class DashboardService_Branch {

	@Autowired
	DashboardMapper dashboardMapper;

	public DashboardResponse fetchDashboard(DashboardBranchRequest dashboard_request) {
		DashboardResponse aa = dashboardMapper.getBranchAuditDashboard(dashboard_request.getUser_id(),
				dashboard_request.getUser_roles(), dashboard_request.getUser_region_id());
		// List<DashboardRecentActivities> bb = dashboardMapper.getRecentActivities(dashboard_request.getUser_id());
		if(aa != null)
			aa.setRecentActivities(dashboardMapper.getRecentActivities(dashboard_request.getUser_id()));
		return aa;
	}

	public DashboardResponseOne fetchDashboardDataOne(DashboardBranchRequest dashboard_request) {
		return dashboardMapper.getResponseOne(dashboard_request.getUser_id(), dashboard_request.getUser_roles(),
				dashboard_request.getUser_region_id());
	}

	public DashboardResponseTwo fetchDashboardDataTwo(DashboardBranchRequest dashboard_request) {
		return dashboardMapper.getResponseTwo(dashboard_request.getUser_id(), dashboard_request.getUser_roles(),
				dashboard_request.getUser_region_id());
	}

	public DashboardResponseThree fetchDashboardDataThree(DashboardBranchRequest dashboard_request) {
		return dashboardMapper.getResponseThree(dashboard_request.getUser_id(), dashboard_request.getUser_roles(),
				dashboard_request.getUser_region_id());
	}

	public DashboardResponseFour fetchDashboardDataFour(DashboardBranchRequest dashboard_request) {
		return dashboardMapper.getResponseFour(dashboard_request.getUser_id(), dashboard_request.getUser_roles(),
				dashboard_request.getUser_region_id());
	}


	public DashboardResponseFive fetchDashboardDataFive(DashboardBranchRequest dashboard_request) {
		return dashboardMapper.getResponseFive(dashboard_request.getUser_id(), dashboard_request.getUser_roles(),
				dashboard_request.getUser_region_id());
	}
}
