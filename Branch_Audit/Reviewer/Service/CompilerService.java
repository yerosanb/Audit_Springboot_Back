package com.afr.fms.Branch_Audit.Reviewer.Service;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Admin.Mapper.UserMapper;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Entity.CompiledBranchAudit;
import com.afr.fms.Branch_Audit.Entity.IncompleteAccountBranch;
import com.afr.fms.Branch_Audit.Reviewer.Mapper.CompilerMapper;
import com.afr.fms.Common.RecentActivity.RecentActivityMapper;
import com.afr.fms.Payload.endpoint.Endpoint;
import com.afr.fms.Security.email.context.Reviewer_for_ApproverContext;
import com.afr.fms.Security.email.service.EmailService;

@Service
public class CompilerService {

    @Autowired
    private CompilerMapper compilerMapper;

    @Autowired

    private RecentActivityMapper recentActivityMapper;

    private String baseURL = Endpoint.URL;

    @Autowired
    UserMapper userMapper;

    @Autowired
    private EmailService emailService;

    public void submitCompiledFindings(CompiledBranchAudit audit) {
        compilerMapper.submitCompiledFindings(audit);
        for (BranchFinancialAudit branchFinancialAudit : audit.getBranchFinancialAudits()) {
            if (branchFinancialAudit.getId() != null) {
                if (!identifyRectifiedAudit(branchFinancialAudit)) {
                    compilerMapper.updateBranchFinancialAuditAfterSubmitted(branchFinancialAudit.getId(), 1);
                }
            }
        }
        // List<User> user = userMapper.getUserByCategory(audit.getCategory());
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

    public void compileFindings(CompiledBranchAudit compiledBranchAudit) {
        if (identifyCompiledAudits(compiledBranchAudit)) {
            User user = userMapper.getAuditorById(compiledBranchAudit.getCompiler().getId());
            compiledBranchAudit.setRegion(user.getRegion());
            compiledBranchAudit
                    .setCase_number(
                            generateCaseNumber(user.getRegion().getName() + " " + compiledBranchAudit.getAudit_type()));
            Long id = compilerMapper.saveCompiledFinding(compiledBranchAudit);
            for (BranchFinancialAudit branchFinancialAudit : compiledBranchAudit.getBranchFinancialAudits()) {
                if (branchFinancialAudit.getId() != null) {
                    if (!identifyRectifiedAudit(branchFinancialAudit)) {
                        compilerMapper.saveIntoCompiledAudits(id, branchFinancialAudit.getId());
                        compilerMapper.updateBranchFinancialAudit(branchFinancialAudit.getId(), 1);
                    }
                }
            }
        }
    }

    public Boolean identifyCompiledAudits(CompiledBranchAudit compiledBranchAudit) {
        if (compiledBranchAudit != null) {
            for (BranchFinancialAudit bAudit : compiledBranchAudit.getBranchFinancialAudits()) {
                if (identifyCompiledAudit(bAudit)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public Boolean identifyRectifiedAudit(BranchFinancialAudit branchFinancialAudit) {
        Boolean is_rectified = compilerMapper.isBranchFinancialAuditRectified(branchFinancialAudit.getId());
        // if (is_rectified) {
        // compilerMapper.updateRectificationStatusCompiledAudits(branchFinancialAudit.getId(),
        // 1);
        // }
        return is_rectified;
    }

    public Boolean identifyCompiledAudit(BranchFinancialAudit branchFinancialAudit) {
        Boolean is_compiled = compilerMapper.isBranchFinancialAuditCompiled(branchFinancialAudit.getId());
        // if (is_compiled) {
        // compilerMapper.updateRectificationStatusCompiledAudits(branchFinancialAudit.getId(),
        // 1);
        // }
        return is_compiled;
    }

    public void decompileFindings(CompiledBranchAudit compiledBranchAudit) {
        for (BranchFinancialAudit branchFinancialAudit : compiledBranchAudit.getBranchFinancialAudits()) {
            if (branchFinancialAudit != null) {
                if (!identifyRectifiedAudit(branchFinancialAudit)) {
                    compilerMapper.updateBranchFinancialAudit(branchFinancialAudit.getId(), 0);
                }
            }
        }
        compilerMapper.removeMappedCompiledFindings(compiledBranchAudit.getId());
        compilerMapper.removeCompiledFinding(compiledBranchAudit.getId());
    }

    public String generateCaseNumber(String category) {
        int case_number_number = 1;
        String last_case_number = compilerMapper.getLatestCaseNumber();
        if (last_case_number != null) {
            case_number_number = Integer.parseInt(last_case_number.replaceAll("[^0-9]", ""));
        }
        String case_number = category + (case_number_number + 1);
        return case_number;
    }

    public List<CompiledBranchAudit> getCompiledFindings(Long compiler_id) {

        List<CompiledBranchAudit> compiledBranchAudits = compilerMapper.getCompiledAudits(compiler_id);

        List<CompiledBranchAudit> populatedCompiledBranchAudits = new ArrayList<CompiledBranchAudit>();

        for (CompiledBranchAudit compiledBranchAudit : compiledBranchAudits) {
            List<BranchFinancialAudit> branchFinancialAudits = new ArrayList<BranchFinancialAudit>();

            // if (compiledBranchAudit.getAudit_type().trim().equals("IncompleteAccount")) {
            List<Long> compiled_audits = compilerMapper.getMappedCompiledAudits(compiledBranchAudit.getId());
            for (Long audit_id : compiled_audits) {
                branchFinancialAudits
                        .add(compilerMapper.getBranchFinancialAuditForCompiler(audit_id));
            }
            compiledBranchAudit.setBranchFinancialAudits(branchFinancialAudits);
            populatedCompiledBranchAudits.add(compiledBranchAudit);
            // }
            // else if (compiledBranchAudit.getAudit_type().trim().equals("ATMCard")) {
            // List<Long> compiled_audits =
            // compilerMapper.getMappedCompiledAudits(compiledBranchAudit.getId());
            // for (Long audit_id : compiled_audits) {
            // branchFinancialAudits
            // .add(compilerMapper.getBranchFinancialAuditForCompiler(audit_id));
            // }
            // compiledBranchAudit.setBranchFinancialAudits(branchFinancialAudits);
            // populatedCompiledBranchAudits.add(compiledBranchAudit);
            // }

        }

        return populatedCompiledBranchAudits;
    }

    public List<CompiledBranchAudit> getSubmittedCompiledFindings(User user) {

        List<CompiledBranchAudit> compiledBranchAudits = compilerMapper.getSubmittedCompiledAudits(user);

        List<CompiledBranchAudit> populatedCompiledBranchAudits = new ArrayList<CompiledBranchAudit>();

        for (CompiledBranchAudit compiledBranchAudit : compiledBranchAudits) {
            List<BranchFinancialAudit> branchFinancialAudits = new ArrayList<BranchFinancialAudit>();

            // if (compiledBranchAudit.getAudit_type().trim().equals("IncompleteAccount")) {
            List<Long> compiled_audits = compilerMapper.getMappedCompiledAudits(compiledBranchAudit.getId());
            for (Long audit_id : compiled_audits) {
                branchFinancialAudits
                        .add(compilerMapper.getBranchFinancialAuditForCompiler(audit_id));
            }
            compiledBranchAudit.setBranchFinancialAudits(branchFinancialAudits);
            populatedCompiledBranchAudits.add(compiledBranchAudit);
            // }
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
