package com.karthi.securityiitest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

    @GetMapping("/dashboard")
    public ResponseEntity<String> teachersDatsBoard(){
        return new ResponseEntity<>("This is teachers Dashboard " , HttpStatus.OK);
    }


}
