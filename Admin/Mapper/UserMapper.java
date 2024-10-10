package com.afr.fms.Admin.Mapper;

import java.util.List;
import org.apache.ibatis.annotations.*;

import com.afr.fms.Admin.Entity.Role;
import com.afr.fms.Admin.Entity.User;

@Mapper
public interface UserMapper {

        @Select("select * from [afrfms].[dbo].[user]")
        @Results(value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "employee_id", column = "emp_id"),
                        @Result(property = "photoUrl", column = "photo_url"),
                        @Result(property = "jobPosition", column = "job_position_id", one = @One(select = "com.afr.fms.Admin.Mapper.JobPositionMapper.getJobPositionById")),
                        @Result(property = "region", column = "region_id", one = @One(select = "com.afr.fms.Admin.Mapper.RegionMapper.getRegionById")),
                        @Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
                        @Result(property = "user_security", column = "id", one = @One(select = "com.afr.fms.Security.UserSecurity.mapper.UserSecurityMapper.getUserSecurityInfoByUserId")),
                        @Result(property = "userCopyFromHR", column = "emp_id", one = @One(select = "com.afr.fms.Admin.Mapper.CopyHRUsersMapper.checkUserEmployeeId")),
                        @Result(property = "roles", javaType = List.class, column = "id", many = @Many(select = "com.afr.fms.Admin.Mapper.UserRoleMapper.getRolesByUserId"))
        })
        public List<User> getUsers();

        @Select("select * from [afrfms].[dbo].[user]")
        @Results(value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "employee_id", column = "emp_id"),
                        @Result(property = "photoUrl", column = "photo_url"),
                        @Result(property = "region", column = "region_id", one = @One(select = "com.afr.fms.Admin.Mapper.RegionMapper.getRegionById")),
                        @Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
                        @Result(property = "user_security", column = "id", one = @One(select = "com.afr.fms.Security.UserSecurity.mapper.UserSecurityMapper.getUserSecurityInfoByUserId")),
                        @Result(property = "roles", javaType = List.class, column = "id", many = @Many(select = "com.afr.fms.Admin.Mapper.UserRoleMapper.getRolesByUserId"))
        })
        public List<User> getUsersStatus();

        @Select("SELECT * FROM [afrfms].[dbo].[user] WHERE id= #{id}")
        @Results(value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "employee_id", column = "emp_id"),
                        @Result(property = "photoUrl", column = "photo_url"),
                        @Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
                        @Result(property = "roles", javaType = List.class, column = "id", many = @Many(select = "com.afr.fms.Admin.Mapper.RoleMapper.getRolesByUserId")),
                        @Result(property = "region", column = "region_id", one = @One(select = "com.afr.fms.Admin.Mapper.RegionMapper.getRegionById")),
                        @Result(property = "user_security", column = "id", one = @One(select = "com.afr.fms.Security.UserSecurity.mapper.UserSecurityMapper.getUserSecurityInfoByUserId")),
                        @Result(property = "userCopyFromHR", column = "emp_id", one = @One(select = "com.afr.fms.Admin.Mapper.CopyHRUsersMapper.checkUserEmployeeId"))

        })
        public User getUserById(Long id);

        @Select("SELECT * FROM [afrfms].[dbo].[user] WHERE id= #{id}")
        @Results(value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "employee_id", column = "emp_id"),
                        @Result(property = "photoUrl", column = "photo_url"),
                        @Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
                        @Result(property = "region", column = "region_id", one = @One(select = "com.afr.fms.Admin.Mapper.RegionMapper.getRegionById")),

                        @Result(property = "roles", javaType = List.class, column = "id", many = @Many(select = "com.afr.fms.Admin.Mapper.RoleMapper.getRolesByUserId")),
        })
        public User getAuditorById(Long id);

        // @Select("SELECT * FROM [afrfms].[dbo].[user] WHERE first_name like
        // '%'+#{key}+'%' "
        // + "or last_name like '%'+#{key}+'%' "
        // + "or email like '%'+#{key}+'%' "
        // + "or phone_number like '%'+#{key}+'%' ")
        public List<User> searchUser(String key);

        @Insert("INSERT INTO [afrfms].[dbo].[user](first_name, middle_name, last_name, email, password, phone_number, status, branch_id, photo_url, region_id, gender, emp_id, job_position_id, category, ing)"
                        + " VALUES (#{first_name},#{middle_name}, #{last_name}, #{email}, #{password}, #{phone_number},0, #{branch.id}, #{photoUrl}, #{region.id}, #{gender}, #{employee_id}, #{jobPosition.id}, #{category}, #{ing})")
        public void create_user(User user);

        @Update("UPDATE [afrfms].[dbo].[user] "
                        + " SET first_name = #{first_name}, "
                        + " last_name = #{last_name}, "
                        + " middle_name = #{middle_name}, "
                        + " email = #{email}, "
                        + " phone_number = #{phone_number}, "
                        + " status = #{status}, "
                        + " branch_id = #{branch.id}, "
                        + " region_id = #{region.id}, "
                        + " emp_id = #{employee_id} ,"
                        + " photo_url = #{photoUrl} ,"
                        + " gender = #{gender},"
                        + " category = #{category}, "
                        + " ing = #{ing}"
                        + " WHERE id = #{id}")
        public void updateUser(User user);

        @Update("UPDATE [afrfms].[dbo].[user] set"
                        + " status = #{status}, "
                        + " branch_id = #{branch.id}, "
                        + " region_id = #{region.id}, "
                        + " job_position_id = #{jobPosition.id}"
                        + " WHERE id = #{id}")
        public void updateUserScheduler(User user);

        @Update("UPDATE [afrfms].[dbo].[user] SET status=#{status} WHERE id=#{id}")
        public void changeUserStatus(Long id, Boolean status);

        @Update("UPDATE [afrfms].[dbo].[user] "
                        + "SET status = 1 WHERE id=#{id}")
        public void accountVerified(Long id);

        @Select("select id from [afrfms].[dbo].[user] where email = #{email}")
        public long getUserIdByEmail(String email);

        @Select("select email from [afrfms].[dbo].[user] where id = #{id}")
        public long getEmailById(Long id);

        @Select("select * from [afrfms].[dbo].[user] where id = #{id}")
        @Results(value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
                        @Result(property = "region", column = "region_id", one = @One(select = "com.afr.fms.Admin.Mapper.RegionMapper.getRegionById")),
        })
        public User findById(Long id);

        @Select("select * from [afrfms].[dbo].[user] where email = #{email}")
        @Results(value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "employee_id", column = "emp_id"),
                        @Result(property = "userCopyFromHR", column = "emp_id", one = @One(select = "com.afr.fms.Admin.Mapper.CopyHRUsersMapper.checkUserEmployeeId")),
                        @Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
                        @Result(property = "region", column = "region_id", one = @One(select = "com.afr.fms.Admin.Mapper.RegionMapper.getRegionById")),
                        @Result(property = "photoUrl", column = "photo_url"),
                        @Result(property = "user_security", column = "id", one = @One(select = "com.afr.fms.Security.UserSecurity.mapper.UserSecurityMapper.getUserSecurityInfoByUserId")),
                        @Result(property = "roles", javaType = List.class, column = "id", many = @Many(select = "com.afr.fms.Admin.Mapper.RoleMapper.getRolesByUserId"))
        })
        public User findByUsername(String email);

        @Select("select * from [afrfms].[dbo].[user] where emp_id = #{emp_id}")
        @Results(value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "employee_id", column = "emp_id"),
                        @Result(property = "userCopyFromHR", column = "emp_id", one = @One(select = "com.afr.fms.Admin.Mapper.CopyHRuserMapper.checkUserEmployeeId")),
                        @Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
                        @Result(property = "region", column = "region_id", one = @One(select = "com.afr.fms.Admin.Mapper.RegionMapper.getRegionById")),
                        @Result(property = "photoUrl", column = "photo_url"),
                        @Result(property = "jobPosition", column = "job_position_id", one = @One(select = "com.afr.fms.Admin.Mapper.JobPositionMapper.getJobPositionById")),
                        @Result(property = "user_security", column = "id", one = @One(select = "com.afr.fms.Security.UserSecurity.mapper.UserSecurityMapper.getUserSecurityInfoByUserId")),
                        @Result(property = "roles", javaType = List.class, column = "id", many = @Many(select = "com.afr.fms.Admin.Mapper.UserRoleMapper.getRolesByUserId"))
        })
        public User findByEmployeeID(String emp_id);

        @Select("select * from [afrfms].[dbo].[user] where emp_id = #{emp_id}")
        @Results(value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "employee_id", column = "emp_id"),
                        @Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
                        @Result(property = "region", column = "region_id", one = @One(select = "com.afr.fms.Admin.Mapper.RegionMapper.getRegionById")),
                        @Result(property = "jobPosition", column = "job_position_id", one = @One(select = "com.afr.fms.Admin.Mapper.JobPositionMapper.getJobPositionById")),
                        @Result(property = "roles", javaType = List.class, column = "id", many = @Many(select = "com.afr.fms.Admin.Mapper.UserRoleMapper.getRolesByUserId"))
        })
        public User findByEmployeeIDScheduler(String emp_id);

        @Select("select * from [afrfms].[dbo].[user] where email = #{email} and status = 1")
        @Results(value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "employee_id", column = "emp_id"),
                        @Result(property = "photoUrl", column = "photo_url"),
                        @Result(property = "user_security", column = "id", one = @One(select = "com.afr.fms.Security.UserSecurity.mapper.UserSecurityMapper.getUserSecurityInfoByUserId")),
                        @Result(property = "roles", javaType = List.class, column = "id", many = @Many(select = "com.afr.fms.Admin.Mapper.UserRoleMapper.getRolesByUserId"))
        })
        public User findByVerifiedEmail(String email);

        @Select("select * from [afrfms].[dbo].[user] where phone_number = #{phone_number} and status = 1")
        @Results(value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "employee_id", column = "emp_id"),
                        @Result(property = "photoUrl", column = "photo_url"),
                        @Result(property = "user_security", column = "id", one = @One(select = "com.afr.fms.Security.UserSecurity.mapper.UserSecurityMapper.getUserSecurityInfoByUserId")),
                        @Result(property = "roles", javaType = List.class, column = "id", many = @Many(select = "com.afr.fms.Admin.Mapper.UserRoleMapper.getRolesByUserId"))
        })
        public User findByVerifiedPhoneNumber(String phone_number);

        @Select("select u.* from [afrfms].[dbo].[user] u join loan_user lu on u.id = lu.user_id where lu.loan_id = #{loanId}")
        @Results(value = {

                        @Result(property = "id", column = "id"),
                        @Result(property = "employee_id", column = "emp_id"),
                        @Result(property = "userCopyFromHR", column = "emp_id", one = @One(select = "com.afr.fms.Admin.Mapper.CopyHRuserMapper.checkUserEmployeeId")),
                        @Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
                        @Result(property = "region", column = "region_id", one = @One(select = "com.afr.fms.Admin.Mapper.RegionMapper.getRegionById")),
        })

        public User getUserByLoanId(Long loanId);

        @Select("Select photo_url from [afrfms].[dbo].[user] where id=#{id}")
        public String getPhotoUrlById(Long id);

        @Select("select count(u.id) as numberOfMakers from [afrfms].[dbo].[user] u "
                        + "inner join user_role ur on ur.user_id = u.id and ur.role_id in (select id from role where code = 'AUDITOR')"
                        + " where u.branch_id in (select br.id from branch br where br.region_id=#{region_id}) or u.region_id =#{region_id}")
        public Long getMakersPerRegion(Long region_id);

        @Select("select count(u.id) as numberOfApprovers from [afrfms].[dbo].[user] u " +
                        "inner join user_role ur on ur.user_id = u.id and ur.role_id in (select id from role where code = 'APPROVER')"
                        + " where u.region_id=#{region_id}")
        public Long getApproversPerRegion(Long region_id);

        @Select("select * from [afrfms].[dbo].[user] where email = #{email}")
        @Results(value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "jobPosition", column = "job_position_id", one = @One(select = "com.afr.fms.Admin.Mapper.JobPositionMapper.getJobPositionById")),
        })
        public User findByEmail(String email);

        @Select("select * from [afrfms].[dbo].[user] where branch_id = #{branch_id}")
        @Results(value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "employee_id", column = "emp_id"),
                        @Result(property = "photoUrl", column = "photo_url"),
                        @Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
                        @Result(property = "roles", javaType = List.class, column = "id", many = @Many(select = "com.afr.fms.Admin.Mapper.RoleMapper.getRolesByUserId")),
        })
        public List<User> findUserByBranchID(Long branch_id);

        // checkEmployeeIdSystem
        @Select("select * from [afrfms].[dbo].[user] where emp_id = #{employee_id}")
        @Results(value = {
                        @Result(property = "id", column = "id"),
        })
        public User checkEmployeeIdSystem(String employee_id);

        @Select("select * from [afrfms].[dbo].[user] where job_position_id = #{job_position_id}")
        @Results(value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "jobPosition", column = "job_position_id", one = @One(select = "com.afr.fms.Admin.Mapper.JobPositionMapper.getJobPositionById")),
        })
        public List<User> getUsersByJobPositionId(Long job_position_id);

        @Select("select * from [afrfms].[dbo].[user] where job_position_id = #{job_position_id} and category = #{role_location}")
        @Results(value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "jobPosition", column = "job_position_id", one = @One(select = "com.afr.fms.Admin.Mapper.JobPositionMapper.getJobPositionById")),
                        @Result(property = "roles", javaType = List.class, column = "id", many = @Many(select = "com.afr.fms.Admin.Mapper.RoleMapper.getRolesByUserId")),

        })
        public List<User> getUsersByJobPositionIdandRolePosition(Long job_position_id, String role_location);

        @Select("Select * from [afrfms].[dbo].[user] where id in (SELECT user_id from user_role where role_id = #{role_id})")
        @Results(value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "jobPosition", column = "job_position_id", one = @One(select = "com.afr.fms.Admin.Mapper.JobPositionMapper.getJobPositionById")),
        })
        public List<User> getUsersByRole(Long role_id);

        @Select("Select * from [afrfms].[dbo].[user] where id in (SELECT user_id from user_role where role_id in (select id from role where code = #{code})) and category= #{category}")
        @Results(value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "jobPosition", column = "job_position_id", one = @One(select = "com.afr.fms.Admin.Mapper.JobPositionMapper.getJobPositionById")),
        })
        public User getUserByRole(String code, String category);

        @Delete("delete from [afrfms].[dbo].[user] where id = #{user_id}")
        public void deleteUserById(Long user_id);

        @Select("SELECT * FROM [afrfms].[dbo].[user] WHERE category= #{category}")
        @Results(value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "employee_id", column = "emp_id"),
                        @Result(property = "photoUrl", column = "photo_url"),
                        @Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
                        @Result(property = "roles", javaType = List.class, column = "id", many = @Many(select = "com.afr.fms.Admin.Mapper.RoleMapper.getRolesByUserId")),
                        @Result(property = "jobPosition", column = "job_position_id", one = @One(select = "com.afr.fms.Admin.Mapper.JobPositionMapper.getJobPositionById")),
        })
        public List<User> getUserByCategory(String category);

        @Select("SELECT * FROM [afrfms].[dbo].[user] WHERE id in (select user_id from user_role where role_id in ( select id from role where code = #{role} )) and category= #{category} ")
        @Results(value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "employee_id", column = "emp_id"),
                        @Result(property = "photoUrl", column = "photo_url"),
                        @Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
                        @Result(property = "roles", javaType = List.class, column = "id", many = @Many(select = "com.afr.fms.Admin.Mapper.RoleMapper.getRolesByUserId")),
                        @Result(property = "jobPosition", column = "job_position_id", one = @One(select = "com.afr.fms.Admin.Mapper.JobPositionMapper.getJobPositionById")),
        })
        public List<User> getUserByCategoryandRole(String category, String role);

        @Select("SELECT * FROM [afrfms].[dbo].[user] WHERE id in (select user_id from user_role where role_id in ( select id from role where code = #{role} )) and branch_id = #{branch_id}")
        @Results(value = {
                        @Result(property = "id", column = "id"),
                        @Result(property = "employee_id", column = "emp_id"),
                        @Result(property = "photoUrl", column = "photo_url"),
                        @Result(property = "branch", column = "branch_id", one = @One(select = "com.afr.fms.Admin.Mapper.BranchMapper.getBranchById")),
                        @Result(property = "roles", javaType = List.class, column = "id", many = @Many(select = "com.afr.fms.Admin.Mapper.RoleMapper.getRolesByUserId")),
                        @Result(property = "jobPosition", column = "job_position_id", one = @One(select = "com.afr.fms.Admin.Mapper.JobPositionMapper.getJobPositionById")),
        })
        public List<User> getUserByBranchandRole(Long branch_id, String role);

        @Insert("insert into user_role (user_id, role_id) values (#{id}, #{role.id})")
        public void addUserRole(Long id, Role role);

        @Delete("delete from user_role where user_id = #{id}")
        public void removeAllUserRoles(Long id);

        @Delete("delete from user_role where user_id = #{user_id} and role_id = #{role_id}")
        public void removeRolesByUserRole(Long user_id, Long role_id);

}
