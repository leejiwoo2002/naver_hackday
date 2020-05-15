package com.hackday.sns_timeline.common.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	@Bean
	public Docket swaggerApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(swaggerInfo()).select()
			.apis(RequestHandlerSelectors.any())
			.paths(PathSelectors.any())
			.build();
			// .useDefaultResponseMessages(false); // 기본으로 세팅되는 200,401,403,404 메시지를 표시 하지 않음
	}

	private ApiInfo swaggerInfo() {
		return new ApiInfoBuilder().title("Spring API Documentation")
			.description("타임라인 웹 어플리케이션 연동 문서입니다").build();
	}
}
