package com.inn.service;

import java.util.List;

import com.inn.model.Attachment;

public interface IAttachmentService {

  Attachment create(Attachment userRegisterationAttachment) throws Exception;

  List<Attachment> findByUsername(String username) throws Exception;

  Attachment findByIdentifier(String identifier) throws Exception;

  String deleteByIdentifier(String identifier) throws Exception;

  List<Attachment> findByUserIdentifier(String userIdentifier) throws Exception;

}
