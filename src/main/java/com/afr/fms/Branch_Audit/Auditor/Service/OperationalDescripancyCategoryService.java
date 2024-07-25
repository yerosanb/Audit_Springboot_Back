package com.afr.fms.Branch_Audit.Auditor.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Branch_Audit.Auditor.Mapper.OperationalDescripancyCategoryMapper;

import com.afr.fms.Branch_Audit.Entity.OperationalDescripancyCategory;

@Service
public class OperationalDescripancyCategoryService {
    @Autowired
    private OperationalDescripancyCategoryMapper operationalDescripancyCategoryMapper;

    public void createDescripancyCategory(OperationalDescripancyCategory OperationalDescripancyCategory) {

        operationalDescripancyCategoryMapper.createDescripancyCategory(OperationalDescripancyCategory);

    }

    public void updateOperationalDescripancyCategory(OperationalDescripancyCategory operationalDescripancyCategory) {
        operationalDescripancyCategoryMapper.updateOperationalDescripancyCategory(operationalDescripancyCategory);

    }

    public List<OperationalDescripancyCategory> getOperationalDescripancyCategory() {
        return operationalDescripancyCategoryMapper.getOperationalDescripancyCategory();
    }

    public List<OperationalDescripancyCategory> getOperationalDescripancyCategoryByCategory(String category) {
        return operationalDescripancyCategoryMapper.getOperationalDescripancyCategoryByCategory(category);
    }
    

    
    public void deleteOperationalDescripancyCategory(OperationalDescripancyCategory OperationalDescripancyCategory) {

        operationalDescripancyCategoryMapper.deleteOperationalDescriptionalCategory(OperationalDescripancyCategory.getId());

    }

}
