package com.inn.service.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inn.constant.RoomConstant;
import com.inn.customException.CustomException;
import com.inn.model.UserRegistration;
import com.inn.repository.IUserRegistrationRepository;
import com.inn.service.ISigInService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SigInServiceImpl implements ISigInService {

  @Autowired
  IUserRegistrationRepository registrationRepository;

  @Override
  public Boolean signin(String username, String password) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "signin {} {}", kv("username", username), kv("password", password));
      UserRegistration userRegistration = registrationRepository.findByUsername(username);
      if (userRegistration == null) {
        throw new CustomException("Invalid username");
      } else {
        if (!userRegistration.getPassword().equals(password)) {
          throw new CustomException("Invalid Password");
        }
      }
      return Boolean.TRUE;
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

}
