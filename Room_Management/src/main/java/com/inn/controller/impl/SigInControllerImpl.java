package com.inn.controller.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.inn.constant.RoomConstant;
import com.inn.controller.ISigInController;
import com.inn.service.ISigInService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class SigInControllerImpl implements ISigInController {

  @Autowired
  ISigInService sigInService;

  @Override
  public Boolean signin(String username, String password) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "signin {} {}", kv("username", username), kv("password", password));
      return sigInService.signin(username, password);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

}
