package com.afr.fms.Branch_Audit.Reviewer.Service;

import java.util.List;

import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Admin.Entity.Role;
import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Admin.Mapper.UserMapper;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Common.Audit_Remark.RemarkBranchAudit;
import com.afr.fms.Branch_Audit.Common.Audit_Remark.RemarkBranchAuditService;
import com.afr.fms.Branch_Audit.Reviewer.Mapper.OperationalDiscrepancyBranchReviewerMapper;
import com.afr.fms.Common.RecentActivity.RecentActivityMapper;
import com.afr.fms.Payload.endpoint.Endpoint;
import com.afr.fms.Security.email.context.Reviewer_for_ApproverContext;
import com.afr.fms.Security.email.service.EmailService;

@Service
public class OperationalDiscrepancyBranchReviewerService {
    @Autowired
    private OperationalDiscrepancyBranchReviewerMapper reviewerMapper;

    @Autowired
    private RemarkBranchAuditService remarkService;

    @Autowired

    private RecentActivityMapper recentActivityMapper;

    private String baseURL = Endpoint.URL;

    @Autowired
    UserMapper userMapper;

    @Autowired
    private EmailService emailService;

    
    public List<BranchFinancialAudit> getReviewedFindings(Long reviewer_d) {
        return reviewerMapper.getReviewedFindings(reviewer_d);
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
