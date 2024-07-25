package com.afr.fms.Branch_Audit.Dashboard.Model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DashboardResponse {
	private long drafted_1;
	private long passed_1;
	private long regional_reviewed_1;

	private long regional_compiled_2;
	private long regional_submitted_2;
	private long division_reviewed_2;

	private long division_compiled_3;
	private long division_submitted_3;
	private long approved_3;

	private long passed_4;
	private long regional_compiled_4;
	private long division_compiled_4;
	private long approved_4;

	private long passed_5;
	private long responded_5;
	private long partially_rectified_5;
	private long rectified_5;
	
	private long passed_0;
	private long reviewed_0;
	private long compiled_0;
	private long submitted_0;
	private long approved_0;

	private List<DashboardRecentActivities> recentActivities;
}
