package com.afr.fms.Branch_Audit.Auditor.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.afr.fms.Admin.Mapper.UserMapper;
import com.afr.fms.Branch_Audit.Auditor.Mapper.CashPerformanceAuditorMapper;
import com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper;
import com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAudit;
import com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditService;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;

@Service
public class CashPerfomanceAuditorService {

  @Autowired
  private CashPerformanceAuditorMapper cashPerformanceMapper;

  @Autowired
  private IncompleteAccountBranchMapper auditMapper;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private ChangeTrackerBranchAuditService changeTrackerService;

  public void updateBranchFinantialAudit(BranchFinancialAudit audit) {
    if (audit.getFinding_detail() != null) {
      cashPerformanceMapper.updateBranchFinantialAuditWithfindingDetail(audit);
    } else {
      cashPerformanceMapper.updateBranchFinantialAudit(audit);
    }
    cashPerformanceMapper.updateCashFinding(audit.getCashPerformanceBranch(), audit.getId());

    if (audit.getEdit_auditee()) {
      auditMapper.removeFileUrls(audit.getId());
      for (String file_name : audit.getFile_urls()) {
        auditMapper.InsertFileUrl(file_name, audit.getId());
      }

    }

    for (ChangeTrackerBranchAudit changeTrackerBranchAudit : audit.getChange_tracker_branch_audit()) {
      if (changeTrackerBranchAudit != null) {
        if (changeTrackerBranchAudit != null) {
          changeTrackerBranchAudit.setAudit_id(audit.getId());
          changeTrackerBranchAudit.setChanger(audit.getEditor());
          changeTrackerService.insertChanges(changeTrackerBranchAudit);
        }
      }
    }

  }

  public void createCashFinding(BranchFinancialAudit audit) {
    audit.setCase_number(generateCaseNumber(audit.getCategory()));
    Long branch_id;

    if (audit.getFinding_detail() != null) {
      branch_id = cashPerformanceMapper.createCashWithFindingDetail(audit);
    } else {
      branch_id = cashPerformanceMapper.createCashFinding(audit);
    }

    audit.getCashPerformanceBranch().setBranch_audit_id(branch_id);
    cashPerformanceMapper.createBranchCashAudit(audit.getCashPerformanceBranch());

    if (audit.getEdit_auditee()) {
      for (String file_name : audit.getFile_urls()) {
        auditMapper.InsertFileUrl(file_name, branch_id);
      }
    }

    for (ChangeTrackerBranchAudit changeTrackerBranchAudit : audit.getChange_tracker_branch_audit()) {
      if (changeTrackerBranchAudit != null) {
        changeTrackerBranchAudit.setAudit_id(branch_id);
        changeTrackerBranchAudit.setChanger(audit.getEditor());
        changeTrackerService.insertChanges(changeTrackerBranchAudit);
      }
    }
  }

  public String generateCaseNumber(String category) {
    int case_number_number = 1;
    String last_case_number = cashPerformanceMapper.getLatestCaseNumber();
    if (last_case_number != null) {
      case_number_number = Integer.parseInt(last_case_number.replaceAll("[^0-9]", ""));
    }
    String case_number = category + (case_number_number + 1);
    return case_number;
  }

  public List<BranchFinancialAudit> getPassedCashPerfomanceFindings(Long auditor_id) {
    return cashPerformanceMapper.getPassedCashPerfomanceFindings(auditor_id);
  }

  public List<BranchFinancialAudit> getDraftingCashPerformanceFindings(Long auditor_id) {
    return cashPerformanceMapper.getDraftingCashPerformanceFindings(auditor_id);
  }

}
