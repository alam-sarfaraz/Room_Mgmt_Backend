package com.inn.controller.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.inn.constant.RoomConstant;
import com.inn.controller.INotificationDetailController;
import com.inn.customException.CustomException;
import com.inn.model.NotificationDetail;
import com.inn.service.INotificationDetailService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class NotificationDetailControllerImpl implements INotificationDetailController {

  @Autowired
  INotificationDetailService notificationDetailService;

  @Override
  public NotificationDetail create(NotificationDetail notificationDetail) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "create {}", kv("NotificationDetail", notificationDetail));
      return notificationDetailService.create(notificationDetail);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<NotificationDetail> findAll() throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findAll ");
      return notificationDetailService.findAll();
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<NotificationDetail> findByPoIdentifier(String poIdentifier) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByPoIdentifier {}", kv("PoIdentifier :", poIdentifier));
      return notificationDetailService.findByPoIdentifier(poIdentifier);
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
      return notificationDetailService.findByUsernameAndNotificationIdentifier(username, niIdentifier);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public NotificationDetail findByUsernameAndPoIdentifier(String username, String poIdentifier) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByUsernameAndNotificationIdentifier {} {}",
          kv("Username", username), kv("PoIdentifier :", poIdentifier));
      return notificationDetailService.findByUsernameAndPoIdentifier(username, poIdentifier);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public NotificationDetail findByEmailAndNotificationIdentifier(String email, String niIdentifier) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByEmailAndNotificationIdentifier {} {}", kv("Email", email),
          kv("NiIdentifier", niIdentifier));
      return notificationDetailService.findByEmailAndNotificationIdentifier(email, niIdentifier);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public NotificationDetail findByEmailAndPoIdentifier(String email, String poIdentifier) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByEmailAndPoIdentifier {} {}", kv("email :", email),
          kv("PoIdentifier", poIdentifier));
      return notificationDetailService.findByEmailAndPoIdentifier(email, poIdentifier);
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
      return notificationDetailService.updateNotificationDetailStatusByUsernameAndNotificationIdentifier(username,
          niIdentifier, status);
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
      return notificationDetailService.updateNotificationDetailStatusUsernameAndPoIdentifier(username, poIdentifier,
          status);
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
      return notificationDetailService.updateNotificationDetailStatusByEmailAndNotificationIdentifier(email,
          niIdentifier, status);
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
      return notificationDetailService.updateNotificationDetailStatusByEmailAndPoIdentifier(email, poIdentifier,
          status);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public Map<String, List<NotificationDetail>> groupDataByPoIdentifier() throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "groupDataByPoIdentifier :");
      return notificationDetailService.groupDataByPoIdentifier();
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
      return notificationDetailService.exportNotificationDetailByPoIdentifier(poIdentifier);
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
      return notificationDetailService.exportSheetByPoIdentifierListForUpdateNotificationDetailStatus(poIdentifierList);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public String uploadSheetToUpdateNotificationDetailStatus(MultipartFile multipart) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "uploadSheetToUpdateNotificationDetailStatus");
      String ext = StringUtils.substringAfterLast(multipart.getOriginalFilename(), ".");
      if (!ext.equalsIgnoreCase("xlsx")) {
        throw new CustomException("Please upload only xlsx file ....");
      }
      return notificationDetailService.uploadSheetToUpdateNotificationDetailStatus(multipart.getInputStream(),
          multipart.getOriginalFilename());
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public String processSheetToUpdateNotificationDetailStatus() throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "processSheetToUpdateNotificationDetailStatus ");
      return notificationDetailService.processSheetToUpdateNotificationDetailStatus();
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public   Set<String> findUniqueAllNoitifId(String groupName) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findUniqueAllNoitifId ");
      return notificationDetailService.findUniqueAllNoitifId(groupName);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public Set<String> findUniqueAllEmailId(String groupName) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findUniqueAllEmailId ");
      return notificationDetailService.findUniqueAllEmailId(groupName);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public Set<String> findUniqueAllusername(String groupName) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findUniqueAllEmailId ");
      return notificationDetailService.findUniqueAllusername(groupName);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public Set<String> findUniqueAllPoId(String groupName) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findUniqueAllEmailId ");
      return notificationDetailService.findUniqueAllPoId(groupName);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

}
