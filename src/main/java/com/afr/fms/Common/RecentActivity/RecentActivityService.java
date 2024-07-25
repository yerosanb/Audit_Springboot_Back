package com.afr.fms.Common.RecentActivity;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.afr.fms.Admin.Entity.Role;
import com.afr.fms.Admin.Mapper.UserMapper;
import com.afr.fms.Common.Entity.Report;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecentActivityService {

    @Autowired
    private RecentActivityMapper recentActivityMapper;

    @Autowired
    private UserMapper userMapper;

    public void addRecentActivity(RecentActivity ra) {
        recentActivityMapper.addRecentActivity(ra);
    }

    public List<RecentActivity> getRecentActivityByUserId(Long user_id) {
        Role role = ((List<Role>) userMapper.getUserById(user_id).getRoles()).get(0);
        if (role.getName().equalsIgnoreCase("ROLE_ADMIN")) {
            return recentActivityMapper.getAllAdminRecentActivity(user_id);
        }
        return recentActivityMapper.getAllRecentActivity(user_id);
    }

    public List<RecentActivity> getRecentActivityAdmin(Report report) {
        return recentActivityMapper.getAllRecentActivityAdmin(report);
    }

    public List<RecentActivity> getAllRecentActivityByUserId(Report report) {
        return recentActivityMapper.getAllRecentActivityByUserId(report);
    }
}
