package com.afr.fms.Branch_Audit.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusOfAudit {
	private Long id;
	private Long branch_audit_id;
	private Integer number_of_back_log_day;
	private Integer is_updated;
	private String justification; 
}
