package com.inn.service.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.inn.constant.RoomConstant;
import com.inn.model.DocumentAttachment;
import com.inn.repository.IDocumentAttachmentRepository;
import com.inn.service.IDocumentAttachmentService;
import com.inn.utils.RoomUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DocumentAttachmentServiceImpl implements IDocumentAttachmentService {

  @Autowired
  IDocumentAttachmentRepository documentAttachmentRepository;

  @Override
  public DocumentAttachment create(DocumentAttachment documentAttachment) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "create {}", kv("documentAttachment", documentAttachment));
      documentAttachment.setCreatedDate(RoomUtil.dateFormatter());
      documentAttachment.setCreatedTime(RoomUtil.timeFormatter());
      return documentAttachmentRepository.save(documentAttachment);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public List<DocumentAttachment> getDocumentAttachmentListByStatus() throws Exception {
    try {
      int limit = 1;
      Pageable pageable = PageRequest.of(0, limit);
      log.info(RoomConstant.INSIDE_THE_METHOD + "getDocumentAttachmentListByStatus ");
      String status = "Pending";
      Boolean isHeaderMatched = true;
      Boolean isProcessed = false;
      return documentAttachmentRepository.getDocumentAttachmentListByStatus(status, isHeaderMatched, isProcessed,
          pageable);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }
}
