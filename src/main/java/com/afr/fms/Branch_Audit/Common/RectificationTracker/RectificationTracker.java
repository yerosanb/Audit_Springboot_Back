package com.afr.fms.Branch_Audit.Common.RectificationTracker;

import java.util.List;

import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RectificationTracker {
    private Long id;
    private List<BranchFinancialAudit> audits;
    private BranchFinancialAudit audit;
    private Long audit_id;
    private String rectification_date;
    private boolean status;
    private Long sender;
    private Long reciever;

}
