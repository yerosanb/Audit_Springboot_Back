package com.afr.fms.Branch_Audit.Report.Controller;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeSeriesData {
  private LocalDateTime timestamp;
  private double value;
}
