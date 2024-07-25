package com.afr.fms.Branch_Audit.Core.Service;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Admin.Mapper.UserMapper;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Entity.CompiledBranchAudit;
import com.afr.fms.Branch_Audit.Entity.CompiledRegionalAudit;
import com.afr.fms.Branch_Audit.Approver.Mapper.ApproverBranchMapper;
import com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAudit;
import com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditService;
import com.afr.fms.Branch_Audit.Common.Audit_Remark.RemarkBranchAuditService;
import com.afr.fms.Branch_Audit.Core.Mapper.TrialBalanceMapper;
import com.afr.fms.Common.RecentActivity.RecentActivity;
import com.afr.fms.Common.RecentActivity.RecentActivityMapper;
import com.afr.fms.Payload.endpoint.Endpoint;
import com.afr.fms.Security.email.context.Reviewer_for_ApproverContext;
import com.afr.fms.Security.email.service.EmailService;

@Service
public class TrialBalanceService {

    @Autowired
    private TrialBalanceMapper approverBranchMapper;

    @Autowired
    private RemarkBranchAuditService remarkService;

    @Autowired
    private ChangeTrackerBranchAuditService changeTrackerBranchAuditService;

    @Autowired

    private RecentActivityMapper recentActivityMapper;

    private String baseURL = Endpoint.URL;

    @Autowired
    UserMapper userMapper;

    @Autowired
    private EmailService emailService;

    RecentActivity recentActivity = new RecentActivity();

    public void approveFinding(CompiledRegionalAudit audit) {
        approverBranchMapper.approveFinding(audit);
        this.updateBranchFinancialAudit(audit.getCompiledBranchAudits(), 1, audit.getApprover().getId());
        List<User> user = userMapper.getUserByCategory("BFA");

        recentActivity.setUser(audit.getApprover());

        recentActivity
                .setMessage("Audit with " + audit.getCase_number() + " is approved");

        recentActivityMapper.addRecentActivity(recentActivity);
        // for (User user2 : user) {
        // if (Long.compare(user2.getId(), audit.getReviewer().getId()) == 0) {
        // audit.setReviewer(user2);
        // }

        // for (Role role : user2.getRoles()) {
        // if (role.getCode().equalsIgnoreCase("APPROVER")) {
        // audit.setApprover(user2);
        // }
        // }
        // }

        // try {
        // sendEmailtoApprover(audit);
        // } catch (Exception e) {
        // System.out.print(e);
        // }

    }

    public void updateBranchFinancialAudit(List<CompiledBranchAudit> compiledBranchAudits, int status,
            Long approver_id) {

        for (CompiledBranchAudit compiledBranchAudit : compiledBranchAudits) {
            if (status == 1 || status == 2) {
                approverBranchMapper.updateCompiledBranchAuditAfterApproved(compiledBranchAudit.getId(), approver_id,
                        status);
            }

            for (BranchFinancialAudit branchFinancialAudit : compiledBranchAudit.getBranchFinancialAudits()) {
                if (branchFinancialAudit.getId() != null) {
                    if (status == 1) {
                        approverBranchMapper.updateBranchFinancialAuditAfterApproved(branchFinancialAudit.getId(),
                                status, approver_id);

                    } else if (status == 2) {
                        approverBranchMapper.updateBranchFinancialAuditAfterUnapproved(branchFinancialAudit.getId(),
                                status);

                    } else if (status == 3) {
                        approverBranchMapper.updateBranchFinancialAuditAfterDrafted(branchFinancialAudit.getId(),
                                status, approver_id);
                    }

                    else {
                        approverBranchMapper.updateBranchFinancialAuditAfterEdited(branchFinancialAudit.getId(),
                                status, approver_id);

                    }

                }
            }
        }
    }

    public void unapproveFinding(CompiledRegionalAudit audit) {
        approverBranchMapper.unapproveFinding(audit);
        this.updateBranchFinancialAudit(audit.getCompiledBranchAudits(), 2, audit.getApprover().getId());

        recentActivity.setUser(audit.getApprover());

        recentActivity
                .setMessage("Audit with " + audit.getCase_number() + " is unapproved");

        recentActivityMapper.addRecentActivity(recentActivity);

        // RecentActivity recentActivity = new RecentActivity();
        // recentActivity.setMessage("Audit " + audit.getCategory() + " " +
        // audit.getCase_number() + " is unrectified");
        // recentActivity.setUser(audit.getFollowup_officer());
        // recentActivityMapper.addRecentActivity(recentActivity);
    }

    public void addToDrafting(CompiledRegionalAudit audit) {
        approverBranchMapper.addToDrafting(audit);
        this.updateBranchFinancialAudit(audit.getCompiledBranchAudits(), 3, audit.getApprover().getId());

        // RecentActivity recentActivity = new RecentActivity();
        // recentActivity.setMessage("Audit " + audit.getCategory() + " " +
        // audit.getCase_number() + " is unrectified");
        // recentActivity.setUser(audit.getFollowup_officer());
        // recentActivityMapper.addRecentActivity(recentActivity);
    }

    public void updateCompiledFinding(CompiledRegionalAudit audit) {
        approverBranchMapper.updateCompiledFinding(audit);
        this.updateBranchFinancialAudit(audit.getCompiledBranchAudits(), 4, audit.getApprover().getId());

        for (ChangeTrackerBranchAudit changeTrackerBranchAudit : audit.getChange_tracker_branch_audit()) {
            if (changeTrackerBranchAudit != null) {
                if (changeTrackerBranchAudit != null) {
                    changeTrackerBranchAudit.setCompiled_audit_id(audit.getId());
                    changeTrackerBranchAudit.setChanger(audit.getApprover());
                    changeTrackerBranchAuditService.insertCompiledAuditChanges(changeTrackerBranchAudit);
                }
            }
        }
        // RecentActivity recentActivity = new RecentActivity();
        // recentActivity.setMessage("Audit " + audit.getCategory() + " " +
        // audit.getCase_number() + " is unrectified");
        // recentActivity.setUser(audit.getFollowup_officer());
        // recentActivityMapper.addRecentActivity(recentActivity);
    }

