





package com.afr.fms.Branch_Audit.Auditor.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Admin.Mapper.UserMapper;
import com.afr.fms.Branch_Audit.Auditor.Mapper.ControllableExpenseTypeAuditorMapper;

import com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAuditService;
import com.afr.fms.Branch_Audit.Entity.ControllableExpenseType;
import com.afr.fms.Payload.endpoint.Endpoint;
import com.afr.fms.Security.email.service.EmailService;

@Service
public class ControllableExpenseTypeAuditorService {
    @Autowired
    private ControllableExpenseTypeAuditorMapper auditMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ChangeTrackerBranchAuditService changeTrackerService;
    private String baseURL = Endpoint.URL;
    @Autowired
    private EmailService emailService;

    public List<ControllableExpenseType> getControllableExpenseType() {
        return auditMapper.getControllableExpenseType();
    }

    

    public void createControllableExpenseType(ControllableExpenseType audit) {
        auditMapper.createControllableExpenseType(audit);
    }

    public void updateControllableExpenseType(ControllableExpenseType audit) {
        auditMapper.updateControllableExpenseType(audit);
        
    }

    public void deleteBAFinding(Long id) {
        auditMapper.deleteBAFinding(id);
    }

   }

