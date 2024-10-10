package com.afr.fms.Branch_Audit.Report.Controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
class TotalsForStatChart {
    Double reported;
    Double approved;
    Double rectified;
    Double unrectified;
}
