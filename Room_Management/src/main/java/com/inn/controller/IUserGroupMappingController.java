package com.inn.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.inn.model.UserGroupMapping;

@CrossOrigin
@RequestMapping(path = "/userGroupMapping")
public interface IUserGroupMappingController {

  @GetMapping(path = "/findByGroupName")
  List<UserGroupMapping> findByGroupName(@RequestParam(name = "groupName", required = true) String groupName)
      throws Exception;

  @GetMapping(path = "/findByEmail")
  List<UserGroupMapping> findByEmail(@RequestParam(name = "email", required = true) String email) throws Exception;

  @GetMapping(path = "/sendEmailByGroupWise")
  String sendEmailByGroupWise(@RequestParam(name = "groupName", required = true) String groupName) throws Exception;

  @GetMapping(path = "/exportDataByGroupName")
  ResponseEntity<byte[]> exportDataByGroupName(@RequestParam(name = "groupName", required = true) String groupName)
      throws Exception;

  @GetMapping(path = "/findByUsernameByGroupName")
  List<String> findByUsernameByGroupName(@RequestParam(name = "groupName", required = true) String groupName)
      throws Exception;
}
