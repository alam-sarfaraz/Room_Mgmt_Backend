package com.inn.service;

import java.util.List;

import com.inn.model.DocumentAttachment;

public interface IDocumentAttachmentService {

  DocumentAttachment create(DocumentAttachment documentAttachment) throws Exception;

  List<DocumentAttachment> getDocumentAttachmentListByStatus() throws Exception;

}
