package com.afr.fms.Branch_Audit.Reviewer.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Reviewer.Mapper.NegotiableInstrumentReviewerMapper;

@Service
public class NegotiableInstrumentReviewerService {

    @Autowired
    private NegotiableInstrumentReviewerMapper negotiableInstrumentReviewerMapper;

    public List<BranchFinancialAudit> getPendingAudits(User user) {
        return negotiableInstrumentReviewerMapper.getPendingFindings(user.geting(), user);
    }

    public List<BranchFinancialAudit> getReviewedAudits(Long reviewer_id) {

        return negotiableInstrumentReviewerMapper.getReviewedAudits(reviewer_id);
    }

  

    

   
}
