package com.afr.fms.Branch_Audit.Reviewer.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Reviewer.Mapper.LoanAndAdvanceReviewerMapper;

@Service
public class LoanAndAdvanceReviewerService {

    @Autowired
    private LoanAndAdvanceReviewerMapper loanMapper;

    
    public List<BranchFinancialAudit> getReviewedLoanAndAdvance(Long reviewer_d) {
        return loanMapper.getReviewedLoanAndAdvance(reviewer_d);
    }

   

    public List<BranchFinancialAudit> getAuditsForReviewer(User user ) {
        return loanMapper.getPendingAudits(user.geting(), user);
    }

}
