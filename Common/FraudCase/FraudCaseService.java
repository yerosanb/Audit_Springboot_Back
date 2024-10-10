package com.afr.fms.Common.FraudCase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FraudCaseService {
@Autowired
private FraudCaseMapper fraudCaseMapper;

    public void createFraudCase(FraudCase fraudCase) {
        

        fraudCaseMapper.createFraudCase(fraudCase);
        }

    public void updateFraudCase(FraudCase fraudcase) {
        fraudCaseMapper.updateFraudCase(fraudcase);
    
    }
    public void approveFraudCase(FraudCase fraudcase) {
        fraudCaseMapper.approveFraudCase(fraudcase);
    
    }

     public FraudCase getInitialFraudCase(Long initial) {
        return  fraudCaseMapper.getInitialFraudCase(initial);
    }
    public List<FraudCase> getFraudCases() {
        return  fraudCaseMapper.getFraudCases();
    }

    public List<FraudCase> getPendingFraudCase() {
        return fraudCaseMapper.getPendingFraudCases();
    }

    public List<FraudCase> getApprovedFraudCase(Long user_id) {
        return fraudCaseMapper.getApprovedFraudCases(user_id);
    }

    public void cancelApprovedFraudCase(FraudCase fraudcase) {
        fraudCaseMapper.cancelApprovedFraudCase(fraudcase);
        }
    public void deleteFraudCase(Long id) {

        fraudCaseMapper.deleteFraudCase(id);
    }
  }
