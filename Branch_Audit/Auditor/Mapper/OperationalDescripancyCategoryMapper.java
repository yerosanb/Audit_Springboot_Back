package com.afr.fms.Branch_Audit.Auditor.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.afr.fms.Branch_Audit.Entity.OperationalDescripancyCategory;

@Mapper
public interface OperationalDescripancyCategoryMapper {

	@Select("select * from  operational_descripancy_category")
	public List<OperationalDescripancyCategory> getOperationalDescripancyCategory();

	@Select("select * from  operational_descripancy_category where audit_type=#{category}")
	public List<OperationalDescripancyCategory> getOperationalDescripancyCategoryByCategory(String category);

	@Insert(" insert into operational_descripancy_category(name,description,audit_type) values(#{name},#{description},#{audit_type})")
	public void createDescripancyCategory(OperationalDescripancyCategory operationalDescripancyCategory);

	@Update("update operational_descripancy_category set name=#{name}, description=#{description}, audit_type = #{audit_type} where id=#{id} ")
	public void updateOperationalDescripancyCategory(OperationalDescripancyCategory operationalDescripancyCategory);

	@Delete("delete from operational_descripancy_category where id=#{id} ")
	public void deleteOperationalDescriptionalCategory(Long id);

	@Select("select * from  operational_descripancy_category where id=#{operational_descripancy_category_id}")

	public OperationalDescripancyCategory getOperationalDescripancyCategoryById(
			Long operational_descripancy_category_id);

}