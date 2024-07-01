package com.inn.service;

import java.util.List;

import com.inn.customException.CustomException;
import com.inn.model.UserRegistration;

public interface IUserRegistrationService {

  UserRegistration create(UserRegistration userRegistration) throws Exception;

  UserRegistration findByUsername(String username) throws CustomException;

  UserRegistration findByEmail(String email) throws Exception;

  UserRegistration findByIdentifier(String identifier) throws Exception;

  String deleteByUsername(String username) throws Exception;

  String generateUserRegistrationReport(String username) throws Exception;

//  ResponseEntity<byte[]> downloadUserRegistrationReportByUsername(String username) throws Exception;

  UserRegistration update(String username, UserRegistration userRegistration) throws Exception;

  List<UserRegistration> findAll()throws Exception;

}
