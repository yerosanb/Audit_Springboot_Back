package com.afr.fms.Branch_Audit.Common.Notification;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Admin.Mapper.UserMapper;
import com.afr.fms.Branch_Audit.Common.RectificationTracker.RectificationTracker;
import com.afr.fms.Branch_Audit.Entity.BranchFinancialAudit;
import com.afr.fms.Branch_Audit.Entity.CompiledBranchAudit;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BranchNotificationService {

    @Autowired
    private BranchNotificationMapper rectificationTrackerMapper;

    @Autowired
    private UserMapper userMapper;

    public List<BranchFinancialAudit> getPendingAuditsNotification(User user) {
        List<BranchFinancialAudit> branchFinancialAudits = new ArrayList<>();
        branchFinancialAudits = rectificationTrackerMapper.getPendingAuditsNotification(user,
                user.getCategory());

        return branchFinancialAudits;
    }  
    
    public List<BranchFinancialAudit> getBranchRemarkAuditsNotification(User user) {
        List<BranchFinancialAudit> branchFinancialAudits = new ArrayList<>();
        branchFinancialAudits = rectificationTrackerMapper.getBranchRemarkAuditsNotification(user,
                user.getCategory());
        return branchFinancialAudits;
    }

    public List<CompiledBranchAudit> getPendingAuditsNotificationCompiler(User user) {
        return rectificationTrackerMapper.getPendingAuditsNotificationCompiler(user);
    }

    public void addRectificationTracker(List<BranchFinancialAudit> bFinancialAudits) {
        List<User> users = userMapper.getUserByCategory("BFA");
        Long sender = bFinancialAudits.get(0).getAuditor().getId();

        // // Assuming each user object has an id property
        // List<Long> ids = users.stream()
        // .map(User::getId) // Get the id of each user object
        // .distinct() // Remove any duplicates
        // .collect(Collectors.toList()); // Collect the result into a new list

        // // ids now contains only the unique ids of the user objects

        // ids.remove(sender); // Remove the id from the list

        // Set<Long> reviewerIds = users.stream()
        // .filter(user -> user.getRoles().stream().anyMatch(role ->
        // role.getCode().equals("ROLE_REVIEWER_BFA")))
        // .map(User::getId)
        // .collect(Collectors.toSet());

        // Set<Long> auditorIds = users.stream()
        // .filter(user -> user.getRoles().stream().anyMatch(role ->
        // role.getCode().equals("ROLE_AUDITOR_BFA")))
        // .map(User::getId)
        // .collect(Collectors.toSet());
        // auditorIds.remove(sender); // Remove the id from the list

        // Set<Long> compilerIds = users.stream()
        // .filter(user -> user.getRoles().stream().anyMatch(role ->
        // role.getCode().equals("ROLE_COMPILER_BFA")))
        // .map(User::getId)
        // .collect(Collectors.toSet());

        // Set<Long> approverIDs = users.stream()
        // .filter(user -> user.getRoles().stream().anyMatch(role ->
        // role.getCode().equals("ROLE_APPROVER_BFA")))
        // .map(User::getId)
        // .collect(Collectors.toSet());

        Set<Long> BMIDs = users.stream()
                .filter(user -> user.getRoles().stream().anyMatch(role -> role.getCode().equals("BRANCHM_BFA")))
                .map(User::getId)
                .collect(Collectors.toSet());

        RectificationTracker rectificationTracker = new RectificationTracker();
        rectificationTracker.setSender(sender);
        for (BranchFinancialAudit audit : bFinancialAudits) {
            rectificationTracker.setAudit_id(audit.getId());
            BranchFinancialAudit bfaInfo = rectificationTrackerMapper.getBranchFinancialInfo(audit.getId());
            // if (audit.getReview_status() != 0) {
            // for (Long id : reviewerIds) {
            if (bfaInfo.getReviewer_id() != null) {
                rectificationTracker.setReciever(bfaInfo.getReviewer_id());
                rectificationTrackerMapper.addRectificationTracker(rectificationTracker);
            }
            // }
            // }
            // if (audit.getDivision_compiler_review_status() != 0) {
            // for (Long id : compilerIds) {
            if (bfaInfo.getDivision_compiler_id() != null) {
                rectificationTracker.setReciever(bfaInfo.getDivision_compiler_id());
                rectificationTrackerMapper.addRectificationTracker(rectificationTracker);
            }
            // }
            // }
            // if (audit.getApprove_status() != 0) {
            // for (Long id : approverIDs) {
            if (bfaInfo.getApprover_id() != null) {
                rectificationTracker.setReciever(bfaInfo.getApprover_id());
                rectificationTrackerMapper.addRectificationTracker(rectificationTracker);
            }

            // It should de be modified
            for (Long bm_id : BMIDs) {
                rectificationTracker.setReciever(bm_id);
                rectificationTrackerMapper.addRectificationTracker(rectificationTracker);
            }

            // }
            // }

        }
    }

    public void seenRectificationTracker(List<BranchFinancialAudit> bFinancialAudits) {
        RectificationTracker rectificationTracker = new RectificationTracker();
        Long reciever = bFinancialAudits.get(0).getReciever().getId();
        for (BranchFinancialAudit audit : bFinancialAudits) {
            if (audit.getRectification_status() == 1) {
                rectificationTracker.setAudit_id(audit.getId());
                rectificationTracker.setReciever(reciever);
                rectificationTracker.setStatus(true);
                rectificationTrackerMapper.seenRectificationTracker(rectificationTracker);
                rectificationTrackerMapper.seenBranchFinancial(audit);
            }
        }
    }

}
