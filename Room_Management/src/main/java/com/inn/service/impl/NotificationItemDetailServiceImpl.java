package com.inn.service.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inn.constant.RoomConstant;
import com.inn.model.NotificationItemDetail;
import com.inn.repository.INotificationItemDetailRepository;
import com.inn.service.INotificationItemDetailService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationItemDetailServiceImpl implements INotificationItemDetailService {

  @Autowired
  INotificationItemDetailRepository notificationItemDetailRepository;

  @Override
  public NotificationItemDetail create(NotificationItemDetail notificationItemDetail) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "create {}", kv("NotificationItemDetail", notificationItemDetail));
      return notificationItemDetailRepository.save(notificationItemDetail);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

}
