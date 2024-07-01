package com.inn.controller.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.inn.constant.RoomConstant;
import com.inn.controller.IPurchaseItemDetailController;
import com.inn.model.PurchaseItemDetail;
import com.inn.service.IPurchaseItemDetailService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class PurchaseItemDetailControllerImpl implements IPurchaseItemDetailController {

  @Autowired
  IPurchaseItemDetailService purchaseItemDetailService;

  @Override
  public PurchaseItemDetail create(PurchaseItemDetail purchaseItemDetail) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "create {}", kv("PurchaseItemDetail", purchaseItemDetail));
      return purchaseItemDetailService.create(purchaseItemDetail);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<PurchaseItemDetail> findAll() throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findAll ");
      return purchaseItemDetailService.findAll();
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<PurchaseItemDetail> findByUsername(String username) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByUsername {}", kv("Username::", username));
      return purchaseItemDetailService.findByUsername(username);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<PurchaseItemDetail> findByEmail(String email) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByEmail {}", kv("Email", email));
      return purchaseItemDetailService.findByEmail(email);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public PurchaseItemDetail findByIdentifier(String identifier) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByIdentifier {}", kv("Identifier", identifier));
      return purchaseItemDetailService.findByIdentifier(identifier);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<PurchaseItemDetail> findByUserIdentifier(String userIdentifier) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByUserIdentifier {}", kv("UserIdentifier", userIdentifier));
      return purchaseItemDetailService.findByUserIdentifier(userIdentifier);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<PurchaseItemDetail> findByGroupName(String groupName) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByGroupName {}", kv("GroupName::", groupName));
      return purchaseItemDetailService.findByGroupName(groupName);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<PurchaseItemDetail> findByPurchaseDate(LocalDate purchaseDate) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByPurchaseDate {}", kv("PurchaseDate", purchaseDate));
      return purchaseItemDetailService.findByPurchaseDate(purchaseDate);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<PurchaseItemDetail> findByStatus(String status) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByStatus {}", kv("Status::", status));
      return purchaseItemDetailService.findByStatus(status);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<PurchaseItemDetail> findByUsernameAndStatus(String username, String status) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByUsernameAndStatus {} {}", kv("Username", username),
          kv("Status", status));
      return purchaseItemDetailService.findByUsernameAndStatus(username, status);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<PurchaseItemDetail> findByEmailAndStatus(String email, String status) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByEmailAndStatus {} {}", kv("Email", email), kv("Status", status));
      return purchaseItemDetailService.findByEmailAndStatus(email, status);
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
      return purchaseItemDetailService.findByUserIdentifierAndStatus(userIdentifier, status);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<PurchaseItemDetail> findByUsernameAndpurchaseDate(String username, LocalDate purchaseDate)
      throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByUsernameAndpurchaseDate {} {}", kv("username", username),
          kv("PurchaseDate", purchaseDate));
      return purchaseItemDetailService.findByUsernameAndpurchaseDate(username, purchaseDate);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<PurchaseItemDetail> findByEmailAndpurchaseDate(String email, LocalDate purchaseDate) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByEmailAndpurchaseDate {} {}", kv("email", email),
          kv("purchaseDate", purchaseDate));
      return purchaseItemDetailService.findByEmailAndpurchaseDate(email, purchaseDate);
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
      return purchaseItemDetailService.findByUserIdentifierAndPurchaseDate(userIdentifier, purchaseDate);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public String exportPdfReportByIdentifier(String identifier) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "exportPdfReportByIdentifier {} ", kv("identifier", identifier));
      return purchaseItemDetailService.exportPdfReportByIdentifier(identifier);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public ResponseEntity<byte[]> downloadPdfReportByIdentifier(String identifier) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "downloadPdfReportByIdentifier {} ", kv("identifier", identifier));
      return purchaseItemDetailService.downloadPdfReportByIdentifier(identifier);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public String updatePurchaseItemDetailStatus() throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "updatePurchaseItemDetailStatus :");
      return purchaseItemDetailService.updatePurchaseItemDetailStatus();
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public Map<String, Map<String, Double>> calculatPoDetailTotalCostByUserAndMonthWise(String username)
      throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "calculatPoDetailTotalCostByUserAndMonthWise {}",
          kv("username", username));
      return purchaseItemDetailService.calculatPoDetailTotalCostByUserAndMonthWise(username);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public Map<String, Map<String, Double>> calculatPoDetailTotalCostAllUserAndMonthWise() throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "calculatPoDetailTotalCostAllUserAndMonthWise ");
      return purchaseItemDetailService.calculatPoDetailTotalCostAllUserAndMonthWise();
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
      return purchaseItemDetailService.currentMonthTotalPODetailCostBygroupNameWise(groupName);
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
          kv("GroupName:", groupName), kv("Month", month));
      return purchaseItemDetailService.findPoDetailListByGroupWiseAndMonthWise(groupName, month.toUpperCase());
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<PurchaseItemDetail> findPoDetailListByUsernameGroupAndMonthWise(String username, String groupName,
      String month) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findPoDetailListByUsernameGroupAndMonthWise {} {}",
          kv("GroupName", groupName), kv("Month", month), kv("Username", username));
      return purchaseItemDetailService.findPoDetailListByUsernameGroupAndMonthWise(username, groupName,
          month.toUpperCase());
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public Map<String, Map<String, Double>> splitMoneyByMonthWiseAndGroupWise(String groupName) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "splitMoneyByMonthWiseAndGroupWise {}  ", kv("GroupName", groupName));
      return purchaseItemDetailService.splitMoneyByMonthWiseAndGroupWise(groupName);
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
      return purchaseItemDetailService.exportPoDetailByGroupAndMonthWise(groupName, month.toUpperCase());
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
      return purchaseItemDetailService.exportPoDetailByUsernameGroupAndMonthWise(username, groupName,
          month.toUpperCase());
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

}
