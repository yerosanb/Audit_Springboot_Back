package com.afr.fms.Admin.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Collection;
import java.util.List;

import com.afr.fms.Security.UserSecurity.entity.UserSecurity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String first_name;
    private String middle_name;
    private String last_name;
    private String email;
    private String username;
    private String password;
    private String phone_number;
    private boolean status;
    private boolean accountVerified;
    private String gender;
    private Branch branch;
    private Collection<Role> roles;
    private String photoUrl;
    private UserSecurity user_security;
    private Region region;
    private Long admin_id;
    private String employee_id;
    private JobPosition jobPosition;
    private List<JobPosition> jobPositions;
    private UserCopyFromHR userCopyFromHR;
    private String category;
    private boolean authenthication_media;
    private String ing;
}
