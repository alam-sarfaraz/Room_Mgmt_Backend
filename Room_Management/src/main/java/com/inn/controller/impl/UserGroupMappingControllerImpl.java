package com.inn.controller.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.inn.constant.RoomConstant;
import com.inn.controller.IUserGroupMappingController;
import com.inn.model.UserGroupMapping;
import com.inn.service.IUserGroupMappingService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserGroupMappingControllerImpl implements IUserGroupMappingController {

  @Autowired
  IUserGroupMappingService userGroupMappingService;

  @Override
  public List<UserGroupMapping> findByGroupName(String groupName) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByGroupName {}", kv("GroupName", groupName));
      return userGroupMappingService.findByGroupName(groupName);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<UserGroupMapping> findByEmail(String email) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByEmail {}", kv("Email", email));
      return userGroupMappingService.findByEmail(email);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public String sendEmailByGroupWise(String groupName) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "sendEmailByGroupWise {}", kv("GroupName", groupName));
      return userGroupMappingService.sendEmailByGroupWise(groupName);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public ResponseEntity<byte[]> exportDataByGroupName(String groupName) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "exportDataByGroupName {}", kv("groupName", groupName));
      return userGroupMappingService.exportDataByGroupName(groupName);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<String> findByUsernameByGroupName(String groupName) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByUsernameByGroupName {}", kv("groupName", groupName));
      return userGroupMappingService.findByUsernameByGroupName(groupName);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

}
