package com.afr.fms.Branch_Audit.Dashboard.Model;

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
public class DashboardRecentActivities {

	private String message;
	private String created_date;
	private long user_id;

}
