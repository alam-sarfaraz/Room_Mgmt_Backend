package com.inn.service;

import com.inn.model.EmailDetails;

public interface IEmailDetailService {

  String sendMailWithOutAttachment(EmailDetails details);

  String sendMailWithAttachment(EmailDetails details) throws Exception;

}
