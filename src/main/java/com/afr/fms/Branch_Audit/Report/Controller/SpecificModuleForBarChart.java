package com.afr.fms.Branch_Audit.Report.Controller;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
class SpecificModuleForBarChart {
  private String reported_timestamp;
  private Double reported_value;
  private String approved_timestamp;
  private Double approved_value;
  private String rectified_timestamp;
  private Double rectified_value;
  private String unrectified_timestamp;
  private Double unrectified_value;
}
