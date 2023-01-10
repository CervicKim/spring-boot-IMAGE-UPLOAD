package com.dw.member.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

//WebConfig : 웹 설정
//Config : 설정
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${aws.s3.access-id}")
    private String accessKey;
    @Value("${aws.s3.access-pw}")
    private String secretKey;

    @Autowired //서버 시작Run을 누르면 위의 키가 실행되며, 진짜 아마존aws에서 발급 받은 게 맞는지 검사를 시작한다!
    Interceptor interceptor; // 우리가 만든 인터셉터 불러옴

    // addInterceptors : 인터셉터를 추가하는 함수 재정의
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] patterns = { "/login", "/join", "/error", "/resources/static/*", "/api/v1/login",
                "/api/v1/login-test","/api/v1/image","/upload" };
        registry.addInterceptor(interceptor).excludePathPatterns(patterns);
    }

// AWS에서 발급한 key가 맞는 지 검사
    @Bean
	public BasicAWSCredentials AwsCredentials() {
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
		return awsCreds;
	}

// AWS 위치 및 인증 체크
	@Bean
	public AmazonS3 AwsS3Client() {

		AmazonS3 s3Builder = AmazonS3ClientBuilder.standard().withRegion(Regions.AP_NORTHEAST_2)
				.withCredentials(new AWSStaticCredentialsProvider(this.AwsCredentials())).build();
		return s3Builder;
	}

}
