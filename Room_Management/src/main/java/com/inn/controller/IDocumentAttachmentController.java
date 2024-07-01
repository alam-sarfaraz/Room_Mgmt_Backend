package com.inn.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inn.model.DocumentAttachment;

@RequestMapping(path = "/documentAttachment")
public interface IDocumentAttachmentController {

  @PostMapping(path = "/create")
  DocumentAttachment create(@RequestBody DocumentAttachment documentAttachment) throws Exception;

  @GetMapping(path = "/getDocumentAttachmentListByStatus")
  List<DocumentAttachment> getDocumentAttachmentListByStatus() throws Exception;

}
