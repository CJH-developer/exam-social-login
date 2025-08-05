package com.example.exam_sns_login.common.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource("classpath:config/settings.xml")
})
@ComponentScan({"com.example"})
public class Settings {
}
