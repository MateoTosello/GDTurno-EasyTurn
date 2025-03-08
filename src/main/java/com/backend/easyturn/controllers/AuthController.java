package com.backend.easyturn.controllers;

import com.backend.easyturn.entities.DTOs.UserLoguedDTO;
import com.backend.easyturn.entities.Patient;
import com.backend.easyturn.requests.LoginRequest;
import com.backend.easyturn.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private final AuthService authService;

    @CrossOrigin(origins = "*")
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<UserLoguedDTO> login(@RequestBody LoginRequest userLogin) {
        UserLoguedDTO user = this.authService.login(userLogin);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("sign-in")
    @ResponseBody
    public ResponseEntity<UserLoguedDTO> signIn(@RequestBody Patient patient){
        UserLoguedDTO user = this.authService.signIn(patient);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }





}
