package com.afr.fms.Common.Finding;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonFindingService {
    @Autowired
    private CommonFindingMapper commonFindingMapper;

    public void createCommonFinding(CommonFinding commonFinding) {

        commonFindingMapper.createCommonFinding(commonFinding);

    }

    public List<CommonFinding> getCommonFinding(Long id) {
        return commonFindingMapper.getCommonFinding(id);
    }

    public List<CommonFinding> getCommonFindings() {
        return commonFindingMapper.getCommonFindings();
    }

    public List<CommonFinding> getFindingsByAuditType(String audit_type) {
        return commonFindingMapper.getFindingsByAuditType(audit_type);
    }

    public void updateCommonFinding(CommonFinding commonFinding) {
        commonFindingMapper.updateCommonFinding(commonFinding);
    }

    public void deleteCommonFinding(Long id) {
        commonFindingMapper.deleteCommonFinding(id);

    }

}
