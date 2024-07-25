package com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit;
import java.util.List;
import org.apache.ibatis.annotations.*;
@Mapper
public interface ChangeTrackerBranchAuditMapper {

	@Insert("insert into change_tracker_branch_audit(user_id, change, content_type, audit_id, change_date) values(#{changer.id}, #{change}, #{content_type}, #{audit_id}, CURRENT_TIMESTAMP)")
	public void insertChange(ChangeTrackerBranchAudit changeTrackerBranchAudit);

	
	@Insert("insert into change_tracker_branch_audit(user_id, change, content_type, compiled_audit_id, change_date) values(#{changer.id}, #{change}, #{content_type}, #{compiled_audit_id}, CURRENT_TIMESTAMP)")
	public void insertCompiledAuditChange(ChangeTrackerBranchAudit changeTrackerBranchAudit);

	@Select("select * from change_tracker_branch_audit where audit_id = #{audit_id} order by change_date")
	@Results(value = {
			@Result(property = "id", column = "id"),
			@Result(property = "changer", column = "user_id", one = @One(select = "com.afr.fms.Admin.Mapper.UserMapper.getAuditorById")),
	})
	public List<ChangeTrackerBranchAudit> getChanges(Long audit_id);

	// @Insert("insert into change_tracker_branch_audit(user_id, change, content_type, audit_id, change_date) values(#{changer.id}, #{change}, #{content_type}, #{audit_id}, CURRENT_TIMESTAMP)")
	// public void insertChangeINS(ChangeTrackerBranchAudit changeTrackerBranchAudit);

}
