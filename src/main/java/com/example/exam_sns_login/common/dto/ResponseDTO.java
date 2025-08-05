package com.example.exam_sns_login.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDTO {

    private String token_type;
    private String access_token;
    private String refresh_token;
    private String expires_in;
    private String scope;
    private String id_token;
    private String refresh_token_expires_in;
}
