package com.inn.service.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import com.inn.constant.RoomConstant;
import com.inn.customException.CustomException;
import com.inn.model.Attachment;
import com.inn.model.EmailDetails;
import com.inn.model.ItemDetail;
import com.inn.model.NotificationDetail;
import com.inn.model.NotificationItemDetail;
import com.inn.model.PurchaseItemDetail;
import com.inn.model.UserGroupMapping;
import com.inn.model.UserRegistration;
import com.inn.repository.IPurchaseItemDetailRepository;
import com.inn.repository.ItemDetailRepository;
import com.inn.service.IAttachmentService;
import com.inn.service.IEmailDetailService;
import com.inn.service.IGroupDetailService;
import com.inn.service.INotificationDetailService;
import com.inn.service.INotificationItemDetailService;
import com.inn.service.IPurchaseItemDetailService;
import com.inn.service.IUserGroupMappingService;
import com.inn.service.IUserRegistrationService;
import com.inn.utils.RoomUtil;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
@Slf4j
public class PurchaseItemDetailServiceImpl implements IPurchaseItemDetailService {
  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  IPurchaseItemDetailRepository purchaseItemDetailRepository;

  @Autowired
  ItemDetailRepository itemDetailRepository;

  @Autowired
  IUserRegistrationService userRegistrationService;

  @Autowired
  IGroupDetailService groupDetailService;

  @Autowired
  IAttachmentService attachmentService;

  @Autowired
  IUserGroupMappingService userGroupMappingService;

  @Autowired
  INotificationDetailService notificationDetailService;

  @Autowired
  INotificationItemDetailService notificationItemDetailService;

  @Autowired
  IEmailDetailService emailDetailService;

