package com.inn.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.inn.model.PurchaseItemDetail;

@CrossOrigin
@RequestMapping(path = "purchaseItemDetail")
public interface IPurchaseItemDetailController {

  @PostMapping(path = "/create")
  PurchaseItemDetail create(@RequestBody PurchaseItemDetail purchaseItemDetail) throws Exception;

  @GetMapping(path = "/findAll")
  List<PurchaseItemDetail> findAll() throws Exception;

  @GetMapping(path = "/findByUsername")
  List<PurchaseItemDetail> findByUsername(@RequestParam(name = "username", required = true) String username)
      throws Exception;

  @GetMapping(path = "/findByEmail")
  List<PurchaseItemDetail> findByEmail(@RequestParam(name = "email", required = true) String email) throws Exception;

  @GetMapping(path = "/findByIdentifier")
  PurchaseItemDetail findByIdentifier(@RequestParam(name = "identifier", required = true) String identifier)
      throws Exception;

  @GetMapping(path = "/findByUserIdentifier")
  List<PurchaseItemDetail> findByUserIdentifier(
      @RequestParam(name = "userIdentifier", required = true) String userIdentifier) throws Exception;

  @GetMapping(path = "/findByGroupName")
  List<PurchaseItemDetail> findByGroupName(@RequestParam(name = "groupName", required = true) String groupName)
      throws Exception;

  @GetMapping(path = "/findByPurchaseDate")
  List<PurchaseItemDetail> findByPurchaseDate(
      @RequestParam(name = "purchaseDate", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate purchaseDate)
      throws Exception;

  @GetMapping(path = "/findByStatus")
  List<PurchaseItemDetail> findByStatus(@RequestParam(name = "status", required = true) String status) throws Exception;

  @GetMapping(path = "/findByUsernameAndStatus")
  List<PurchaseItemDetail> findByUsernameAndStatus(@RequestParam(name = "username", required = true) String username,
      @RequestParam(name = "status", required = true) String status) throws Exception;

  @GetMapping(path = "/findByEmailAndStatus")
  List<PurchaseItemDetail> findByEmailAndStatus(@RequestParam(name = "email", required = true) String email,
      @RequestParam(name = "status", required = true) String status) throws Exception;

  @GetMapping(path = "/findByUserIdentifierAndStatus")
  List<PurchaseItemDetail> findByUserIdentifierAndStatus(
      @RequestParam(name = "userIdentifier", required = true) String userIdentifier,
      @RequestParam(name = "status", required = true) String status) throws Exception;

  @GetMapping(path = "/findByUsernameAndPurchaseDate")
  List<PurchaseItemDetail> findByUsernameAndpurchaseDate(
      @RequestParam(name = "username", required = true) String username,
      @RequestParam(name = "purchaseDate", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate purchaseDate)
      throws Exception;

  @GetMapping(path = "/findByEmailAndPurchaseDate")
  List<PurchaseItemDetail> findByEmailAndpurchaseDate(@RequestParam(name = "email", required = true) String email,
      @RequestParam(name = "purchaseDate", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate purchaseDate)
      throws Exception;

  @GetMapping(path = "/findByUserIdentifierAndPurchaseDate")
  List<PurchaseItemDetail> findByUserIdentifierAndPurchaseDate(
      @RequestParam(name = "userIdentifier", required = true) String userIdentifier,
      @RequestParam(name = "purchaseDate", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate purchaseDate)
      throws Exception;

  @GetMapping(path = "/exportPdfReportByIdentifier")
  String exportPdfReportByIdentifier(@RequestParam(name = "identifier", required = true) String identifier)
      throws Exception;

  @GetMapping(path = "/downloadPdfReportByIdentifier")
  ResponseEntity<byte[]> downloadPdfReportByIdentifier(
      @RequestParam(name = "identifier", required = true) String identifier) throws Exception;

  @GetMapping(path = "/updatePurchaseItemDetailStatus")
  String updatePurchaseItemDetailStatus() throws Exception;

  @GetMapping(path = "/calculatPoDetailTotalCostByUserAndMonthWise")
  Map<String, Map<String, Double>> calculatPoDetailTotalCostByUserAndMonthWise(
      @RequestParam(name = "username", required = true) String username) throws Exception;

  @GetMapping(path = "/calculatPoDetailTotalCostAllUserAndMonthWise")
  Map<String, Map<String, Double>> calculatPoDetailTotalCostAllUserAndMonthWise() throws Exception;

  @GetMapping(path = "/currentMonthTotalPODetailCostBygroupNameWise")
  Map<String, Map<String, Double>> currentMonthTotalPODetailCostBygroupNameWise(
      @RequestParam(name = "groupName", required = true) String groupName) throws Exception;

  @GetMapping(path = "/findPoDetailListByGroupWiseAndMonthWise")
  List<PurchaseItemDetail> findPoDetailListByGroupWiseAndMonthWise(
      @RequestParam(name = "groupName", required = true) String groupName,
      @RequestParam(name = "month", required = true) String month) throws Exception;

  @GetMapping(path = "/findPoDetailListByUsernameGroupAndMonthWise")
  List<PurchaseItemDetail> findPoDetailListByUsernameGroupAndMonthWise(
      @RequestParam(name = "username", required = true) String username,
      @RequestParam(name = "groupName", required = true) String groupName,
      @RequestParam(name = "month", required = true) String month) throws Exception;

  @GetMapping(path = "/splitMoneyByMonthWiseAndGroupWise")
  Map<String, Map<String, Double>> splitMoneyByMonthWiseAndGroupWise(
      @RequestParam(name = "groupName", required = true) String groupName) throws Exception;

  @GetMapping(path = "/exportPoDetailByGroupAndMonthWise")
  ResponseEntity<byte[]> exportPoDetailByGroupAndMonthWise(
      @RequestParam(name = "groupName", required = true) String groupName,
      @RequestParam(name = "month", required = true) String month) throws Exception;

  @GetMapping(path = "/exportPoDetailByUsernameGroupAndMonthWise")
  ResponseEntity<byte[]> exportPoDetailByUsernameGroupAndMonthWise(
      @RequestParam(name = "username", required = true) String username,
      @RequestParam(name = "groupName", required = true) String groupName,
      @RequestParam(name = "month", required = true) String month) throws Exception;

}
