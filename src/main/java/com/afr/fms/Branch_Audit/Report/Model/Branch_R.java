package com.afr.fms.Branch_Audit.Report.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Branch_R {
	private Long id;
	private String name;
	private String code;
	private Long region_id;
	private boolean status;
	private Long user_id;
}
