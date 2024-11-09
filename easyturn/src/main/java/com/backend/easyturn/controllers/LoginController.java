package com.backend.easyturn.controllers;

import com.backend.easyturn.entities.DTOs.UserLoginDTO;
import com.backend.easyturn.entities.DTOs.UserLoguedDTO;
import com.backend.easyturn.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @CrossOrigin(origins = "*")
    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<UserLoguedDTO> login(@RequestBody UserLoginDTO userLoginDTO) {
        return new ResponseEntity<>(this.loginService.login(userLoginDTO), HttpStatus.OK);
    }

}
