package com.inn.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.inn.model.NotificationDetail;

@RequestMapping(path = "/notificationDetail")
@CrossOrigin
public interface INotificationDetailController {

  @PostMapping(path = "/create")
  NotificationDetail create(@RequestBody NotificationDetail notificationDetail) throws Exception;

  @GetMapping(path = "/findAll")
  List<NotificationDetail> findAll() throws Exception;

  @GetMapping(path = "/findByPoIdentifier")
  List<NotificationDetail> findByPoIdentifier(@RequestParam(name = "poIdentifier", required = true) String poIdentifier)
      throws Exception;

  @GetMapping(path = "/findByUsernameAndNotificationIdentifier")
  NotificationDetail findByUsernameAndNotificationIdentifier(
      @RequestParam(name = "username", required = true) String username,
      @RequestParam(name = "niIdentifier", required = true) String niIdentifier) throws Exception;

  @GetMapping(path = "/findByUsernameAndPoIdentifier")
  NotificationDetail findByUsernameAndPoIdentifier(@RequestParam(name = "username", required = true) String username,
      @RequestParam(name = "poIdentifier", required = true) String poIdentifier) throws Exception;

  @GetMapping(path = "/findByEmailAndNotificationIdentifier")
  NotificationDetail findByEmailAndNotificationIdentifier(@RequestParam(name = "email", required = true) String email,
      @RequestParam(name = "niIdentifier", required = true) String niIdentifier) throws Exception;

  @GetMapping(path = "/findByEmailAndPoIdentifier")
  NotificationDetail findByEmailAndPoIdentifier(@RequestParam(name = "email", required = true) String email,
      @RequestParam(name = "poIdentifier", required = true) String poIdentifier) throws Exception;

  @GetMapping(path = "/updateNotificationDetailStatusByUsernameAndNotificationIdentifier")
  NotificationDetail updateNotificationDetailStatusByUsernameAndNotificationIdentifier(
      @RequestParam(name = "username", required = true) String username,
      @RequestParam(name = "niIdentifier", required = true) String niIdentifier,
      @RequestParam(name = "status", required = true) String status) throws Exception;

  @GetMapping(path = "/updateNotificationDetailStatusByUsernameAndPoIdentifier")
  NotificationDetail updateNotificationDetailStatusUsernameAndPoIdentifier(
      @RequestParam(name = "username", required = true) String username,
      @RequestParam(name = "poIdentifier", required = true) String poIdentifier,
      @RequestParam(name = "status", required = true) String status) throws Exception;

  @GetMapping(path = "/updateNotificationDetailStatusByEmailAndNotificationIdentifier")
  NotificationDetail updateNotificationDetailStatusByEmailAndNotificationIdentifier(
      @RequestParam(name = "email", required = true) String email,
      @RequestParam(name = "niIdentifier", required = true) String niIdentifier,
      @RequestParam(name = "status", required = true) String status) throws Exception;

  @GetMapping(path = "/updateNotificationDetailStatusByEmailAndPoIdentifier")
  NotificationDetail updateNotificationDetailStatusByEmailAndPoIdentifier(
      @RequestParam(name = "email", required = true) String email,
      @RequestParam(name = "poIdentifier", required = true) String poIdentifier,
      @RequestParam(name = "status", required = true) String status) throws Exception;

  @GetMapping(path = "/groupDataByPoIdentifier")
  Map<String, List<NotificationDetail>> groupDataByPoIdentifier() throws Exception;

  @GetMapping(path = "/exportNotificationDetailByPoIdentifier")
  ResponseEntity<byte[]> exportNotificationDetailByPoIdentifier(
      @RequestParam(name = "poIdentifier", required = true) String poIdentifier) throws Exception;

  @PostMapping(path = "/exportSheetByPoIdentifierListForUpdateNotificationDetailStatus")
  ResponseEntity<byte[]> exportSheetByPoIdentifierListForUpdateNotificationDetailStatus(
      @RequestBody List<String> poIdentifierList) throws Exception;

  @PostMapping(path = "/uploadSheetToUpdateNotificationDetailStatus", consumes = "multipart/form-data", produces = MediaType.APPLICATION_JSON_VALUE)
  String uploadSheetToUpdateNotificationDetailStatus(@RequestPart(name = "file") MultipartFile multipart)
      throws Exception;

  @GetMapping(path = "processSheetToUpdateNotificationDetailStatus")
  String processSheetToUpdateNotificationDetailStatus() throws Exception;

  @GetMapping(path = "/findUniqueAllNoitifId")
  Set<String> findUniqueAllNoitifId(@RequestParam(name = "groupName", required = true) String groupName) throws Exception;

  @GetMapping(path = "/findUniqueAllEmailId")
  Set<String> findUniqueAllEmailId(@RequestParam(name = "groupName", required = true) String groupName) throws Exception;

  @GetMapping(path = "/findUniqueAllusername")
  Set<String> findUniqueAllusername(@RequestParam(name = "groupName", required = true) String groupName) throws Exception;

  @GetMapping(path = "/findUniqueAllPoId")
  Set<String> findUniqueAllPoId(@RequestParam(name = "groupName", required = true) String groupName) throws Exception;




}
