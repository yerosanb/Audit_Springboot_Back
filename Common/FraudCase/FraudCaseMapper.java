package com.afr.fms.Common.FraudCase;

import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface FraudCaseMapper {

	@Insert("insert into fraud_case(format, user_id, initial, created_date,case_type,status) values(#{format}, #{user.id}, 0,CURRENT_TIMESTAMP,#{case_type}, 0)")
	public void createFraudCase(FraudCase fraudCase);

	@Select("select * from fraud_case where initial=#{initial}")
	public FraudCase getInitialFraudCase(Long initial);

	@Update("update fraud_case set format=#{format}, user_id=#{user.id}, initial=#{initial} , updated_date=CURRENT_TIMESTAMP where id=#{id}")
	public void updateFraudCase(FraudCase fraudCase);

	@Update("update fraud_case set status=1, approver_id=#{user.id} where id=#{id}")
	public void approveFraudCase(FraudCase fraudCase);

	@Update("update fraud_case set status=0 where id=#{id} and approver_id=#{user.id}")
	public void cancelApprovedFraudCase(FraudCase fraudCase);

	@Select("select * from fraud_case where initial != 1")
	public List<FraudCase> getFraudCases();

	@Select("select * from fraud_case where status =0 ")
	public List<FraudCase> getPendingFraudCases();

	@Select("select * from fraud_case where approver_id= #{user_id} and status=1")
	public List<FraudCase> getApprovedFraudCases(Long user_id);

	@Delete("delete from fraud_case where id=#{id}")
	public void deleteFraudCase(Long id);

}
