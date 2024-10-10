package com.afr.fms.Branch_Audit.Reviewer.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Reviewer.Mapper.LongOutstandingItemsReviewerMapper;

@Service
public class LongOutstandingItemsReviewerService {
    @Autowired
    private LongOutstandingItemsReviewerMapper mapper;

    public List<BranchFinancialAudit> getLongOutstandingItemsForReviewer(User user) {
        try {
            return mapper.getLongOutstandingItemsForReviewer(user.geting(), user);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public List<BranchFinancialAudit> getReviewedLongOutstandingItems(Long reviewer_d) {
        try {
            return mapper.getReviewedLongOutstandingItems(reviewer_d);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

}
