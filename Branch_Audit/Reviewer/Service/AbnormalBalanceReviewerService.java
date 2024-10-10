package com.afr.fms.Branch_Audit.Reviewer.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Reviewer.Mapper.AbnormalBalanceReviewerMapper;

@Service
public class AbnormalBalanceReviewerService {
    @Autowired
    private AbnormalBalanceReviewerMapper abnormalBalanceReviewerMapper;
  

    public List<BranchFinancialAudit> getPendingAudits(User user) {
        return abnormalBalanceReviewerMapper.getPendingAudits(user.geting(), user);
    }


    public List<BranchFinancialAudit> getReviewedAudits(Long reviewer_id) {

        return abnormalBalanceReviewerMapper.getReviewedAudits(reviewer_id);
    }

}
