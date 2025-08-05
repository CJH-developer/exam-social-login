package com.example.exam_sns_login.common.service;

import com.example.exam_sns_login.common.dto.ResponseDTO;
import com.example.exam_sns_login.common.dto.UserResponseDTO;
import io.netty.handler.codec.http.HttpHeaderValues;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class LoginService {

    private String client_id;

    private final String KAUTH_TOKEN_URL_HOST;

    private final String KAUTH_USER_URL_HOST;

    @Autowired
    public LoginService(@Value("${REST_API_KEY}") String client_id) {
        this.client_id = client_id;
        KAUTH_TOKEN_URL_HOST = "https://kauth.kakao.com";
        KAUTH_USER_URL_HOST = "https://kapi.kakao.com";
    }

    public String getAccessToekn(String code){
        ResponseDTO responseDTO = WebClient.create(KAUTH_TOKEN_URL_HOST).post()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .path("/oauth/token")
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", client_id)
                        .queryParam("code", code)
                        .build(true)
                )
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Invalid Parameter")))
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Internal Server Error")))
                .bodyToMono(ResponseDTO.class)
                .block();

        return responseDTO.getAccess_token();
    }

    public UserResponseDTO getUserInfo(String accessToken){
        UserResponseDTO userInfoDTO = WebClient.create(KAUTH_USER_URL_HOST)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .path("/v2/user/me")
                        .build(true)
                )
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new Exception("InValid Parameter")))
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new Exception("Internal Error")))
                .bodyToMono(UserResponseDTO.class)
                .block();

        return userInfoDTO;
    }
}
