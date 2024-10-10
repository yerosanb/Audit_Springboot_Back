package com.afr.fms.Branch_Audit.Entity;

import java.util.List;
import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class BranchManagerFile {

    private Long id;
      private Long branch_audit_id;
      private List<String>file_urls;
      private String uploaded_date;
      private String file_url;

    
}
