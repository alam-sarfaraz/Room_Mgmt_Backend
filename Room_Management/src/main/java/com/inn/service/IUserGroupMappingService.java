package com.inn.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.inn.model.UserGroupMapping;

public interface IUserGroupMappingService {

  List<UserGroupMapping> findByGroupName(String groupName) throws Exception;

  List<UserGroupMapping> findByEmail(String email) throws Exception;

  String sendEmailByGroupWise(String groupName) throws Exception;

  ResponseEntity<byte[]> exportDataByGroupName(String groupName) throws Exception;

  List<String> findByUsernameByGroupName(String groupName) throws Exception;

}
