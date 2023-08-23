package com.example.moneytransfer.controller;


import com.example.moneytransfer.dto.GroupAddDTO;
import com.example.moneytransfer.dto.GroupCreateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/connect")
    public ResponseEntity<Void> connect() {


       return new ResponseEntity<>(HttpStatus.OK);
    }
}
