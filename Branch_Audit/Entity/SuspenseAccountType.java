package com.afr.fms.Branch_Audit.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuspenseAccountType {
    private Long id;
    private Long suspense_account_id;
    private String name;
    private String description;
}
