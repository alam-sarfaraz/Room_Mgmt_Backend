package com.inn.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.inn.model.PurchaseItemDetail;

public interface IPurchaseItemDetailService {

  PurchaseItemDetail create(PurchaseItemDetail purchaseItemDetail) throws Exception;

  List<PurchaseItemDetail> findAll() throws Exception;

  List<PurchaseItemDetail> findByUsername(String username) throws Exception;

  List<PurchaseItemDetail> findByEmail(String email) throws Exception;

  PurchaseItemDetail findByIdentifier(String identifier) throws Exception;

  List<PurchaseItemDetail> findByUserIdentifier(String userIdentifier) throws Exception;

  List<PurchaseItemDetail> findByGroupName(String groupName) throws Exception;

  List<PurchaseItemDetail> findByPurchaseDate(LocalDate purchaseDate) throws Exception;

  List<PurchaseItemDetail> findByStatus(String status) throws Exception;

  List<PurchaseItemDetail> findByUsernameAndStatus(String username, String status) throws Exception;

  List<PurchaseItemDetail> findByEmailAndStatus(String email, String status) throws Exception;

  List<PurchaseItemDetail> findByUserIdentifierAndStatus(String userIdentifier, String status) throws Exception;

  List<PurchaseItemDetail> findByUsernameAndpurchaseDate(String username, LocalDate purchaseDate) throws Exception;

  List<PurchaseItemDetail> findByEmailAndpurchaseDate(String email, LocalDate purchaseDate) throws Exception;

  List<PurchaseItemDetail> findByUserIdentifierAndPurchaseDate(String userIdentifier, LocalDate purchaseDate)
      throws Exception;

  String exportPdfReportByIdentifier(String identifier) throws Exception;

  ResponseEntity<byte[]> downloadPdfReportByIdentifier(String identifier) throws Exception;

  String updatePurchaseItemDetailStatus() throws Exception;

  Map<String, Map<String, Double>> calculatPoDetailTotalCostByUserAndMonthWise(String username) throws Exception;

  Map<String, Map<String, Double>> calculatPoDetailTotalCostAllUserAndMonthWise() throws Exception;

  Map<String, Map<String, Double>> currentMonthTotalPODetailCostBygroupNameWise(String groupName) throws Exception;

  List<PurchaseItemDetail> findPoDetailListByGroupWiseAndMonthWise(String groupName, String month) throws Exception;

  List<PurchaseItemDetail> findPoDetailListByUsernameGroupAndMonthWise(String username, String groupName, String month)
      throws Exception;

  Map<String, Map<String, Double>> splitMoneyByMonthWiseAndGroupWise(String groupName) throws Exception;

  ResponseEntity<byte[]> exportPoDetailByGroupAndMonthWise(String groupName, String month) throws Exception;

  ResponseEntity<byte[]> exportPoDetailByUsernameGroupAndMonthWise(String username, String groupName, String month)
      throws Exception;

}
