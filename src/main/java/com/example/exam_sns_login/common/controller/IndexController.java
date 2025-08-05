package com.example.exam_sns_login.common.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @Value("${REST_API_KEY}")
    private String client_id;

    @Value("${REDIRECT_URL}")
    private String redirect_url;

    @Value("${GOOGLE_CLIENT_ID}")
    private String google_client_id;
    @Value("${GOOGLE_CLIENT_PW}")
    private String google_client_pw;
    @Value("${GOOGLE_REDIRECT_URL}")
    private String google_redirect_url;

    // 최초 화면 진입
    @GetMapping("/")
    public String index(Model model) {
        String location = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+client_id+"&redirect_uri="+redirect_url;
        model.addAttribute("location",location);

        String url = "https://accounts.google.com/o/oauth2/v2/auth?client_id=" + google_client_id
                + "&redirect_uri=" + google_redirect_url + "&response_type=code&scope=email%20profile%20openid&access_type=offline";

        model.addAttribute("url",url);
        return "index";
    }


}
