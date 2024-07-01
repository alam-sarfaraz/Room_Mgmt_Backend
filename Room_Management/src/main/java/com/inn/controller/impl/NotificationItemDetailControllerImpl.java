package com.inn.controller.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.inn.constant.RoomConstant;
import com.inn.controller.INotificationItemDetailController;
import com.inn.model.NotificationItemDetail;
import com.inn.service.INotificationItemDetailService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class NotificationItemDetailControllerImpl implements INotificationItemDetailController {

  @Autowired
  INotificationItemDetailService notificationItemDetailService;

  @Override
  public NotificationItemDetail create(NotificationItemDetail notificationItemDetail) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "create {}", kv("NotificationItemDetail", notificationItemDetail));
      return notificationItemDetailService.create(notificationItemDetail);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

}
