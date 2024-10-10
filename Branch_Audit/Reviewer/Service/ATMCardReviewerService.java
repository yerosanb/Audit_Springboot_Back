package com.afr.fms.Branch_Audit.Reviewer.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Reviewer.Mapper.ATMCardReviewerMapper;

@Service
public class ATMCardReviewerService {

    @Autowired
    private ATMCardReviewerMapper atmCardMapper;

    public List<BranchFinancialAudit> getPendingAuditsForReviewer(User user) {

        return atmCardMapper.getPendingAuditsForReviewer(user.geting(), user);
    }

    public List<BranchFinancialAudit> getReviewedBranchFindings(Long reviewer_d) {
        return atmCardMapper.getReviewedBranchFindings(reviewer_d);
    }
}
