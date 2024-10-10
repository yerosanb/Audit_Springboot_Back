package com.afr.fms.Common.RecentActivity;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.afr.fms.Common.Entity.Report;

@Mapper
public interface RecentActivityMapper {

    @Insert("insert into recent_activity (message, created_date ,user_id) values (#{message}, CURRENT_TIMESTAMP, #{user.id})")
    public void addRecentActivity(RecentActivity ra);

    @Select("select top 5 * from recent_activity where user_id = #{user_id} ORDER BY id DESC")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "user", column = "user_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getUserById")),
    })
    public List<RecentActivity> getAllRecentActivity(Long user_id);

    @Select("select top 10 * from recent_activity where user_id = #{user_id} ORDER BY id DESC")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "user", column = "user_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getUserById")),
    })
    public List<RecentActivity> getAllAdminRecentActivity(Long user_id);

    @Select("select * from recent_activity rc where rc.created_date BETWEEN #{startDateTime} AND #{endDateTime} ORDER BY id DESC")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "user", column = "user_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getUserById")),
    })
    public List<RecentActivity> getAllRecentActivityAdmin(Report report);

    @Select("select * from recent_activity rc where rc.user_id=#{user_id} and  rc.created_date BETWEEN #{startDateTime} AND #{endDateTime} ORDER BY id DESC")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "user", column = "user_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getUserById")),
    })
    public List<RecentActivity> getAllRecentActivityByUserId(Report report);
}
