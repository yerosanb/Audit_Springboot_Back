package com.afr.fms.Branch_Audit.Report.Service;

import com.afr.fms.Branch_Audit.Report.Mapper.ReportMapper_HigherOfficials;
import com.afr.fms.Branch_Audit.Report.Model.ReportBranch;
import com.afr.fms.Branch_Audit.Report.Model.ReportBranchRequest;
import com.afr.fms.Branch_Audit.Report.Model.ReportBranchRequestHigherOfficials;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService_HigherOfficials {

  @Autowired
  ReportMapper_HigherOfficials reportMapper;

  public List<ReportBranch> fetchReportHigherOfficials(
    ReportBranchRequestHigherOfficials report_request
  ) {

    List<ReportBranch> aa = reportMapper.fetchReportHigherOfficials(
      report_request.getRegion(),
      report_request.getBranch(),
      report_request.getModule_type(),
      report_request.getRisk_level(),
      report_request.getAmount_min(),
      report_request.getAmount_max(),
      report_request.getUser_id(),
      report_request.getUser_roles()
    );
    return aa;
  }

}
