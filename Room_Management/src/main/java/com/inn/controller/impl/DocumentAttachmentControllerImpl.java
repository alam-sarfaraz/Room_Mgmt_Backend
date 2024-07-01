package com.inn.controller.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.inn.constant.RoomConstant;
import com.inn.controller.IDocumentAttachmentController;
import com.inn.model.DocumentAttachment;
import com.inn.service.IDocumentAttachmentService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class DocumentAttachmentControllerImpl implements IDocumentAttachmentController {

  @Autowired
  IDocumentAttachmentService documentAttachmentService;

  @Override
  public DocumentAttachment create(DocumentAttachment documentAttachment) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "create {}", kv("documentAttachment", documentAttachment));
      return documentAttachmentService.create(documentAttachment);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<DocumentAttachment> getDocumentAttachmentListByStatus() throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "getDocumentAttachmentListByStatus ");
      return documentAttachmentService.getDocumentAttachmentListByStatus();
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

}
