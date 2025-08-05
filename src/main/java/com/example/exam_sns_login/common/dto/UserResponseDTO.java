package com.example.exam_sns_login.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponseDTO {

    private Long id;
    private Boolean has_signed_up;
    private Date connected_at;
    private Date synched_at;
    private HashMap<String, String> properties;
    private KakaoAccount kakao_account;
    private Partner partner;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class KakaoAccount {
        private Boolean profile_needs_agreement;
        private Boolean profile_nickname_needs_agreement;
        private Boolean profile_image_needs_agreement;
        private Profile profile;
        private Boolean name_needs_agreement;
        private String name;
        private Boolean email_needs_agreement;
        private Boolean is_email_valid;
        private Boolean is_email_verified;
        private String email;
        private Boolean age_range_needs_agreement;
        private String age_range;
        private Boolean birthyear_needs_agreement;
        private String birthyear;
        private Boolean birthday_needs_agreement;
        private String birthday;
        private String birthday_type;
        private Boolean gender_needs_agreement;
        private String gender;
        private Boolean phone_number_needs_agreement;
        private String phone_number;
        private Boolean ci_needs_agreement;
        private String ci;
        private Date ci_authenticated_at;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public class Profile{
            private String nickname;
            private String thumbnail_image_url;
            private String profile_image_url;
            private String is_default_image;
            private String is_default_nickname;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Partner{
        private String uuid;
    }

}
