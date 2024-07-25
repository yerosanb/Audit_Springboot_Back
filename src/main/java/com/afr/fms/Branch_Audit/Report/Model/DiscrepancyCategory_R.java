package com.afr.fms.Branch_Audit.Report.Model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscrepancyCategory_R {
	private Long id;
	private String name;
	private String description;
}
