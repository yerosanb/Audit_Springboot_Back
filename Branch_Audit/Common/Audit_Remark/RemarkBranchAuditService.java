package com.afr.fms.Branch_Audit.Common.Audit_Remark;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Admin.Mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RemarkBranchAuditService {

    @Autowired
    private RemarkBranchAuditMapper remarkMapper;

    @Autowired
    private UserMapper userMapper;

    public List<RemarkBranchAudit> getRemarks(RemarkBranchAudit remark) {
        return remarkMapper.getRemarks(remark);
    }

    public List<RemarkBranchAudit> getAllUnseenRemarks(RemarkBranchAudit remark) {
        return remarkMapper.getAllUnseenRemarks(remark);
    }

    public List<User> getUserByCategory(String category) {
        List<User> users = userMapper.getUserByCategoryandRole(category, "AUDITOR_BFA");
        users.addAll(userMapper.getUserByCategoryandRole(category, "REVIEWER_BFA"));
        users.addAll(userMapper.getUserByCategoryandRole(category, "BRANCHM_BFA"));

        List<User> uniqueUserList = users.stream()
                .filter(distinctByKey(User::getId))
                .collect(Collectors.toList());

        return uniqueUserList;

    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public List<RemarkBranchAudit> getUnseenRemarks(RemarkBranchAudit remark) {
        return remarkMapper.getUnseenRemarks(remark);
    }

    public List<RemarkBranchAudit> getUnseenRemarksUnassignedApprover(RemarkBranchAudit remark) {
        return remarkMapper.getUnseenRemarksUnassignedApprover(remark);
    }

    public void addRemark(RemarkBranchAudit remark) {
        Long reciever_idM;
        if (remark.isRejected()) {

            // reciever_idM =
            // auditISMMapper.getAudit(remark.getAudit().getId()).getAuditor().getId();
            // User reciever = new User();
            // reciever.setId(reciever_idM);
            // remark.setReciever(reciever);
            remarkMapper.addRejectedRemark(remark);
        }

        else {
            System.out.println(remark);
            remarkMapper.addRemark(remark);
        }
    }

    public void setReciever(List<RemarkBranchAudit> remarks) {
        for (RemarkBranchAudit remark : remarks) {
            remarkMapper.setReciever(remark);
        }
    }

    public void seenRemark(RemarkBranchAudit remark) {
        remarkMapper.seenRemark(remark);
    }

}
