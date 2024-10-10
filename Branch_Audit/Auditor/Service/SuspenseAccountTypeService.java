package com.afr.fms.Branch_Audit.Auditor.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Branch_Audit.Auditor.Mapper.SuspenseAccountTypeMapper;
import com.afr.fms.Branch_Audit.Entity.SuspenseAccountType;

@Service
public class SuspenseAccountTypeService {
    @Autowired
    private SuspenseAccountTypeMapper suspenseAccountTypeMapper;

    public void createSuspenseAccountType(SuspenseAccountType suspenseAccountType) {

        System.out.println(suspenseAccountType);
        suspenseAccountTypeMapper.createSuspenseAccountType(suspenseAccountType);

    }

    public void updateSuspenseAccountType(SuspenseAccountType suspenseAccountType) {
        suspenseAccountTypeMapper.updateSuspenceAccountType(suspenseAccountType);

    }

    public List<SuspenseAccountType> getSuspenseAccountType() {
        return suspenseAccountTypeMapper.getSuspenceAccountType();
    }

    // public List<SuspenseAccountType> getOperationalDescripancyCategoryByCategory(String category) {
    //     return suspenseAccountTypeMapper.getSuspenceAccountType(category);
    // }
    

    
    public void deleteSuspenceAccountType(SuspenseAccountType suspenseAccountType) {

        suspenseAccountTypeMapper.deleteSuspenceAccountType(suspenseAccountType.getId());

    }

}
