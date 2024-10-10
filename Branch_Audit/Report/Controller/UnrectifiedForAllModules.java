package com.afr.fms.Branch_Audit.Report.Controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
class UnrectifiedForAllModules {
    String title;
    Double low;
    Double medium;
    Double high;
}
