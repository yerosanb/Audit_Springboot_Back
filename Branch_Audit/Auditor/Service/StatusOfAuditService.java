package com.afr.fms.Branch_Audit.Auditor.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Admin.Mapper.UserMapper;
import com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper;
import com.afr.fms.Branch_Audit.Auditor.Mapper.StatusOfAuditMapper;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Entity.StatusOfAudit;

@Service
public class StatusOfAuditService {
    @Autowired
    private StatusOfAuditMapper mapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IncompleteAccountBranchMapper auditMapper;

    public List<BranchFinancialAudit> getStatusOfAudit(Long auditor_id) {
        return mapper.getStatusOfAudit(auditor_id);
    }

    public void saveStatusOfAudit(BranchFinancialAudit audit) {
        User user = userMapper.getAuditorById(audit.getAuditor().getId());
        audit.setCategory(user.getCategory());
        audit.setCase_number(generateCaseNumber(audit.getCategory()));
        audit.setBranch(user.getBranch());
        Long branch_audit_id = mapper.insertIntoBranchFinancial(audit);
        StatusOfAudit statusOfAudit = audit.getStatusofAudit();
        statusOfAudit.setBranch_audit_id(branch_audit_id);
        mapper.saveStatusOfAudit(statusOfAudit);

        if (audit.getEdit_auditee()) {
            for (String file_name : audit.getFile_urls()) {
                auditMapper.InsertFileUrl(file_name, branch_audit_id);
            }
        }

        try {
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateStatusOfAudit(BranchFinancialAudit audit) {
        mapper.updateBFAudit(audit);
        mapper.updateStatusOfAudit(audit.getStatusofAudit());
        if (audit.getEdit_auditee()) {
            auditMapper.removeFileUrls(audit.getId());
            for (String file_name : audit.getFile_urls()) {
                auditMapper.InsertFileUrl(file_name, audit.getId());
            }
        }
    }

    public String generateCaseNumber(String category) {
        int case_number_number = 1;
        String last_case_number = mapper.getLatestCaseNumber();
        if (last_case_number != null) {
            case_number_number = Integer.parseInt(last_case_number.replaceAll("[^0-9]", ""));
        }
        String case_number = category + (case_number_number + 1);
        return case_number;
    }

    public void deleteSelecteStatusofdAudits(List<BranchFinancialAudit> BranchFinancialAudits) {
        for (BranchFinancialAudit BranchFinancialAudit : BranchFinancialAudits) {
            mapper.deleteStatusOfAudit(BranchFinancialAudit.getId());
        }
    }

    public void deleteStatusOfAudit(Long id) {
        mapper.deleteStatusOfAudit(id);
    }

    public void passSelecteStatusOfdAudits(List<BranchFinancialAudit> BranchFinancialAudits) {
        for (BranchFinancialAudit BranchFinancialAudit : BranchFinancialAudits) {
            mapper.passStatusOfAudit(BranchFinancialAudit.getId());
        }
    }

    public void passStatusOfAudit(Long id) {
        mapper.passStatusOfAudit(id);
    }

    public List<BranchFinancialAudit> getPassedStatusOfAudit(Long auditor_id) {
        return mapper.getPassedStatusOfAudit(auditor_id);
    }

    public void backSelectedStatusOfdAudits(List<BranchFinancialAudit> BranchFinancialAudits) {
        for (BranchFinancialAudit BranchFinancialAudit : BranchFinancialAudits) {
            mapper.backStatusOfAudit(BranchFinancialAudit.getId());
        }
    }

    public void backStatusOfAudit(Long id) {
        mapper.backStatusOfAudit(id);
    }

}
