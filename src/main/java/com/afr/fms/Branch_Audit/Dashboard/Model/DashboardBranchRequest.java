package com.afr.fms.Branch_Audit.Dashboard.Model;

import java.util.Date;

import com.afr.fms.Admin.Entity.Branch;
import com.afr.fms.Admin.Entity.Region;

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
public class DashboardBranchRequest {
	private Long user_id;
	private String[] user_roles;
	private Long user_region_id;
}
