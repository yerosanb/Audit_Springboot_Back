package com.afr.fms.Branch_Audit.Auditor.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Admin.Mapper.UserMapper;
import com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper;
import com.afr.fms.Branch_Audit.Auditor.Mapper.LongOutStandingItemsAuditorMapper;
import com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAudit;
import com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditService;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Entity.LongOutstandingItemsEntity;

@Service
public class LongOutStandingItemsAuditorService {
    @Autowired
    private LongOutStandingItemsAuditorMapper mapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ChangeTrackerBranchAuditService changeTrackerService;

    @Autowired
    private IncompleteAccountBranchMapper auditMapper;

    public void saveLongOutstandingItems(BranchFinancialAudit audit) {
        try {
            User user = userMapper.getAuditorById(audit.getAuditor().getId());
            audit.setCategory(user.getCategory());
            audit.setCase_number(generateCaseNumber(audit.getCategory()));
            audit.setBranch(user.getBranch());
            Long branch_audit_id;

            if (audit.getFinding_detail() != null) {
                branch_audit_id = mapper.insertIntoBranchFinancialWithFindingDetail(audit);
            } else {
                branch_audit_id = mapper.insertIntoBranchFinancial(audit);
            }

            LongOutstandingItemsEntity longoutstanding=audit.getLong_outstanding();
            longoutstanding.setBranch_audit_id(branch_audit_id);
            mapper.saveLongOutstandingItems(longoutstanding);

            if (audit.getEdit_auditee()) {
                for (String file_name : audit.getFile_urls()) {
                    auditMapper.InsertFileUrl(file_name, branch_audit_id);
                }
            }

            for (ChangeTrackerBranchAudit changeTrackerBranchAudit : audit.getChange_tracker_branch_audit()) {
                if (changeTrackerBranchAudit != null) {
                    if (changeTrackerBranchAudit != null) {
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

    public void updateLongOutstandingItems(BranchFinancialAudit audit) {
    try {
      Long auditId = audit.getLong_outstanding().getBranch_audit_id();
      audit.setId(auditId);

      if (audit.getFinding_detail()==null) {
        mapper.updateBranchFinancial(audit);
      } else {
        mapper.updateBranchFinancialWithFindingDetail(audit);
      }
      
     LongOutstandingItemsEntity longoustanding=audit.getLong_outstanding();
      mapper.updateLongOutstandingItems(longoustanding);
      
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

  public List<BranchFinancialAudit> getPendingOutstandingItems(Long id) {
    try {
        //  List<BranchFinancialAudit> branchFinancialAudits = mapper.getPendingOutstandingItems(id);
      return mapper.getPendingOutstandingItems(id);
    } catch (Exception e) {
      System.out.println(e);
      return null;
    }
  }

  public List<BranchFinancialAudit> getPassedLongOutstandingItems(Long auditor_id) {
    try {
      return mapper.getPassedLongOutstandingItems(auditor_id);
    } catch (Exception e) {
      System.out.println(e);
    }
    return null;
  }

  
}