    public List<CompiledRegionalAudit> getPendingAudits() {

        List<CompiledRegionalAudit> compiledRegionalAudits = approverBranchMapper.getPendingCompiledAudits();

        List<CompiledRegionalAudit> populatedCompiledRegionalAudits = new ArrayList<CompiledRegionalAudit>();

        for (CompiledRegionalAudit compiledRegionalAudit : compiledRegionalAudits) {
            List<CompiledBranchAudit> compiledBranchAudits = new ArrayList<CompiledBranchAudit>();

            List<Long> compiled_audits = approverBranchMapper.getMappedCompiledAudits(compiledRegionalAudit.getId());
            for (Long audit_id : compiled_audits) {
                compiledBranchAudits
                        .add(approverBranchMapper.getCompiledBranchAuditForDivision(audit_id));
            }
            for (CompiledBranchAudit compiledBranchAudit : compiledBranchAudits) {
                if (compiledBranchAudit != null) {
                    List<BranchFinancialAudit> branchFinancialAudits = new ArrayList<BranchFinancialAudit>();

                    List<Long> compiled_branch_audits = approverBranchMapper
                            .getMappedCompiledBranchAudits(compiledBranchAudit.getId());
                    for (Long audit_id : compiled_branch_audits) {
                        branchFinancialAudits
                                .add(approverBranchMapper.getBranchFinancialAuditForCompiler(audit_id));
                    }
                    compiledBranchAudit.setBranchFinancialAudits(branchFinancialAudits);
                }
            }
            compiledRegionalAudit.setCompiledBranchAudits(compiledBranchAudits);
            populatedCompiledRegionalAudits.add(compiledRegionalAudit);
        }

        return populatedCompiledRegionalAudits;
    }

    public List<CompiledRegionalAudit> getApprovedAudits(Long approver_id) {

        List<CompiledRegionalAudit> compiledRegionalAudits = approverBranchMapper
                .getApprovedCompiledAudits(approver_id);

        List<CompiledRegionalAudit> populatedCompiledRegionalAudits = new ArrayList<CompiledRegionalAudit>();

        for (CompiledRegionalAudit compiledRegionalAudit : compiledRegionalAudits) {
            List<CompiledBranchAudit> compiledBranchAudits = new ArrayList<CompiledBranchAudit>();

            List<Long> compiled_audits = approverBranchMapper.getMappedCompiledAudits(compiledRegionalAudit.getId());
            for (Long audit_id : compiled_audits) {
                compiledBranchAudits
                        .add(approverBranchMapper.getCompiledBranchAuditForDivision(audit_id));
            }
            for (CompiledBranchAudit compiledBranchAudit : compiledBranchAudits) {
                if (compiledBranchAudit != null) {
                    List<BranchFinancialAudit> branchFinancialAudits = new ArrayList<BranchFinancialAudit>();
                    List<Long> compiled_branch_audits = approverBranchMapper
                            .getMappedCompiledBranchAudits(compiledBranchAudit.getId());
                    for (Long audit_id : compiled_branch_audits) {
                        branchFinancialAudits
                                .add(approverBranchMapper.getBranchFinancialAuditForCompiler(audit_id));
                    }
                    compiledBranchAudit.setBranchFinancialAudits(branchFinancialAudits);
                }
            }
            compiledRegionalAudit.setCompiledBranchAudits(compiledBranchAudits);
            populatedCompiledRegionalAudits.add(compiledRegionalAudit);
        }

        return populatedCompiledRegionalAudits;
    }

    public List<CompiledRegionalAudit> getDraftedAudits(Long approver_id) {

        List<CompiledRegionalAudit> compiledRegionalAudits = approverBranchMapper.getDraftedCompiledAudits(approver_id);

        List<CompiledRegionalAudit> populatedCompiledRegionalAudits = new ArrayList<CompiledRegionalAudit>();

        for (CompiledRegionalAudit compiledRegionalAudit : compiledRegionalAudits) {
            List<CompiledBranchAudit> compiledBranchAudits = new ArrayList<CompiledBranchAudit>();

            List<Long> compiled_audits = approverBranchMapper.getMappedCompiledAudits(compiledRegionalAudit.getId());
            for (Long audit_id : compiled_audits) {
                compiledBranchAudits
                        .add(approverBranchMapper.getCompiledBranchAuditForDivision(audit_id));
            }
            for (CompiledBranchAudit compiledBranchAudit : compiledBranchAudits) {
                if (compiledBranchAudit != null) {
                    List<BranchFinancialAudit> branchFinancialAudits = new ArrayList<BranchFinancialAudit>();

                    List<Long> compiled_branch_audits = approverBranchMapper
                            .getMappedCompiledBranchAudits(compiledBranchAudit.getId());
                    for (Long audit_id : compiled_branch_audits) {
                        branchFinancialAudits
                                .add(approverBranchMapper.getBranchFinancialAuditForCompiler(audit_id));
                    }
                    compiledBranchAudit.setBranchFinancialAudits(branchFinancialAudits);
                }
            }
            compiledRegionalAudit.setCompiledBranchAudits(compiledBranchAudits);
            populatedCompiledRegionalAudits.add(compiledRegionalAudit);
        }

        return populatedCompiledRegionalAudits;
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

    public void closeFinding(CompiledRegionalAudit compiledRegionalAudit) {
        approverBranchMapper.closeFinding(compiledRegionalAudit);
    }

}
