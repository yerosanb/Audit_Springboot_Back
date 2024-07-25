package com.afr.fms.Branch_Audit.Auditor.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Admin.Mapper.UserMapper;
import com.afr.fms.Branch_Audit.Auditor.Mapper.AtmCardAuditorMapper;
import com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper;
import com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAudit;
import com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditService;
import com.afr.fms.Branch_Audit.Entity.AtmCardBranch;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;

@Service
public class AtmCardAuditorService {

  @Autowired
  private IncompleteAccountBranchMapper auditMapper;

  @Autowired
  private AtmCardAuditorMapper atmCardMapper;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private ChangeTrackerBranchAuditService changeTrackerService;

  public void updateATMFinding(BranchFinancialAudit audit) {
    atmCardMapper.updateATMFinding(audit.getAtmCardBranch(), audit.getId());

    if (audit.getFinding_detail()!=null) {
      atmCardMapper.updateBranchFinantialAuditwithfindingDetail(audit);
    } else {
      atmCardMapper.updateBranchFinantialAudit(audit);
    }

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

  }

  public List<BranchFinancialAudit> getATMPassedAuditsForAuditor(Long auditor_id) {
    return atmCardMapper.getPassedAuditsForAuditor(auditor_id);
  }

  public void createATMFinding(BranchFinancialAudit audit) {
    User user = userMapper.getAuditorById(audit.getAuditor().getId());
    audit.setBranch(user.getBranch());
    audit.setCategory(user.getCategory());
    audit.setCase_number(generateCaseNumber(audit.getCategory()));
    Long atm_card_branch_id ;

    if (audit.getFinding_detail()!=null) {
      atm_card_branch_id = atmCardMapper.createBranchFinancialAuditWithFindingDetail(audit);
    } else {
      atm_card_branch_id = atmCardMapper.createBranchFinancialAudit(audit);
    }

    if (audit.getEdit_auditee()) {
      for (String file_name : audit.getFile_urls()) {
        auditMapper.InsertFileUrl(file_name, atm_card_branch_id);
      }
    }

    AtmCardBranch atmCardBranch = audit.getAtmCardBranch();
    atmCardBranch.setBranch_audit_id(atm_card_branch_id);
    atmCardMapper.createATMFinding(atmCardBranch);

    for (ChangeTrackerBranchAudit changeTrackerBranchAudit : audit.getChange_tracker_branch_audit()) {
      if (!changeTrackerBranchAudit.equals(new ChangeTrackerBranchAudit())) {
        changeTrackerBranchAudit.setAudit_id(atm_card_branch_id);
        changeTrackerBranchAudit.setChanger(audit.getEditor());
        changeTrackerService.insertChanges(changeTrackerBranchAudit);
      }
    }

  }

  public List<BranchFinancialAudit> getATMAuditsOnDraftingBranch(Long auditor_id) {
    return atmCardMapper.getAuditsOnDraftingBranch(auditor_id);
  }

  public String generateCaseNumber(String category) {
    int case_number_number = 1;
    String last_case_number = atmCardMapper.getLatestCaseNumber();
    if (last_case_number != null) {
      case_number_number = Integer.parseInt(last_case_number.replaceAll("[^0-9]", ""));
    }
    String case_number = category + (case_number_number + 1);
    return case_number;
  }

  public void deleteAtmFinding(Long id) {
    atmCardMapper.deleteAtmFinding(id);
  }

  public void passATMFinding(Long id) {
    atmCardMapper.passBranchFinding(id);
  }

  public void backATMFinding(Long id) {
    atmCardMapper.backBranchFinding(id);
  }
}
