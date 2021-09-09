package com.zds.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

/**
 * description
 *
 * @author zhongdongsheng
 * @datetime 2021/9/7 23:22
 */
@Configuration
@EnableOpenApi
public class SwaggerConfiguration {
    private final SwaggerProperties swaggerProperties;

    public SwaggerConfiguration(SwaggerProperties swaggerProperties) {
        this.swaggerProperties = swaggerProperties;
    }

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                // enable：是否开启swagger
                .enable(swaggerProperties.isEnabled())
                // apiInfo：配置apiInfo
                .apiInfo(apiInfo())
                // host：接口的调试地址
                .host(swaggerProperties.getHost()+swaggerProperties.getPort())
                // select：过滤接口
                .select().apis(RequestHandlerSelectors.basePackage("com.zds.controller")).paths(PathSelectors.any()).build();
    }

    /**
     * 配置swagger api相关信息，对应页面最上面swagger信息展示区域
     * 推荐使用ApiInfoBuilder().build()方法创建ApiInfo实例，可以避免使用构造方法指定所有的属性
     *
     * @return apiInfo
     */
    private ApiInfo apiInfo() {
        Contact contact = new Contact("zhongdongsheng", "https://github.com/zhongds01", "zhongds01@163.com");
        Contact defaultContact = new Contact("", "", "");
        // 默认的ApiInfo实例
        ApiInfo defaultApiInfo = new ApiInfo("Api Documentation", "Api Documentation", "1.0", "urn:tos", defaultContact, "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList());
        // 通过ApiInfoBuilder创建ApiInfo实例
        ApiInfo apiInfoBuilder = new ApiInfoBuilder().title("swagger-core").description("swagger-core project description").contact(contact).build();
        // 通过ApiInfo的构造方法构造ApiInfo实例。
        ApiInfo apiInfo = new ApiInfo("swagger-core", "swagger-core project description", "1.0", "urn:tos", contact, "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList());
        return apiInfoBuilder;
    }
}
