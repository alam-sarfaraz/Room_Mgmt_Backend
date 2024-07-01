package com.inn.controller.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.inn.constant.RoomConstant;
import com.inn.controller.IEmialDetailController;
import com.inn.model.EmailDetails;
import com.inn.service.IEmailDetailService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class EmailDetailControllerImpl implements IEmialDetailController {

  @Autowired
  private IEmailDetailService emailDetailService;

  public String sendMail(EmailDetails details) {
    log.info(RoomConstant.INSIDE_THE_METHOD + "sendMail EmailDetailControllerImpl {}", kv("Details", details));
    try {
      return emailDetailService.sendMailWithOutAttachment(details);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  public String sendMailWithAttachment(EmailDetails details) throws Exception {
    log.info(RoomConstant.INSIDE_THE_METHOD + "sendMailWithAttachment EmailDetailControllerImpl {}",
        kv("Details", details));
    try {
      return emailDetailService.sendMailWithAttachment(details);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

}
