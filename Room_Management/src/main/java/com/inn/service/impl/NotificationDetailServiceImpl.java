package com.inn.service.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import com.inn.constant.RoomConstant;
import com.inn.customException.CustomException;
import com.inn.model.DocumentAttachment;
import com.inn.model.NotificationDetail;
import com.inn.model.PurchaseItemDetail;
import com.inn.repository.INotificationDetailRepository;
import com.inn.repository.IPurchaseItemDetailRepository;
import com.inn.service.IDocumentAttachmentService;
import com.inn.service.INotificationDetailService;
import com.inn.utils.RoomUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationDetailServiceImpl implements INotificationDetailService {

  @Autowired
  INotificationDetailRepository notificationDetailRepository;

  @Autowired
  IDocumentAttachmentService documentAttachmentService;

  @Autowired
  IPurchaseItemDetailRepository purchaseItemDetailRepository;

  public static final int MESSAGE_COLUMN_WIDTH = 50000;
  public static final float ERROR_ROW_HEIGHT = 15f;

  @Override
  public NotificationDetail create(NotificationDetail notificationDetail) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "create {}", kv("NotificationDetail", notificationDetail));
      return notificationDetailRepository.save(notificationDetail);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<NotificationDetail> findAll() throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findAll ");
      return notificationDetailRepository.findAll();
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<NotificationDetail> findByPoIdentifier(String poIdentifier) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByPoIdentifier {}", kv("PoIdentifier :", poIdentifier));
      return notificationDetailRepository.findByPoIdentifier(poIdentifier);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public NotificationDetail findByUsernameAndNotificationIdentifier(String username, String niIdentifier)
      throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByUsernameAndNotificationIdentifier {} {}",
          kv("username :", username), kv("niIdentifier :", niIdentifier));
      return notificationDetailRepository.findByUsernameAndNotificationIdentifier(username, niIdentifier);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public NotificationDetail findByUsernameAndPoIdentifier(String username, String poIdentifier) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByUsernameAndNotificationIdentifier {} {}",
          kv("Username", username), kv("poIdentifier :", poIdentifier));
      return notificationDetailRepository.findByUsernameAndPoIdentifier(username, poIdentifier);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public NotificationDetail findByEmailAndNotificationIdentifier(String email, String niIdentifier) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByEmailAndNotificationIdentifier {} {}", kv("Email :", email),
          kv("niIdentifier :", niIdentifier));
      return notificationDetailRepository.findByEmailAndNotificationIdentifier(email, niIdentifier);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public NotificationDetail findByEmailAndPoIdentifier(String email, String poIdentifier) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByEmailAndPoIdentifier {} {}", kv("Email", email),
          kv("poIdentifier :", poIdentifier));
      return notificationDetailRepository.findByEmailAndPoIdentifier(email, poIdentifier);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public NotificationDetail updateNotificationDetailStatusByUsernameAndNotificationIdentifier(String username,
      String niIdentifier, String status) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByUsernameAndNotificationIdentifier {} {} {}",
          kv("username", username), kv("niIdentifier", niIdentifier), kv("Status", status));
      NotificationDetail notificationDetail = this.findByUsernameAndNotificationIdentifier(username, niIdentifier);
      notificationDetail.setStatus(status);
      notificationDetail.setModifiedDate(RoomUtil.dateFormatter());
      notificationDetail.setModifiedTime(RoomUtil.timeFormatter());
      return notificationDetailRepository.save(notificationDetail);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public NotificationDetail updateNotificationDetailStatusUsernameAndPoIdentifier(String username, String poIdentifier,
      String status) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "updateNotificationDetailStatusUsernameAndPoIdentifier {} {} {}",
          kv("username", username), kv("poIdentifier", poIdentifier), kv("status :", status));
      NotificationDetail notificationDetail = this.findByUsernameAndPoIdentifier(username, poIdentifier);
      notificationDetail.setStatus(status);
      notificationDetail.setModifiedDate(RoomUtil.dateFormatter());
      notificationDetail.setModifiedTime(RoomUtil.timeFormatter());
      return notificationDetailRepository.save(notificationDetail);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public NotificationDetail updateNotificationDetailStatusByEmailAndNotificationIdentifier(String email,
      String niIdentifier, String status) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "updateNotificationDetailStatusByEmailAndNotificationIdentifier {} {}",
          kv("email", email), kv("niIdentifier", niIdentifier), kv("status", status));
      NotificationDetail notificationDetail = this.findByEmailAndNotificationIdentifier(email, niIdentifier);
      notificationDetail.setStatus(status);
      notificationDetail.setModifiedDate(RoomUtil.dateFormatter());
      notificationDetail.setModifiedTime(RoomUtil.timeFormatter());
      return notificationDetailRepository.save(notificationDetail);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public NotificationDetail updateNotificationDetailStatusByEmailAndPoIdentifier(String email, String poIdentifier,
      String status) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "updateNotificationDetailStatusByEmailAndPoIdentifier {} {}",
          kv("email", email), kv("poIdentifier", poIdentifier), kv("status", status));
      NotificationDetail notificationDetail = this.findByEmailAndPoIdentifier(email, poIdentifier);
      notificationDetail.setStatus(status);
      notificationDetail.setModifiedDate(RoomUtil.dateFormatter());
      notificationDetail.setModifiedTime(RoomUtil.timeFormatter());
      return notificationDetailRepository.save(notificationDetail);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public Map<String, List<NotificationDetail>> groupDataByPoIdentifier() throws Exception {
    log.info(RoomConstant.INSIDE_THE_METHOD + "groupDataByPoIdentifier ");
    try {
      Map<String, List<NotificationDetail>> resultMap = new HashMap<>();
      resultMap = this.findAll().stream().collect(Collectors.groupingBy(NotificationDetail::getPoIdentifier));
      return resultMap;
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public ResponseEntity<byte[]> exportNotificationDetailByPoIdentifier(String poIdentifier) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "exportNotificationDetailByPoIdentifier {}",
          kv("POIdentifier", poIdentifier));
      List<NotificationDetail> notificationDetails = this.findByPoIdentifier(poIdentifier);
      log.info("notificationDetails Size {}", kv("notificationDetails size", notificationDetails.size()));
      return generateExcelReport(notificationDetails);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  ResponseEntity<byte[]> generateExcelReport(List<NotificationDetail> notificationDetailList) throws Exception {
    log.info(RoomConstant.INSIDE_THE_METHOD + "generateExcelReport ");
    try {
      SXSSFWorkbook workbook = null;
      Date date = new Date();
      SimpleDateFormat sdf = new SimpleDateFormat(RoomConstant.DATE_TIME);
      String folder = sdf.format(date);
      String downloadPath = RoomConstant.DOWNLOAD_EXCEL_SHEET_NOTIFICATION_DETAIL_PATH + folder + File.separator;
      File createFolder = new File(downloadPath);
      if (!createFolder.exists()) {
        createFolder.mkdirs();
      }
      log.info("Download Path {}", kv("DownloadPath", downloadPath));
      File file = ResourceUtils.getFile("classpath:Notification_Detail_Sample.xlsx");
      String sampleFilePath = file.getAbsolutePath();
      log.info("sampleFilePath Detail  {}", kv("SampleFilePath", sampleFilePath));
      String downloadedFileName = "NotificationDetailResult.xlsx";
      FileInputStream fileInputStream = new FileInputStream(sampleFilePath);
      XSSFWorkbook wbTemplate = new XSSFWorkbook(fileInputStream);
      workbook = new SXSSFWorkbook(wbTemplate);
      workbook.setCompressTempFiles(true);
      SXSSFSheet workSheet = workbook.getSheetAt(0);
      workSheet.setRandomAccessWindowSize(1000);
      Integer rowIndex = 1;
      for (NotificationDetail notificetionDetail : notificationDetailList) {
        log.info("Inside the for loop ...");
        rowIndex = populateWorkSheetCellData(workSheet, rowIndex, notificetionDetail);
      }
      FileOutputStream outputStream = new FileOutputStream(new File(downloadPath + downloadedFileName));
      workbook.write(outputStream);
      outputStream.close();
      log.info("Full file path and file name {}", kv("Full path ", downloadPath + downloadedFileName));
      return RoomUtil.downloadFile(downloadPath + downloadedFileName);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  private Integer populateWorkSheetCellData(SXSSFSheet workSheet, Integer rowIndex,
      NotificationDetail notificationDetail) {
    log.info(RoomConstant.INSIDE_THE_METHOD, "populateWorkSheetCellData");
    try {
      Row row = workSheet.getRow(rowIndex);
      if (row == null) {
        row = workSheet.createRow(rowIndex);
      }
      List<String> cellValueList = new ArrayList<>();
      cellValueList.add(notificationDetail.getId().toString());
      cellValueList.add(notificationDetail.getIdentifier());
      cellValueList.add(notificationDetail.getPoIdentifier());
      cellValueList.add(notificationDetail.getStatus());
      cellValueList.add(notificationDetail.getBuyerUserName());
      cellValueList.add(notificationDetail.getBuyerEmailId());
      cellValueList.add(notificationDetail.getNotificationReceiverUsername());
      cellValueList.add(notificationDetail.getNotificationReceiverEmailId());
      cellValueList.add(notificationDetail.getGroupName());
      cellValueList.add(notificationDetail.getCreatedDate());
      cellValueList.add(notificationDetail.getCreatedTime());
      cellValueList.add(notificationDetail.getModifiedDate() != null ? notificationDetail.getModifiedDate() : "-");
      cellValueList.add(notificationDetail.getModifiedTime() != null ? notificationDetail.getModifiedDate() : "-");
      RoomUtil.cellRender(cellValueList, row);
      return ++rowIndex;
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public ResponseEntity<byte[]> exportSheetByPoIdentifierListForUpdateNotificationDetailStatus(
      List<String> poIdentifierList) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "exportSheetByPoIdentifierListForUpdateNotificationDetailStatus {}",
          kv("poIdentifierList", poIdentifierList));
      String path = "";
      if (poIdentifierList != null && !poIdentifierList.isEmpty()) {
        path = exportSheetByPoIdentifierList(poIdentifierList);
      } else {
        throw new CustomException("PO Identifier cannot be empty ...");
      }
      return RoomUtil.downloadFile(path);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  private String exportSheetByPoIdentifierList(List<String> poIdentifierList) throws Exception {
    log.info(RoomConstant.INSIDE_THE_METHOD + "exportSheetByPoIdentifierList {}",
        kv("poIdentifierList", poIdentifierList));
    try {
      SXSSFWorkbook workbook = null;
      Date date = new Date();
      SimpleDateFormat sdf = new SimpleDateFormat(RoomConstant.DATE_TIME);
      String folder = sdf.format(date);
      String downloadPath = RoomConstant.EXPORT_CHECKLIST_SHEET + folder + File.separator;
      File createFolder = new File(downloadPath);
      if (!createFolder.exists()) {
        createFolder.mkdirs();
      }
      log.info("Download Path {}", kv("DownloadPath", downloadPath));
      File file = ResourceUtils.getFile("classpath:ExportCheckList.xlsx");
      String sampleFilePath = file.getAbsolutePath();
      log.info("sampleFilePath Detail  {}", kv("SampleFilePath", sampleFilePath));
      String downloadedFileName = "ChecListSheet.xlsx";
      FileInputStream fileInputStream = new FileInputStream(sampleFilePath);
      XSSFWorkbook wbTemplate = new XSSFWorkbook(fileInputStream);
      workbook = new SXSSFWorkbook(wbTemplate);
      workbook.setCompressTempFiles(true);
      SXSSFSheet workSheet = workbook.getSheetAt(0);
      workSheet.setRandomAccessWindowSize(1000);
      Set<PurchaseItemDetail> purchaseItemDetailList = new HashSet<>();
      purchaseItemDetailList = poIdentifierList.stream().map(e -> purchaseItemDetailRepository.findByIdentifier(e))
          .collect(Collectors.toSet());
      log.info("PurchaseItemDetailList Size  {}", kv("PurchaseItemDetailList Size", purchaseItemDetailList.size()));
      Integer rowIndex = 1;
      for (PurchaseItemDetail PurchaseItemDetail : purchaseItemDetailList) {
        log.info("Inside the for loop ...");
        rowIndex = populateWorkSheetCellData(workSheet, rowIndex, PurchaseItemDetail);
      }
      FileOutputStream outputStream = new FileOutputStream(new File(downloadPath + downloadedFileName));
      workbook.write(outputStream);
      outputStream.close();
      log.info("Full file path and file name {}", kv("Full path ", downloadPath + downloadedFileName));
      return downloadPath + downloadedFileName;
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  private Integer populateWorkSheetCellData(SXSSFSheet workSheet, Integer rowIndex,
      PurchaseItemDetail purchaseItemDetail) {
    log.info(RoomConstant.INSIDE_THE_METHOD, "populateWorkSheetCellData");
    try {
      Row row = workSheet.getRow(rowIndex);
      if (row == null) {
        row = workSheet.createRow(rowIndex);
      }
      List<String> cellValueList = new ArrayList<>();
      cellValueList.add(purchaseItemDetail.getIdentifier());
      cellValueList.add(purchaseItemDetail.getUserName());
      cellValueList.add("");
      cellValueList.add("");
      RoomUtil.cellRender(cellValueList, row);
      return ++rowIndex;
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public String uploadSheetToUpdateNotificationDetailStatus(InputStream inputStream, String fileName) throws Exception {
    log.info(RoomConstant.INSIDE_THE_METHOD + "uploadSheetToUpdateNotificationDetailStatus");
    try {
      List<String> headerList = RoomUtil.getHeaderList();
      DocumentAttachment documentAttachment = this.genericSchedularImport(inputStream, fileName, headerList);
      if (documentAttachment != null)
        documentAttachment.setStatus("Pending");
      documentAttachmentService.create(documentAttachment);
      return RoomConstant.SUCCESS_JSON;

    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  public Boolean headerMatched(XSSFSheet sheet, List<String> headerList) throws IOException {
    log.info(RoomConstant.INSIDE_THE_METHOD + "headerMatched");
    Boolean isHeadermatched = Boolean.TRUE;
    XSSFRow row = sheet.getRow(0);
    if (row.getPhysicalNumberOfCells() == RoomUtil.getHeaderList().size()) {
      log.info("Inside the if cond ...");
      for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
        log.info(row.getCell(i).toString() + "<------->" + headerList.get(i));
        if (!row.getCell(i).toString().equals(headerList.get(i))) {
          isHeadermatched = Boolean.FALSE;
          break;
        }
      }
    } else {
      isHeadermatched = Boolean.FALSE;
    }
    return isHeadermatched;
  }

  private Map<String, Object> storeImportExcelFile(InputStream inputStream, String fileName) {
    log.info("Inside the  Method storeImportExcelFile fileName {}", kv("FileName", fileName));
    FileOutputStream outputStream = null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    String date = dateFormat.format(new Date());
    log.info("Date format :{}", kv("Date", date));
    String filePath = RoomConstant.DOCUMENT_UPLOAD_PATH + date + File.separator;
    log.info("File path :{}", kv("filePath::", filePath));
    Map<String, Object> returnMap = new HashMap<>();
    XSSFSheet sheet = null;
    try {
      File createFolder = new File(filePath);
      if (!createFolder.exists()) {
        createFolder.mkdirs();
      }
      log.info("File Path {} ", kv("FilePath", filePath));
      outputStream = new FileOutputStream(filePath + fileName);
      XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
      workbook.write(outputStream);
      sheet = workbook.getSheetAt(0);
      returnMap.put("filePath", filePath);
      returnMap.put("sheet", sheet);
      returnMap.put("fileSize", createFolder.getTotalSpace());
      returnMap.put("extension", StringUtils.substringAfterLast(fileName, "."));
      log.info("After set the values in returnMap :", kv("returnMap", returnMap));
      workbook.close();
    } catch (FileNotFoundException e) {
      log.error("Inside Method storeImportExcelFile FileNotFoundException ", e);
      log.error("Error occured inside method :storeImportExcelFile @error :" + e.getLocalizedMessage());
    } catch (IOException e) {
      log.error("Inside Method storeImportExcelFile IOException ", e);
      log.error("Error occured inside method :storeImportExcelFile @error :" + e.getLocalizedMessage());
    } finally {
      if (outputStream != null) {
        try {
          outputStream.close();
          inputStream.close();
        } catch (IOException e) {}
      }
    }
    return returnMap;
  }

  private Map<String, Object> createFileUploadInfo(InputStream inputStream, String fileName) throws Exception {
    log.info("Inside  Method createFileUploadInfo {}", kv("FileName", fileName));
    Map<String, Object> returnMap = null;
    returnMap = this.storeImportExcelFile(inputStream, fileName);
    XSSFSheet sheet = (XSSFSheet) returnMap.get("sheet");
    if (sheet != null) {
      DocumentAttachment document = new DocumentAttachment();
      document.setIdentifier(RoomUtil.generateIdentifier("DOC"));
      document.setFilePath((String) returnMap.get("filePath"));
      document.setIsProcessed(false);
      document.setFileName(fileName);
      document.setFileSize((Long) returnMap.get("fileSize"));
      document.setExtension((String) returnMap.get("extension"));
      documentAttachmentService.create(document);
      returnMap.put("document", document);
    }
    return returnMap;
  }

  private DocumentAttachment genericSchedularImport(InputStream inputStream, String fileName, List<String> headerList)
      throws CustomException {
    try {
      XSSFSheet sheet = null;
      Map<String, Object> returnMap = this.createFileUploadInfo(inputStream, fileName);
      sheet = (XSSFSheet) returnMap.get("sheet");
      DocumentAttachment paymentDocument = (DocumentAttachment) returnMap.get("document");
      log.info("simdetailsSheetGlobal:::: {}", sheet.getLastRowNum());
      Boolean headerMatch = this.headerMatched(sheet, headerList);
      log.info("Is Header Match {}", kv("IsheaderMatch", headerMatch));
      if (Boolean.FALSE.equals(headerMatch)) {
        if (paymentDocument != null) {
          paymentDocument.setIsHeaderMatched(false);
          documentAttachmentService.create(paymentDocument);
        }
      } else {
        if (paymentDocument != null) {
          paymentDocument.setIsHeaderMatched(true);
          paymentDocument = documentAttachmentService.create(paymentDocument);
        }
      }
      log.info("check genericSchedularImport  end return {}", new Date());
      return paymentDocument;
    } catch (Exception e) {
      log.error("Error Occured Inside @Class :  inventoryUtils @Method genericSchedularImport ", e);
      throw new CustomException(e.getMessage());
    }
  }

  Map<String, Map<String, Object>> readExcelSheetData(String filePath) throws Exception {
    log.info(RoomConstant.INSIDE_THE_METHOD + "readExcelSheetData :");
    try {
      Map<String, Map<String, Object>> resultMap = new LinkedHashMap<>();
      FileInputStream inputStream = new FileInputStream(filePath);
      List<String> headerList = getHeaderList(filePath);
      log.info("headerList {}", kv("headerList", headerList));
      XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
      XSSFSheet sheet = workbook.getSheetAt(0);
      for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
        Map<String, Object> map = new LinkedHashMap<>();
        XSSFRow row = sheet.getRow(i);
        for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
          log.info(row.getCell(j) + " ");
          map.put(headerList.get(j), row.getCell(j).toString());
        }
        resultMap.put((String) map.get("PURCHASE ID"), map);
      }
      log.info("resultMap {}", kv("resultMap", resultMap));
      return resultMap;
    } catch (IOException e) {
      log.error(RoomConstant.ERROR_DUE_TO + e.getMessage());
      throw new CustomException("Something went wrong ..");
    }
  }

  List<String> getHeaderList(String filePath) throws Exception {
    log.info(RoomConstant.INSIDE_THE_METHOD + "getHeaderList :");
    try {
      List<String> headerList = new ArrayList<>();
      FileInputStream inputStream = new FileInputStream(filePath);
      XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
      XSSFSheet sheet = workbook.getSheetAt(0);
      for (int i = 0; i < 1; i++) {
        XSSFRow row = sheet.getRow(i);
        for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
          headerList.add(row.getCell(j).toString());
        }
      }
      return headerList;
    } catch (IOException e) {
      log.error(RoomConstant.ERROR_DUE_TO + e.getMessage());
      throw new CustomException("Something went wrong ..");
    }
  }

  @Override
  @Transactional
  @Scheduled(cron = "0 */10 * * * *")
  public String processSheetToUpdateNotificationDetailStatus() throws Exception {
    log.info(RoomConstant.INSIDE_THE_METHOD + "processSheetToUpdateNotificationDetailStatus ");
    try {
      List<String> errorList = new ArrayList<>();
      Map<String, List<String>> responseMap = new HashMap<>();
      String result = RoomConstant.SUCCESS_JSON;
      List<DocumentAttachment> documentAttachmentList = documentAttachmentService.getDocumentAttachmentListByStatus();
      log.info("documentAttachmentList {} {}", kv("documentAttachmentList", documentAttachmentList),
          kv("documentAttachmentList size", documentAttachmentList.size()));
      if (documentAttachmentList != null && !documentAttachmentList.isEmpty()) {
        for (DocumentAttachment documentAttachment : documentAttachmentList) {
          String fileName = documentAttachment.getFileName();
          String filePath = documentAttachment.getFilePath();
          // for read excel sheet Data
          Map<String, Map<String, Object>> resultMap = readExcelSheetData(filePath + fileName);
          for (String mapdata : resultMap.keySet()) {
            log.info("Map data  {}", kv("mapdata", mapdata));
            Map<String, Object> data = resultMap.get(mapdata);
            // update Notification Detail Status
            responseMap = updateStatusNotificationDetail(data, errorList);
            log.info("Error List  {}", kv("ERROR_LIST", responseMap.get(RoomConstant.ERROR_LIST)));
          }
          documentAttachment.setIsProcessed(true);
          documentAttachment.setStatus(RoomConstant.COMPLETED);
          documentAttachment.setModifiedDate(RoomUtil.dateFormatter());
          documentAttachment.setModifiedTime(RoomUtil.timeFormatter());
          documentAttachmentService.create(documentAttachment);
        }
      } else {
        result = "No Files are in Pending Status";
      }
      if (documentAttachmentList != null && !documentAttachmentList.isEmpty()) {
        createFileRespone(documentAttachmentList.get(0), responseMap);
      }
      return result;
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  Map<String, List<String>> updateStatusNotificationDetail(Map<String, Object> mapdata, List<String> errorList)
      throws Exception {
    log.info(RoomConstant.INSIDE_THE_METHOD + "updateStatusNotificationDetail ");
    Map<String, List<String>> responseMap = new HashMap<>();
    String confirmed = (String) mapdata.get(RoomConstant.COMFIRMED_STATUS);
    String username = (String) mapdata.get(RoomConstant.USERNAME);
    String status = (String) mapdata.get(RoomConstant.STATUS);
    String purchaseId = (String) mapdata.get(RoomConstant.PURCHASE_ID);
    log.info("confirmed,username,status,purchaseId {} {} {} {} {}", kv("confirmed", confirmed),
        kv("username", username), kv("status", status), kv("purchaseId", purchaseId));
    if (confirmed.equalsIgnoreCase("YES")) {
      NotificationDetail notificationDetail = notificationDetailRepository.findByUsernameAndPoIdentifier(username,
          purchaseId);
      if (notificationDetail != null) {
        notificationDetail.setStatus(status);
        notificationDetail.setModifiedDate(RoomUtil.dateFormatter());
        notificationDetail.setModifiedTime(RoomUtil.timeFormatter());
        notificationDetailRepository.save(notificationDetail);
        errorList.add("Status updated Successfully.");
        responseMap.put(RoomConstant.ERROR_LIST, errorList);
      } else {
        errorList.add("Invalid Username or purchase Id");
        responseMap.put(RoomConstant.ERROR_LIST, errorList);
      }
    } else {
      errorList.add("Confirmed Status is No");
      responseMap.put(RoomConstant.ERROR_LIST, errorList);
    }
    log.info("Response Map {}", kv("responseMap", responseMap));
    return responseMap;
  }

  public void createFileRespone(DocumentAttachment documentAttachment, Map<String, List<String>> responseMap)
      throws Exception {
    log.info(RoomConstant.INSIDE_THE_METHOD + "createFileRespone");
    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
      String date = dateFormat.format(new Date());
      log.info("Date format :{}", kv("Date", date));
      String responeFilePath = RoomConstant.DOCUMENT_DOWNLOAD_PATH + date + File.separator;
      log.info("File path :{}", kv("filePath::", responeFilePath));
      String responeFileName = "Result_" + date + ".xlsx";
      File createFolder = new File(responeFilePath);
      if (!createFolder.exists()) {
        createFolder.mkdirs();
      }
      log.info("download Path {} ", kv("downloadPath", responeFilePath));

      String fileName = documentAttachment.getFileName();
      String filePath = documentAttachment.getFilePath();
      InputStream inputStream = new FileInputStream(filePath + fileName);
      XSSFWorkbook wb = new XSSFWorkbook(inputStream);
      XSSFSheet sheet = wb.getSheetAt(0);
      XSSFCellStyle style = RoomUtil.getCheckListCellStyle(wb);
      XSSFRow row = sheet.getRow(0);
      List<String> errorList = responseMap.get(RoomConstant.ERROR_LIST);
      for (int i = 0; i <= sheet.getLastRowNum(); i++) {
        if (i == 0) {
          XSSFCell msgCell = row.createCell(row.getLastCellNum());
          msgCell.setCellValue("Message");
          msgCell.setCellStyle(style);
          sheet.setColumnWidth(row.getLastCellNum(), MESSAGE_COLUMN_WIDTH);
          sheet.setHorizontallyCenter(true);
        } else {
          XSSFRow row2 = sheet.getRow(i);
          XSSFCell createCell = row2.createCell(row.getLastCellNum() - 1, CellType.STRING);
          if (errorList.size() >= i)
            createCell.setCellValue(errorList.get(i - 1));
        }
      }
      FileOutputStream fos = new FileOutputStream(responeFilePath + responeFileName);
      wb.write(fos);
      fos.close();
      inputStream.close();
      documentAttachment.setResponseFilePath(responeFilePath);
      documentAttachment.setResponseFileName(responeFileName);
      documentAttachmentService.create(documentAttachment);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public Set<String> findUniqueAllNoitifId(String groupName) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findUniqueAllNoitifId ");

      return this.findAll().stream().distinct().filter(e->e.getGroupName().equals(groupName)).map(NotificationDetail::getIdentifier).collect(Collectors.toSet());
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public Set<String> findUniqueAllEmailId(String groupName) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findUniqueAllEmailId ");
      return this.findAll().stream().distinct().filter(e->e.getGroupName().equals(groupName)).filter(e->e.getStatus().equals("Pending")).map(NotificationDetail::getNotificationReceiverEmailId).collect(Collectors.toSet());
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public Set<String> findUniqueAllusername(String groupName) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findUniqueAllusername ");
      return this.findAll().stream().distinct().filter(e->e.getGroupName().equals(groupName)).filter(e->e.getStatus().equals("Pending")).map(NotificationDetail::getNotificationReceiverUsername).collect(Collectors.toSet());
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public Set<String> findUniqueAllPoId(String groupName) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findUniqueAllPoId ");
      return this.findAll().stream().distinct().filter(e->e.getGroupName().equals(groupName)).map(NotificationDetail::getPoIdentifier).collect(Collectors.toSet());
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

}
