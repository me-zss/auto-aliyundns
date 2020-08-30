package com.shun.dns.config;

import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DnsProfile {

    @Value("${aliyun.AccessKeyID}")
    private String accessKeyID;
    @Value("${aliyun.AccessKeySecret}")
    private String accessKeySecret;

    @Bean
    public DefaultProfile myProfile() {
        return DefaultProfile.getProfile("cn-hangzhou", accessKeyID, accessKeySecret);
    }
}
