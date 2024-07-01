package com.inn.controller.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.inn.constant.RoomConstant;
import com.inn.controller.IUserRegistrationController;
import com.inn.customException.CustomException;
import com.inn.model.UserRegistration;
import com.inn.service.IUserRegistrationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserRegistrationControllerImpl implements IUserRegistrationController {

  @Autowired
  IUserRegistrationService userRegistrationService;

  @Override
  public UserRegistration create(UserRegistration userRegistration) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "create {}", kv("UserRegistration", userRegistration));
      return userRegistrationService.create(userRegistration);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public UserRegistration findByUsername(String username) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByUsername {}", kv("username", username));
      return userRegistrationService.findByUsername(username);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public UserRegistration findByEmail(String email) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByEmail {}", kv("Email", email));
      return userRegistrationService.findByEmail(email);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public UserRegistration findByIdentifier(String identifier) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByIdentifier {}", kv("Identifier", identifier));
      return userRegistrationService.findByIdentifier(identifier);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public String deleteByUsername(String username) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "deleteByUsername {}", kv("username", username));
      return userRegistrationService.deleteByUsername(username);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public String generateUserRegistrationReport(String username) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "generateUserRegistrationReport {}", kv("username", username));
      return userRegistrationService.generateUserRegistrationReport(username);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

//  @Override
//  public ResponseEntity<byte[]> downloadUserRegistrationReportByUsername(String username) throws Exception {
//    try {
//      log.info(RoomConstant.INSIDE_THE_METHOD + "downloadUserRegistrationReportByUsername {}",
//          kv("username", username));
//      return userRegistrationService.downloadUserRegistrationReportByUsername(username);
//    } catch (Exception e) {
//      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
//      throw e;
//    }
//  }

  @Override
  public UserRegistration update(String username, UserRegistration userRegistration) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "update {} {} ", kv("Username", username),
          kv("UserRegistration", userRegistration));
      if (userRegistration.getUserName() != null) {
        throw new CustomException("Username can't be update ....");
      }
      return userRegistrationService.update(username, userRegistration);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<UserRegistration> findAll() throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findAll {}");
      return userRegistrationService.findAll();
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

}
