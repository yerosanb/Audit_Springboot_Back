package com.afr.fms.Branch_Audit.Common.OperationalDescripancyPooledData;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Admin.Mapper.UserMapper;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OperationalDescripancyPooledDataService {

    @Autowired
    private OperationalDescripancyPooledDataMapper poolMapper;

    @Autowired
    private UserMapper userMapper;

    public List<BranchFinancialAudit> getPooledData(OperationalDescripancyPooledData pooler) {
        List<BranchFinancialAudit> branchFinancialAudits = new ArrayList<>();
        branchFinancialAudits = poolMapper.getPooledData(pooler);
        return branchFinancialAudits;
    }

    public void addToPool(OperationalDescripancyPooledData pooledData) {
        for (BranchFinancialAudit audit : pooledData.getBranchFinancialAudits()) {
            pooledData.setBranch_financial_audit_id(audit.getId());
            poolMapper.addToPool(pooledData);
        }
    }

    public void deletePooledData(List<BranchFinancialAudit> bFinancialAudits) {
        OperationalDescripancyPooledData operationalDescripancyPooledData = new OperationalDescripancyPooledData();
        Long reciever = bFinancialAudits.get(0).getReciever().getId();
        operationalDescripancyPooledData.setPooler(reciever);
        for (BranchFinancialAudit audit : bFinancialAudits) {
            operationalDescripancyPooledData.setBranch_financial_audit_id(audit.getId());
            operationalDescripancyPooledData.setStatus(false);
            poolMapper.deletePooledData(operationalDescripancyPooledData);
        }
    }

}
