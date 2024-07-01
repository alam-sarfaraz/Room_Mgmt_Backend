package com.inn.controller.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.inn.constant.RoomConstant;
import com.inn.controller.IAttachmentController;
import com.inn.model.Attachment;
import com.inn.service.IAttachmentService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class AttachmentControllerImpl implements IAttachmentController {

  @Autowired
  IAttachmentService userRegisterationAttachmentService;

  @Override
  public Attachment create(Attachment userRegisterationAttachment) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "create {}",
          kv("UserRegisterationAttachment", userRegisterationAttachment));
      return userRegisterationAttachmentService.create(userRegisterationAttachment);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<Attachment> findByUsername(String username) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByUsername {}", kv("username", username));
      return userRegisterationAttachmentService.findByUsername(username);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public Attachment findByIdentifier(String identifier) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByIdentifier {}", kv("identifier", identifier));
      return userRegisterationAttachmentService.findByIdentifier(identifier);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<Attachment> findByUserIdentifier(String userIdentifier) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByUserIdentifier {}", kv("UserIdentifier", userIdentifier));
      return userRegisterationAttachmentService.findByUserIdentifier(userIdentifier);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public String deleteByIdentifier(String identifier) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "deleteByIdentifier {}", kv("identifier", identifier));
      return userRegisterationAttachmentService.deleteByIdentifier(identifier);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

}
