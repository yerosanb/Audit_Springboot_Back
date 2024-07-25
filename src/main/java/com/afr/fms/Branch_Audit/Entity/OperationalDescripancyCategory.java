package com.afr.fms.Branch_Audit.Entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationalDescripancyCategory {
    private Long id;
    private String name;
    private String description;
    private String audit_type;

}
