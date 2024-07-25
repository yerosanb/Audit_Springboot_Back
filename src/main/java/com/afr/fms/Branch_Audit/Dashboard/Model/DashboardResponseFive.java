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
public class DashboardResponseFive {
	private long passed_5;
	private long responded_5;
	private long partially_rectified_5;
	private long rectified_5;
}
