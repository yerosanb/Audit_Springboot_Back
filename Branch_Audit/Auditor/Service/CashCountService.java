package com.afr.fms.Branch_Audit.Auditor.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Admin.Mapper.UserMapper;
import com.afr.fms.Branch_Audit.Auditor.Mapper.CashCountMapper;
import com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper;
import com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAudit;
import com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditService;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Entity.CashCountEntity;

@Service
public class CashCountService {
  @Autowired
  private CashCountMapper mapper;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private IncompleteAccountBranchMapper auditMapper;

  @Autowired
  private ChangeTrackerBranchAuditService changeTrackerService;

  public List<BranchFinancialAudit> getCashCount(Long id) {
    try {
         List<BranchFinancialAudit> branchFinancialAudits = mapper.getCashCount(id);
         return mapper.getCashCount(id);
    } catch (Exception e) {
      System.out.println(e);
      return null;
    }
  }

  public void addCashCount(BranchFinancialAudit audit) {
    try {
      User user = userMapper.getAuditorById(audit.getAuditor().getId());
      audit.setCategory(user.getCategory());
      audit.setCase_number(generateCaseNumber(audit.getCategory()));
      audit.setBranch(user.getBranch());
      Long branch_audit_id;

      if (audit.getFinding_detail()!=null) {
        branch_audit_id = mapper.insertIntoBranchFinancialWithFindingDetail(audit);
      } else {
        branch_audit_id = mapper.insertIntoBranchFinancial(audit);
      }
      
       CashCountEntity cashcount = audit.getCashcount();
        cashcount.setBranch_audit_id(branch_audit_id);
        mapper.addCashCount(cashcount);

      if (audit.getEdit_auditee()) {
        for (String file_name : audit.getFile_urls()) {
          auditMapper.InsertFileUrl(file_name, branch_audit_id);
        }
      }

      for (ChangeTrackerBranchAudit changeTrackerBranchAudit : audit.getChange_tracker_branch_audit()) {
        if (changeTrackerBranchAudit != null) {
          if (!changeTrackerBranchAudit.equals(new ChangeTrackerBranchAudit())) {
            changeTrackerBranchAudit.setAudit_id(branch_audit_id);
            changeTrackerBranchAudit.setChanger(audit.getEditor());
            changeTrackerService.insertChanges(changeTrackerBranchAudit);
          }
        }

      }

      try {
        // sendEmailtoReviewer(audit);
      } catch (Exception e) {
        System.out.println(e);
      }

    } catch (Exception e) {
      System.out.println(e);
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

  public List<BranchFinancialAudit> getAuditsOnDrafting(Long auditor_id) {
    try {

      List<BranchFinancialAudit> branchFinancialAudits = mapper.getAuditsOnDrafting(auditor_id);
      int size  = branchFinancialAudits.size()-2;
      
      return mapper.getAuditsOnDrafting(auditor_id);

    } catch (Exception e) {
      System.out.println(e);
      return null;
    }
  }

  public void deleteSelectedCashCount(List<BranchFinancialAudit> BranchFinancialAudits) {

    try {
      for (BranchFinancialAudit BranchFinancialAudit : BranchFinancialAudits) {
        mapper.deleteSelectedCashCount(BranchFinancialAudit.getId());
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public void passSelectedCashCount(List<BranchFinancialAudit> BranchFinancialAudits) {
    try {
      for (BranchFinancialAudit BranchFinancialAudit : BranchFinancialAudits) {
        mapper.passSelectedCashCount(BranchFinancialAudit.getId());
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public void editCashCount(BranchFinancialAudit audit) {
    try {
      Long auditId = audit.getCashcount().getBranch_audit_id();
      audit.setId(auditId);

      if (audit.getFinding_detail()==null) {
        mapper.updateBranchFinancial(audit);
      } else {
        mapper.updateBranchFinancialWithFindingDetail(audit);
      }
      
      CashCountEntity cashcount = audit.getCashcount();
      mapper.editCashCount(cashcount);
      
      if (audit.getEdit_auditee()) {
        auditMapper.removeFileUrls(audit.getId());
        for (String file_name : audit.getFile_urls()) {
          auditMapper.InsertFileUrl(file_name, audit.getId());
        }

      }

      for (ChangeTrackerBranchAudit changeTrackerBranchAudit : audit.getChange_tracker_branch_audit()) {
        if (changeTrackerBranchAudit != null) {
          if (!changeTrackerBranchAudit.equals(new ChangeTrackerBranchAudit())) {
            changeTrackerBranchAudit.setAudit_id(audit.getId());
            changeTrackerBranchAudit.setChanger(audit.getEditor());
            changeTrackerService.insertChanges(changeTrackerBranchAudit);
          }
        }
      }

    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public void deleteCashCount(Long id) {
    try {
      mapper.deleteCashCount(id);
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public void passCashCount(Long id) {
    try {
      mapper.passCashCount(id);
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public List<BranchFinancialAudit> getPassedCashCount(Long auditor_id) {
    try {
      return mapper.getPassedCashCount(auditor_id);
    } catch (Exception e) {
      System.out.println(e);
    }
    return null;
  }

  public void backSelectedCashCount(List<BranchFinancialAudit> BranchFinancialAudits) {
    try {
      for (BranchFinancialAudit BranchFinancialAudit : BranchFinancialAudits) {
        mapper.backSelectedCashCount(BranchFinancialAudit.getId());
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public void editPassedCashCount(BranchFinancialAudit audit) {
    try {
      Long auditId = audit.getCashcount().getBranch_audit_id();
      audit.setId(auditId);
      mapper.updateBranchFinancial(audit);
      CashCountEntity cashcount = audit.getCashcount();
      mapper.editPassedCashCount(cashcount);

      if (audit.getEdit_auditee()) {
        auditMapper.removeFileUrls(audit.getId());
        for (String file_name : audit.getFile_urls()) {
          auditMapper.InsertFileUrl(file_name, audit.getId());
        }
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public void backPassedCashCount(Long id) {
    try {
      mapper.backPassedCashCount(id);
    } catch (Exception e) {
      System.out.println(e);
    }
  }

}
