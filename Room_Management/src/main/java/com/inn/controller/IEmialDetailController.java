package com.inn.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inn.model.EmailDetails;

@RequestMapping("/sendEmail")
public interface IEmialDetailController {

  @PostMapping("/sendMailWithoutAttachment")
  public String sendMail(@RequestBody EmailDetails details);

  @PostMapping("/sendMailWithAttachment")
  public String sendMailWithAttachment(@RequestBody EmailDetails details) throws Exception;
}
