package com.afr.fms.Branch_Audit.Reviewer.Service;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Admin.Entity.Role;
import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Admin.Mapper.UserMapper;
import com.afr.fms.Branch_Audit.Common.Audit_Remark.RemarkBranchAudit;
import com.afr.fms.Branch_Audit.Common.Audit_Remark.RemarkBranchAuditService;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Reviewer.Mapper.CommonReviewerBranchFinancialMapper;
import com.afr.fms.Common.RecentActivity.RecentActivity;
import com.afr.fms.Common.RecentActivity.RecentActivityMapper;
import com.afr.fms.Payload.endpoint.Endpoint;
import com.afr.fms.Security.email.context.Reviewer_for_ApproverContext;
import com.afr.fms.Security.email.service.EmailService;

@Service
public class CommonReviewerBranchFinancialService {
    @Autowired
    private CommonReviewerBranchFinancialMapper reviewerMapper;

    @Autowired
    private RemarkBranchAuditService remarkService;

    @Autowired

    private RecentActivityMapper recentActivityMapper;

    private String baseURL = Endpoint.URL;

    @Autowired
    UserMapper userMapper;

    @Autowired
    private EmailService emailService;

    RecentActivity recentActivity = new RecentActivity();

    public void reviewFindings(BranchFinancialAudit audit) {
        reviewerMapper.reviewFinding(audit);
        List<User> user = userMapper.getUserByCategory(audit.getCategory());
        for (User user2 : user) {
            if (Long.compare(user2.getId(), audit.getReviewer().getId()) == 0) {
                audit.setReviewer(user2);
            }

            for (Role role : user2.getRoles()) {
                if (role.getCode().equalsIgnoreCase("APPROVER")) {
                    audit.setApprover(user2);
                }
            }
        }
        BranchFinancialAudit branchFinancialAudit = reviewerMapper.getAudit(audit.getId());
        recentActivity.setUser(audit.getReviewer());

        recentActivity.setMessage("Audit with " + branchFinancialAudit.getCase_number() + " is reviewed");

        recentActivityMapper.addRecentActivity(recentActivity);

        // try {
        // sendEmailtoApprover(audit);
        // } catch (Exception e) {
        // System.out.print(e);
        // }

    }

    public void cancelFinding(RemarkBranchAudit remark) {
        reviewerMapper.cancelFinding(remark.getBranchFinancialAudit());
        remark.setRejected(true);
        remarkService.addRemark(remark);
    }

    public void unReviewFinding(BranchFinancialAudit audit) {
        reviewerMapper.unReviewFinding(audit);

        BranchFinancialAudit branchFinancialAudit = reviewerMapper.getAudit(audit.getId());

        recentActivity.setUser(audit.getReviewer());

        recentActivity.setMessage("Audit with " + branchFinancialAudit.getCase_number() + " is unreviewed");

        recentActivityMapper.addRecentActivity(recentActivity);
        // RecentActivity recentActivity = new RecentActivity();
        // recentActivity.setMessage("Audit " + audit.getCategory() + " " +
        // audit.getCase_number() + " is unrectified");
        // recentActivity.setUser(audit.getFollowup_officer());
        // recentActivityMapper.addRecentActivity(recentActivity);
    }

    public List<BranchFinancialAudit> getAuditsOnProgressAudits(Long reviewer_id) {
        return reviewerMapper.getAuditsOnProgressAudits(reviewer_id);
        // RecentActivity recentActivity = new RecentActivity();
        // recentActivity.setMessage("Audit " + audit.getCategory() + " " +
        // audit.getCase_number() + " is unrectified");
        // recentActivity.setUser(audit.getFollowup_officer());
        // recentActivityMapper.addRecentActivity(recentActivity);
    }

    public void multiReviewFindings(List<BranchFinancialAudit> audits) {
        User reviewer = audits.get(0).getReviewer();
        User userUser = userMapper.getAuditorById(reviewer.getId());

        for (BranchFinancialAudit BranchFinancialAudit : audits) {
            BranchFinancialAudit.setReviewer(userUser);
            reviewerMapper.reviewFinding(BranchFinancialAudit);

            List<User> user = userMapper.getUserByCategory(BranchFinancialAudit.getCategory());
            for (User user2 : user) {
                for (Role role : user2.getRoles()) {
                    if (role.getCode().equalsIgnoreCase("APPROVER")) {
                        BranchFinancialAudit.setApprover(user2);
                        try {
                            // sendEmailtoApprover(BranchFinancialAudit);
                        } catch (Exception e) {
                            System.out.print(e);
                        }
                    }
                }
            }

        }

    }

    public void unReviewMultipleFindings(List<BranchFinancialAudit> audits) {
        User reviewer = audits.get(0).getReviewer();
        for (BranchFinancialAudit audit : audits) {
            reviewerMapper.unReviewFinding(audit);
        }
    }

    public void sendEmailtoApprover(BranchFinancialAudit audit) {
        Reviewer_for_ApproverContext emailContext = new Reviewer_for_ApproverContext();
        emailContext.init(audit);
        // emailContext.buildApproveRequestUrl(baseURL);
        try {
            emailService.sendMail(emailContext);
            System.out.println("Audit Approve Request is sent");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

}
