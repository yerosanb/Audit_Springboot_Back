package com.afr.fms.Common.Mapper;

import com.afr.fms.Common.Entity.Functionalities;

import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface FunctionalitiesMapper {

        @Select("select * from functionality ORDER BY name")
        @Results(value = {
                        @Result(property = "id", column = "id")
        })
        public List<Functionalities> getAllFunctionalities();

        @Select("select * from functionality where id in (select functionality_id from role_functionality where role_id = #{role_id}  and status = 1) and status = 1")
        @Results(value = {
                        @Result(property = "id", column = "id")
        })
        public List<Functionalities> getFunctionalitiesByRole(Long role_id);

        @Select("select f.id, f.name, f.description, rf.status from functionality f "
                        + " inner join role_functionality rf on rf.functionality_id = f.id and rf.role_id = #{role_id} where f.status = 1 ")
        @Results(value = {
                        @Result(property = "id", column = "id")
        })
        public List<Functionalities> getAllFunctionalitiesByRole(Long role_id);

        @Update("update role_functionality set status = #{status} where role_id = #{role_id} and functionality_id = #{functionality_id}")
        public void updateRoleFunctionalitiesById(@Param("role_id") Long role_id,
                        @Param("functionality_id") Long functionality_id, @Param("status") int status);

        @Update("update functionality set status = #{status} where id = #{id}")
        public void updateFunctionalitiesById(
                         @Param("id") Long id, @Param("status") int status);

}
