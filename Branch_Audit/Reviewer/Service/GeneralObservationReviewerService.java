package com.afr.fms.Branch_Audit.Reviewer.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Reviewer.Mapper.GeneralObservationReviewerMapper;

@Service
public class GeneralObservationReviewerService {

    @Autowired
    private GeneralObservationReviewerMapper reviewerMapper;

    public List<BranchFinancialAudit> getPendingGeneralObservation(User user) {
        return reviewerMapper.getPendingGeneralObservation(user.geting(), user);
    }

    public List<BranchFinancialAudit> getReviewedGeneralObservationForReviewer(Long reviewer_id) {

        return reviewerMapper.getReviewedGeneralObservationForReviewer(reviewer_id);
    }

}
