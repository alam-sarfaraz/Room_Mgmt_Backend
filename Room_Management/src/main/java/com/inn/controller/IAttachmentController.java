package com.inn.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.inn.model.Attachment;

@RequestMapping(path = "/attachment")
public interface IAttachmentController {

//  @ApiOperation(value = "Create Attachment", response = Attachment.class)
//  @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved the resource"),
//      @ApiResponse(code = 404, message = "The resource does not exist") })
  @PostMapping(path = "/create")
  Attachment create(@RequestBody Attachment userRegisterationAttachment) throws Exception;

//  @ApiOperation(value = "Get list of Attachment By Username", response = Attachment.class)
//  @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved the resource"),
//      @ApiResponse(code = 404, message = "The resource does not exist") })
  @GetMapping(path = "/findByUsername")
  List<Attachment> findByUsername(@RequestParam(name = "username", required = true) String username) throws Exception;

//  @ApiOperation(value = "Get list of Attachment By Identifier", response = Attachment.class)
//  @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved the resource"),
//      @ApiResponse(code = 404, message = "The resource does not exist") })
  @GetMapping(path = "/findByIdentifier")
  Attachment findByIdentifier(@RequestParam(name = "identifier", required = true) String identifier) throws Exception;

//  @ApiOperation(value = "Get list of Attachment By User Identifier", response = Attachment.class)
//  @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved the resource"),
//      @ApiResponse(code = 404, message = "The resource does not exist") })
  @GetMapping(path = "/findByUserIdentifier")
  List<Attachment> findByUserIdentifier(@RequestParam(name = "userIdentifier", required = true) String userIdentifier)
      throws Exception;

//  @ApiOperation(value = "Delete Attachment By Identifier", response = Attachment.class)
//  @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved the resource"),
//      @ApiResponse(code = 404, message = "The resource does not exist") })
  @DeleteMapping(path = "/deleteByIdentifier")
  String deleteByIdentifier(@RequestParam(name = "identifier", required = true) String identifier) throws Exception;

}
