package com.afr.fms.Branch_Audit.Reviewer.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Reviewer.Mapper.CashPerformanceReviewerMapper;

@Service
public class CashPerformanceReviewerService {

    @Autowired
    private CashPerformanceReviewerMapper cashPerformanceMapper;

    public List<BranchFinancialAudit> getAuditsForBranchReviewer(User user) {

        return cashPerformanceMapper.getAuditsForBranchReviewer(user.geting(), user);
    }

    public List<BranchFinancialAudit> getReviewed_cash_Findings(Long reviewer_d) {
        return cashPerformanceMapper.getReviewed_cash_Findings(reviewer_d);
    }

}
