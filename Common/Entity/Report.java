package com.afr.fms.Common.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Report {
    private Long user_id;
    private String startDateTime;
    private String endDateTime;
    private String account_id;
    private Long region_id;
    private String institution_code;
    private String branch_code;
    private Long branch_id;

}