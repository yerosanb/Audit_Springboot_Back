package com.afr.fms.Admin.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.afr.fms.Admin.Entity.SMS;

@Mapper
public interface SMSMapper {
	@Insert("insert into SMS(user_name, password, message, status)values(#{user_name}, #{password}, #{message}, #{status})")
	public void createSMS(SMS sms);

	@Select("select * from SMS Order By user_name")
	public List<SMS> getSMS();

	@Select("select * from SMS where status = 1 ")
	public List<SMS> getActiveSMS();

	@Update("update SMS set user_name=#{user_name}, password=#{password}, message=#{message}, status=#{status} where id=#{id}")
	public void updateSMS(SMS sms);

	@Update("update SMS set status=#{status} where id=#{id}")
	public void manageSMSStatus(SMS sms);
}
