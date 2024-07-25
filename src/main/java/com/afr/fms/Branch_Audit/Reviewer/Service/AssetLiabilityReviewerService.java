package com.afr.fms.Branch_Audit.Reviewer.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Reviewer.Mapper.AssetLiabilityReviewerMapper;

@Service
public class AssetLiabilityReviewerService {

    @Autowired
    private AssetLiabilityReviewerMapper assetLiabilityReviewerMapper;
 

    public List<BranchFinancialAudit> getPendingAudits(User user) {

        return assetLiabilityReviewerMapper.getPendingFindings(user.getBanking(), user);
    }

    public List<BranchFinancialAudit> getReviewedAudits(Long reviewer_id) {

        return assetLiabilityReviewerMapper.getReviewedAudits(reviewer_id);
    }

}
