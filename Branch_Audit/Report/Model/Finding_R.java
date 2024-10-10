package com.afr.fms.Branch_Audit.Report.Model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Finding_R {
	private Long id;
	private String content;
	private String identifier;
	private Date created_date;
}
