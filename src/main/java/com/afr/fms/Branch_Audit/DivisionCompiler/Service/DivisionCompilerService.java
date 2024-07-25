package com.afr.fms.Branch_Audit.DivisionCompiler.Service;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Admin.Entity.Role;
import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Admin.Mapper.UserMapper;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Entity.CompiledBranchAudit;
import com.afr.fms.Branch_Audit.Common.Audit_Remark.RemarkBranchAudit;
import com.afr.fms.Branch_Audit.Common.Audit_Remark.RemarkBranchAuditService;
import com.afr.fms.Branch_Audit.DivisionCompiler.Mapper.DivisionCompilerMapper;
import com.afr.fms.Branch_Audit.Reviewer.Mapper.IncompleteAccountBranchReviewerMapper;
import com.afr.fms.Common.RecentActivity.RecentActivity;
import com.afr.fms.Common.RecentActivity.RecentActivityMapper;
import com.afr.fms.Payload.endpoint.Endpoint;
import com.afr.fms.Security.email.context.Reviewer_for_ApproverContext;
import com.afr.fms.Security.email.service.EmailService;

@Service
public class DivisionCompilerService {
    @Autowired
    private IncompleteAccountBranchReviewerMapper reviewerMapper;

    @Autowired
    private DivisionCompilerMapper divisionCompilerMapper;

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

