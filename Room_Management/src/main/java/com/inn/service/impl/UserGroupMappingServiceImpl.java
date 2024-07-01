package com.inn.service.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.inn.constant.RoomConstant;
import com.inn.model.EmailDetails;
import com.inn.model.UserGroupMapping;
import com.inn.model.UserRegistration;
import com.inn.repository.IUserGroupMappingRepository;
import com.inn.service.IEmailDetailService;
import com.inn.service.IUserGroupMappingService;
import com.inn.utils.RoomUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserGroupMappingServiceImpl implements IUserGroupMappingService {

  @Autowired
  IUserGroupMappingRepository userGroupMappingRepository;

  @Autowired
  IEmailDetailService emailDetailService;

  @Override
  public List<UserGroupMapping> findByGroupName(String groupName) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByGroupName {}", kv("GroupName", groupName));
      return userGroupMappingRepository.findByGroupName(groupName);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<UserGroupMapping> findByEmail(String email) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByEmail {}", kv("Email", email));
      return userGroupMappingRepository.findByEmail(email);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public String sendEmailByGroupWise(String groupName) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "sendEmailByGroupWise {}", kv("GroupName", groupName));
      List<UserGroupMapping> userGrpMappingList = userGroupMappingRepository.findByGroupName(groupName);
      List<String> emailList = new ArrayList<>();
      if (userGrpMappingList != null) {
        for (UserGroupMapping userGroupMapping : userGrpMappingList) {
          emailList.add(userGroupMapping.getEmail());
          sendEmai(userGroupMapping.getEmail());
        }
        return "Email sent successfully to there users [" + StringUtils.join(emailList, ",") + "]";
      } else {
        return "No user found ....";
      }
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  private void sendEmai(String email) throws Exception {
    log.info(RoomConstant.INSIDE_THE_METHOD + "sendEmailToUser {} {}", kv("Email", email));
    EmailDetails emailDetails = new EmailDetails();
    emailDetails.setRecipient(email);
    emailDetails.setSubject("Regarding purchase item Approval.");
    emailDetails.setMsgBody("");
    emailDetails.setAttachment(null);
    emailDetailService.sendMailWithOutAttachment(emailDetails);
  }

  @Override
  public ResponseEntity<byte[]> exportDataByGroupName(String groupName) throws Exception {
    try {
      String filepath = generateReport(groupName);
      log.info("filepath {}", kv("filepath", filepath));
      return RoomUtil.downloadFile(filepath);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  private Integer populateWorkSheetCellData(SXSSFSheet workSheet, Integer rowIndex, UserGroupMapping userGroupMapping) {
    try {
      Row row = workSheet.getRow(rowIndex);
      if (row == null) {
        row = workSheet.createRow(rowIndex);
      }
      List<String> cellValueList = new ArrayList<>();
      cellValueList.add(userGroupMapping.getId().toString());
      cellValueList.add(userGroupMapping.getEmail());
      cellValueList.add(userGroupMapping.getGroupName());
      RoomUtil.cellRender(cellValueList, row);
      return ++rowIndex;
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  String generateReport(String groupName) throws Exception {
    log.info(RoomConstant.INSIDE_THE_METHOD + "generateReport {}", kv("GroupName", groupName));
    SXSSFWorkbook workbook = null;
    File file = ResourceUtils.getFile("classpath:GroupAndEmailDetail.xlsx");
    FileInputStream fileInputStream = new FileInputStream(file);
    XSSFWorkbook wbTemplate = new XSSFWorkbook(fileInputStream);
    SimpleDateFormat sdf = new SimpleDateFormat(RoomConstant.DATE_TIME);
    String folder = sdf.format(new Date());
    String downloadPath = RoomConstant.DOWNLOAD_EXCEL_SHEET_PATH + folder + File.separator;
    File createFolder = new File(downloadPath);
    if (!createFolder.exists()) {
      createFolder.mkdirs();
    }
    workbook = new SXSSFWorkbook(wbTemplate);
    workbook.setCompressTempFiles(true);
    SXSSFSheet workSheet = workbook.getSheetAt(0);
    workSheet.setRandomAccessWindowSize(100000);
    List<UserGroupMapping> userGroupMappingList = this.findByGroupName(groupName);
    Integer rowIndex = 1;
    for (UserGroupMapping userGroupMapping : userGroupMappingList) {
      rowIndex = populateWorkSheetCellData(workSheet, rowIndex, userGroupMapping);
    }
    String fileName = "Result" + folder + ".xlsx";
    FileOutputStream outputStream = new FileOutputStream(new File(downloadPath + fileName));
    workbook.write(outputStream);
    outputStream.close();
    return downloadPath + fileName;
  }

  @Override
  public List<String> findByUsernameByGroupName(String groupName) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByUsernameByGroupName {}", kv("groupName", groupName));
      List<UserGroupMapping> userGroupMappings = this.findByGroupName(groupName);
      List<String> usernameList = userGroupMappings.stream().distinct().map(e -> e.getUsername())
          .collect(Collectors.toList());
      log.info("Username List :{}", usernameList);
      return usernameList;
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

}
