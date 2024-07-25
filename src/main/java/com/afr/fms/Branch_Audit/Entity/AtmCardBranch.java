package com.afr.fms.Branch_Audit.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtmCardBranch {
    private Long id;
    private Long branch_audit_id;
    private Long issued_card;
    private Long distributed_card;
    private Long returned_card;
    private Long remaining_card;

}