    public void reviewFinding(CompiledBranchAudit audit) {
        divisionCompilerMapper.reviewFinding(audit);
        this.updateBranchFinancialAuditAfterReviewed(audit, 1);
        List<User> user = userMapper.getUserByCategory("BFA");

        recentActivity.setUser(audit.getCompiler());

        recentActivity.setMessage("Audit with " + audit.getCase_number() + " is reviewed");

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

    public void updateBranchFinancialAuditAfterReviewed(CompiledBranchAudit compiledBranchAudit, int status) {
        for (BranchFinancialAudit branchFinancialAudit : compiledBranchAudit.getBranchFinancialAudits()) {
            if (branchFinancialAudit.getId() != null) {
                if (status == 1) {
                    divisionCompilerMapper.updateBranchFinancialAuditAfterReviewed(branchFinancialAudit.getId(), status,
                            compiledBranchAudit.getCompiler().getId());

                } else {
                    divisionCompilerMapper.updateBranchFinancialAuditAfterUnreviewed(branchFinancialAudit.getId(),
                            status);

                }

            }

        }
    }

    // public void cancelFinding(RemarkBranchAudit remark) {
    // reviewerMapper.cancelFinding(remark.getAudit());
    // remark.setRejected(true);
    // remarkService.addRemark(remark);
    // }

    public void unreviewFinding(CompiledBranchAudit audit) {
        divisionCompilerMapper.unreviewFinding(audit);
        this.updateBranchFinancialAuditAfterReviewed(audit, 0);

        recentActivity.setUser(audit.getCompiler());

        recentActivity.setMessage("Audit with " + audit.getCase_number() + " is unreviewed");

        recentActivityMapper.addRecentActivity(recentActivity);

        // RecentActivity recentActivity = new RecentActivity();
        // recentActivity.setMessage("Audit " + audit.getCategory() + " " +
        // audit.getCase_number() + " is unrectified");
        // recentActivity.setUser(audit.getFollowup_officer());
        // recentActivityMapper.addRecentActivity(recentActivity);
    }

    public List<BranchFinancialAudit> getRejectedFindings(Long reviewer_d) {
        return reviewerMapper.getRejectedFindings(reviewer_d);
    }

    public List<BranchFinancialAudit> getReviewedFindingsStatus(Long reviewer_d) {
        return reviewerMapper.getReviewedFindingsStatus(reviewer_d);
    }

    public List<CompiledBranchAudit> getPendingAudits(List<Object> parameters) {
        String compiler_category = (String) parameters.get(0);
        String audit_type = (String) parameters.get(1);

        List<CompiledBranchAudit> compiledBranchAudits = divisionCompilerMapper.getPendingAudits(audit_type);

        List<CompiledBranchAudit> populatedCompiledBranchAudits = new ArrayList<CompiledBranchAudit>();

        for (CompiledBranchAudit compiledBranchAudit : compiledBranchAudits) {
            List<BranchFinancialAudit> branchFinancialAudits = new ArrayList<BranchFinancialAudit>();
            List<Long> compiled_audits = divisionCompilerMapper.getMappedCompiledAudits(compiledBranchAudit.getId());

            for (Long audit_id : compiled_audits) {
                if (audit_type.equals("IncompleteAccount")) {

                    branchFinancialAudits
                            .add(divisionCompilerMapper.getIncompleteAccountBranch(audit_id));
                } else if (audit_type.equals("ATMCard")) {
                    branchFinancialAudits
                            .add(divisionCompilerMapper.getAtmCardBranch(audit_id));
                } else if (audit_type.equals("CashCount")) {
                    branchFinancialAudits
                            .add(divisionCompilerMapper.getCashCount(audit_id));
                } else if (audit_type.equals("AbnormalBalance")) {
                    branchFinancialAudits
                            .add(divisionCompilerMapper.get_abnormal_balance_branch(audit_id));
                } else if (audit_type.equals("ControllableExpense")) {
                    branchFinancialAudits
                            .add(divisionCompilerMapper.get_controllable_expense_branch(audit_id));
                } else if (audit_type.equals("AssetLiability")) {
                    branchFinancialAudits
                            .add(divisionCompilerMapper.get_asset_liability_branch(audit_id));
                } else if (audit_type.equals("CashManagement")) {
                    branchFinancialAudits
                            .add(divisionCompilerMapper.get_cash_management_branch(audit_id));
                } else if (audit_type.equals("Dormant")) {
                    branchFinancialAudits
                            .add(divisionCompilerMapper.get_dormant_inactive_account_branch(audit_id));
                } else if (audit_type.equals("OperationalDiscrepancy")) {
                    branchFinancialAudits
                            .add(divisionCompilerMapper.get_operational_descripancy_branch(audit_id, (long) 0,
                                    "Pending"));
                } else if (audit_type.equals("CashPerformance")) {
                    branchFinancialAudits
                            .add(divisionCompilerMapper.getCashPerformanceBranch(audit_id));
                } else if (audit_type.equals("NegotiableInstrument")) {
                    branchFinancialAudits
                            .add(divisionCompilerMapper.getNegotiableInstrumentBranch(audit_id));
                } else if (audit_type.equals("GeneralObservation")) {
                    branchFinancialAudits
                            .add(divisionCompilerMapper.getGeneralObservation(audit_id));
                } else if (audit_type.equals("LongOutstandingItems")) {
                    branchFinancialAudits
                            .add(divisionCompilerMapper.getLongOutstandingItems(audit_id));
                } else if (audit_type.equals("LoanAndAdvance")) {
                    branchFinancialAudits
                            .add(divisionCompilerMapper.getLoanAndAdvance(audit_id));
                } else if (audit_type.equals("StatusOfAudit")) {
                    branchFinancialAudits
                            .add(divisionCompilerMapper.getstatusofAudit(audit_id));
                } else if (audit_type.equals("Contigent")) {
                    branchFinancialAudits
                            .add(divisionCompilerMapper.getmemorandumContigent(audit_id));
                }

                else if (audit_type.equals("ControllableExpense")) {
                    branchFinancialAudits
                            .add(divisionCompilerMapper.get_controllable_expense_branch(audit_id));
                }
                else if (audit_type.equals("SuspenseAccount")) {
                    
                    branchFinancialAudits
                            .add(divisionCompilerMapper.getSuspenseAccountBranch(audit_id));
                }

            }
            compiledBranchAudit.setBranchFinancialAudits(branchFinancialAudits);
            populatedCompiledBranchAudits.add(compiledBranchAudit);
        }
        return populatedCompiledBranchAudits;
    }

    public List<CompiledBranchAudit> getReviewedAudits(List<Object> parameters) {
        Long compiler_id = Long.parseLong((String) parameters.get(0));
        String audit_type = (String) parameters.get(1);

        List<CompiledBranchAudit> compiledBranchAudits = divisionCompilerMapper.getReviewedAudits(audit_type,
                compiler_id);
        List<CompiledBranchAudit> populatedCompiledBranchAudits = new ArrayList<CompiledBranchAudit>();

        for (CompiledBranchAudit compiledBranchAudit : compiledBranchAudits) {
            List<BranchFinancialAudit> branchFinancialAudits = new ArrayList<BranchFinancialAudit>();
            List<Long> compiled_audits = divisionCompilerMapper.getMappedCompiledAudits(compiledBranchAudit.getId());
            for (Long audit_id : compiled_audits) {

                if (audit_type.equals("IncompleteAccount")) {

                    branchFinancialAudits
                            .add(divisionCompilerMapper.getIncompleteAccountBranch(audit_id));
                } else if (audit_type.equals("ATMCard")) {
                    branchFinancialAudits
                            .add(divisionCompilerMapper.getAtmCardBranch(audit_id));
                } else if (audit_type.equals("CashCount")) {
                    // System.out.println("CashCount");
                    branchFinancialAudits
                            .add(divisionCompilerMapper.getCashCount(audit_id));
                } else if (audit_type.equals("AbnormalBalance")) {
                    branchFinancialAudits
                            .add(divisionCompilerMapper.get_abnormal_balance_branch(audit_id));
                } else if (audit_type.equals("ControllableExpense")) {
                    branchFinancialAudits
                            .add(divisionCompilerMapper.get_controllable_expense_branch(audit_id));
                } else if (audit_type.equals("AssetLiability")) {
                    branchFinancialAudits
                            .add(divisionCompilerMapper.get_asset_liability_branch(audit_id));
                } else if (audit_type.equals("CashManagement")) {
                    branchFinancialAudits
                            .add(divisionCompilerMapper.get_cash_management_branch(audit_id));
                } else if (audit_type.equals("Dormant")) {
                    branchFinancialAudits
                            .add(divisionCompilerMapper.get_dormant_inactive_account_branch(audit_id));
                } else if (audit_type.equals("OperationalDiscrepancy")) {
                    branchFinancialAudits
                            .add(divisionCompilerMapper.get_operational_descripancy_branch(audit_id, compiler_id,
                                    "Reviewed"));
                } else if (audit_type.equals("CashPerformance")) {
                    branchFinancialAudits
                            .add(divisionCompilerMapper.getCashPerformanceBranch(audit_id));
                } else if (audit_type.equals("NegotiableInstrument")) {
                    branchFinancialAudits
                            .add(divisionCompilerMapper.getNegotiableInstrumentBranch(audit_id));
                } else if (audit_type.equals("GeneralObservation")) {
                    branchFinancialAudits
                            .add(divisionCompilerMapper.getGeneralObservation(audit_id));
                } else if (audit_type.equals("LongOutstandingItems")) {
                    branchFinancialAudits
                            .add(divisionCompilerMapper.getLongOutstandingItems(audit_id));
                } else if (audit_type.equals("LoanAndAdvance")) {
                    branchFinancialAudits
                            .add(divisionCompilerMapper.getLoanAndAdvance(audit_id));
                } else if (audit_type.equals("StatusOfAudit")) {
                    branchFinancialAudits
                            .add(divisionCompilerMapper.getstatusofAudit(audit_id));
                } else if (audit_type.equals("Contigent")) {
                    branchFinancialAudits
                            .add(divisionCompilerMapper.getmemorandumContigent(audit_id));
                }
                else if (audit_type.equals("ControllableExpense")) {
                    branchFinancialAudits
                            .add(divisionCompilerMapper.get_controllable_expense_branch(audit_id));
                }
                else if (audit_type.equals("SuspenseAccount")) {
                    
                    branchFinancialAudits
                            .add(divisionCompilerMapper.getSuspenseAccountBranch(audit_id));
                }
            }
            compiledBranchAudit.setBranchFinancialAudits(branchFinancialAudits);
            populatedCompiledBranchAudits.add(compiledBranchAudit);
        }
        return populatedCompiledBranchAudits;
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
