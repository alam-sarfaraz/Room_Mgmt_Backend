package com.inn.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inn.model.NotificationItemDetail;

@RequestMapping(path = "/notificationItemDetail")
public interface INotificationItemDetailController {

  @PostMapping(path = "/create")
  NotificationItemDetail create(@RequestBody NotificationItemDetail notificationItemDetail) throws Exception;

}
