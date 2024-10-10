
package com.afr.fms.Branch_Audit.Auditor.Mapper;

import java.util.List;
import org.apache.ibatis.annotations.*;

import com.afr.fms.Branch_Audit.Entity.ControllableExpenseType;

@Mapper
public interface ControllableExpenseTypeAuditorMapper {
	@Insert("insert into controllable_expense_type (name, description)  values (#{name}, #{description})")
	public void createControllableExpenseType(ControllableExpenseType audit);

	@Select("select * from controllable_expense_type")
	public List<ControllableExpenseType> getControllableExpenseType();

	@Delete("delete from uploaded_file_branch where branch_audit_id = #{branch_audit_id}")
	public void removeFileUrls(Long branch_audit_id);

	@Update("update controllable_expense_type set name=#{name}, description = #{description} where id = #{id}")
	public void updateControllableExpenseType(ControllableExpenseType audit);

	@Select("select * from controllable_expense_type where id=#{id}")
	public List<ControllableExpenseType> getControllableExpenseTypeById(Long id);

	@Delete("Delete from  controllable_expense_type where id=#{id}")
	public void deleteBAFinding(Long id);

}
