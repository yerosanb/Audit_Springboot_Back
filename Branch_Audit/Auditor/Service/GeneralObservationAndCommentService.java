package com.afr.fms.Branch_Audit.Auditor.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Branch_Audit.Auditor.Mapper.GeneralObservationAndCommentMapper;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;

@Service
public class GeneralObservationAndCommentService {

    @Autowired
    private GeneralObservationAndCommentMapper generalOBMapper;

    public void createGeneralObservationAndComment(BranchFinancialAudit generalObservation) {
        generalObservation.setCase_number(generateCaseNumber(generalObservation.getAuditor().getCategory()));
        if (generalObservation.getFinding_detail() == null) {
            generalOBMapper.createGeneralObservationAndComment(generalObservation);

        } else {
            generalOBMapper.createGeneralObservationAndCommentWithFindingDetail(generalObservation);

        }

    }

    public String generateCaseNumber(String category) {
        int case_number_number = 1;
        String last_case_number = generalOBMapper.getLatestCaseNumber();
        if (last_case_number != null) {
            case_number_number = Integer.parseInt(last_case_number.replaceAll("[^0-9]", ""));
        }
        String case_number = category + (case_number_number + 1);
        return case_number;
    }

    public List<BranchFinancialAudit> getDraftedGeneralObservationAndComment(Long auditor_id) {
        return generalOBMapper.getDraftedGeneralObservationAndComment(auditor_id);
    }

    public void deleteSingleGeneralObservationAndComment(Long id) {
        generalOBMapper.deleteSingleGeneralObservationAndComment(id);
    }

    public void deleteSelectedGeneralObservation(List<BranchFinancialAudit> branchFinancialAudits) {
        for (BranchFinancialAudit branchFinancialAudit : branchFinancialAudits) {

            generalOBMapper.deleteSingleGeneralObservationAndComment(branchFinancialAudit.getId());

        }

    }

    public void passSelectedGeneralObservation(List<BranchFinancialAudit> branchFinancialAudits) {
        for (BranchFinancialAudit branchFinancialAudit : branchFinancialAudits) {
            generalOBMapper.passSingleGeneralObservation(branchFinancialAudit.getId());
        }
    }

    public void passSingleGeneralObservation(BranchFinancialAudit branchFinancialAudit) {
        generalOBMapper.passSingleGeneralObservation(branchFinancialAudit.getId());

    }

    public void updateGeneralObservationAndComment(BranchFinancialAudit audit) {
        if (audit.getFinding_detail() == null) {
            generalOBMapper.updateGeneralObservationAndComment(audit);
        } else {
            generalOBMapper.updateGeneralObservationAndCommentWithFindingDetail(audit);
        }
    }

    public List<BranchFinancialAudit> getPassedGeneralObservation(Long auditor_id) {
        return generalOBMapper.getPassedGeneralObservation(auditor_id);
    }

    public void backPassedGeneralObservation(Long id) {
        generalOBMapper.backPassedGeneralObservation(id);
    }

    public void backMultiPassedGeneralObservation(List<BranchFinancialAudit> branchFinancialAudits) {
        for (BranchFinancialAudit branchFinancialAudit : branchFinancialAudits) {
            generalOBMapper.backPassedGeneralObservation(branchFinancialAudit.getId());
        }
    }

}
