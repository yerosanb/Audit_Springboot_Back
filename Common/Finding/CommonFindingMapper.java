package com.afr.fms.Common.Finding;

import java.util.List;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CommonFindingMapper {

    @Select("insert into finding( content, created_date, modified_date, user_id,identifier) values(#{content}, CURRENT_TIMESTAMP, #{modified_date}, #{user.id},#{identifier})")
    public Long createCommonFinding(CommonFinding commonFinding);

    @Select("select * from finding where user_id=#{id} order by created_date DESC")
    public List<CommonFinding> getCommonFinding(Long id);

    @Select("select * from finding order by created_date DESC")
    public List<CommonFinding> getCommonFindings();

    @Select("select * from finding where identifier = #{audit_type} order by created_date DESC")
    public List<CommonFinding> getFindingsByAuditType(String audit_type);

    @Update("update finding set content=#{content}, identifier = #{identifier}, modified_date = CURRENT_TIMESTAMP, user_id=#{user.id} where id=#{id}")
    public void updateCommonFinding(CommonFinding commonFinding);

    @Delete("delete from  finding  where id = #{id}")
    public void deleteCommonFinding(Long id);
}
