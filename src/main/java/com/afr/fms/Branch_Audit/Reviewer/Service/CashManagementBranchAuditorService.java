
package com.afr.fms.Branch_Audit.Reviewer.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Reviewer.Mapper.CashManagementBranchAuditorMapper;

@Service
public class CashManagementBranchAuditorService {
    @Autowired
    private CashManagementBranchAuditorMapper reviewerMapper;


    public List<BranchFinancialAudit> getReviewedFindings(Long reviewer_id) {
        return reviewerMapper.getReviewedFindings(reviewer_id);
    }

    public List<BranchFinancialAudit> getRejectedFindings(Long reviewer_d) {
        return reviewerMapper.getRejectedFindings(reviewer_d);
    }

    public List<BranchFinancialAudit> getReviewedFindingsStatus(Long reviewer_d) {
        return reviewerMapper.getReviewedFindingsStatus(reviewer_d);
    }

    public List<BranchFinancialAudit> getAuditsForReviewer(User user) {
        return reviewerMapper.getPendingAudits(user.getBanking(), user);
    }

}



