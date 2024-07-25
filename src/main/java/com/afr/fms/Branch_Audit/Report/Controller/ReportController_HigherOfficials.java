package com.afr.fms.Branch_Audit.Report.Controller;

import com.afr.fms.Branch_Audit.Report.Model.ReportBranchRequest;
import com.afr.fms.Branch_Audit.Report.Model.ReportBranchRequestHigherOfficials;
import com.afr.fms.Branch_Audit.Report.Service.ReportService_Branch;
import com.afr.fms.Branch_Audit.Report.Service.ReportService_HigherOfficials;
import com.afr.fms.Payload.endpoint.Endpoint;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/branch/report/")
public class ReportController_HigherOfficials {

  @Autowired
  ReportService_HigherOfficials reportService;

  @PostMapping("fetchReportHigherOfficials")
  public ResponseEntity<?> fetchReportHigherOfficials(
    @RequestBody ReportBranchRequestHigherOfficials report_request
  ) {
    try {
      return ResponseEntity
        .status(HttpStatus.OK)
        .body(reportService.fetchReportHigherOfficials(report_request));
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
