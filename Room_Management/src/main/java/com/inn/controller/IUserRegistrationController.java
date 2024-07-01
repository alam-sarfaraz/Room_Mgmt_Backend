package com.inn.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.inn.model.UserRegistration;

@CrossOrigin
@RequestMapping("/user")
public interface IUserRegistrationController {

  @PostMapping(path = "/create")
  UserRegistration create(@RequestBody UserRegistration userRegistration) throws Exception;

  @GetMapping(path = "/findByUsername")
  UserRegistration findByUsername(@RequestParam(name = "username", required = true) String username) throws Exception;

  @GetMapping(path = "/findByEmail")
  UserRegistration findByEmail(@RequestParam(name = "email", required = true) String email) throws Exception;

  @GetMapping(path = "/findByIdentifier")
  UserRegistration findByIdentifier(@RequestParam(name = "identifier", required = true) String identifier)
      throws Exception;

  @DeleteMapping(path = "/deleteByUsername")
  String deleteByUsername(@RequestParam(name = "username", required = true) String username) throws Exception;

  @GetMapping(path = "/generateUserRegistrationReport")
  String generateUserRegistrationReport(@RequestParam(name = "username", required = true) String username)
      throws Exception;

//  @GetMapping(path = "/downloadUserRegistrationReportByUsername")
//  ResponseEntity<byte[]> downloadUserRegistrationReportByUsername(
//      @RequestParam(name = "username", required = true) String username) throws Exception;

  @PutMapping(path = "update")
  UserRegistration update(@RequestParam(name = "username", required = true) String username,
      @RequestBody UserRegistration userRegistration) throws Exception;

  @GetMapping(path = "/findAll")
  List<UserRegistration> findAll() throws Exception;

}
