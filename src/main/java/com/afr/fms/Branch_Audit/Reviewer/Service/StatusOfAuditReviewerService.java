package com.afr.fms.Branch_Audit.Reviewer.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.afr.fms.Admin.Entity.Role;
import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Admin.Mapper.UserMapper;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Reviewer.Mapper.StatusOfAuditReviewerMapper;

@Service
public class StatusOfAuditReviewerService {
    @Autowired
    private StatusOfAuditReviewerMapper mapper;

    @Autowired
    UserMapper userMapper;

    public List<BranchFinancialAudit> getPendingStatusOfAudit(User user) {
        return mapper.getPendingStatusOfAudit(user.getBanking(), user);
    }

    public List<BranchFinancialAudit> getReviewedStatusOfAudit(Long reviewer_d) {
        try {
            return mapper.getReviewedStatusOfAudit(reviewer_d);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

}
