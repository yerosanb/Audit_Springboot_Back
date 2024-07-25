package com.afr.fms.Branch_Audit.Entity;

import java.util.List;
import com.afr.fms.Admin.Entity.Branch;
import com.afr.fms.Admin.Entity.User;
import com.afr.fms.Branch_Audit.Common.Audit_Change_Tracker.BranchAudit.ChangeTrackerBranchAudit;
import com.afr.fms.Branch_Audit.Common.RectificationTracker.RectificationTracker;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchFinancialAudit {
    private Long id;
    private String case_number;
    private User branch_manager;
    private User auditor;
    private User reviewer;
    private User followup_officer;
    private User approver;
    private User auditee;
    private String audit_type;
    private String finding_date;
    private String finding;
    private String finding_detail;
    private String impact;
    private String recommendation;
    private String observation;
    private int review_status;
    private int approve_status;
    private String rectification_date;
    private String unrectification_date;
    private int rectification_status;
    private String category;
    private int status;
    private Boolean save_template;
    private Boolean edit_auditee;
    private List<Branch> auditees;
    private String created_date;
    private String updated_date;
    private Boolean response_added;
    private Boolean division_assigned;
    private String reviewer_edit_date;
    private String approver_edit_date;
    private String audit_date;
    private Boolean is_edited;
    private List<ChangeTrackerBranchAudit> change_tracker_branch_audit;
    private User Editor;
    private String finding_status;
    private String approved_date;
    private String reviewed_date;
    private String responded_date;
    private int auditor_status;
    private Long branch_id;
    private Long auditor_id;
    private Long approver_id;
    private Long reviewer_id;
    private List<String> bmFileUrls;
    private List<String> file_urls;
    private boolean file_flag;
    private String action_plan;
    private Branch branch;
    private AtmCardBranch atmCardBranch;
    private CashPerformanceBranch cashPerformanceBranch;
    private AssetLiablity assetLiabilityBranch;
    private AbnormalBalance abnormalBalanceBranch;
    private IncompleteAccountBranch incompleteAccountBranch;
    private OperationalDescripancyBranch operationalDescripancyBranch;
    private DormantInactive dormantInactive;
    private StatusOfAudit statusofAudit;
    private MemorandumContigent memorandumContigent;
    private LoanAndAdvance loanAndAdvance;
    private CashCountEntity cashcount;
    private SuspenseAccountBranch suspenseAccountBranch;
    private NegotiableInstrumentBranch negotiableInstrument;
    private CashManagementBranch cashManagementBranch;
    private ControllableExpense controllableExpense;
    private LongOutstandingItemsEntity long_outstanding;
    private int regional_compiler_status;
    private String regional_compiler_compiled_date;
    private int regional_compiler_submitted;;
    private String regional_compiler_submitted_date;
    private Long division_compiler_id;
    private int division_compiler_review_status;
    private String division_compiler_reviewed_date;
    private int division_compiler_status;;
    private String division_compiler_compiled_date;
    private int division_compiler_submitted;
    private String division_compiler_submitted_date;
    private String approver_edited_date;

    private List<RectificationTracker> rectificationTrackers;
    private User reciever;

    private String branch_name;
    private String audit_finding;
    private String audit_impact;
    private String audit_recommendation;
    private String audit_case_number;
    private int outstanding_date;
    private String passed_date;
    private int unseen_remark;

    private String banking;

}
