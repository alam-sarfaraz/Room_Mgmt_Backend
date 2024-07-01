package com.inn.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
@CrossOrigin("*")
@RequestMapping("/sigin")
public interface ISigInController {

  @GetMapping(path = "/")
  Boolean signin(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) throws Exception;

}
