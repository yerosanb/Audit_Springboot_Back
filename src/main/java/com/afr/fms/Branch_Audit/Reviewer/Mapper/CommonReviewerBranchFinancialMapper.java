package com.afr.fms.Branch_Audit.Reviewer.Mapper;

import java.util.List;
import org.apache.ibatis.annotations.*;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;

@Mapper
public interface CommonReviewerBranchFinancialMapper {

	@Update("update branch_financial_audit set  reviewer_id=#{reviewer.id}, review_status = 1 , reviewed_date= CURRENT_TIMESTAMP, finding_status= 'Reviewed' where id=#{id}")
	public void reviewFinding(BranchFinancialAudit audit);

	@Update("update branch_financial_audit set  reviewer_id=#{reviewer.id}, review_status = 0, approve_status = 0 , finding_status= 'Unreviewed'  where id=#{id}")
	public void unReviewFinding(BranchFinancialAudit audit);

	@Update("update branch_financial_audit set reviewer_id=#{reviewer.id}, review_status = 2, reviewer_rejected_date = CURRENT_TIMESTAMP, finding_status= 'Reviewer Rejected' where id=#{id}")
	public void cancelFinding(BranchFinancialAudit audit);

	@Select("select * from branch_financial_audit where reviewer_id = #{reviewer_id} and rectification_status !=1 and review_status = 1 and status = 1 Order By reviewed_date")
	@Results(value = {
			@Result(property = "id", column = "id"),
	})
	public List<BranchFinancialAudit> getAuditsOnProgressAudits(Long reviewer_id);

	@Select("select * from branch_financial_audit where id = #{audit_id}")
	@Results(value = {
			@Result(property = "id", column = "id"),
	})
	public BranchFinancialAudit getAudit(Long audit_id);

	

}
