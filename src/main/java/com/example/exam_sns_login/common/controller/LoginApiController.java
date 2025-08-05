package com.example.exam_sns_login.common.controller;

import com.example.exam_sns_login.common.dto.UserResponseDTO;
import com.example.exam_sns_login.common.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginApiController {

    private final LoginService loginService;

    @GetMapping("/loginProcess")
    public ResponseEntity<String> loginProcess(@RequestParam("code") String code){
        String access_token = loginService.getAccessToekn(code);
        UserResponseDTO userInfoDTO = loginService.getUserInfo(access_token);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
