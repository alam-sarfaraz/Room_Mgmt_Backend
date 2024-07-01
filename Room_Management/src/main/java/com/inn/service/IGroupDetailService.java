package com.inn.service;

import java.util.List;

import com.inn.model.GroupDetail;

public interface IGroupDetailService {

  GroupDetail create(GroupDetail groupDetail) throws Exception;

  GroupDetail findByIdentifier(String identifier) throws Exception;

  GroupDetail findByGroupName(String groupName) throws Exception;

  List<GroupDetail> findAll();

}
