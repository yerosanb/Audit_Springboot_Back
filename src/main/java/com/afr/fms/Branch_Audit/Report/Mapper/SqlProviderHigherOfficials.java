package com.afr.fms.Branch_Audit.Report.Mapper;

import java.util.Arrays;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class SqlProviderHigherOfficials {

  public String fetchReportHigherOfficials(
    @Param("region_id") Long region_id,
    @Param("branch_id") Long branch_id,
    @Param("module_type") String module_type,
    @Param("risk_level") String risk_level,
    @Param("amount_min") Double amount_min,
    @Param("amount_max") Double amount_max,
    @Param("user_id") Long user_id,
    @Param("user_roles") String[] user_roles
  ) {
    System.out.println("region_id: " + region_id);
    System.out.println("branch_id: " + branch_id);
    System.out.println("module_type: " + module_type);
    System.out.println("risk_level: " + risk_level);
    System.out.println("amount_min: " + amount_min);
    System.out.println("amount_max: " + amount_max);
    System.out.println("user_id: " + user_id);
    System.out.println("user_roles: " + Arrays.toString(user_roles));

    SQL sql = new SQL();
    if (module_type.equalsIgnoreCase("IS")) {} else if (
      module_type.equalsIgnoreCase("Management")
    ) {}
    if (module_type.equalsIgnoreCase("Inspection")) {}
    if (module_type.equalsIgnoreCase("Branch Financial")) {}

    return sql.toString();
  }
}
