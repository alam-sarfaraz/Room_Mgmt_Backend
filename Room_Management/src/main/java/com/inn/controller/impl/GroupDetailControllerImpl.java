package com.inn.controller.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.inn.constant.RoomConstant;
import com.inn.controller.IGroupDetailController;
import com.inn.customException.CustomException;
import com.inn.model.GroupDetail;
import com.inn.service.IGroupDetailService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class GroupDetailControllerImpl implements IGroupDetailController {

  @Autowired
  IGroupDetailService groupDetailService;

  @Override
  public GroupDetail create(GroupDetail groupDetail) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "create {}", kv("GroupDetail", groupDetail));
      if (groupDetail.getGroupName() == null) {
        throw new CustomException("Group name can't be empty ...");
      }
      return groupDetailService.create(groupDetail);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public GroupDetail findByIdentifier(String identifier) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByIdentifier {}", kv("Identifier", identifier));
      return groupDetailService.findByIdentifier(identifier);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public GroupDetail findByGroupName(String groupName) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByGroupName {}", kv("GroupName", groupName));
      return groupDetailService.findByGroupName(groupName);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<GroupDetail> findAll() {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findAll");
      return groupDetailService.findAll();
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

}
