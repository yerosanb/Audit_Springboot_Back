package com.afr.fms.Branch_Audit.Report.Controller;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalsForTimeSeriesModuleSpecific {

  private LocalDateTime reported_timestamp;
  private Double reported_value;
  private LocalDateTime approved_timestamp;
  private Double approved_value;
  private LocalDateTime rectified_timestamp;
  private Double rectified_value;
  private LocalDateTime unrectified_timestamp;
  private Double unrectified_value;
}
