package com.afr.fms.Branch_Audit.Auditor.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.afr.fms.Branch_Audit.Entity.SuspenseAccountType;

@Mapper
public interface SuspenseAccountTypeMapper {

	@Select("select * from  suspense_account_type")
	public List<SuspenseAccountType> getSuspenceAccountType();

	@Insert(" insert into suspense_account_type(name,description) values(#{name},#{description})")
	public void createSuspenseAccountType(SuspenseAccountType suspenseAccountType);

	@Update("update suspense_account_type set name=#{name}, description=#{description} where id=#{id} ")
	public void updateSuspenceAccountType(SuspenseAccountType suspenseAccountType);

	@Delete("delete from suspense_account_type where id=#{id} ")
	public void deleteSuspenceAccountType(Long id);

	@Select("select * from  suspense_account_type where id=#{suspense_account_id}")
	public SuspenseAccountType getSuspenceAccountTypeById(Long suspense_account_id);

}