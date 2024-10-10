package com.afr.fms.Branch_Audit.Common.Audit_Remark.CompileBranchAudit;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Admin.Mapper.UserMapper;
import com.afr.fms.Branch_Audit.Auditor.Mapper.IncompleteAccountBranchMapper;
import com.afr.fms.Branch_Audit.Common.Audit_Remark.RemarkBranchAudit;

import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class RemarkCompiledBranchAuditService {

    @Autowired
    private RemarkCompiledBranchAuditMapper remarkMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IncompleteAccountBranchMapper incompleteAccountBranchMapper;

    public List<RemarkBranchAudit> getRemarks(RemarkBranchAudit remark) {
        return remarkMapper.getRemarks(remark);
    }

    public List<User> getUserByCategory(String category) {
        // return userMapper.getUserByCategory(category);

        List<User> users = userMapper.getUserByCategoryandRole(category, "REVIEWER_BFA");
        users.addAll(userMapper.getUserByCategoryandRole(category, "COMPILER_BFA"));
        users.addAll(userMapper.getUserByCategoryandRole(category, "REGIONALD_BFA"));
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
