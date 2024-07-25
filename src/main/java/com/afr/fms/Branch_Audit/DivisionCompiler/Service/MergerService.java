package com.afr.fms.Branch_Audit.DivisionCompiler.Service;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Admin.Mapper.UserMapper;
import com.afr.fms.Branch_Audit.DivisionCompiler.Mapper.MergerMapper;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Entity.CompiledBranchAudit;
import com.afr.fms.Branch_Audit.Entity.CompiledRegionalAudit;
import com.afr.fms.Branch_Audit.Reviewer.Mapper.CompilerMapper;
import com.afr.fms.Common.RecentActivity.RecentActivityMapper;
import com.afr.fms.Payload.endpoint.Endpoint;
import com.afr.fms.Security.email.context.Reviewer_for_ApproverContext;
import com.afr.fms.Security.email.service.EmailService;

@Service
public class MergerService {

    @Autowired
    private MergerMapper compilerMapper;

    @Autowired

    private RecentActivityMapper recentActivityMapper;

    private String baseURL = Endpoint.URL;

    @Autowired
    UserMapper userMapper;

    @Autowired
    private EmailService emailService;

    public void submitCompiledFindings(CompiledRegionalAudit audit) {
        compilerMapper.submitCompiledFindings(audit);
        this.updateBranchFinancialAuditAfterSubmitted(audit.getCompiledBranchAudits());

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

    public void updateBranchFinancialAuditAfterSubmitted(List<CompiledBranchAudit> compiledBranchAudits) {
        for (CompiledBranchAudit compiledBranchAudit : compiledBranchAudits) {
            compilerMapper.updateCompiledBranchAuditAfterSubmitted(compiledBranchAudit.getId(), 1);
            for (BranchFinancialAudit branchFinancialAudit : compiledBranchAudit.getBranchFinancialAudits()) {
                if (branchFinancialAudit.getId() != null) {
                    compilerMapper.updateBranchFinancialAuditAfterSubmitted(branchFinancialAudit.getId(), 1);
                }
            }
        }
    }

    public void compileFindings(CompiledRegionalAudit compiledRegionalAudit) {
        if (identifyCompiledAudits(compiledRegionalAudit)) {
            // User user =
            // userMapper.getAuditorById(compiledRegionalAudit.getCompiler().getId());
            compiledRegionalAudit
                    .setCase_number(
                            generateCaseNumber(compiledRegionalAudit.getAudit_type()));
            Long id = compilerMapper.saveCompiledFinding(compiledRegionalAudit);
            for (CompiledBranchAudit compiledBranchAudit : compiledRegionalAudit.getCompiledBranchAudits()) {
                if (compiledBranchAudit.getId() != null) {
                    compilerMapper.saveIntoCompiledAudits(id, compiledBranchAudit.getId());
                    compilerMapper.updateCompiledBranchAudit(compiledBranchAudit.getId(), 1);
                }
            }

            this.updateBranchFinancialAuditAfterCompiled(compiledRegionalAudit.getCompiledBranchAudits());
        }
    }

    public Boolean identifyCompiledAudits(CompiledRegionalAudit compiledRegionalAudit) {
        if (compiledRegionalAudit != null) {
            for (CompiledBranchAudit compiledBranchAudit : compiledRegionalAudit.getCompiledBranchAudits()) {
                if (identifyCompiledAudit(compiledBranchAudit)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public Boolean identifyCompiledAudit(CompiledBranchAudit compiledBranchAudit) {
        Boolean is_compiled = compilerMapper.isCompiledBranchAuditCompiled(compiledBranchAudit.getId());
        // if (is_rectified) {
        // compilerMapper.updateRectificationStatusCompiledAudits(branchFinancialAudit.getId(),
        // 1);
        // }
        return is_compiled;
    }

    public void updateBranchFinancialAuditAfterCompiled(List<CompiledBranchAudit> compiledBranchAudits) {
        for (CompiledBranchAudit compiledBranchAudit : compiledBranchAudits) {
            for (BranchFinancialAudit branchFinancialAudit : compiledBranchAudit.getBranchFinancialAudits()) {
                if (branchFinancialAudit.getId() != null) {
                    compilerMapper.updateBranchFinancialAuditAfterCompiled(branchFinancialAudit.getId(), 1);
                }
            }
        }
    }

    public void updateBranchFinancialAuditAfterDecompiled(List<CompiledBranchAudit> compiledBranchAudits) {
        for (CompiledBranchAudit compiledBranchAudit : compiledBranchAudits) {
            for (BranchFinancialAudit branchFinancialAudit : compiledBranchAudit.getBranchFinancialAudits()) {
                if (branchFinancialAudit.getId() != null) {
                    compilerMapper.updateBranchFinancialAuditAfterDecompiled(branchFinancialAudit.getId(), 0);
                }
            }
        }
    }

    public void decompileFindings(CompiledRegionalAudit compiledRegionalAudit) {
        for (CompiledBranchAudit compiledBranchAudit : compiledRegionalAudit.getCompiledBranchAudits()) {
            if (compiledBranchAudit != null) {
                compilerMapper.updateCompiledBranchAudit(compiledBranchAudit.getId(), 0);
            }
        }
        compilerMapper.removeMappedCompiledFindings(compiledRegionalAudit.getId());
        compilerMapper.removeCompiledFinding(compiledRegionalAudit.getId());
        this.updateBranchFinancialAuditAfterDecompiled(compiledRegionalAudit.getCompiledBranchAudits());

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

    public List<CompiledRegionalAudit> getCompiledFindings(Long compiler_id) {

        List<CompiledRegionalAudit> compiledRegionalAudits = compilerMapper.getCompiledAudits(compiler_id);

        List<CompiledRegionalAudit> populatedCompiledRegionalAudits = new ArrayList<CompiledRegionalAudit>();

        for (CompiledRegionalAudit compiledRegionalAudit : compiledRegionalAudits) {
            List<CompiledBranchAudit> compiledBranchAudits = new ArrayList<CompiledBranchAudit>();
            List<Long> compiled_audits = compilerMapper.getMappedCompiledAudits(compiledRegionalAudit.getId());
            for (Long audit_id : compiled_audits) {
                compiledBranchAudits
                        .add(compilerMapper.getCompiledBranchAuditForDivision(audit_id));
            }

            for (CompiledBranchAudit compiledBranchAudit : compiledBranchAudits) {
                if (compiledBranchAudit != null) {

                    List<BranchFinancialAudit> branchFinancialAudits = new ArrayList<BranchFinancialAudit>();

                    List<Long> compiled_branch_audits = compilerMapper
                            .getMappedCompiledBranchAudits(compiledBranchAudit.getId());
                    for (Long audit_id : compiled_branch_audits) {
                        branchFinancialAudits
                                .add(compilerMapper.getBranchFinancialAuditForCompiler(audit_id,
                                        compiledBranchAudit.getAudit_type()));
                    }
                    compiledBranchAudit.setBranchFinancialAudits(branchFinancialAudits);
                }

            }

            compiledRegionalAudit.setCompiledBranchAudits(compiledBranchAudits);
            populatedCompiledRegionalAudits.add(compiledRegionalAudit);

        }

        return populatedCompiledRegionalAudits;
    }

    public List<CompiledRegionalAudit> getSubmittedCompiledFindings(Long compiler_id) {

        List<CompiledRegionalAudit> compiledRegionalAudits = compilerMapper.getSubmittedCompiledAudits(compiler_id);

        List<CompiledRegionalAudit> populatedCompiledRegionalAudits = new ArrayList<CompiledRegionalAudit>();

        for (CompiledRegionalAudit compiledRegionalAudit : compiledRegionalAudits) {
            List<CompiledBranchAudit> compiledBranchAudits = new ArrayList<CompiledBranchAudit>();

            List<Long> compiled_audits = compilerMapper.getMappedCompiledAudits(compiledRegionalAudit.getId());
            for (Long audit_id : compiled_audits) {
                compiledBranchAudits
                        .add(compilerMapper.getCompiledBranchAuditForDivision(audit_id));
            }
            for (CompiledBranchAudit compiledBranchAudit : compiledBranchAudits) {
                List<BranchFinancialAudit> branchFinancialAudits = new ArrayList<BranchFinancialAudit>();

                List<Long> compiled_branch_audits = compilerMapper
                        .getMappedCompiledBranchAudits(compiledBranchAudit.getId());
                for (Long audit_id : compiled_branch_audits) {

                    branchFinancialAudits
                            .add(compilerMapper.getBranchFinancialAuditForCompiler(audit_id,
                                    compiledRegionalAudit.getAudit_type()));
                }
                compiledBranchAudit.setBranchFinancialAudits(branchFinancialAudits);

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

}