  @Override
  @Transactional
  public PurchaseItemDetail create(PurchaseItemDetail purchaseItemDetail) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "create {}", kv("PurchaseItemDetail", purchaseItemDetail));
      /* user name validation */
      UserRegistration userRegistration = userRegistrationService.findByUsername(purchaseItemDetail.getUserName());
      /* group name validation */
      groupDetailService.findByGroupName(purchaseItemDetail.getGroupName());
      String emailId = userRegistration.getEmailId();
      String userIdentifier = userRegistration.getIdentifier();
      LocalDate localDate = LocalDate.now();
      String monthName = localDate.getMonth().name();
      String day = localDate.getDayOfWeek().name();
      purchaseItemDetail.setMonth(monthName);
      purchaseItemDetail.setDay(day);
      purchaseItemDetail.setEmailId(emailId);
      purchaseItemDetail.setUserIdentifier(userIdentifier);
      String identifier = RoomUtil.generateIdentifier("PO");
      purchaseItemDetail.setIdentifier(identifier);
      purchaseItemDetail.setCreatedDate(RoomUtil.dateFormatter());
      purchaseItemDetail.setCreatedTime(RoomUtil.timeFormatter());
      purchaseItemDetail.setStatus("Pending");
      PurchaseItemDetail purchaseItemDetails = purchaseItemDetailRepository.save(purchaseItemDetail);
      List<ItemDetail> itemDetails = purchaseItemDetails.getItemDetail();
      for (ItemDetail itemDetail : itemDetails) {
        itemDetail.setPurchaseItemDetail(purchaseItemDetails);
        itemDetailRepository.save(itemDetail);
      }
      // Generate Report Pdf
      String filePath = this.generateReport(identifier);
      // save attachment in database
      createAttachment(purchaseItemDetails, filePath, identifier);

      // send Notification to user by groupWise
      sendNotificationToUserByGroupWise(purchaseItemDetail, filePath, identifier);

      return purchaseItemDetails;
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  private void sendNotificationToUserByGroupWise(PurchaseItemDetail purchaseItemDetails, String filePath,
      String identifier) throws Exception {
    log.info("Path and identifier :{} {}", kv("Identifier :", identifier), kv("filePath :", filePath));
    List<UserGroupMapping> userGroupMappingDetail = userGroupMappingService
        .findByGroupName(purchaseItemDetails.getGroupName());
    List<ItemDetail> itemDetails = purchaseItemDetails.getItemDetail();
    Set<String> emailList = userGroupMappingDetail.parallelStream().map(UserGroupMapping::getEmail)
        .collect(Collectors.toSet());
    log.info("email Id List {}", kv("emailList", emailList));
    String niIdentiifer = RoomUtil.generateIdentifier("NI");
    for (String email : emailList) {
      log.info("EmailId :{}", kv("Email ::", email));
      UserRegistration userRegistration = userRegistrationService.findByEmail(email);
      if (userRegistration != null) {
        NotificationDetail notificationDetail = new NotificationDetail();
        notificationDetail.setIdentifier(niIdentiifer);
        notificationDetail.setPoIdentifier(identifier);
        notificationDetail.setCreatedDate(RoomUtil.dateFormatter());
        notificationDetail.setCreatedTime(RoomUtil.timeFormatter());
        notificationDetail.setGroupName(purchaseItemDetails.getGroupName());
        notificationDetail.setStatus("Pending");
        notificationDetail.setBuyerEmailId(purchaseItemDetails.getEmailId());
        notificationDetail.setBuyerUserName(purchaseItemDetails.getUserName());
        notificationDetail.setBuyerUserIdentifier(purchaseItemDetails.getUserIdentifier());
        notificationDetail.setNotificationReceiverEmailId(userRegistration.getEmailId());
        notificationDetail.setNotificationReceiverUserIdentifier(userRegistration.getIdentifier());
        notificationDetail.setNotificationReceiverUsername(userRegistration.getUserName());
        List<NotificationItemDetail> notificationItemDetailList = new ArrayList<>();
        for (ItemDetail itemDetail : itemDetails) {
          NotificationItemDetail notificationItemDetail = new NotificationItemDetail();
          notificationItemDetail.setId(itemDetail.getId());
          notificationItemDetail.setItemName(itemDetail.getItemName());
          notificationItemDetail.setPrice(itemDetail.getPrice());
          notificationItemDetail.setQuantity(itemDetail.getQuantity());
          notificationItemDetail.setUnitPrice(itemDetail.getUnitPrice());
          notificationItemDetail.setUnitOfMeasurement(itemDetail.getUnitOfMeasurement());
          notificationItemDetailList.add(notificationItemDetail);
        }
        notificationDetail.setNotificationItemDetail(notificationItemDetailList);
        NotificationDetail create = entityManager.merge(notificationDetail);
        List<NotificationItemDetail> notificationItemDetail2 = create.getNotificationItemDetail();
        for (NotificationItemDetail NotificationItemDetail : notificationItemDetail2) {
          NotificationItemDetail.setNotificationDetail(create);
          entityManager.merge(NotificationItemDetail);
        }
      }
      sendEmailNotification(email, niIdentiifer, filePath, purchaseItemDetails);
    }

  }

  private void sendEmailNotification(String email, String niIdentiifer, String filePath,
      PurchaseItemDetail purchaseItemDetails) throws Exception {
    log.info("Inside the sendEmailNotification {} {} {} ", kv("niIdentiifer", niIdentiifer), kv("FilePath", filePath));
    EmailDetails emailDetails = new EmailDetails();
    emailDetails.setRecipient(email);
    emailDetails.setSubject("Regardig new item approval.");
    emailDetails.setMsgBody("Purchase Order Id : " + purchaseItemDetails.getIdentifier() + "\nNotification Id : "
        + niIdentiifer + "\nBuyer UserName : " + purchaseItemDetails.getUserName() + "\n" + "Buyer Email : "
        + purchaseItemDetails.getEmailId() + "\nStatus : " + purchaseItemDetails.getStatus()
        + "\nBuy below item in pdf.");
    emailDetails.setAttachment(filePath);
    emailDetailService.sendMailWithAttachment(emailDetails);
  }

  private Attachment createAttachment(PurchaseItemDetail purchaseItemDetails, String filepath, String identifier)
      throws Exception {
    log.info(RoomConstant.INSIDE_THE_METHOD + "createAttachment {} {} ", kv("filepath", filepath),
        kv("Identifier", identifier));
    Attachment attachment = new Attachment();
    attachment.setPidIdentifier(identifier);
    attachment.setUserIdentifier(purchaseItemDetails.getUserIdentifier());
    attachment.setUserName(purchaseItemDetails.getUserName());
    File file = new File(filepath);
    String fileName = file.getName();
    String extension = StringUtils.substringAfterLast(fileName, ".");
    String filePath = StringUtils.substringBeforeLast(file.getPath(), "\\") + File.separator;
    long fileSize = file.getTotalSpace();
    log.info("Detail of files  {} {} {}", kv("fileName", fileName), kv("filePath", filePath), kv("fileSize", fileSize));
    attachment.setFileName(fileName);
    attachment.setFilePath(filePath);
    attachment.setExtension(extension);
    attachment.setFileSize(fileSize);
    return attachmentService.create(attachment);
  }

  @Override
  public List<PurchaseItemDetail> findAll() throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findAll ");
      return purchaseItemDetailRepository.findAll();
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<PurchaseItemDetail> findByUsername(String username) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByUsername {}", kv("Username.", username));
      return purchaseItemDetailRepository.findByUsername(username);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<PurchaseItemDetail> findByEmail(String email) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByEmail {}", kv("Email", email));
      return purchaseItemDetailRepository.findByEmail(email);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public PurchaseItemDetail findByIdentifier(String identifier) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByIdentifier {}", kv("Identifier", identifier));
      return purchaseItemDetailRepository.findByIdentifier(identifier);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<PurchaseItemDetail> findByUserIdentifier(String userIdentifier) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByUserIdentifier {}", kv("UserIdentifier", userIdentifier));
      return purchaseItemDetailRepository.findByUserIdentifier(userIdentifier);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<PurchaseItemDetail> findByGroupName(String groupName) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByGroupName {}", kv("GroupName", groupName));
      return purchaseItemDetailRepository.findByGroupName(groupName);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<PurchaseItemDetail> findByPurchaseDate(LocalDate purchaseDate) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByPurchaseDate {}", kv("PurchaseDate", purchaseDate));
      return purchaseItemDetailRepository.findByPurchaseDate(purchaseDate);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<PurchaseItemDetail> findByStatus(String status) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByStatus {}", kv("Status :", status));
      return purchaseItemDetailRepository.findByStatus(status);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<PurchaseItemDetail> findByUsernameAndStatus(String username, String status) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByUsernameAndStatus {} {}", kv("Username", username),
          kv("Status :", status));
      return purchaseItemDetailRepository.findByUsernameAndStatus(username, status);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<PurchaseItemDetail> findByEmailAndStatus(String email, String status) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByEmailAndStatus {} {}", kv("Email", email), kv("Status", status));
      return purchaseItemDetailRepository.findByEmailAndStatus(email, status);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<PurchaseItemDetail> findByUserIdentifierAndStatus(String userIdentifier, String status) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByUserIdentifierAndStatus {} {}",
          kv("userIdentifier", userIdentifier), kv("status", status));
      return purchaseItemDetailRepository.findByUserIdentifierAndStatus(userIdentifier, status);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<PurchaseItemDetail> findByUsernameAndpurchaseDate(String username, LocalDate purchaseDate)
      throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByUsernameAndpurchaseDate {} {}", kv("username.", username),
          kv("PurchaseDate", purchaseDate));
      return purchaseItemDetailRepository.findByUsernameAndpurchaseDate(username, purchaseDate);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<PurchaseItemDetail> findByEmailAndpurchaseDate(String email, LocalDate purchaseDate) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByEmailAndpurchaseDate {} {}", kv("email", email),
          kv("purchaseDate :", purchaseDate));
      return purchaseItemDetailRepository.findByEmailAndpurchaseDate(email, purchaseDate);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<PurchaseItemDetail> findByUserIdentifierAndPurchaseDate(String userIdentifier, LocalDate purchaseDate)
      throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByUserIdentifierAndPurchaseDate {} {}",
          kv("userIdentifier", userIdentifier), kv("purchaseDate", purchaseDate));
      return purchaseItemDetailRepository.findByUserIdentifierAndPurchaseDate(userIdentifier, purchaseDate);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public String exportPdfReportByIdentifier(String identifier) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "exportPdfReportByIdentifier {} ", kv("Identifier :", identifier));
      return generateReport(identifier);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  String generateReport(String identifier) throws Exception {
    log.info(RoomConstant.INSIDE_THE_METHOD + "generateReport {}", kv("identifier", identifier));
    try {
      Date date = new Date();
      SimpleDateFormat sdf = new SimpleDateFormat(RoomConstant.DATE_TIME);
      String folder = sdf.format(date);
      String downloadPath = RoomConstant.DOWNLOAD_PDF_FILE_PATH + folder + File.separator;
      File createFolder = new File(downloadPath);
      if (!createFolder.exists()) {
        createFolder.mkdirs();
      }
      log.info("Download Path {}", kv("DownloadPath", downloadPath));
      File file = ResourceUtils.getFile("classpath:PurchaseItemDetail.jasper");
      String sampleFilePath = file.getAbsolutePath();
      log.info("sampleFilePath Detail  {}", kv("SampleFilePath", sampleFilePath));
      PurchaseItemDetail purchaseItemDetail = this.findByIdentifier(identifier);
      String downloadedFileName = "";
      if (purchaseItemDetail != null) {
        downloadedFileName = purchaseItemDetail.getUserName() + ".pdf";
        Map<String, Object> parameter = setParameterForPurchaseDetail(purchaseItemDetail);
        log.info("After set values in parameter  {}", kv("Parameter", parameter));
        JRBeanCollectionDataSource jbcs = new JRBeanCollectionDataSource(purchaseItemDetail.getItemDetail());
        JasperRunManager.runReportToPdfFile(sampleFilePath, downloadPath + downloadedFileName, parameter, jbcs);
      }
      log.info("Full file path and file name {}", kv("Full path ", downloadPath + downloadedFileName));
      return downloadPath + downloadedFileName;
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  private Map<String, Object> setParameterForPurchaseDetail(PurchaseItemDetail purchaseItemDetail) {
    Map<String, Object> variableMap = new HashMap<>();
    variableMap.put("username", purchaseItemDetail.getUserName());
    variableMap.put("status", purchaseItemDetail.getStatus());
    variableMap.put("email", purchaseItemDetail.getEmailId());
    variableMap.put("userId", purchaseItemDetail.getUserIdentifier());
    variableMap.put("groupName", purchaseItemDetail.getGroupName());
    variableMap.put("purchaseId", purchaseItemDetail.getIdentifier());
    variableMap.put("paymentStatus", purchaseItemDetail.getPaymentStatus().toString());
    variableMap.put("purchaseDate", purchaseItemDetail.getPurchaseDate().toString());
    variableMap.put("modeOfPayment", purchaseItemDetail.getModeOfPayment().toString());
    return variableMap;
  }

  @Override
  public ResponseEntity<byte[]> downloadPdfReportByIdentifier(String identifier) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "downloadPdfReportByIdentifier {} ", kv("identifier", identifier));
      String filepath = generateReport(identifier);
      return RoomUtil.downloadFile(filepath);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
//   @Scheduled(cron = "0 * * * * *")
  @Scheduled(cron = "0 */10 * * * *")
  public String updatePurchaseItemDetailStatus() throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "updatePurchaseItemDetailStatus :");
      PurchaseItemDetail purchaseItemDetail = null;
      Map<String, List<NotificationDetail>> resultMap = notificationDetailService.groupDataByPoIdentifier();
      log.info("After get the data from groupDataByPoIdentifier :");

      for (String poIdentifier : resultMap.keySet()) {
        log.info("Inside the for loop {}", kv("poIdentifier", poIdentifier));
        List<NotificationDetail> notificationDetailList = resultMap.get(poIdentifier);
        Boolean isApproved = notificationDetailList.parallelStream()
            .allMatch(e -> e.getStatus().equalsIgnoreCase(RoomConstant.APPROVED_STAUTS));
        log.info("value of isApproved {}", kv("IsApproved", isApproved));
        Boolean isCancelled = notificationDetailList.parallelStream()
            .anyMatch(e -> e.getStatus().equalsIgnoreCase(RoomConstant.CANCELLED_STAUTS));
        log.info("value of isCancelled {}", kv("isCancelled", isCancelled));
        purchaseItemDetail = this.findByIdentifier(poIdentifier);
        log.info("Purchase Item Detail Status : {}", kv("Status", purchaseItemDetail.getStatus()));
        if (!purchaseItemDetail.getStatus().equalsIgnoreCase(RoomConstant.APPROVED_STAUTS)) {
          if (Boolean.TRUE.equals(isApproved)) {
            purchaseItemDetail.setStatus(RoomConstant.APPROVED_STAUTS);
          } else if (Boolean.TRUE.equals(isCancelled)) {
            purchaseItemDetail.setStatus(RoomConstant.CANCELLED_STAUTS);
          } else {
            purchaseItemDetail.setStatus(RoomConstant.WAITING_FOR_APPROVAL);
          }
          PurchaseItemDetail updatePurchaseItemDetail = purchaseItemDetailRepository.save(purchaseItemDetail);
          // Generate Report Pdf
          String filePath = this.generateReport(updatePurchaseItemDetail.getIdentifier());
          // save attachment in database
          createAttachment(updatePurchaseItemDetail, filePath, updatePurchaseItemDetail.getIdentifier());
          // Trigger Email
          sendApprovalNotification(filePath, updatePurchaseItemDetail);
        }
      }
      return " Status Updated successfully...";
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  private void sendApprovalNotification(String filePath, PurchaseItemDetail purchaseItemDetails) throws Exception {
    log.info("Inside the sendEmailNotification {} ", kv("filePath", filePath));
    List<NotificationDetail> notificationDetailList = notificationDetailService
        .findByPoIdentifier(purchaseItemDetails.getIdentifier());
    List<String> emailList = notificationDetailList.parallelStream()
        .map(NotificationDetail::getNotificationReceiverEmailId).collect(Collectors.toList());
    log.info("Email List and GroupName :", kv("emailList", emailList),
        kv("GroupName", purchaseItemDetails.getGroupName()));
    for (String email : emailList) {
      EmailDetails emailDetails = new EmailDetails();
      emailDetails.setRecipient(email);
      emailDetails.setSubject("Regardig Status approval.");
      emailDetails.setMsgBody("Purchase Order Id : " + purchaseItemDetails.getIdentifier() + "\nBuyer UserName : "
          + purchaseItemDetails.getUserName() + "\n" + "Buyer Email : " + purchaseItemDetails.getEmailId()
          + "\nStatus : " + purchaseItemDetails.getStatus() + "\nBuy below item in pdf.");
      emailDetails.setAttachment(filePath);
      emailDetailService.sendMailWithAttachment(emailDetails);
    }
  }

  @Override
  public Map<String, Map<String, Double>> calculatPoDetailTotalCostByUserAndMonthWise(String username)
      throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "calculatPoDetailTotalCostByUserAndMonthWise {}",
          kv("Username", username));
      Map<String, Map<String, Double>> resultMap = new HashMap<>();
      Map<String, Double> result = new HashMap<>();
      List<PurchaseItemDetail> purchaseDetailList = this.findByUsername(username);
      if (purchaseDetailList != null && !purchaseDetailList.isEmpty()) {
        Map<String, List<PurchaseItemDetail>> groupDataBymonthWise = purchaseDetailList.stream()
            .collect(Collectors.groupingBy(PurchaseItemDetail::getMonth));
        for (String month : groupDataBymonthWise.keySet()) {
          List<PurchaseItemDetail> purchaseDetail = groupDataBymonthWise.get(month);
          double sum = purchaseDetail.stream()
              .map(e -> e.getItemDetail().stream().mapToDouble(p -> p.getPrice().doubleValue()).sum())
              .collect(Collectors.toList()).stream().mapToDouble(Double::doubleValue).sum();
          log.info(" ------->{}", kv("sum", sum));
          result.put(month, sum);
          log.info("Result------->{}", kv("result", result));
        }
      } else {
        throw new CustomException("Invalid Username ....");
      }
      resultMap.put(username, result);
      return resultMap;
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public Map<String, Map<String, Double>> calculatPoDetailTotalCostAllUserAndMonthWise() throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "calculatPoDetailTotalCostAllUserAndMonthWise ");
      Map<String, Map<String, Double>> resultMap = new HashMap<>();
      List<PurchaseItemDetail> findAll = this.findAll();
      Map<String, List<PurchaseItemDetail>> groupByUserWise = findAll.parallelStream()
          .collect(Collectors.groupingBy(PurchaseItemDetail::getUserName));
      for (String username : groupByUserWise.keySet()) {
        resultMap.putAll(this.calculatPoDetailTotalCostByUserAndMonthWise(username));
      }
      return resultMap;
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public Map<String, Map<String, Double>> currentMonthTotalPODetailCostBygroupNameWise(String groupName)
      throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "currentMonthTotalPODetailCostBygroupNameWise ");
      LocalDate date = LocalDate.now();
      String month = date.getMonth().name();
      Map<String, Map<String, Double>> resultMap = new HashMap<>();
      List<PurchaseItemDetail> purchaseDetailList = this.findByGroupName(groupName);
      log.info("purchaseDetailList Size {} ", kv("purchaseDetailList Size", purchaseDetailList.size()));
      if (purchaseDetailList != null && !purchaseDetailList.isEmpty()) {
        Map<String, List<PurchaseItemDetail>> groupDataByMonthWise = purchaseDetailList.stream()
            .collect(Collectors.groupingBy(PurchaseItemDetail::getMonth));
        if (groupDataByMonthWise != null && !groupDataByMonthWise.isEmpty()) {
          List<PurchaseItemDetail> currentMonthWise = groupDataByMonthWise.get(month);
          if (currentMonthWise != null && !currentMonthWise.isEmpty()) {
            Map<String, List<PurchaseItemDetail>> userWise = currentMonthWise.parallelStream()
                .collect(Collectors.groupingBy(PurchaseItemDetail::getUserName));
            for (String username : userWise.keySet()) {
              Map<String, Double> result = new HashMap<>();
              log.info(" username------->{}", kv("username", username));
              double sum = 0.0;
              List<PurchaseItemDetail> purchaseDetail = userWise.get(username);
              sum = purchaseDetail.stream().filter(e -> e.getStatus().equalsIgnoreCase(RoomConstant.APPROVED_STAUTS))
                  .map(e -> e.getItemDetail().stream().mapToDouble(p -> p.getPrice().doubleValue()).sum())
                  .collect(Collectors.toList()).stream().mapToDouble(Double::doubleValue).sum();
              log.info(" Sum------->{}", kv("sum", sum));
              result.put(month, sum);
              resultMap.put(username, result);
            }
          } else {
            throw new CustomException("No PO Detail exist in this month [" + month + "]");
          }
        }
      } else {
        throw new CustomException("Invalid Group [" + groupName + "]");
      }
      return resultMap;
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<PurchaseItemDetail> findPoDetailListByGroupWiseAndMonthWise(String groupName, String month)
      throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findPoDetailListByGroupWiseAndMonthWise {} {} {} ",
          kv("GroupName", groupName), kv("Month", month));
      List<PurchaseItemDetail> poDetailList = purchaseItemDetailRepository
          .findPoDetailListByGroupWiseAndMonthWise(groupName, month);
      if (poDetailList != null && !poDetailList.isEmpty()) {
        return poDetailList;
      } else {
        throw new CustomException("Something went wrong .....");
      }
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<PurchaseItemDetail> findPoDetailListByUsernameGroupAndMonthWise(String username, String groupName,
      String month) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findPoDetailListByUsernameGroupAndMonthWise {} {} {} ",
          kv("GroupName", groupName), kv("Month", month), kv("Username", username));
      List<PurchaseItemDetail> poDetailList = purchaseItemDetailRepository
          .findPoDetailListByUsernameGroupAndMonthWise(username, groupName, month);
      if (poDetailList != null && !poDetailList.isEmpty()) {
        return poDetailList;
      } else {
        throw new CustomException("Something went wrong .....");
      }

    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public Map<String, Map<String, Double>> splitMoneyByMonthWiseAndGroupWise(String groupName) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "splitMoneyByMonthWiseAndGroupWise {}  ", kv("GroupName", groupName));
      return null;
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public ResponseEntity<byte[]> exportPoDetailByGroupAndMonthWise(String groupName, String month) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "exportPoDetailByGroupAndMonthWise {} {}", kv("GroupName:", groupName),
          kv("Month", month));
      List<PurchaseItemDetail> poDetailList = this.findPoDetailListByGroupWiseAndMonthWise(groupName, month);
      return generateExcelReport(poDetailList);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public ResponseEntity<byte[]> exportPoDetailByUsernameGroupAndMonthWise(String username, String groupName,
      String month) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "exportPoDetailByUsernameGroupAndMonthWise {} {}",
          kv("GroupName", groupName), kv("Month", month), kv("Username", username));
      List<PurchaseItemDetail> poDetailList = this.findPoDetailListByUsernameGroupAndMonthWise(username, groupName,
          month);
      return generateExcelReport(poDetailList);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  ResponseEntity<byte[]> generateExcelReport(List<PurchaseItemDetail> poDetailList) throws Exception {
    log.info(RoomConstant.INSIDE_THE_METHOD + "generateExcelReport ");
    try {
      SXSSFWorkbook workbook = null;
      Date date = new Date();
      SimpleDateFormat sdf = new SimpleDateFormat(RoomConstant.DATE_TIME);
      String folder = sdf.format(date);
      String downloadPath = RoomConstant.DOWNLOAD_EXCEL_SHEET_PO_DETAIL_PATH + folder + File.separator;
      File createFolder = new File(downloadPath);
      if (!createFolder.exists()) {
        createFolder.mkdirs();
      }
      log.info("Download Path {}", kv("DownloadPath", downloadPath));
      File file = ResourceUtils.getFile("classpath:PO_Details_Sample.xlsx");
      String sampleFilePath = file.getAbsolutePath();
      log.info("sampleFilePath Detail  {}", kv("SampleFilePath", sampleFilePath));
      String downloadedFileName = "PoDetailResult.xlsx";
      FileInputStream fileInputStream = new FileInputStream(sampleFilePath);
      XSSFWorkbook wbTemplate = new XSSFWorkbook(fileInputStream);
      workbook = new SXSSFWorkbook(wbTemplate);
      workbook.setCompressTempFiles(true);
      SXSSFSheet workSheet = workbook.getSheetAt(0);
      workSheet.setRandomAccessWindowSize(1000);
      Integer rowIndex = 1;
      for (PurchaseItemDetail poDetail : poDetailList) {
        rowIndex = populateWorkSheetCellData(workSheet, rowIndex, poDetail);
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
      PurchaseItemDetail purchaseItemDetail) {
    log.info(RoomConstant.INSIDE_THE_METHOD, "populateWorkSheetCellData");
    try {
      Row row = workSheet.getRow(rowIndex);
      if (row == null) {
        row = workSheet.createRow(rowIndex);
      }
      List<String> cellValueList = new ArrayList<>();
      cellValueList.add(purchaseItemDetail.getId().toString());
      cellValueList.add(purchaseItemDetail.getIdentifier());
      cellValueList.add(purchaseItemDetail.getUserName());
      cellValueList.add(purchaseItemDetail.getEmailId());
      cellValueList.add(purchaseItemDetail.getStatus());
      cellValueList.add(purchaseItemDetail.getPurchaseDate().toString());
      cellValueList.add(purchaseItemDetail.getGroupName());
      cellValueList.add(purchaseItemDetail.getMonth());
      cellValueList.add(purchaseItemDetail.getDay());
      cellValueList.add(purchaseItemDetail.getPaymentStatus().toString());
      cellValueList.add(purchaseItemDetail.getModeOfPayment().toString());
      cellValueList.add(purchaseItemDetail.getCreatedDate().toString());
      cellValueList.add(purchaseItemDetail.getCreatedTime().toString());
      RoomUtil.cellRender(cellValueList, row);
      return ++rowIndex;
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }
}
