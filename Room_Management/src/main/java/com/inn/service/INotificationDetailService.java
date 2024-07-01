package com.inn.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.http.ResponseEntity;

import com.inn.model.NotificationDetail;

public interface INotificationDetailService {

  NotificationDetail create(NotificationDetail notificationDetail) throws Exception;

  List<NotificationDetail> findAll() throws Exception;

  List<NotificationDetail> findByPoIdentifier(String poIdentifier) throws Exception;

  NotificationDetail findByUsernameAndNotificationIdentifier(String username, String niIdentifier) throws Exception;

  NotificationDetail findByUsernameAndPoIdentifier(String username, String poIdentifier) throws Exception;

  NotificationDetail findByEmailAndNotificationIdentifier(String email, String niIdentifier) throws Exception;

  NotificationDetail findByEmailAndPoIdentifier(String email, String poIdentifier) throws Exception;

  NotificationDetail updateNotificationDetailStatusByUsernameAndNotificationIdentifier(String username,
      String niIdentifier, String status) throws Exception;

  NotificationDetail updateNotificationDetailStatusUsernameAndPoIdentifier(String username, String poIdentifier,
      String status) throws Exception;

  NotificationDetail updateNotificationDetailStatusByEmailAndNotificationIdentifier(String email, String niIdentifier,
      String status) throws Exception;

  NotificationDetail updateNotificationDetailStatusByEmailAndPoIdentifier(String email, String poIdentifier,
      String status) throws Exception;

  Map<String, List<NotificationDetail>> groupDataByPoIdentifier() throws Exception;

  ResponseEntity<byte[]> exportNotificationDetailByPoIdentifier(String poIdentifier) throws Exception;

  ResponseEntity<byte[]> exportSheetByPoIdentifierListForUpdateNotificationDetailStatus(List<String> poIdentifierList)
      throws Exception;

  String uploadSheetToUpdateNotificationDetailStatus(InputStream inputStream, String originalFilename) throws Exception;

  String processSheetToUpdateNotificationDetailStatus() throws Exception;

  Set<String> findUniqueAllNoitifId(String groupName)throws Exception;

  Set<String> findUniqueAllEmailId(String groupName)throws Exception;

  Set<String> findUniqueAllusername(String groupName)throws Exception;

  Set<String> findUniqueAllPoId(String groupName)throws Exception;

}
