package com.afr.fms.Branch_Audit.Report.Mapper;

import com.afr.fms.Branch_Audit.Report.Model.ReportBranch;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

@Mapper
public interface ReportMapper_HigherOfficials {
  @SelectProvider(
    type = SqlProviderHigherOfficials.class,
    method = "fetchReportHigherOfficials"
  )
  List<ReportBranch> fetchReportHigherOfficials(
    @Param("region_id") Long region_id,
    @Param("branch_id") Long branch_id,
    @Param("module_type") String module_type,
    @Param("risk_level") String risk_level,
    @Param("amount_min") Double amount_min,
    @Param("amount_max") Double amount_max,
    @Param("user_id") Long user_id,
    @Param("user_roles") String[] user_roles
  );
}
