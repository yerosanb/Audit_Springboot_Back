package com.afr.fms.Branch_Audit.Reviewer.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Reviewer.Mapper.SuspenseAccountReviewerMapper;

@Service
public class SuspenseAccountReviewerService {
    @Autowired
    private SuspenseAccountReviewerMapper suspenseAccountReviewerMapper;


    public List<BranchFinancialAudit> getPendingAudits(User user) {

        return suspenseAccountReviewerMapper.getPendingAudits(user.getBanking(), user);
    }

    public List<BranchFinancialAudit> getReviewedAudits(Long reviewer_id) {

        return suspenseAccountReviewerMapper.getReviewedAudits(reviewer_id);
    }

}
