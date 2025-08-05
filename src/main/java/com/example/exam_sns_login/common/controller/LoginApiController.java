package com.example.exam_sns_login.common.controller;

import com.example.exam_sns_login.common.dto.GoogleRequestDTO;
import com.example.exam_sns_login.common.dto.GoogleResponseDTO;
import com.example.exam_sns_login.common.dto.GoogleUserInfoResponse;
import com.example.exam_sns_login.common.dto.UserResponseDTO;
import com.example.exam_sns_login.common.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class LoginApiController {

    private final LoginService loginService;

    @GetMapping("/loginProcess")
    public ResponseEntity<String> loginProcess(@RequestParam("code") String code){
        String access_token = loginService.getAccessToekn(code);
        UserResponseDTO userInfoDTO = loginService.getUserInfo(access_token);
        return new ResponseEntity<>(HttpStatus.OK);
    }



    @Value("${GOOGLE_CLIENT_ID}")
    private String google_client_id;
    @Value("${GOOGLE_CLIENT_PW}")
    private String google_client_pw;
    @Value("${GOOGLE_REDIRECT_URL}")
    private String google_redirect_url;

    @GetMapping("/auth/google")
    public String loginGoogle(@RequestParam("code") String authCode) {
        RestTemplate restTemplate = new RestTemplate();

        // 토큰 요청
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", google_client_id);
        params.add("client_secret", google_client_pw);
        params.add("code", authCode);
        params.add("redirect_uri", google_redirect_url);
        params.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<GoogleResponseDTO> tokenResponse = restTemplate.postForEntity(
                "https://oauth2.googleapis.com/token",
                request,
                GoogleResponseDTO.class
        );
        String idToken = tokenResponse.getBody().getId_token();
        String accessToken = tokenResponse.getBody().getAccess_token();
        System.out.println("idToken: " + idToken);
        System.out.println("accessToken: " + accessToken);
        // id_token을 통해 사용자 정보 디코딩하거나 사용자 정보 API 호출
        HttpHeaders headers2 = new HttpHeaders();
        headers2.setBearerAuth(accessToken);

        HttpEntity<String> entity2 = new HttpEntity<>(headers2);

        ResponseEntity<GoogleUserInfoResponse> userInfoResponse = restTemplate.exchange(
                "https://openidconnect.googleapis.com/v1/userinfo",
                HttpMethod.GET,
                entity2,
                GoogleUserInfoResponse.class
        );

        return userInfoResponse.getBody().getEmail();
    }

}
