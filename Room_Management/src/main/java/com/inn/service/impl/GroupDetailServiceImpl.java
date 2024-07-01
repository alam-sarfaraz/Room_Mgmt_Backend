package com.inn.service.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inn.constant.RoomConstant;
import com.inn.customException.CustomException;
import com.inn.model.GroupDetail;
import com.inn.repository.IGroupDetailRepository;
import com.inn.service.IGroupDetailService;
import com.inn.utils.RoomUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GroupDetailServiceImpl implements IGroupDetailService {

  @Autowired
  IGroupDetailRepository groupDetailRepository;

  @Override
  public GroupDetail create(GroupDetail groupDetail) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "create {}", kv("GroupDetail", groupDetail));
      groupDetail.setIdentifier(RoomUtil.generateIdentifier("GRP"));
      groupDetail.setCreatedDate(RoomUtil.dateFormatter());
      groupDetail.setCreatedTime(RoomUtil.timeFormatter());
      GroupDetail groupDetaildb = groupDetailRepository.findByGroupName(groupDetail.getGroupName());
      if(groupDetaildb!=null){
        throw new CustomException(groupDetail.getGroupName() +" is Already Exist.Please choose another Group Name..");
      }
      return groupDetailRepository.save(groupDetail);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public GroupDetail findByIdentifier(String identifier) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByIdentifier {}", kv("Identifier", identifier));
      GroupDetail groupDetail = groupDetailRepository.findByIdentifier(identifier);
      if (groupDetail != null) {
        return groupDetail;
      } else {
        throw new CustomException("Invalid Identifier ........");
      }
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public GroupDetail findByGroupName(String groupName) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByGroupName {}", kv("GroupName", groupName));
      GroupDetail groupDetail = groupDetailRepository.findByGroupName(groupName);
      if (groupDetail != null) {
        return groupDetail;
      } else {
        throw new CustomException(
            "Invalid group Name [" + groupName + "].Please create a new Group name or choose another group name..");
      }
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<GroupDetail> findAll() {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findAll");
      return groupDetailRepository.findAll();
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

}
