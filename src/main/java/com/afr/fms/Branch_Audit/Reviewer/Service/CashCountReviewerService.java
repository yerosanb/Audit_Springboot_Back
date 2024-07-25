package com.afr.fms.Branch_Audit.Reviewer.Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Admin.Mapper.UserMapper;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Reviewer.Mapper.CashCountReviewerMapper;

@Service
public class CashCountReviewerService {
    @Autowired
    private CashCountReviewerMapper mapper;

    @Autowired
    UserMapper userMapper;
    
    public List<BranchFinancialAudit> getCashCountForReviewer(User user) {
        try {
             return mapper.getCashCountForReviewer(user.getBanking(), user);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        } 
    }

   
    public List<BranchFinancialAudit> getReviewedCashCount(Long reviewer_d) {
        try {
            return mapper.getReviewedCashCount(reviewer_d);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        } 
    }

   

}
