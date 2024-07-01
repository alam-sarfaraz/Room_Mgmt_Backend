package com.inn.service.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inn.constant.RoomConstant;
import com.inn.customException.CustomException;
import com.inn.model.Attachment;
import com.inn.repository.IAttachmentRepository;
import com.inn.service.IAttachmentService;
import com.inn.utils.RoomUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AttachmentServiceImpl implements IAttachmentService {

  @Autowired
  IAttachmentRepository userRegisterationAttachmentRepository;

  @Override
  public Attachment create(Attachment userRegisterationAttachment) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "create {}",
          kv("UserRegisterationAttachment", userRegisterationAttachment));
      userRegisterationAttachment.setIdentifier(RoomUtil.generateIdentifier("ATT"));
      userRegisterationAttachment.setCreatedDate(RoomUtil.dateFormatter());
      userRegisterationAttachment.setCreatedTime(RoomUtil.timeFormatter());
      return userRegisterationAttachmentRepository.save(userRegisterationAttachment);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<Attachment> findByUsername(String username) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByUsername {}", kv("username", username));
      List<Attachment> userRegisterationAttachment = userRegisterationAttachmentRepository.findByUsername(username);
      if (userRegisterationAttachment != null) {
        return userRegisterationAttachment;
      } else {
        throw new CustomException("Invalid Username ........");
      }
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public Attachment findByIdentifier(String identifier) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByIdentifier {}", kv("identifier", identifier));
      Attachment userRegisterationAttachment = userRegisterationAttachmentRepository.findByIdentifier(identifier);
      if (userRegisterationAttachment != null) {
        return userRegisterationAttachment;
      } else {
        throw new CustomException("Invalid Identifier ........");
      }
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<Attachment> findByUserIdentifier(String userIdentifier) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByUserIdentifier {}", kv("userIdentifier", userIdentifier));
      List<Attachment> userRegisterationAttachment = userRegisterationAttachmentRepository
          .findByUserIdentifier(userIdentifier);
      if (userRegisterationAttachment != null) {
        return userRegisterationAttachment;
      } else {
        throw new CustomException("Invalid User Identifier .......");
      }
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public String deleteByIdentifier(String identifier) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "deleteByIdentifier {}", kv("identifier", identifier));
      String response = "Invalid Identifier ....";
      Attachment userRegisterationAttachment = this.findByIdentifier(identifier);
      log.info("User Registeration Attachment Detail {}",
          kv("UserRegisterationAttachment", userRegisterationAttachment));
      if (userRegisterationAttachment != null) {
        Integer id = userRegisterationAttachment.getId();
        userRegisterationAttachmentRepository.deleteById(id);
        response = "Attachment Delete successfully ...";
      }
      return response;
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

}
