package com.afr.fms.Branch_Audit.Dashboard.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.afr.fms.Branch_Audit.Dashboard.Model.DashboardRecentActivities;
import com.afr.fms.Branch_Audit.Dashboard.Model.DashboardResponse;
import com.afr.fms.Branch_Audit.Dashboard.Model.DashboardResponseFive;
import com.afr.fms.Branch_Audit.Dashboard.Model.DashboardResponseFour;
import com.afr.fms.Branch_Audit.Dashboard.Model.DashboardResponseOne;
import com.afr.fms.Branch_Audit.Dashboard.Model.DashboardResponseThree;
import com.afr.fms.Branch_Audit.Dashboard.Model.DashboardResponseTwo;

@Mapper
public interface DashboardMapper {

	@SelectProvider(type = DashboardSqlProvider.class, method = "getBranchAuditDashboard")
	DashboardResponse getBranchAuditDashboard(@Param("user_id") Long long3, @Param("user_roles") String[] strings3,
			@Param("user_region_id") Long long5);

	@Select("select top 5 * from recent_activity where user_id = #{user_id} order by id desc")
//	@Select("select top 5 * from recent_activity order by id desc")
	List<DashboardRecentActivities> getRecentActivities(@Param("user_id") Long user_id);

	@SelectProvider(type = DashboardSqlProvider.class, method = "getResponseOne")
	DashboardResponseOne getResponseOne(@Param("user_id") Long long3, @Param("user_roles") String[] strings3,
			@Param("user_region_id") Long long5);

	@SelectProvider(type = DashboardSqlProvider.class, method = "getResponseTwo")
	DashboardResponseTwo getResponseTwo(@Param("user_id") Long long3, @Param("user_roles") String[] strings3,
			@Param("user_region_id") Long long5);

	@SelectProvider(type = DashboardSqlProvider.class, method = "getResponseThree")
	DashboardResponseThree getResponseThree(@Param("user_id") Long long3, @Param("user_roles") String[] strings3,
			@Param("user_region_id") Long long5);

	@SelectProvider(type = DashboardSqlProvider.class, method = "getResponseFour")
	DashboardResponseFour getResponseFour(@Param("user_id") Long long3, @Param("user_roles") String[] strings3,
			@Param("user_region_id") Long long5);
	
	@SelectProvider(type = DashboardSqlProvider.class, method = "getResponseFive")
	DashboardResponseFive getResponseFive(@Param("user_id") Long long3, @Param("user_roles") String[] strings3,
			@Param("user_region_id") Long long5);
}
