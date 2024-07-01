package com.inn.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.inn.model.GroupDetail;

@RequestMapping(path = "/groupDetail")
@CrossOrigin("*")
public interface IGroupDetailController {

  @PostMapping(path = "/create")
  GroupDetail create(@RequestBody GroupDetail groupDetail) throws Exception;

  @GetMapping(path = "/findByIdentifier")
  GroupDetail findByIdentifier(@RequestParam(name = "identifier", required = true) String identifier) throws Exception;

  @GetMapping(path = "/findByGroupName")
  GroupDetail findByGroupName(@RequestParam(name = "groupName", required = true) String groupName) throws Exception;

  @GetMapping(path = "/findAll")
  List<GroupDetail> findAll();
}
