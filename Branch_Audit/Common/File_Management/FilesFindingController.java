package com.afr.fms.Branch_Audit.Common.File_Management;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.afr.fms.Common.Entity.FileInfo;
import com.afr.fms.Common.Message.ResponseMessage;
import com.afr.fms.Payload.endpoint.Endpoint;

@CrossOrigin(origins = Endpoint.URL, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/all_audit")
// @PreAuthorize("hasRole('ADMIN')")
public class FilesFindingController {

  @Autowired
  FilesStorageFindingService storageService;

  @PostMapping("/upload")
  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
    String message = "";
    try {
      storageService.save(file);
      message = "Uploaded the file successfully: " + file.getOriginalFilename();
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    } catch (Exception e) {
      message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
    }
  }

  @GetMapping("/files")
  public ResponseEntity<List<FileInfo>> getListFiles() {
    List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
      String filename = path.getFileName().toString();
      String url = MvcUriComponentsBuilder
          .fromMethodName(FilesFindingController.class, "getFile", path.getFileName().toString()).build().toString();
      return new FileInfo(filename, url);
    }).collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
  }

  @PostMapping("/files")
  public ResponseEntity<List<FileInfo>> getFiles(@RequestBody List<String> uploaded_files) {
    System.out.println(uploaded_files);
    List<FileInfo> fileInfos = new ArrayList<>();
    String url;
    for (String name : uploaded_files) {
      FileInfo fileInfo = new FileInfo();
      fileInfo.setName(name);
      url = MvcUriComponentsBuilder
          .fromMethodName(FilesFindingController.class, "getFile", name.toString()).build().toString();
      fileInfo.setUrl(url);
      fileInfos.add(fileInfo);
    }
    return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
  }

  @GetMapping("/files/{filename:.+}")
  @ResponseBody
  public ResponseEntity<Resource> getFile(@PathVariable String filename) {
    Resource file = storageService.load(filename);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
  }
}